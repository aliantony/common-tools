package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p> CPE表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetHardSoftLibDao extends IBaseDao<AssetHardSoftLib> {

    /**
     * 操作系统（下拉项）
     *
     * @return
     */
    List<SelectResponse> pullDownOs(OsQuery query);

}
