package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetSecondCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.antiy.biz.file.FileHelper.logger;

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
    @Resource
    private IAssetCategoryModelService                    assetCategoryModelService;

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
            LogUtils.recordOperLog(new BusinessData (AssetEventEnum.ASSET_GROUP_INSERT.getName(), assetGroup.getId (), assetGroup.getName (),
                    assetGroup, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_INSERT.getName() + " {}", assetGroup.toString());
        }

        Map<String, Object> map = new HashMap<>();
        StringBuilder assetNameBuilder = new StringBuilder();
        if (ArrayUtils.isNotEmpty(request.getAssetIds())) {
            for (String assetId : request.getAssetIds()) {
                List<String> assetGroupNameList = assetGroupRelationDao
                    .findAssetGroupNameByAssetId(DataTypeUtils.stringToInteger(assetId));
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
        Integer[] assetIdArr = DataTypeUtils.stringArrayToIntegerArray(request.getAssetIds());

        List<AssetGroupRelation> assetGroupRelationList = new ArrayList<>();

        Integer delResult = assetGroupRelationDao.deleteByAssetGroupId(assetGroup.getId());

        if (!Objects.equals(0, delResult)) {
            // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName(),
                AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData (AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName(), assetGroup.getId (), assetGroup.getName (),
                    assetGroup, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName() + " {}", assetGroup.toString());
        }
        int result = 0;
        for (Integer assetId : assetIdArr) {
            AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
            assetGroupRelation.setAssetGroupId(assetGroup.getStringId());
            assetGroupRelation.setAssetId(assetId.toString());
            assetGroupRelation.setGmtCreate(System.currentTimeMillis());
            assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetGroupRelationList.add(assetGroupRelation);
        }

        if (CollectionUtils.isNotEmpty(assetGroupRelationList)) {
            result = assetGroupRelationDao.insertBatch(assetGroupRelationList);
        }

        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(),
                AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.recordOperLog(new BusinessData (AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(), assetGroup.getId (), assetGroup.getName (),
                    assetGroup, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName() + " {}", assetGroup.toString());
        }
        assetGroupDao.update(assetGroup);

        // -----------------------------更新资产主表的资产组字段内容start-----------------------------
        Map<String, Object> map = new HashMap<>();
        StringBuilder assetNameBuilder = new StringBuilder();
        for (Integer assetId : assetIdArr) {
            List<String> assetGroupNameList = assetGroupRelationDao.findAssetGroupNameByAssetId(assetId);
            // 资产关联的资产组不能超过10个
            ParamterExceptionUtils.isTrue(assetGroupNameList.size() <= Constants.MAX_ASSET_RELATION_GROUP_COUNT,
                "资产关联的资产组不能超过10个");
            String assetGroupName = assetGroupNameList.toString();
            updateAssetGroupName(map, assetNameBuilder, assetId.toString(), assetGroupNameList, assetGroupName);
        }

        // -----------------------------更新资产主表的资产组字段内容end-----------------------------

        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(),
                AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.recordOperLog(new BusinessData (AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(), assetGroup.getId (), assetGroup.getName (),
                    assetGroup, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName() + " {}", assetGroup.toString());
        }

        assetGroupDao.update(assetGroup);
        return result;
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
    public List<SelectResponse> queryUnconnectedGroupInfo(Boolean searchNetworkDevice) throws Exception {
        AssetQuery query = new AssetQuery();
        List<Integer> categoryCondition = new ArrayList<>();
        Map<String, String> categoryMap = assetCategoryModelService.getSecondCategoryMap();
        List<AssetCategoryModel> all = assetCategoryModelService.getAll();
        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            if (searchNetworkDevice == null || !searchNetworkDevice) {
                if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
                    categoryCondition.addAll(
                        assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
                }
            }
            if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
                categoryCondition.addAll(
                    assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
            }
        }
        query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryCondition));
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        return selectConvert.convert(assetGroupDao.findPulldownUnconnectedGroup(query), SelectResponse.class);
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
            selectResponse.setValue(sysUser.getName());
            selectResponseList.add(selectResponse);
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
        }
    }
}

@Component
class SelectConvert extends BaseConverter<AssetGroup, SelectResponse> {
    @Override
    protected void convert(AssetGroup assetGroup, SelectResponse selectResponse) {
        selectResponse.setValue(assetGroup.getName());
        selectResponse.setId(Objects.toString(assetGroup.getId()));
        super.convert(assetGroup, selectResponse);
    }
}
