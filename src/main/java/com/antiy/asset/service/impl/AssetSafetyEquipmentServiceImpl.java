package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.entity.dto.AssetSafetyEquipmentDTO;
import com.antiy.asset.entity.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.entity.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.entity.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.asset.service.IAssetSafetyEquipmentService;
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
 * 安全设备详情表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetSafetyEquipmentServiceImpl extends BaseServiceImpl<AssetSafetyEquipment> implements IAssetSafetyEquipmentService{


        @Resource
        private AssetSafetyEquipmentDao assetSafetyEquipmentDao;
        @Resource
        private BaseConverter<AssetSafetyEquipmentRequest, AssetSafetyEquipment>  requestConverter;
        @Resource
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
            List<AssetSafetyEquipmentDTO> assetSafetyEquipmentDTO = assetSafetyEquipmentDao.findListAssetSafetyEquipment(query);
            //TODTO;
            //需要将assetSafetyEquipmentDTO转达成AssetSafetyEquipmentResponse
            List<AssetSafetyEquipmentResponse> assetSafetyEquipmentResponse = new ArrayList<AssetSafetyEquipmentResponse>();
            return assetSafetyEquipmentResponse;
        }

        public Integer findCountAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            return assetSafetyEquipmentDao.findCount(query);
        }

        @Override
        public PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetSafetyEquipment(query),query.getCurrentPage(), this.findListAssetSafetyEquipment(query));
        }
}
