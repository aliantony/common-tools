package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetSoftwareDTO;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 软件信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetSoftwareDao extends IBaseDao<AssetSoftware> {

    List<AssetSoftwareDTO> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;
}
