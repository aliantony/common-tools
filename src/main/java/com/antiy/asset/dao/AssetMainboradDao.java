package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.vo.query.AssetMainboradQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 主板表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetMainboradDao extends IBaseDao<AssetMainborad> {

    List<AssetMainborad> findListAssetMainborad(AssetMainboradQuery query) throws Exception;
}
