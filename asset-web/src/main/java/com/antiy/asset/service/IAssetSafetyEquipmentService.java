package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.vo.query.AssetSafetyEquipmentQuery;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.response.AssetSafetyEquipmentResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 安全设备详情表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetSafetyEquipmentService extends IBaseService<AssetSafetyEquipment> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    String saveAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetSafetyEquipment(AssetSafetyEquipmentRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetSafetyEquipmentResponse> findListAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetSafetyEquipmentResponse> findPageAssetSafetyEquipment(AssetSafetyEquipmentQuery query) throws Exception;

    /**
     * 通过id查询方案
     *
     * @param id
     * @return
     */
    AssetSafetyEquipmentResponse findSafetyEquipmentById(String id) throws Exception;
}
