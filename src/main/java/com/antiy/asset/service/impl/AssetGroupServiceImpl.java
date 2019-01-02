package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dto.AssetGroupDTO;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetGroupService;
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
        List<AssetGroupDTO> assetGroupDTO = assetGroupDao.findListAssetGroup(query);
        //TODO
        //需要将assetGroupDTO转达成AssetGroupResponse
        List<AssetGroupResponse> assetGroupResponse = new ArrayList<AssetGroupResponse>();
        return assetGroupResponse;
    }

    public Integer findCountAssetGroup(AssetGroupQuery query) throws Exception {
        return assetGroupDao.findCount(query);
    }

    @Override
    public PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetGroup(query), query.getCurrentPage(), this.findListAssetGroup(query));
    }
}
