package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.vo.query.AssetLableQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 标签信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetLableDao extends IBaseDao<AssetLable> {

    List<AssetLable> findListAssetLable(AssetLableQuery query) throws Exception;
}
