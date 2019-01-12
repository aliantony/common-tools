package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetMemoryDao;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.service.IAssetMemoryService;
import com.antiy.asset.vo.query.AssetMemoryQuery;
import com.antiy.asset.vo.request.AssetMemoryRequest;
import com.antiy.asset.vo.response.AssetMemoryResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

/**
 * <p> 内存表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetMemoryServiceImpl extends BaseServiceImpl<AssetMemory> implements IAssetMemoryService {

    @Resource
    private AssetMemoryDao                                  assetMemoryDao;
    @Resource
    private BaseConverter<AssetMemoryRequest, AssetMemory>  requestConverter;
    @Resource
    private BaseConverter<AssetMemory, AssetMemoryResponse> responseConverter;

    @Override
    public Integer saveAssetMemory(AssetMemoryRequest request) throws Exception {
        AssetMemory assetMemory = requestConverter.convert(request, AssetMemory.class);
        return assetMemoryDao.insert(assetMemory);
    }

    @Override
    public Integer updateAssetMemory(AssetMemoryRequest request) throws Exception {
        AssetMemory assetMemory = requestConverter.convert(request, AssetMemory.class);
        return assetMemoryDao.update(assetMemory);
    }

    @Override
    public List<AssetMemoryResponse> findListAssetMemory(AssetMemoryQuery query) throws Exception {
        List<AssetMemory> assetMemory = assetMemoryDao.findListAssetMemory(query);
        // TODO
        List<AssetMemoryResponse> assetMemoryResponse = new ArrayList<AssetMemoryResponse>();
        return assetMemoryResponse;
    }

    public Integer findCountAssetMemory(AssetMemoryQuery query) throws Exception {
        return assetMemoryDao.findCount(query);
    }

    @Override
    public PageResult<AssetMemoryResponse> findPageAssetMemory(AssetMemoryQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetMemory(query), query.getCurrentPage(),
            this.findListAssetMemory(query));
    }
}
