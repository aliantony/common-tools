package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.service.IAssetNetworkEquipmentService;
import com.antiy.asset.entity.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkEquipmentResponse;
import com.antiy.asset.entity.vo.query.AssetNetworkEquipmentQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 网络设备详情表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetNetworkEquipmentServiceImpl extends BaseServiceImpl<AssetNetworkEquipment> implements IAssetNetworkEquipmentService{


        @Resource
        private AssetNetworkEquipmentDao assetNetworkEquipmentDao;

    private BaseConverter<AssetNetworkEquipmentRequest, AssetNetworkEquipment>  requestConverter = new BaseConverter<>();
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> responseConverter = new BaseConverter<>();
    ;

        @Override
        public Integer saveAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
            AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
            return assetNetworkEquipmentDao.insert(assetNetworkEquipment);
        }

        @Override
        public Integer updateAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception {
            AssetNetworkEquipment assetNetworkEquipment = requestConverter.convert(request, AssetNetworkEquipment.class);
            return assetNetworkEquipmentDao.update(assetNetworkEquipment);
        }

        @Override
        public List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
            return assetNetworkEquipmentDao.findListAssetNetworkEquipment(query);
        }

        public Integer findCountAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
            return assetNetworkEquipmentDao.findCount(query);
        }

        @Override
        public PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkEquipment(query),query.getCurrentPage(), this.findListAssetNetworkEquipment(query));
        }
}
