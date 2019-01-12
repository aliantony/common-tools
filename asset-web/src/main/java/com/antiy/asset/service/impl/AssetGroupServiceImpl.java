package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.convert.GroupSelectResponseConverter;
import com.antiy.asset.vo.response.GroupValueResponse;
import com.antiy.asset.vo.response.SelectResponse;
import org.springframework.stereotype.Service;

import com.antiy.asset.convert.AssetGroupResponseConverter;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

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
    private BaseConverter<AssetGroupRequest, AssetGroup>  requestConverter;
    @Resource
    private BaseConverter<AssetGroup, AssetGroupResponse> responseConverter;
    @Resource
    private BaseConverter<AssetGroup, GroupValueResponse> groupValueResponseConverter;
    @Resource
    private GroupSelectResponseConverter groupSelectResponseConverter;
    private AssetGroupResponseConverter                   assetGroupResponseConverter;

    @Override
    public Integer saveAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
        assetGroup.setGmtCreate(System.currentTimeMillis());
        assetGroupDao.insert(assetGroup);
        return assetGroup.getId();
    }

    @Override
    public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
        assetGroup.setGmtModified(System.currentTimeMillis());
        return assetGroupDao.update(assetGroup);
    }

    @Override
    public List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception {
        List<AssetGroup> assetGroup = assetGroupDao.findListAssetGroup(query);
        return convert(assetGroup);
    }

    public Integer findCountAssetGroup(AssetGroupQuery query) throws Exception {
        return assetGroupDao.findCount(query);
    }

    private List<AssetGroupResponse> convert(List<AssetGroup> assetGroups) {
        return responseConverter.convert(assetGroups, AssetGroupResponse.class);
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


}
