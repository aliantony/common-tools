package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.TopologyTypeEnum;
import com.antiy.common.base.BasicRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/2 14:13
 * @Description: 网络拓扑请求类
 */
@ApiModel(value = "网络拓扑请求")
public class TopologyRequest extends BasicRequest {
    @ApiModelProperty(value = "保存内容,JSON数据格式")
    @NotBlank(message = "网络拓扑保存不能为空")
    private String           dataJson;

    /**
     * · 拓扑类型
     */
    @ApiModelProperty(value = "拓扑类型", allowableValues = "PHYSICS(物理拓扑),LOGIC(逻辑拓扑),CONNECT(通联关系)")
    @NotNull(message = "拓扑类型不能为空")
    private TopologyTypeEnum topologyType;

    public TopologyTypeEnum getTopologyType() {
        return topologyType;
    }

    public void setTopologyType(TopologyTypeEnum topologyType) {
        this.topologyType = topologyType;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
