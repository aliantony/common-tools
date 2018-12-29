package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetMemoryDao;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.entity.dto.AssetMemoryDTO;
import com.antiy.asset.entity.vo.query.AssetMemoryQuery;
import com.antiy.asset.entity.vo.request.AssetMemoryRequest;
import com.antiy.asset.entity.vo.response.AssetMemoryResponse;
import com.antiy.asset.service.IAssetMemoryService;
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
            List<AssetMemoryDTO> assetMemoryDTO = assetMemoryDao.findListAssetMemory(query);
            //TODTO;
            //需要将assetMemoryDTO转达成AssetMemoryResponse
            List<AssetMemoryResponse> assetMemoryResponse = new ArrayList<AssetMemoryResponse>();
            return assetMemoryResponse;
        }

        public Integer findCountAssetMemory(AssetMemoryQuery query) throws Exception {
            return assetMemoryDao.findCount(query);
        }

        @Override
        public PageResult<AssetMemoryResponse> findPageAssetMemory(AssetMemoryQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetMemory(query),query.getCurrentPage(), this.findListAssetMemory(query));
        }
}
