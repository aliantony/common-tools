package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 分页查询资产列表
     * @param assetQuery
     * @return
     */
    PageResult<AssetResponse> queryAssetPage(AssetQuery assetQuery);

    /**
     * 查询资产列表
     * @param assetQuery
     * @return
     */
    List<AssetResponse> queryAssetList(AssetQuery assetQuery);

    /**
     * 分页查询已关联资产关系列表
     * @param assetLinkRelationQuery
     * @return
     */
    PageResult<AssetLinkRelationResponse> queryLinekedRelationPage(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 查询已关联资产关系列表
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkRelationResponse> queryLinekedRelationList(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 资产通联数量分页查询
     * @param assetLinkRelationQuery
     * @return
     */
    PageResult<AssetLinkedCountResponse> queryAssetLinkedCountPage(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception;

    /**
     * 资产通联数量分页查询
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkedCountResponse> queryAssetLinkedCountList(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception;

    /**
     * 与当前资产通联的资产列表查询
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkRelationResponse> queryLinkedAssetListByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception;

    /**
     * 与当前资产通联的资产列表查询
     * @param assetLinkRelationQuery
     * @return
     */
    PageResult<AssetLinkRelationResponse> queryLinkedAssetPageByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception;

    List<SelectResponse> queryPortById(QueryCondition queryCondition);

    /**
     * 批量保存接口
     * @param assetLinkRelationRequestList
     * @return
     */
    ActionResponse saveAssetLinkRelationList(List<AssetLinkRelationRequest> assetLinkRelationRequestList);

    /**
     * 查询生于ip
     * @param useableIpRequest
     * @return
     */
    List<String> queryUseableIp(UseableIpRequest useableIpRequest);
}
