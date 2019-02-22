package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
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
    private BaseConverter<AssetGroup, AssetGroupResponse> assetGroupToResponseConverter;
    @Resource
    private BaseConverter<AssetGroupRequest, AssetGroup>  assetGroupToAssetGroupConverter;

    @Override
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
                assetGroupRelationDao.insert(assetGroupRelation);
            }
        }

        if (!Objects.equals(0, result)) { // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_INSERT.getName(),
                AssetEventEnum.ASSET_GROUP_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_INSERT.getName() + " {}", assetGroup.toString());
        }
        return aesEncoder.encode(assetGroup.getStringId(), LoginUserUtil.getLoginUser().getUsername());
    }

    @Override
    public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = (AssetGroup) BeanConvert.convert(request, AssetGroup.class);
        assetGroup.setId(DataTypeUtils.stringToInteger(request.getId()));
        assetGroup.setId(DataTypeUtils.stringToInteger(request.getId()));
        Integer[] assetIdArr = DataTypeUtils.stringArrayToIntegerArray(request.getAssetIds());

        List<AssetGroupRelation> assetGroupRelationList = new ArrayList<>();

        Integer delResult = assetGroupRelationDao.deleteByAssetGroupId(assetGroup.getId());

        if (!Objects.equals(0, delResult)) {
            // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName(),
                AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_DELETE.getName() + " {}", assetGroup.toString());
        }

        for (Integer assetId : assetIdArr) {
            AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
            assetGroupRelation.setAssetGroupId(assetGroup.getStringId());
            assetGroupRelation.setAssetId(assetId.toString());
            assetGroupRelation.setGmtCreate(System.currentTimeMillis());
            assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetGroupRelationList.add(assetGroupRelation);
        }

        Integer result = assetGroupRelationDao.insertBatch(assetGroupRelationList);

        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(),
                AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
            LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName() + " {}", assetGroup.toString());
        }
        assetGroupDao.update(assetGroup);

        // -----------------------------更新资产主表的资产组字段内容start-----------------------------
        Map<String, Object> map = new HashMap<>();
        StringBuilder assetNameBuilder = new StringBuilder();
        for (Integer assetId : assetIdArr) {
            List<String> assetGroupNameList = assetGroupRelationDao.findAssetGroupNameByAssetId(assetId);
            ParamterExceptionUtils.isTrue(assetGroupNameList.size() < 10, "资产关联的资产组不能超过10个");
            String assetGroupName = assetGroupNameList.toString();
            if (assetGroupNameList.size() > 0) {
                assetNameBuilder.append(assetGroupNameList.toString(), 1, assetGroupName.length() - 1);
                map.put("assetGroupName", assetNameBuilder.toString());
                map.put("assetId", assetId);
                assetDao.updateAssetGroupNameWithAssetId(map);
                // 写入业务日志
                LogHandle.log(assetGroupNameList.toString(), AssetEventEnum.ASSET_MODIFY.getName(),
                    AssetEventEnum.ASSET_MODIFY.getStatus(), ModuleEnum.ASSET.getCode());
                LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", assetGroupNameList.toString());
            }
        }

        // -----------------------------更新资产主表的资产组字段内容end-----------------------------

        if (!Objects.equals(0, result)) {
            // 写入业务日志
            LogHandle.log(assetGroup.toString(), AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getName(),
                AssetEventEnum.ASSET_GROUP_RELATION_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
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

    @Override
    public AssetGroupResponse findGroupById(String id) throws Exception {
        AssetGroupResponse assetGroupResponse = assetGroupToResponseConverter
            .convert(assetGroupDao.getById(Integer.valueOf(id)), AssetGroupResponse.class);
        return assetGroupResponse;
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
