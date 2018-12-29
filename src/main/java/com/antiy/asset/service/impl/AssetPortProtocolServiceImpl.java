package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetPortProtocolDao;
import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.entity.dto.AssetPortProtocolDTO;
import com.antiy.asset.entity.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.entity.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.entity.vo.response.AssetPortProtocolResponse;
import com.antiy.asset.service.IAssetPortProtocolService;
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
        @Resource
        private BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>  requestConverter;
        @Resource
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
            List<AssetPortProtocolDTO> assetPortProtocolDTO = assetPortProtocolDao.findListAssetPortProtocol(query);
            //TODTO;
            //需要将assetPortProtocolDTO转达成AssetPortProtocolResponse
            List<AssetPortProtocolResponse> assetPortProtocolResponse = new ArrayList<AssetPortProtocolResponse>();
            return assetPortProtocolResponse;
        }

        public Integer findCountAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
            return assetPortProtocolDao.findCount(query);
        }

        @Override
        public PageResult<AssetPortProtocolResponse> findPageAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetPortProtocol(query),query.getCurrentPage(), this.findListAssetPortProtocol(query));
        }
}
