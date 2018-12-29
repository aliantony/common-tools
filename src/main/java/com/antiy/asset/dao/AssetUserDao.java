package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.asset.entity.vo.query.AssetUserQuery;
import com.antiy.asset.asset.entity.vo.response.AssetUserResponse;

/**
 * <p>
 * 资产用户信息 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetUserDao extends IBaseDao<AssetUser> {

    List<AssetUserResponse> findListAssetUser(AssetUserQuery query) throws Exception;
}
