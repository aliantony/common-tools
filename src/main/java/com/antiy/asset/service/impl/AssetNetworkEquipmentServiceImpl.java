package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.service.IAssetNetworkEquipmentService;
import com.antiy.asset.entity.dto.AssetNetworkEquipmentDTO;
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
        @Resource
        private BaseConverter<AssetNetworkEquipmentRequest, AssetNetworkEquipment>  requestConverter;
        @Resource
        private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> responseConverter;

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
            List<AssetNetworkEquipmentDTO> assetNetworkEquipmentDTO = assetNetworkEquipmentDao.findListAssetNetworkEquipment(query);
            //TODO
            //需要将assetNetworkEquipmentDTO转达成AssetNetworkEquipmentResponse
            List<AssetNetworkEquipmentResponse> assetNetworkEquipmentResponse = new ArrayList<AssetNetworkEquipmentResponse>();
            return assetNetworkEquipmentResponse;
        }

        public Integer findCountAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
            return assetNetworkEquipmentDao.findCount(query);
        }

        @Override
        public PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkEquipment(query),query.getCurrentPage(), this.findListAssetNetworkEquipment(query));
        }
}
