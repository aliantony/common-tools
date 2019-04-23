package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.dao.AssetPortProtocolDao;
import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.vo.response.AssetPortProtocolResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.PageResult;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 端口协议 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetPortProtocolServiceImpl extends BaseServiceImpl<AssetPortProtocol>
                                          implements IAssetPortProtocolService {

    @Resource
    private AssetPortProtocolDao                                        assetPortProtocolDao;
    @Resource
    private BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>  requestConverter;
    @Resource
    private BaseConverter<AssetPortProtocol, AssetPortProtocolResponse> responseConverter;
    private static Logger logger = LogUtils.get(AssetNetworkEquipmentServiceImpl.class);

    @Override
    @Transactional
    public Integer saveAssetPortProtocol(AssetPortProtocolRequest request) throws Exception {
        AssetPortProtocol assetPortProtocol = requestConverter.convert(request, AssetPortProtocol.class);
        assetPortProtocol.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetPortProtocol.setGmtCreate(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_PORT_INSERT.getName(), assetPortProtocol.getId(), null,
                assetPortProtocol, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_PORT_INSERT.getName() + " {}", assetPortProtocol);

        return assetPortProtocolDao.insert(assetPortProtocol);
    }

    @Override
    @Transactional
    public Integer updateAssetPortProtocol(AssetPortProtocolRequest request) throws Exception {
        AssetPortProtocol assetPortProtocol = requestConverter.convert(request, AssetPortProtocol.class);
        assetPortProtocol.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetPortProtocol.setGmtCreate(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_PORT_UPDATE.getName(), assetPortProtocol.getId(), null,
                assetPortProtocol, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_PORT_UPDATE.getName() + " {}", assetPortProtocol);
        return assetPortProtocolDao.update(assetPortProtocol);
    }

    @Override
    public List<AssetPortProtocolResponse> findListAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
        List<AssetPortProtocol> assetPortProtocol = assetPortProtocolDao.findListAssetPortProtocol(query);
        // TODO
        List<AssetPortProtocolResponse> assetPortProtocolResponse = responseConverter.convert(assetPortProtocol,
            AssetPortProtocolResponse.class);
        return assetPortProtocolResponse;
    }

    public Integer findCountAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
        return assetPortProtocolDao.findCount(query);
    }

    @Override
    public PageResult<AssetPortProtocolResponse> findPageAssetPortProtocol(AssetPortProtocolQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetPortProtocol(query), query.getCurrentPage(),
            this.findListAssetPortProtocol(query));
    }
}
