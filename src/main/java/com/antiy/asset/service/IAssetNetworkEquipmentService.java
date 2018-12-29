package com.antiy.asset.service;

import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.entity.vo.query.AssetNetworkEquipmentQuery;
import com.antiy.asset.entity.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkEquipmentResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

;


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
         * @param request
         * @return
         */
        Integer saveAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetNetworkEquipment(AssetNetworkEquipmentRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetNetworkEquipmentResponse> findListAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetNetworkEquipmentResponse> findPageAssetNetworkEquipment(AssetNetworkEquipmentQuery query) throws Exception;

}
