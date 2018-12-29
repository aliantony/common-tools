package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.dao.AssetPortProtocolDao;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.entity.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.entity.vo.response.AssetPortProtocolResponse;
import com.antiy.asset.entity.vo.query.AssetPortProtocolQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 端口协议 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetPortProtocolServiceImpl extends BaseServiceImpl<AssetPortProtocol> implements IAssetPortProtocolService{


        @Resource
        private AssetPortProtocolDao assetPortProtocolDao;

        private BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>  requestConverter;
        
        private BaseConverter<AssetPortProtocol, AssetPortProtocolResponse> responseConverter;

        @Override
        public Integer saveAssetPortProtocol(AssetPortProtocolRequest request) throws Exception {
            AssetPortProtocol assetPortProtocol = requestConverter.convert(request, AssetPortProtocol.class);
            return assetPortProtocolDao.insert(assetPortProtocol);
        }

        @Override
        public Integer updateAssetPortProtocol(AssetPortProtocolRequest request) throws Exception {
            AssetPortProtocol assetPortProtocol = requestConverter.convert(request, AssetPortProtocol.class);
            return assetPortProtocolDao.update(assetPortProtocol);
        }

        @Override
        public List<AssetPortProtocolResponse> findListAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
            return assetPortProtocolDao.findListAssetPortProtocol(query);
        }

        public Integer findCountAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
            return assetPortProtocolDao.findCount(query);
        }

        @Override
        public PageResult<AssetPortProtocolResponse> findPageAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetPortProtocol(query),query.getCurrentPage(), this.findListAssetPortProtocol(query));
        }
}
