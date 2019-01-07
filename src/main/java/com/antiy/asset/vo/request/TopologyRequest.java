package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private String  dataJson;

    /**
     * 拓扑类型
     */
    @ApiModelProperty("拓扑类型")
    @NotNull(message = "拓扑类型不能为空")
    private Integer topologyType;

    public Integer getTopologyType() {
        return topologyType;
    }

    public void setTopologyType(Integer topologyType) {
        this.topologyType = topologyType;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
