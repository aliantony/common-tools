package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.entity.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.entity.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkEquipmentResponse;
import com.antiy.asset.entity.AssetNetworkEquipment;


/**
 * <p>
 * 网络设备详情表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetNetworkEquipmentService extends IBaseService<AssetNetworkEquipment> {

        /**
         * 保存
         * @param assetNetworkEquipmentRequest
         * @return
         */
        Integer saveAssetNetworkEquipment(AssetNetworkEquipmentRequest assetNetworkEquipmentRequest) throws Exception;

        /**
         * 修改
         * @param assetNetworkEquipmentRequest
         * @return
         */
        Integer updateAssetNetworkEquipment(AssetNetworkEquipmentRequest assetNetworkEquipmentRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetNetworkEquipmentQuery
         * @return
         */
        List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery assetNetworkEquipmentQuery) throws Exception;

        /**
         * 批量查询
         * @param assetNetworkEquipmentQuery
         * @return
         */
        PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery assetNetworkEquipmentQuery) throws Exception;

}
