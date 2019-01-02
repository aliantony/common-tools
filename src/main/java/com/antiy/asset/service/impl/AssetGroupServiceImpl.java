package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资产组表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Slf4j
public class AssetGroupServiceImpl extends BaseServiceImpl<AssetGroup> implements IAssetGroupService {


    @Resource
    private AssetGroupDao assetGroupDao;
    @Resource
    private BaseConverter<AssetGroupRequest, AssetGroup> requestConverter;
    @Resource
    private BaseConverter<AssetGroup, AssetGroupResponse> responseConverter;

    @Override
    public Integer saveAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
        return assetGroupDao.insert(assetGroup);
    }

    @Override
    public Integer updateAssetGroup(AssetGroupRequest request) throws Exception {
        AssetGroup assetGroup = requestConverter.convert(request, AssetGroup.class);
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
        if (assetGroups.size() > 0) {
            List assetGroupResponses = BeanConvert.convert(assetGroups, AssetGroupResponse.class);
            setListID(assetGroups, assetGroupResponses);
            return assetGroupResponses;
        } else
            return new ArrayList<>();
    }

    private void setListID(List<AssetGroup> assetGroups, List<AssetGroupResponse> assetGroupResponses) {
        for (int i = 0; i < assetGroupResponses.size(); i++) {
            setID(assetGroups.get(i), assetGroupResponses.get(i));
        }
    }

    private void setID(AssetGroup assetGroup, AssetGroupResponse assetGroupResponse) {
        assetGroupResponse.setId(assetGroup.getId());
    }

    @Override
    public PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetGroup(query), query.getCurrentPage(), this.findListAssetGroup(query));
    }
}
