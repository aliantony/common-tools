package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCpuDao;
import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.service.IAssetCpuService;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.asset.vo.response.AssetCpuResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 处理器表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetCpuServiceImpl extends BaseServiceImpl<AssetCpu> implements IAssetCpuService {


    @Resource
    private AssetCpuDao assetCpuDao;
    @Resource
    private BaseConverter<AssetCpuRequest, AssetCpu> requestConverter;
    @Resource
    private BaseConverter<AssetCpu, AssetCpuResponse> responseConverter;

    @Override
    public Integer saveAssetCpu(AssetCpuRequest request) throws Exception {
        AssetCpu assetCpu = requestConverter.convert(request, AssetCpu.class);
        assetCpuDao.insert(assetCpu);
        return assetCpu.getId();
    }

    @Override
    public Integer updateAssetCpu(AssetCpuRequest request) throws Exception {
        AssetCpu assetCpu = requestConverter.convert(request, AssetCpu.class);
        return assetCpuDao.update(assetCpu);
    }

    @Override
    public List<AssetCpuResponse> findListAssetCpu(AssetCpuQuery query) throws Exception {
        List<AssetCpu> assetCpu = assetCpuDao.findListAssetCpu(query);
        //TODO
        List<AssetCpuResponse> assetCpuResponse = responseConverter.convert(assetCpu, AssetCpuResponse.class);
        return assetCpuResponse;
    }

    public Integer findCountAssetCpu(AssetCpuQuery query) throws Exception {
        return assetCpuDao.findCount(query);
    }

    @Override
    public PageResult<AssetCpuResponse> findPageAssetCpu(AssetCpuQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetCpu(query), query.getCurrentPage(), this.findListAssetCpu(query));
    }
}
