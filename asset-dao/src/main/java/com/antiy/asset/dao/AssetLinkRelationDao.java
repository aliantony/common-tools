package com.antiy.asset.dao;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

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
}
