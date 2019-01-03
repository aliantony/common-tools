package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.service.IAssetSafetyEquipmentService;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 安全设备详情表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
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
            //TODO 添加创建人信息
            assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
            return assetSafetyEquipmentDao.insert(assetSafetyEquipment);
        }

        @Override
        public Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception {
            AssetSafetyEquipment assetSafetyEquipment = requestConverter.convert(request, AssetSafetyEquipment.class);
            //TODO 添加修改人信息
            return assetSafetyEquipmentDao.update(assetSafetyEquipment);
        }

        @Override
        public List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            List<AssetSafetyEquipment> assetSafetyEquipmentList = assetSafetyEquipmentDao.findQuery(query);
            //TODO
            List<AssetSafetyEquipmentResponse> assetSafetyEquipmentResponse = responseConverter.convert(assetSafetyEquipmentList,AssetSafetyEquipmentResponse.class );
            return assetSafetyEquipmentResponse;
        }

        @Override
        public PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.findListAssetSafetyEquipment(query));
        }
}
