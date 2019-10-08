package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * 模板适用操作系统响应类
 *
 * @author liulusheng
 * @since 2019/9/27
 */
public class AssetInstallTemplateOsResponse {
    @ApiModelProperty("操作系统编号")
    String osCode;
    @ApiModelProperty("操作系统名称")
    String osName;

    public String getOsCode() {
        return osCode;
    }

    public void setOsCode(String osCode) {
        this.osCode = osCode;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplateOsResponse{" +
                "osCode=" + osCode +
                ", osName='" + osName + '\'' +
                '}';
    }
}
