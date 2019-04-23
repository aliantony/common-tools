package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.ObjectQuery;

/**
 * <p> AssetTopology 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetTopologyQuery extends ObjectQuery {

    /**
     * 登录用户所属区域ids
     */
    private List<Integer> userAreaIds;

    public List<Integer> getUserAreaIds() {
        return userAreaIds;
    }

    public void setUserAreaIds(List<Integer> userAreaIds) {
        this.userAreaIds = userAreaIds;
    }
}