package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.ObjectQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: lvliang
 * @Date: 2020/4/27 9:56
 */
@ApiModel
public class AssetOsQuery extends ObjectQuery {

    @ApiModelProperty("操作系统大类")
    private String       parentNode;
    @ApiModelProperty("系统版本")
    private String       sysVersion;
    @ApiModelProperty("语言")
    private String       language;
    @ApiModelProperty("软件版本")
    private String       softVersion;
    @ApiModelProperty("软件平台")
    private String       softPlatform;
    @ApiModelProperty("硬件平台")
    private String       hardPlatform;
    @ApiModelProperty("其他")
    private String       other;

    private List<String> childIds;

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getSoftPlatform() {
        return softPlatform;
    }

    public void setSoftPlatform(String softPlatform) {
        this.softPlatform = softPlatform;
    }

    public String getHardPlatform() {
        return hardPlatform;
    }

    public void setHardPlatform(String hardPlatform) {
        this.hardPlatform = hardPlatform;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public List<String> getChildIds() {
        return childIds;
    }

    public void setChildIds(List<String> childIds) {
        this.childIds = childIds;
    }
}
