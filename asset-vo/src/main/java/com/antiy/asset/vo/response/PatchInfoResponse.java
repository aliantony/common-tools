package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p></p>
 *
 * @author machine
 * @since 2019-09-17
 */

public class PatchInfoResponse {
    @ApiModelProperty("补丁主键id")
    private String stringId;

    /**
     * 安天补丁编号
     */
    @ApiModelProperty("安天补丁编号")
    private String antiyPatchNumber;
    /**
     * 补丁编号
     */
    @ApiModelProperty("补丁编号")
    private String patchNumber;
    /**
     * 补丁名称
     */
    @ApiModelProperty("补丁名称")
    private String patchName;
    /**
     * 补丁等级（1 重要 2 中等 3 严重）
     */
    @ApiModelProperty("补丁等级")
    private String patchLevel;
    /**
     * 补丁来源(1 软件厂商 2 自主研发)
     */
    @ApiModelProperty("补丁来源")
    private String pathSource;
    /**
     * 0 支持热补 1 不支持热补（安装补丁后续重启）
     */
    @ApiModelProperty("补丁热支持")
    private String hotfix;

    /**
     * 需要用户进行交互操作 0 否 1 是
     */
    @ApiModelProperty("需要用户进行交互")
    private String userInteraction;
    /**
     * 必须以独占方式安装 0 否 1 是
     */
    @ApiModelProperty("独占方式")
    private String exclusiveInstall;
    /**
     * 安装此补丁是否需要联网 0 否 1 是
     */
    @ApiModelProperty("联网状态")
    private String networkStatus;

    public void setPatchLevel(String patchLevel) {
        this.patchLevel = "1".equals(patchLevel) ? "重要"
                : "2".equals(patchLevel) ? "中等" : "2".equals(patchLevel) ? "严重" : "";
    }

    public String getPathSource() {
        return pathSource;
    }

    public void setPathSource(String pathSource) {
        this.pathSource = "1".equals(pathSource) ? "软件厂商" : "2".equals(pathSource) ? "自主研发" : "";
    }

    public String getHotfix() {
        return hotfix;
    }

    public void setHotfix(String hotfix) {
        this.hotfix = "0".equals(hotfix) ? "支持热补" : "0".equals(hotfix) ? "不支持热补" : "";
    }

    public String getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(String userInteraction) {
        this.userInteraction = "0".equals(userInteraction) ? "不需要用户交互" : "1".equals(userInteraction) ? "需要用户交互" : "";
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getPatchNumber() {
        return patchNumber;
    }

    public void setPatchNumber(String patchNumber) {
        this.patchNumber = patchNumber;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public String getExclusiveInstall() {
        return exclusiveInstall;
    }

    public void setExclusiveInstall(String exclusiveInstall) {
        this.exclusiveInstall = "0".equals(exclusiveInstall) ? "不需要独占方式安装"
                : "1".equals(exclusiveInstall) ? "需要独占方式安装" : "";
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = "0".equals(networkStatus) ? "不需要用户联网" : "1".equals(networkStatus) ? "需要用户联网" : "";
    }

    public String getPatchLevel() {
        return patchLevel;
    }

    public String getAntiyPatchNumber() {
        return antiyPatchNumber;
    }

    public void setAntiyPatchNumber(String antiyPatchNumber) {
        this.antiyPatchNumber = antiyPatchNumber;
    }
}