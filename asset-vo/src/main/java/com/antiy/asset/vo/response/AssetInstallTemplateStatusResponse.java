package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 装机模板所有操作系统和状态响应类
 *
 * @author liulusheng
 * @since 2019-9-25
 */

public class AssetInstallTemplateStatusResponse {
    @ApiModelProperty("状态码")
    Integer statusCode;
    @ApiModelProperty("状态名称")
    String statusName;

    public AssetInstallTemplateStatusResponse(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
