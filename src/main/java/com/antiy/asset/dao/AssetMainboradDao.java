package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetMainboradDTO;
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
 * @since 2018-12-29
 */
public interface AssetMainboradDao extends IBaseDao<AssetMainborad> {

    List<AssetMainboradDTO> findListAssetMainborad(AssetMainboradQuery query) throws Exception;
}
