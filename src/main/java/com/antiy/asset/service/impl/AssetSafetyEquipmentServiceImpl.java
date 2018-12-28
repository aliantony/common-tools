package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.service.IAssetSafetyEquipmentService;
import com.antiy.asset.entity.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.entity.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.asset.entity.vo.query.AssetSafetyEquipmentQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 安全设备详情表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetSafetyEquipmentServiceImpl extends BaseServiceImpl<AssetSafetyEquipment> implements IAssetSafetyEquipmentService{


        @Resource
        private AssetSafetyEquipmentDao assetSafetyEquipmentDao;

        private BaseConverter<AssetSafetyEquipmentRequest, AssetSafetyEquipment>  requestConverter;
        
        private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentResponse> responseConverter;

        @Override
        public Integer saveAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception {
            AssetSafetyEquipment assetSafetyEquipment = requestConverter.convert(request, AssetSafetyEquipment.class);
            return assetSafetyEquipmentDao.insert(assetSafetyEquipment);
        }

        @Override
        public Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception {
            AssetSafetyEquipment assetSafetyEquipment = requestConverter.convert(request, AssetSafetyEquipment.class);
            return assetSafetyEquipmentDao.update(assetSafetyEquipment);
        }

        @Override
        public List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            return assetSafetyEquipmentDao.findListAssetSafetyEquipment(query);
        }

        public Integer findCountAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            return assetSafetyEquipmentDao.findCount(query);
        }

        @Override
        public PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            return new PageResult<>(this.findCountAssetSafetyEquipment(query),this.findListAssetSafetyEquipment(query));
        }
}
