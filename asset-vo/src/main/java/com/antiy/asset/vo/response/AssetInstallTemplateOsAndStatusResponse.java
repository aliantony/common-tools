package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 装机模板所有操作系统和状态响应类
 *
 * @author liulusheng
 * @since 2019-9-25
 */
public class AssetInstallTemplateOsAndStatusResponse {
    @ApiModelProperty("适用操作系统")
    List<String> systems;
    @ApiModelProperty("状态")
    List<String> status;

    public AssetInstallTemplateOsAndStatusResponse(List<String> systems, List<String> status) {
        this.systems = systems;
        this.status = status;
    }

    public List<String> getSystems() {
        return systems;
    }

    public List<String> getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplateOsAndStatusResponse{" +
                "osName=" + systems +
                ", status=" + status +
                '}';
    }
}
