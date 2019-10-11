package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author liulusheng
 * @since 2019/10/11
 */
public class AssetTemplateSoftwareRelationQuery extends ObjectQuery {

    @ApiModelProperty("操作系统id")
    private String operationSystem;
    @ApiModelProperty("移除软件关联id")
    private List<String> removeBusinessIds;

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public List<String> getRemoveBusinessIds() {
        return removeBusinessIds;
    }

    public void setRemoveBusinessIds(List<String> removeBusinessIds) {
        this.removeBusinessIds = removeBusinessIds;
    }
}
