package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetPeripheralEquipmentDao;
import com.antiy.asset.entity.AssetPeripheralEquipment;
import com.antiy.asset.service.IAssetPeripheralEquipmentService;
import com.antiy.asset.vo.query.AssetPeripheralEquipmentQuery;
import com.antiy.asset.vo.request.AssetPeripheralEquipmentRequest;
import com.antiy.asset.vo.response.AssetPeripheralEquipmentResponse;
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
 * 输入设备表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-11
 */
@Service
public class AssetPeripheralEquipmentServiceImpl extends BaseServiceImpl<AssetPeripheralEquipment> implements IAssetPeripheralEquipmentService {

        private static final Logger logger = LogUtils.get();

        @Resource
        private AssetPeripheralEquipmentDao assetPeripheralEquipmentDao;
        @Resource
        private BaseConverter<AssetPeripheralEquipmentRequest, AssetPeripheralEquipment>  requestConverter;
        @Resource
        private BaseConverter<AssetPeripheralEquipment, AssetPeripheralEquipmentResponse> responseConverter;

        @Override
        public Integer saveAssetPeripheralEquipment(AssetPeripheralEquipmentRequest request) throws Exception {
            AssetPeripheralEquipment assetPeripheralEquipment = requestConverter.convert(request, AssetPeripheralEquipment.class);
            assetPeripheralEquipmentDao.insert(assetPeripheralEquipment);
            return assetPeripheralEquipment.getId();
        }

        @Override
        public Integer updateAssetPeripheralEquipment(AssetPeripheralEquipmentRequest request) throws Exception {
            AssetPeripheralEquipment assetPeripheralEquipment = requestConverter.convert(request, AssetPeripheralEquipment.class);
            return assetPeripheralEquipmentDao.update(assetPeripheralEquipment);
        }

        @Override
        public List<AssetPeripheralEquipmentResponse> findListAssetPeripheralEquipment(AssetPeripheralEquipmentQuery query) throws Exception {
            List<AssetPeripheralEquipment> assetPeripheralEquipmentList = assetPeripheralEquipmentDao.findQuery(query);
            //TODO
            List<AssetPeripheralEquipmentResponse> assetPeripheralEquipmentResponse = responseConverter.convert(assetPeripheralEquipmentList,AssetPeripheralEquipmentResponse.class );
            return assetPeripheralEquipmentResponse;
        }

        @Override
        public PageResult<AssetPeripheralEquipmentResponse> findPageAssetPeripheralEquipment(AssetPeripheralEquipmentQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.findListAssetPeripheralEquipment(query));
        }
}
