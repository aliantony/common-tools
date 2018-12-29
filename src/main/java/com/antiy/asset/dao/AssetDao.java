package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.asset.entity.vo.query.AssetQuery;
import com.antiy.asset.asset.entity.vo.response.AssetResponse;

/**
 * <p>
 * 资产主表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetDao extends IBaseDao<Asset> {

    List<AssetResponse> findListAsset(AssetQuery query) throws Exception;
}
