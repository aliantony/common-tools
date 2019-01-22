package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 资产组表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetGroupServiceImpl extends BaseServiceImpl<AssetGroup> implements IAssetGroupService {

    @Resource
    private AssetGroupDao         assetGroupDao;
    @Resource
    private AssetGroupRelationDao assetGroupRelationDao;
    @Resource
    private AesEncoder            aesEncoder;

    @Override
    public String saveAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = (AssetGroup) BeanConvert.convert(request, AssetGroup.class);
        // assetGroup.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetGroup.setGmtCreate(System.currentTimeMillis());
        assetGroupDao.insert(assetGroup);
        // return aesEncoder.decode(assetGroup.getId().toString(), LoginUserUtil.getLoginUser().getPassword());
        // TODO 解密id
        return assetGroup.getId().toString();
    }

    @Override
    public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = (AssetGroup) BeanConvert.convert(request, AssetGroup.class);
        Integer[] assetIdArr = DataTypeUtils.stringArrayToIntegerArray(request.getAssetIds());
        List<AssetGroupRelation> assetGroupRelationList = new ArrayList<>();
        assetGroupRelationDao.deleteByAssetGroupId(assetGroup.getId());
        for (Integer assetId : assetIdArr) {
            AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
            assetGroupRelation.setAssetGroupId(assetGroup.getId());
            assetGroupRelation.setAssetId(assetId);
            assetGroupRelation.setGmtCreate(System.currentTimeMillis());
            // TODO 创建人
            assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetGroupRelationList.add(assetGroupRelation);
        }
        return assetGroupRelationDao.insertBatch(assetGroupRelationList);
    }

    @Override
    public List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception {
        List<AssetGroup> assetGroupList = assetGroupDao.findQuery(query);
        List<AssetGroupResponse> assetResponseList = BeanConvert.convert(assetGroupList, AssetGroupResponse.class);
        for (AssetGroupResponse assetGroupResponse : assetResponseList) {
            List<String> assetList = assetGroupRelationDao
                .findAssetNameByAssetGroupId(Integer.valueOf(assetGroupResponse.getId()));
            assetGroupResponse.setAssetList(assetList);
        }
        return assetResponseList;
    }

    @Override
    public PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception {
        return new PageResult<AssetGroupResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetGroup(query));
    }

    @Override
    public List<SelectResponse> queryGroupInfo() throws Exception {
        return BeanConvert.convert(assetGroupDao.findPulldownGroup(), SelectResponse.class);
    }

    @Override
    public AssetGroupResponse findGroupById(String id) throws Exception {
        return (AssetGroupResponse) BeanConvert.convert(assetGroupDao.getById(Integer.valueOf(id)),
            AssetGroupResponse.class);
    }
}
