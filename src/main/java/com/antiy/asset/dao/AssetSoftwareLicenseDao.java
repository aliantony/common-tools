package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 软件许可表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSoftwareLicenseDao extends IBaseDao<AssetSoftwareLicense> {

    List<AssetSoftwareLicense> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception;
}
