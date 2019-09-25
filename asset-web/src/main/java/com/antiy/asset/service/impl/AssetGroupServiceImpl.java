package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.convert.SelectConvert;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.request.RemoveAssociateAssetRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 资产组表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetGroupServiceImpl extends BaseServiceImpl<AssetGroup> implements IAssetGroupService {

    @Resource
    private AssetGroupDao                                 assetGroupDao;
    @Resource
    private AssetDao                                      assetDao;
    @Resource
    private AesEncoder                                    aesEncoder;
    @Resource
    private AssetGroupRelationDao                         assetGroupRelationDao;
    @Resource
    private SelectConvert                                 selectConvert;
    @Resource
    private RedisUtil                                     redisUtil;
    @Resource
    private BaseConverter<AssetGroup, AssetGroupResponse> assetGroupToResponseConverter;
    @Resource
    private BaseConverter<AssetGroupRequest, AssetGroup>  assetGroupToAssetGroupConverter;

    @Override
    @Transactional
    public String saveAssetGroup(AssetGroupRequest request) throws Exception {
        // 判重
        String assetName = request.getName();
        Boolean removeDuplicateResult = assetGroupDao.removeDuplicate(assetName);
        if (removeDuplicateResult) {
            throw new BusinessException("资产组名称重复");
        }
        AssetGroup assetGroup = assetGroupToAssetGroupConverter.convert(request, AssetGroup.class);
        assetGroup.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetGroup.setGmtCreate(System.currentTimeMillis());
        int result = assetGroupDao.insert(assetGroup);
        if (ArrayUtils.isNotEmpty(request.getAssetIds())) {
            for (String assetId : request.getAssetIds()) {
                AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetGroupRelation.setCreateUserName(LoginUserUtil.getLoginUser().getUsername());
                assetGroupRelation.setAssetGroupId(assetGroup.getStringId());
                assetGroupRelation.setAssetId(assetId);
                assetGroupRelation.setAssetGroupId(assetGroup.getStringId());
                assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                assetGroupRelationDao.insert(assetGroupRelation);
            }
        }

        if (!Objects.equals(0, result)) { // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_INSERT.getName(),
                AssetEventEnum.ASSET_GROUP_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_GROUP_INSERT.getName(), assetGroup.getId(),
                assetGroup.getName(), assetGroup, BusinessModuleEnum.ASSET_GROUP_MANAGEMENT, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_INSERT.getName() + " {}", assetGroup.toString());
        }

        Map<String, Object> map = new HashMap<>();
        StringBuilder assetNameBuilder = new StringBuilder();
        if (ArrayUtils.isNotEmpty(request.getAssetIds())) {
            for (String assetId : request.getAssetIds()) {
                List<String> assetGroupNameList = assetGroupRelationDao.findAssetGroupNameByAssetId(assetId);
                String assetGroupName = assetGroupNameList.toString();
                updateAssetGroupName(map, assetNameBuilder, assetId, assetGroupNameList, assetGroupName);
            }
        }
        return aesEncoder.encode(assetGroup.getStringId(), LoginUserUtil.getLoginUser().getUsername());
    }

    @Override
    public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
        String groupName = assetGroupDao.getById(request.getId()).getName();
        if (!request.getName().equals(groupName)) {
            // 判重
            String assetName = request.getName();
            Boolean removeDuplicateResult = assetGroupDao.removeDuplicate(assetName);
            if (removeDuplicateResult) {
                throw new BusinessException("资产组名称重复");
            }
        }
        AssetGroup assetGroup = (AssetGroup) BeanConvert.convert(request, AssetGroup.class);
        assetGroup.setId(DataTypeUtils.stringToInteger(request.getId()));
        assetGroup.setGmtModified(System.currentTimeMillis());
        assetGroup.setModifyUser(LoginUserUtil.getLoginUser().getId());
        String[] assetIdArr = request.getAssetIds();

        List<AssetGroupRelation> assetGroupRelationList = new ArrayList<>();
        int assetRelationResult;
        int updateGroupResult;
        int deleteGroupResult = 0;

        // 删除关联资产
        // assetRelationResult = assetGroupRelationDao
        // .deleteByAssetGroupId(DataTypeUtils.stringToInteger(request.getId()));

        Map<String, Object> map = new HashMap<>();
        StringBuilder assetNameBuilder = new StringBuilder();

        // 查询已有的关联信息
        List<AssetGroupRelation> existedRelationList = assetGroupRelationDao
            .listRelationByGroupId(DataTypeUtils.stringToInteger(request.getId()));
        // 创建资产组关联关系,插入记录
        if (ArrayUtils.isNotEmpty(assetIdArr)) {
            for (String assetId : assetIdArr) {
                boolean addRelation = true;
                for (AssetGroupRelation groupRelation : existedRelationList) {
                    // 请求的资产id与所有已存在的有一个相等,就不用添加
                    if (groupRelation.getAssetId().equals(assetId)) {
                        addRelation = false;
                        break;
                    }
                }
                if (addRelation) {
                    AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                    assetGroupRelation.setAssetGroupId(assetGroup.getStringId());
                    assetGroupRelation.setAssetId(assetId);
                    assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                    assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetGroupRelationList.add(assetGroupRelation);
                }
            }
            // 移除库中existedRelationList已经有的与本次请求相等的,剩下的existedRelationList是本次需要操作删除的
            for (String requestAssetId : request.getAssetIds()) {
                existedRelationList.removeIf(e -> e.getAssetId().equals(requestAssetId));
            }
            List<Integer> deleteIdList = existedRelationList.stream().map(AssetGroupRelation::getId)
                .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(deleteIdList)) {
                assetRelationResult = assetGroupRelationDao.deleteBatch(deleteIdList);
                if (!Objects.equals(0, assetRelationResult)) {
                    // 写入业务日志(删除关联资产)
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName(),
                        assetGroup.getId(), assetGroup.getName(), assetGroup, BusinessModuleEnum.ASSET_GROUP_MANAGEMENT,
                        BusinessPhaseEnum.NONE));
                    LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName() + " {}", assetGroup);
                }
            }

            if (CollectionUtils.isNotEmpty(assetGroupRelationList)) {
                assetGroupRelationDao.insertBatch(assetGroupRelationList);
            }

            updateGroupResult = assetGroupDao.update(assetGroup);

            // -----------------------------更新资产主表的资产组字段内容start-----------------------------
            for (String assetId : assetIdArr) {
                List<String> assetGroupNameList = assetGroupRelationDao.findAssetGroupNameByAssetId(assetId);
                // 资产关联的资产组不能超过10个
                ParamterExceptionUtils.isTrue(assetGroupNameList.size() <= Constants.MAX_ASSET_RELATION_GROUP_COUNT,
                    "资产关联的资产组不能超过10个");
                String assetGroupName = assetGroupNameList.toString();
                updateAssetGroupName(map, assetNameBuilder, assetId, assetGroupNameList, assetGroupName);
            }
            // -----------------------------更新资产主表的资产组字段内容end-----------------------------
        } else {
            updateGroupResult = assetGroupDao.update(assetGroup);
            List<String> assetIdList = assetGroupRelationDao.findAssetIdByAssetGroupId(request.getId());
            assetIdList.forEach(assetId -> {
                List<String> assetGroupNameList = new ArrayList<>();
                try {
                    assetGroupNameList = assetGroupRelationDao.findAssetGroupNameByAssetId(assetId);
                } catch (Exception e) {
                    LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName() + " {}",
                        assetGroup.toString());
                }
                // 资产关联的资产组不能超过10个
                ParamterExceptionUtils.isTrue(assetGroupNameList.size() <= Constants.MAX_ASSET_RELATION_GROUP_COUNT,
                    "资产关联的资产组不能超过10个");
                String assetGroupName = assetGroupNameList.toString();
                updateAssetGroupName(map, assetNameBuilder, assetId, assetGroupNameList, assetGroupName);
            });
        }

        // 移除资产后，更新资产主表中对应资产组字段
        assetNameBuilder.setLength(0);
        if (ArrayUtils.isNotEmpty(request.getDeleteAssetIds())) {

            RemoveAssociateAssetRequest removeAssociateAssetRequest = new RemoveAssociateAssetRequest();
            removeAssociateAssetRequest.setGroupId(request.getId());
            removeAssociateAssetRequest.setRemoveAsset(request.getDeleteAssetIds());
            deleteGroupResult = assetGroupRelationDao.batchDeleteById(removeAssociateAssetRequest);
            for (String assetId : request.getDeleteAssetIds()) {
                List<String> assetGroupNameList = assetGroupRelationDao.findAssetGroupNameByAssetId(assetId);
                String assetGroupName = assetGroupNameList.toString();
                updateAssetGroupName(map, assetNameBuilder, assetId, assetGroupNameList, assetGroupName);
            }
        }

        // if (!Objects.equals(0, assetRelationResult)) {
        // // 写入业务日志
        // LogUtils.recordOperLog(
        // new BusinessData(AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(), assetGroup.getId(),
        // assetGroup.getName(), assetGroup, BusinessModuleEnum.ASSET_GROUP_MANAGEMENT, BusinessPhaseEnum.NONE));
        // LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName() + " {}", assetGroup);
        // }
        // if (!Objects.equals(0, deleteGroupResult)) {
        // // 写入业务日志
        // LogUtils.recordOperLog(
        // new BusinessData(AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName(), assetGroup.getId(),
        // assetGroup.getName(), assetGroup, BusinessModuleEnum.ASSET_GROUP_MANAGEMENT, BusinessPhaseEnum.NONE));
        // LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName() + " {}", assetGroup);
        // }

        if (!Objects.equals(0, updateGroupResult)) {
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_GROUP_UPDATE.getName(), assetGroup.getId(),
                assetGroup.getName(), assetGroup, BusinessModuleEnum.ASSET_GROUP_MANAGEMENT, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_UPDATE.getName() + " {}", assetGroup);
        }

        return updateGroupResult;
    }

    @Override
    public List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception {
        List<AssetGroup> assetGroupList = assetGroupDao.findQuery(query);
        List<AssetGroupResponse> assetResponseList = assetGroupToResponseConverter.convert(assetGroupList,
            AssetGroupResponse.class);

        for (AssetGroupResponse assetGroupResponse : assetResponseList) {
            List<String> assetList = assetGroupRelationDao
                .findAssetNameByAssetGroupId(Integer.valueOf(assetGroupResponse.getStringId()));
            StringBuilder assetDetail = new StringBuilder();
            for (String assetName : assetList) {
                if (assetList.size() == 1 || assetList.size() == assetList.size() - 1) {
                    assetDetail.append(assetName);
                } else {
                    assetDetail.append(assetName).append(",");
                }
            }
            assetGroupResponse.setAssetDetail(assetDetail.toString());
            assetGroupResponse.setAssetList(assetList);
            assetGroupResponse.setCreateUser(assetGroupResponse.getCreateUser());
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                assetGroupResponse.getCreateUser());
            SysUser sysUser = redisUtil.getObject(key, SysUser.class);
            assetGroupResponse.setCreateUserName(sysUser == null ? "" : sysUser.getName());
        }
        return assetResponseList;
    }

    @Override
    public PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetGroup(query));
    }

    @Override
    public List<SelectResponse> queryGroupInfo() throws Exception {
        return selectConvert.convert(assetGroupDao.findPulldownGroup(), SelectResponse.class);
    }

    /**
     * 通联查询的资产组下拉项接口
     * @return
     * @throws Exception
     */
    @Override
    public List<SelectResponse> queryUnconnectedGroupInfo(Integer isNet, String primaryKey) throws Exception {
        AssetQuery query = new AssetQuery();
        List<Integer> categoryCondition = new ArrayList<>();
        // Map<String, String> categoryMap = assetCategoryModelService.getSecondCategoryMap();
        // List<AssetCategoryModel> all = assetCategoryModelService.getAll();
        // for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
        // if ((isNet == null) || isNet == 1) {
        // if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
        // categoryCondition.addAll(
        // assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
        // }
        // }
        // if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
        // categoryCondition.addAll(
        // assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
        // }
        // }
        // query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryCondition));
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        query.setPrimaryKey(primaryKey);
        return selectConvert.convert(assetGroupDao.findPulldownUnconnectedGroup(query), SelectResponse.class);
    }

    @Override
    public Integer removeAssociateAsset(RemoveAssociateAssetRequest request) {
        return assetGroupRelationDao.batchDeleteById(request);
    }

    @Override
    public AssetGroupResponse findGroupById(String id) throws Exception {
        AssetGroupResponse assetGroupResponse = assetGroupToResponseConverter.convert(assetGroupDao.getById(id),
            AssetGroupResponse.class);
        return assetGroupResponse;
    }

    @Override
    public List<SelectResponse> queryCreateUser() throws Exception {
        List<SelectResponse> selectResponseList = new ArrayList<>();
        for (AssetGroup assetGroup : assetGroupDao.findCreateUser()) {
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                assetGroup.getCreateUser());
            SysUser sysUser = redisUtil.getObject(key, SysUser.class);
            SelectResponse selectResponse = new SelectResponse();
            selectResponse.setId(DataTypeUtils.integerToString(assetGroup.getCreateUser()));
            if (sysUser != null) {
                selectResponse.setValue(sysUser.getName());
                selectResponseList.add(selectResponse);
            }

        }
        return selectResponseList;
    }

    /**
     * 更新资产主表资产组信息
     * @param map
     * @param assetNameBuilder
     * @param assetId
     * @param assetGroupNameList
     * @param assetGroupName
     */
    private void updateAssetGroupName(Map<String, Object> map, StringBuilder assetNameBuilder, String assetId,
                                      List<String> assetGroupNameList, String assetGroupName) {
        if (assetGroupNameList.size() > 0) {
            assetNameBuilder.append(assetGroupNameList.toString(), 1, assetGroupName.length() - 1);
            map.put("assetGroupName", assetNameBuilder.toString());
            map.put("assetId", assetId);
            assetDao.updateAssetGroupNameWithAssetId(map);
            assetNameBuilder.delete(0, assetNameBuilder.length());
            // 写入业务日志
            LogHandle.log(assetGroupNameList.toString(), AssetEventEnum.ASSET_MODIFY.getName(),
                AssetEventEnum.ASSET_MODIFY.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", assetGroupNameList.toString());
        } else {
            map.put("assetGroupName", null);
            map.put("assetId", assetId);
            assetDao.updateAssetGroupNameWithAssetId(map);
        }
    }

    @Override
    public Integer deleteById(Serializable id) throws Exception {
        // 存在关联资产不能删除资产组
        Integer amount = assetGroupRelationDao.existRelateAssetInGroup(id);

        if (amount > 0) {
            throw new BusinessException("您已关联对应资产，无法进行注销");
        } else {
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_GROUP_DELETE.getName(),
                DataTypeUtils.stringToInteger(id.toString()), assetGroupDao.getNameById(id), id,
                BusinessModuleEnum.ASSET_GROUP_MANAGEMENT, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_DELETE.getName() + " {}", id);
            return assetGroupDao.deleteById(Integer.valueOf((String) id));
        }
    }
}
