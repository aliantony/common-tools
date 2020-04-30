package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * WorkFlow 查询条件
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class WorkFlowQuery extends BasicRequest {

    @ApiModelProperty("流程id")
    private String       flowId;

    @ApiModelProperty("流程节点标识")
    private String       flowNodeTag;

    @ApiModelProperty("资产关联的区域id")
    @Encode
    private List<String> areaId;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowNodeTag() {
        return flowNodeTag;
    }

    public void setFlowNodeTag(String flowNodeTag) {
        this.flowNodeTag = flowNodeTag;
    }

    public List<String> getAreaId() {
        return areaId;
    }

    public void setAreaId(List<String> areaId) {
        this.areaId = areaId;
    }
}
