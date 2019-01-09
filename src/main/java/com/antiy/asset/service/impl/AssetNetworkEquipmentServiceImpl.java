package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.service.IAssetNetworkEquipmentService;
import com.antiy.asset.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.response.AssetNetworkEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网络设备详情表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetNetworkEquipmentServiceImpl extends BaseServiceImpl<AssetNetworkEquipment> implements IAssetNetworkEquipmentService {


    @Resource
    private AssetNetworkEquipmentDao assetNetworkEquipmentDao;
    @Resource
    private BaseConverter<AssetNetworkEquipmentRequest, AssetNetworkEquipment> requestConverter;
    @Resource
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> responseConverter;

    @Override
    public Integer saveAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
        assetNetworkEquipmentDao.insert(assetNetworkEquipment);
        return assetNetworkEquipment.getId();
    }

    @Override
    public Integer updateAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
        return assetNetworkEquipmentDao.update(assetNetworkEquipment);
    }

    @Override
    public List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        List<AssetNetworkEquipment> assetNetworkEquipment = assetNetworkEquipmentDao.findListAssetNetworkEquipment(query);
        //TODO
        List<AssetNetworkEquipmentResponse> assetNetworkCardResponse = responseConverter.convert(assetNetworkEquipment, AssetNetworkEquipmentResponse.class);
        return assetNetworkCardResponse;
    }

    public Integer findCountAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        return assetNetworkEquipmentDao.findCount(query);
    }

    @Override
    public PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkEquipment(query), query.getCurrentPage(), this.findListAssetNetworkEquipment(query));
    }
}
