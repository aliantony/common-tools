package com.antiy.asset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
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
    Integer deleteRelationByAssetId(@Param(value = "assetIds") List<Integer> assetIds);

    List<Integer> findUsePort(AssetLinkRelationQuery query);
}
