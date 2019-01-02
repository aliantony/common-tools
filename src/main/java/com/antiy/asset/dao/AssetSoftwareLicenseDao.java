package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.dto.AssetSoftwareLicenseDTO;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;

/**
 * <p>
 * 软件许可表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetSoftwareLicenseDao extends IBaseDao<AssetSoftwareLicense> {

    List<AssetSoftwareLicenseDTO> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception;
}
