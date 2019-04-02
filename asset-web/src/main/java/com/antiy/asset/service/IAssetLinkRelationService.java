package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 通联关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
public interface IAssetLinkRelationService extends IBaseService<AssetLinkRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetLinkRelationResponse> queryListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetLinkRelationResponse> queryPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetLinkRelationResponse queryAssetLinkRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetLinkRelationById(BaseRequest baseRequest) throws Exception;

    /**
     * 通过资产Id查询资产的Ip地址
     * @param assetId 资产Id
     * @return
     * @throws Exception
     */
    List<String> queryIpAddressByAssetId(String assetId, Boolean enable) throws Exception;

}
