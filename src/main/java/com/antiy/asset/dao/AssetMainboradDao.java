package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.entity.dto.AssetMainboradDTO;
import com.antiy.asset.entity.vo.query.AssetMainboradQuery;

/**
 * <p>
 * 主板表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetMainboradDao extends IBaseDao<AssetMainborad> {

    List<AssetMainboradDTO> findListAssetMainborad(AssetMainboradQuery query) throws Exception;
}
