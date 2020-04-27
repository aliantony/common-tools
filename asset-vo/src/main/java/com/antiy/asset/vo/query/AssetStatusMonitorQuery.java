package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetStatusMonitor 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetStatusMonitorQuery extends ObjectQuery {

    @ApiModelProperty("监控资源类型")
    Integer type;
    @ApiModelProperty("资产id")
    @Encode
    private String assetId;
    @ApiModelProperty("关联筛选")
    int relation;
    /**
     * 更新时间
     */
    private Long gmtModified;

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public int getRelations() {
        return relation;
    }

    public void setRelations(int relation) {
        this.relation = relation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}