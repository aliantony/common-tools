package com.antiy.asset.service;

import com.antiy.asset.entity.AssetPeripheralEquipment;
import com.antiy.asset.vo.query.AssetPeripheralEquipmentQuery;
import com.antiy.asset.vo.request.AssetPeripheralEquipmentRequest;
import com.antiy.asset.vo.response.AssetPeripheralEquipmentResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 输入设备表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-11
 */
public interface IAssetPeripheralEquipmentService extends IBaseService<AssetPeripheralEquipment> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetPeripheralEquipment(AssetPeripheralEquipmentRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetPeripheralEquipment(AssetPeripheralEquipmentRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetPeripheralEquipmentResponse> findListAssetPeripheralEquipment(AssetPeripheralEquipmentQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetPeripheralEquipmentResponse> findPageAssetPeripheralEquipment(AssetPeripheralEquipmentQuery query) throws Exception;

}
