package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.AssetTopology;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.request.AssetTopologyRequest;
import com.antiy.asset.vo.response.AssetTopologyResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产拓扑表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
@Service
public class AssetTopologyServiceImpl extends BaseServiceImpl<AssetTopology> implements IAssetTopologyService{

        private static final Logger logger = LogUtils.get();

        @Resource
        private AssetTopologyDao assetTopologyDao;
        @Resource
        private BaseConverter<AssetTopologyRequest, AssetTopology>  requestConverter;
        @Resource
        private BaseConverter<AssetTopology, AssetTopologyResponse> responseConverter;

        @Override
        public Integer saveAssetTopology(AssetTopologyRequest request) throws Exception {
            AssetTopology assetTopology = requestConverter.convert(request, AssetTopology.class);
            return assetTopologyDao.insert(assetTopology);
        }

        @Override
        public Integer updateAssetTopology(AssetTopologyRequest request) throws Exception {
            AssetTopology assetTopology = requestConverter.convert(request, AssetTopology.class);
            return assetTopologyDao.update(assetTopology);
        }

        @Override
        public List<AssetTopologyResponse> findListAssetTopology(AssetTopologyQuery query) throws Exception {
            List<AssetTopology> assetTopologyList = assetTopologyDao.findQuery(query);
            //TODO
            List<AssetTopologyResponse> assetTopologyResponse = responseConverter.convert(assetTopologyList,AssetTopologyResponse.class );
            return assetTopologyResponse;
        }

        @Override
        public PageResult<AssetTopologyResponse> findPageAssetTopology(AssetTopologyQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.findListAssetTopology(query));
        }
}
