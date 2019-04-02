package com.antiy.asset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 通联关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
public interface AssetLinkRelationDao extends IBaseDao<AssetLinkRelation> {

    /**
     * 查询资产对应的IP地址
     * @param assetId 资产Id
     * @param enable 是否可用
     * @return
     */
    List<String> queryIpAddressByAssetId(@Param(value = "assetId") String assetId,
                                         @Param(value = "enable") Boolean enable);
}
