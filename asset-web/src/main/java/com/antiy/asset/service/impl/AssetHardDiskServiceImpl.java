package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import io.protostuff.runtime.ArraySchemas;
import org.slf4j.Logger;
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
    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetHardDiskDao                                   assetHardDiskDao;
    @Resource
    private BaseConverter<AssetHardDiskRequest, AssetHardDisk> requestConverter;

    @Override
    public Integer saveAssetHardDisk(AssetHardDiskRequest request) throws Exception {
        AssetHardDisk assetHardDisk = requestConverter.convert(request, AssetHardDisk.class);
        LogHandle.log(request, AssetEventEnum.ASSET_DISK_INSERT.getName(), AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_DISK_INSERT.getName() + " {}", request.toString());
        return assetHardDiskDao.insert(assetHardDisk);
    }

    @Override
    public Integer updateAssetHardDisk(AssetHardDiskRequest request) throws Exception {
        AssetHardDisk assetHardDisk = requestConverter.convert(request, AssetHardDisk.class);
        LogHandle.log(request, AssetEventEnum.ASSET_DISK_INSERT.getName(), AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_DISK_INSERT.getName() + " {}", request.toString());
        return assetHardDiskDao.update(assetHardDisk);
    }

    @Override
    public List<AssetHardDiskResponse> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception {
        List<AssetHardDisk> assetHardDisk = assetHardDiskDao.findListAssetHardDisk(query);
        List<AssetHardDiskResponse> assetHardDiskResponse = BeanConvert.convert(assetHardDisk, AssetHardDiskResponse.class);
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
    @Override
    public Integer deleteById(Serializable id) throws Exception {
        LogHandle.log(id, AssetEventEnum.ASSET_DISK_DELETE.getName(), AssetEventEnum.ASSET_DISK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_DISK_DELETE.getName() + " {}", id);
        return super.deleteById(id);
    }
}
