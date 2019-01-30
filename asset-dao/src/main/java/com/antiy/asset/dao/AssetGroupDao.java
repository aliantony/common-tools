package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产组表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetGroupDao extends IBaseDao<AssetGroup> {

    List<AssetGroup> findListAssetGroup(AssetGroupQuery query) throws Exception;

    /**
     * 查询下拉项的资产组信息
     *
     * @return
     */
    List<AssetGroup> findPulldownGroup() throws Exception;

    /**
     * 资产组名称去重
     *
     * @return
     */
    Boolean removeDuplicate(String assetName) throws Exception;
}
