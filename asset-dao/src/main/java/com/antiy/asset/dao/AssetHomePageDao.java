package com.antiy.asset.dao;

/**
 * <p> 首页 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetHomePageDao {
    Integer countIncludeManage() throws Exception;

    Integer countUnincludeManage() throws Exception;
}
