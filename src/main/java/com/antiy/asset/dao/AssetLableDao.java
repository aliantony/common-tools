package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.asset.entity.vo.query.AssetLableQuery;
import com.antiy.asset.asset.entity.vo.response.AssetLableResponse;

/**
 * <p>
 * 标签信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetLableDao extends IBaseDao<AssetLable> {

    List<AssetLableResponse> findListAssetLable(AssetLableQuery query) throws Exception;
}
