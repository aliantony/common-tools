package com.antiy.asset.service;

import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.entity.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.entity.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.entity.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

;


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
         * @param request
         * @return
         */
        Integer saveAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;

}
