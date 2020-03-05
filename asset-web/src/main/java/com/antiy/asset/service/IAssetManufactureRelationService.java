package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetManufactureRelation;
import com.antiy.asset.vo.query.AssetManufactureRelationQuery;
import com.antiy.asset.vo.request.AssetManufactureRelationRequest;
import com.antiy.asset.vo.response.AssetManufactureRelationResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 厂商与资产关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */
public interface IAssetManufactureRelationService extends IBaseService<AssetManufactureRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetManufactureRelation(AssetManufactureRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetManufactureRelation(AssetManufactureRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetManufactureRelationResponse> queryListAssetManufactureRelation(AssetManufactureRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetManufactureRelationResponse> queryPageAssetManufactureRelation(AssetManufactureRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetManufactureRelationResponse queryAssetManufactureRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetManufactureRelationById(BaseRequest baseRequest) throws Exception;

}
