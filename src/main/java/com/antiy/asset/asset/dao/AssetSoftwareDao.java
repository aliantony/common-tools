package com.antiy.asset.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.asset.entity.AssetSoftware;
import com.antiy.asset.asset.entity.vo.query.AssetSoftwareQuery;
import com.antiy.asset.asset.entity.vo.response.AssetSoftwareResponse;

/**
 * <p>
 * 软件信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetSoftwareDao extends IBaseDao<AssetSoftware> {

    List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;
}
