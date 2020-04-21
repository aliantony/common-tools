package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 资产用户信息 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetUserDao extends IBaseDao<AssetUser> {

    List<AssetUser> findListAssetUser(AssetUserQuery query) throws Exception;
    /**
     * 查询资产的使用者
     *
     * @return
     */
    List<AssetUser> findUserInAsset() throws Exception;

    Integer findListCount(AssetUserQuery objectQuery) throws Exception;

    void insertBatch(List<AssetUser> assetUserList);

    List<AssetUser> queryUserList(AssetUserQuery query);

    List<AssetUser> findExportListAssetUser(AssetUserQuery assetUser);

    String findUserName(String id);

    /**
     * 查询资产的使用者及所属部门
     */
    AssetUser findUserAndDepartment(String userId);

}
