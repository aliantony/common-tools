package com.antiy.asset.vo.query;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.ObjectQuery;

/**
 * cpe信息查询条件
 * @author zhouye
 */
public class AssetSynchCpeQuery extends ObjectQuery {
    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不以为空")
    private Long start;
    /**
     * 结束时间
     */
    @NotNull()
    private Long end;

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
