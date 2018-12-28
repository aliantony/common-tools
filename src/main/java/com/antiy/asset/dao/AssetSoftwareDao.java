package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.vo.query.AssetSoftwareQuery;
import com.antiy.asset.entity.vo.response.AssetSoftwareResponse;

/**
 * <p>
 * 软件信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface AssetSoftwareDao extends IBaseDao<AssetSoftware> {

    List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;
}
