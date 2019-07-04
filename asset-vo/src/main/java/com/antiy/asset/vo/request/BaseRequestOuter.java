package com.antiy.asset.vo.request;

import javax.validation.Valid;

import com.antiy.common.base.BasicRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/4/9 13:31
 * @description:
 */
@ApiModel(value = "基本请求对外接口")
public class BaseRequestOuter<T> extends BasicRequest {
    @ApiModelProperty(value = "请求流水号")
    private String requestId;

    @ApiModelProperty(value = "请求时间戳")
    private Long   timestamp = System.currentTimeMillis();

    @ApiModelProperty(value = "请求实体对象")
    @Valid
    private T      data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
