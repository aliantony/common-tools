package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.entity.dto.AssetLableDTO;
import com.antiy.asset.entity.vo.query.AssetLableQuery;

/**
 * <p>
 * 标签信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetLableDao extends IBaseDao<AssetLable> {

    List<AssetLableDTO> findListAssetLable(AssetLableQuery query) throws Exception;
}
