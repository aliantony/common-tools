package com.antiy.asset.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.asset.entity.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.asset.entity.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.asset.asset.entity.AssetSafetyEquipment;


/**
 * <p>
 * 安全设备详情表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetSafetyEquipmentService extends IBaseService<AssetSafetyEquipment> {

        /**
         * 保存
         * @param assetSafetyEquipmentRequest
         * @return
         */
        Integer saveAssetSafetyEquipment(AssetSafetyEquipmentRequest assetSafetyEquipmentRequest) throws Exception;

        /**
         * 修改
         * @param assetSafetyEquipmentRequest
         * @return
         */
        Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest assetSafetyEquipmentRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetSafetyEquipmentQuery
         * @return
         */
        List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery assetSafetyEquipmentQuery) throws Exception;

        /**
         * 批量查询
         * @param assetSafetyEquipmentQuery
         * @return
         */
        PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery assetSafetyEquipmentQuery) throws Exception;

}
