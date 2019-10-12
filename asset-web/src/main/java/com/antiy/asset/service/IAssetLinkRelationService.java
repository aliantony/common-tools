package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.AssetLinkedCountResponse;
import com.antiy.asset.vo.response.IpPortResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

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
    Boolean saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

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

    /**
     * 查询剩余ip端口
     * @param useableIpRequest
     * @return
     */
    List<IpPortResponse> queryUseableIp(UseableIpRequest useableIpRequest);

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetLinkRelationById(BaseRequest baseRequest) throws Exception;

}
