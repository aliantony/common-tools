package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.response.AssetNodeInfoResponse;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 通联关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
public interface AssetLinkRelationDao extends IBaseDao<AssetLinkRelation> {
    /**
     * 查询资产列表
     * @param assetQuery
     * @return
     */
    List<Asset> queryAssetList(AssetQuery assetQuery);

    /**
     * 查询已与当前资产关联的资产id
     * @param primaryKey
     * @return
     */
    List<String> queryLinkedAssetById(String primaryKey);

    /**
     * 查询关系表中已存在的资产id
     * @return
     */
    List<String> queryLinkedAssetWithoutId();

    /**
     * 查询已关联资产关系列表
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkRelation> queryLinekedRelationList(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 查询资产对应的IP地址
     * @param assetId 资产Id
     * @param enable 是否可用,true表示可用的资产IP,false表示全部IP
     * @return
     */
    List<String> queryIpAddressByAssetId(@Param(value = "assetId") String assetId,
                                         @Param(value = "enable") Boolean enable,
                                         @Param(value = "assetPort") String assetPort);

    /**
     * 批量删除通联关系,父节点或者子节点为当前资产Id的均会删除
     * @param assetIds 资产列表Id,注意资产列表一定不能为空,否则全部删除
     * @return
     */
    Integer deleteRelationByAssetId(@Param(value = "assetIds") Map<String, String> assetIds);

    List<Integer> findUsePort(AssetLinkRelationQuery query);

    /**
     * 资产通联数量查询
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkedCount> queryAssetLinkedCountList(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 与当前资产通联的资产列表查询
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkRelation> queryLinkedAssetListByAssetId(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 与当前资产通联的资产数量查询
     * @param assetLinkRelationQuery
     * @return
     */
    Integer queryLinkedCountAssetByAssetId(AssetLinkRelationQuery assetLinkRelationQuery);

    List<Integer> findUsePort(String assetId);

    /**
     * 查询网络设备端口数量
     * @param id
     * @return
     */
    Integer queryPortSize(@Param(value = "id") Integer id);

    /**
     * 资产通联数量
     * @param assetLinkRelationQuery
     * @return
     */
    Integer queryAssetLinkedCount(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 批量保存
     * @param assetLinkRelationList
     * @return
     */
    Integer insertBatch(@Param("list") List<AssetLinkRelation> assetLinkRelationList);

    /**
     * 查询品类型号
     * @return
     */
    List<String> queryCategoryModes();

    /**
     * 查询拓扑通联节点信息
     * @param assetId
     * @return
     */
    AssetNodeInfoResponse queryAssetNodeInfo(String assetId);

    /**
     * 统计已建立关联关系的资产数量 资产状态为已入网7和待退役8
     * @param assetTopologyQuery 封装查询条件
     * @return
     */
    Integer countAssetTopology(AssetTopologyQuery assetTopologyQuery);

    /**
     * 查询可用ip
     * @param assetId
     * @param type
     * @return
     */
    List<String> queryUseableIp(@Param("assetId") String assetId, @Param("type") String type);
    /**
     * 已管控拓扑管理的资产的资产组信息(下拉) queryGroupList
     * @param query
     * @return
     */
    List<AssetGroup> queryGroupList(AssetTopologyQuery query);

}
