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

    /**
     * 被操作对象类型
     */
    private Integer targetType;

    public Integer getTargetObject() {
        return targetObjectId;
    }

    public void setTargetObjectId(Integer targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }
}