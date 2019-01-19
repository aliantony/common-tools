package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.convert.AssetGroupResponseConverter;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
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
    private AssetGroupDao                                 assetGroupDao;
    @Resource
    private AssetDao                                      assetDao;
    @Resource
    private AssetGroupRelationDao                         assetGroupRelationDao;
    @Resource
    AesEncoder                                            aesEncoder;
    @Resource
    private BaseConverter<AssetGroupRequest, AssetGroup>  requestConverter;
    @Resource
    private BaseConverter<AssetGroup, AssetGroupResponse> responseConverter;
    @Resource
    private AssetGroupResponseConverter                   assetGroupResponseConverter;

    @Override
    public String saveAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
        assetGroup.setGmtCreate(System.currentTimeMillis());
        assetGroupDao.insert(assetGroup);
        return aesEncoder.decode(assetGroup.getId().toString(), LoginUserUtil.getLoginUser().getPassword());
    }

    @Override
    public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
        return assetGroupDao.update(assetGroup);
    }

    @Override
    public List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception {
        List<AssetGroup> assetGroupList = assetGroupDao.findQuery(query);
        for (AssetGroup assetGroup : assetGroupList) {
            AssetGroupResponse assetGroupResponse = responseConverter.convert(assetGroup, AssetGroupResponse.class);
            List<String> assetList = assetGroupRelationDao.findAssetByAssetGroupId(assetGroup.getId());
            assetGroupResponse.setAssetList(assetList);
        }
        return responseConverter.convert(assetGroupList, AssetGroupResponse.class);
    }

    public Integer findCountAssetGroup(AssetGroupQuery query) throws Exception {
        return assetGroupDao.findCount(query);
    }

    @Override
    public PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetGroup(query), query.getCurrentPage(),
            this.findListAssetGroup(query));
    }

    @Override
    public List<SelectResponse> queryGroupInfo() throws Exception {
        return assetGroupResponseConverter.convert(assetGroupDao.findPulldownGroup(), SelectResponse.class);
    }

    @Override
    public AssetGroupResponse findGroupById(String id) throws Exception {
        return null;
    }

}
