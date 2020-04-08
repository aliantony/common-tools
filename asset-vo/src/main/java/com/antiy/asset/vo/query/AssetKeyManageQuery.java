package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;

/**
 * @author chenchaowu
 */
public class AssetKeyManageQuery extends ObjectQuery {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * key编号
     */
    private String keyNum;

    /**
     * 使用者 -- 资产编号 or 用户名
     */
    private String userNumName;

    /**
     * key当前状态-- 0:未领用 1:领用 2:冻结
     */
    private Integer recipState;

    /**
     * 领用时间 -- 起始时间
     */
    private Long startRecipTime;

    /**
     * 领用时间 -- 截止时间
     */
    private Long endRecipTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getUserNumName() {
        return userNumName;
    }

    public void setUserNumName(String userNumName) {
        this.userNumName = userNumName;
    }

    public Integer getRecipState() {
        return recipState;
    }

    public void setRecipState(Integer recipState) {
        this.recipState = recipState;
    }

    public Long getStartRecipTime() {
        return startRecipTime;
    }

    public void setStartRecipTime(Long startRecipTime) {
        this.startRecipTime = startRecipTime;
    }

    public Long getEndRecipTime() {
        return endRecipTime;
    }

    public void setEndRecipTime(Long endRecipTime) {
        this.endRecipTime = endRecipTime;
    }

    @Override
    public String toString() {
        return "AssetKeyManageQuery{" +
                "id='" + id + '\'' +
                ", keyNum=" + keyNum +
                ", userNumName=" + userNumName +
                ", recipState=" + recipState +
                ", startRecipTime=" + startRecipTime +
                ", endRecipTime=" + endRecipTime +
                '}';
    }
}
