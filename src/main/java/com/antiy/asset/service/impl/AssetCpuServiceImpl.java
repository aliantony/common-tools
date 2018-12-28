package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.dao.AssetCpuDao;
import com.antiy.asset.service.IAssetCpuService;
import com.antiy.asset.entity.vo.request.AssetCpuRequest;
import com.antiy.asset.entity.vo.response.AssetCpuResponse;
import com.antiy.asset.entity.vo.query.AssetCpuQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 处理器表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetCpuServiceImpl extends BaseServiceImpl<AssetCpu> implements IAssetCpuService{


        @Resource
        private AssetCpuDao assetCpuDao;

        private BaseConverter<AssetCpuRequest, AssetCpu>  requestConverter;
        
        private BaseConverter<AssetCpu, AssetCpuResponse> responseConverter;

        @Override
        public Integer saveAssetCpu(AssetCpuRequest request) throws Exception {
            AssetCpu assetCpu = requestConverter.convert(request, AssetCpu.class);
            return assetCpuDao.insert(assetCpu);
        }

        @Override
        public Integer updateAssetCpu(AssetCpuRequest request) throws Exception {
            AssetCpu assetCpu = requestConverter.convert(request, AssetCpu.class);
            return assetCpuDao.update(assetCpu);
        }

        @Override
        public List<AssetCpuResponse> findListAssetCpu(AssetCpuQuery query) throws Exception {
            return assetCpuDao.findListAssetCpu(query);
        }

        public Integer findCountAssetCpu(AssetCpuQuery query) throws Exception {
            return assetCpuDao.findCount(query);
        }

        @Override
        public PageResult<AssetCpuResponse> findPageAssetCpu(AssetCpuQuery query) throws Exception {
            return new PageResult<>(this.findCountAssetCpu(query),this.findListAssetCpu(query));
        }
}
