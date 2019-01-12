package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetHardDiskDao;
import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.service.IAssetHardDiskService;
import com.antiy.asset.vo.query.AssetHardDiskQuery;
import com.antiy.asset.vo.request.AssetHardDiskRequest;
import com.antiy.asset.vo.response.AssetHardDiskResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

/**
 * <p> 硬盘表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetHardDiskServiceImpl extends BaseServiceImpl<AssetHardDisk> implements IAssetHardDiskService {

    @Resource
    private AssetHardDiskDao                                    assetHardDiskDao;
    @Resource
    private BaseConverter<AssetHardDiskRequest, AssetHardDisk>  requestConverter;
    @Resource
    private BaseConverter<AssetHardDisk, AssetHardDiskResponse> responseConverter;

    @Override
    public Integer saveAssetHardDisk(AssetHardDiskRequest request) throws Exception {
        AssetHardDisk assetHardDisk = requestConverter.convert(request, AssetHardDisk.class);
        return assetHardDiskDao.insert(assetHardDisk);
    }

    @Override
    public Integer updateAssetHardDisk(AssetHardDiskRequest request) throws Exception {
        AssetHardDisk assetHardDisk = requestConverter.convert(request, AssetHardDisk.class);
        return assetHardDiskDao.update(assetHardDisk);
    }

    @Override
    public List<AssetHardDiskResponse> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception {
        List<AssetHardDisk> assetHardDisk = assetHardDiskDao.findListAssetHardDisk(query);
        // TODO
        List<AssetHardDiskResponse> assetHardDiskResponse = new ArrayList<AssetHardDiskResponse>();
        return assetHardDiskResponse;
    }

    public Integer findCountAssetHardDisk(AssetHardDiskQuery query) throws Exception {
        return assetHardDiskDao.findCount(query);
    }

    @Override
    public PageResult<AssetHardDiskResponse> findPageAssetHardDisk(AssetHardDiskQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetHardDisk(query), query.getCurrentPage(),
            this.findListAssetHardDisk(query));
    }
}
