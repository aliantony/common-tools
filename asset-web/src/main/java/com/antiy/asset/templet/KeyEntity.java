package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class KeyEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "keyNum", align = 1, title = "key编号(必填)", required = true, length = 64)
    private String keyNum;
    /**
     * 使用者
     */
    @ExcelField(value = "number", align = 1, title = "可借用设备编号", defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String number;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "可借用人员姓名", defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String user;
    /**
     * 资产名称
     */
    @ExcelField(value = "recipTime", align = 1, title = "领用时间(必填) ", isDate = true, required = true)
    private Long   recipTime;

    /**
     * 重要程度
     */
    @ExcelField(value = "recipState", align = 1, title = "领用状态", defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String recipState;

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getRecipTime() {
        return recipTime;
    }

    public void setRecipTime(Long recipTime) {
        this.recipTime = recipTime;
    }

    public String getRecipState() {
        return recipState;
    }

    public void setRecipState(String recipState) {
        this.recipState = recipState;
    }
}
