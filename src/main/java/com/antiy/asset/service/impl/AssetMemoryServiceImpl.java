package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.dao.AssetMemoryDao;
import com.antiy.asset.service.IAssetMemoryService;
import com.antiy.asset.asset.entity.vo.request.AssetMemoryRequest;
import com.antiy.asset.asset.entity.vo.response.AssetMemoryResponse;
import com.antiy.asset.asset.entity.vo.query.AssetMemoryQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 内存表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetMemoryServiceImpl extends BaseServiceImpl<AssetMemory> implements IAssetMemoryService{


        @Resource
        private AssetMemoryDao assetMemoryDao;

        private BaseConverter<AssetMemoryRequest, AssetMemory>  requestConverter;
        
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
            return assetMemoryDao.findListAssetMemory(query);
        }

        public Integer findCountAssetMemory(AssetMemoryQuery query) throws Exception {
            return assetMemoryDao.findCount(query);
        }

        @Override
        public PageResult<AssetMemoryResponse> findPageAssetMemory(AssetMemoryQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetMemory(query),query.getCurrentPage(), this.findListAssetMemory(query));
        }
}
