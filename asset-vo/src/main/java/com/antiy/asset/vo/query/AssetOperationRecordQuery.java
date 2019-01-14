package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

/**
 * <p> AssetOperationRecord 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetOperationRecordQuery extends ObjectQuery {

    /**
     * 被操作的对象ID
     */
    private Integer targetObjectId;

    public Integer getTargetObject() {
        return targetObjectId;
    }

    public void setTargetObject(Integer targetObjectId) {
        this.targetObjectId = targetObjectId;
    }
}