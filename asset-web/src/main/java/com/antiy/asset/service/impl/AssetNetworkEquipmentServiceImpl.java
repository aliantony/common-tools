package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.common.base.BusinessData;
import com.antiy.common.enums.BusinessModuleEnum;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.service.IAssetNetworkEquipmentService;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.response.AssetNetworkEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 网络设备详情表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetNetworkEquipmentServiceImpl extends BaseServiceImpl<AssetNetworkEquipment>
                                              implements IAssetNetworkEquipmentService {

    @Resource
    private AssetNetworkEquipmentDao                                            assetNetworkEquipmentDao;
    @Resource
    AssetLinkRelationDao                                                        linkRelationDao;
    @Resource
    private BaseConverter<AssetNetworkEquipmentRequest, AssetNetworkEquipment>  requestConverter;
    @Resource
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> responseConverter;
    private static Logger logger = LogUtils.get(AssetNetworkEquipmentServiceImpl.class);
    @Override
    public Integer saveAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkEquipmentDao.insert(assetNetworkEquipment);
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName(), assetNetworkEquipment.getId(), null,
                assetNetworkEquipment, BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName() + " {}", assetNetworkEquipment);
        return assetNetworkEquipment.getId();
    }

    @Override
    public Integer updateAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
        assetNetworkEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkEquipment.setGmtModified(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getName(), assetNetworkEquipment.getId(), null,
                assetNetworkEquipment, BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getName() + " {}", assetNetworkEquipment);
        return assetNetworkEquipmentDao.update(assetNetworkEquipment);
    }

    @Override
    public List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {

        List<AssetNetworkEquipment> assetNetworkEquipment = assetNetworkEquipmentDao
            .findListAssetNetworkEquipment(query);
        List<AssetNetworkEquipmentResponse> assetNetworkCardResponse = responseConverter.convert(assetNetworkEquipment,
            AssetNetworkEquipmentResponse.class);
        return assetNetworkCardResponse;
    }

    public Integer findCountAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        return assetNetworkEquipmentDao.findCount(query);
    }

    @Override
    public PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkEquipment(query), query.getCurrentPage(),
            this.findListAssetNetworkEquipment(query));
    }
}
