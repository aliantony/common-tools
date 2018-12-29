package com.antiy.asset.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.asset.entity.AssetMainborad;
import com.antiy.asset.asset.entity.vo.query.AssetMainboradQuery;
import com.antiy.asset.asset.entity.vo.response.AssetMainboradResponse;

/**
 * <p>
 * 主板表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetMainboradDao extends IBaseDao<AssetMainborad> {

    List<AssetMainboradResponse> findListAssetMainborad(AssetMainboradQuery query) throws Exception;
}
