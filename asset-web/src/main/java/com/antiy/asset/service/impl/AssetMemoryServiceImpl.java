package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.common.base.BusinessData;
import com.antiy.common.enums.BusinessModuleEnum;
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
    private static Logger                                   logger = LogUtils.get(AssetMemoryServiceImpl.class);
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
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_INSERT.getName(), assetMemory.getId(), null,
            assetMemory, BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_INSERT.getName() + " {}", assetMemory);
        return assetMemoryDao.insert(assetMemory);
    }

    @Override
    public Integer updateAssetMemory(AssetMemoryRequest request) throws Exception {
        AssetMemory assetMemory = requestConverter.convert(request, AssetMemory.class);
        assetMemory.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetMemory.setGmtModified(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_UPDATE.getName(), assetMemory.getId(),
            null, assetMemory, BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_UPDATE.getName() + " {}", assetMemory);
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
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_DELETE.getName(), (Integer) id, null, id,
            BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_DELETE.getName() + " {}", id);
        return super.deleteById(id);
    }
}
