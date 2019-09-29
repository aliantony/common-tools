package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p></p>
 *
 * @author machine
 * @since 2019-09-17
 */

public class PatchInfo extends BaseEntity {
    /**
     * 安天补丁编号
     */
    private String antiyPatchNumber;
    /**
     * 补丁编号
     */
    private String patchNumber;
    /**
     * 补丁名称
     */
    private String patchName;
    /**
     * 补丁等级（1 重要 2 中等 3 严重）
     */
    private String patchLevel;
    /**
     * 补丁来源(1 软件厂商 2 自主研发)
     */
    private String pathSource;
    /**
     * 0 支持热补 1 不支持热补（安装补丁后续重启）
     */
    private String hotfix;

    /**
     * 需要用户进行交互操作 0 否 1 是
     */
    private String userInteraction;
    /**
     * 必须以独占方式安装 0 否 1 是
     */
    private String exclusiveInstall;
    /**
     * 安装此补丁是否需要联网 0 否 1 是
     */
    private String networkStatus;

    /**
     * 补丁标识 1 突发补丁 2 四库补丁 3 合并数据
     */
    private String patchSource;

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

    public String getPatchLevel() {
        return patchLevel;
    }

    public void setPatchLevel(String patchLevel) {
        this.patchLevel = patchLevel;
    }

    public String getPathSource() {
        return pathSource;
    }

    public void setPathSource(String pathSource) {
        this.pathSource = pathSource;
    }

    public String getHotfix() {
        return hotfix;
    }

    public void setHotfix(String hotfix) {
        this.hotfix = hotfix;
    }

    public String getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(String userInteraction) {
        this.userInteraction = userInteraction;
    }

    public String getExclusiveInstall() {
        return exclusiveInstall;
    }

    public void setExclusiveInstall(String exclusiveInstall) {
        this.exclusiveInstall = exclusiveInstall;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getPatchSource() {
        return patchSource;
    }

    public void setPatchSource(String patchSource) {
        this.patchSource = patchSource;
    }

    public String getAntiyPatchNumber() {
        return antiyPatchNumber;
    }

    public void setAntiyPatchNumber(String antiyPatchNumber) {
        this.antiyPatchNumber = antiyPatchNumber;
    }
}