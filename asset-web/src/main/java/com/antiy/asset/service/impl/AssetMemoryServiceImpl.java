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
import org.slf4j.Logger;
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
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 内存表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetMemoryServiceImpl extends BaseServiceImpl<AssetMemory> implements IAssetMemoryService {
    private Logger                                          logger = LogUtils.get(this.getClass());
    @Resource
    private AssetMemoryDao                                  assetMemoryDao;
    @Resource
    private BaseConverter<AssetMemoryRequest, AssetMemory>  requestConverter;
    @Resource
    private BaseConverter<AssetMemory, AssetMemoryResponse> responseConverter;

    @Override
    public Integer saveAssetMemory(AssetMemoryRequest request) throws Exception {
        AssetMemory assetMemory = requestConverter.convert(request, AssetMemory.class);
        assetMemory.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetMemory.setGmtCreate(System.currentTimeMillis());
        LogHandle.log(request, AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
            AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_INSERT.getName() + " {}", request.toString());
        return assetMemoryDao.insert(assetMemory);
    }

    @Override
    public Integer updateAssetMemory(AssetMemoryRequest request) throws Exception {
        AssetMemory assetMemory = requestConverter.convert(request, AssetMemory.class);
        assetMemory.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetMemory.setGmtModified(System.currentTimeMillis());
        LogHandle.log(request, AssetEventEnum.ASSET_MEMORY_UPDATE.getName(),
            AssetEventEnum.ASSET_MEMORY_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_UPDATE.getName() + " {}", request.toString());
        return assetMemoryDao.update(assetMemory);
    }

    @Override
    public List<AssetMemoryResponse> findListAssetMemory(AssetMemoryQuery query) throws Exception {
        List<AssetMemory> assetMemory = assetMemoryDao.findListAssetMemory(query);
        // TODO
        List<AssetMemoryResponse> assetMemoryResponse = BeanConvert.convert(assetMemory, AssetMemoryResponse.class);
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

    @Override
    public Integer deleteById(Serializable id) throws Exception {
        LogHandle.log(id, AssetEventEnum.ASSET_MEMORY_DELETE.getName(), AssetEventEnum.ASSET_MEMORY_DELETE.getStatus(),
            ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_DELETE.getName() + " {}", id);
        return super.deleteById(id);
    }
}
