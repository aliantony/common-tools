package com.antiy.asset.templet;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Filename: AssetLendRelationEntity Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2020/4/10
 */
public class AssetLendRelationEntity {
    @ApiModelProperty("归还日期")
    private String lendPeriods;
    @ApiModelProperty("出借状态 ")
    private String lendStatusDesc;
    @ApiModelProperty("出借日期")
    private String lendTime;
    @ApiModelProperty("资产名称")
    private String assetName;
    @ApiModelProperty("资产编号")
    private String assetNumber;
    @ApiModelProperty("资产类型")
    private String categoryModel;
    @ApiModelProperty("key")
    private String keyNum;
    @ApiModelProperty("资产使用者")
    private String responsibleUserName;

    public String getLendPeriods() {
        return lendPeriods;
    }

    public void setLendPeriods(String lendPeriods) {
        this.lendPeriods = lendPeriods;
    }

    public String getLendStatusDesc() {
        return lendStatusDesc;
    }

    public void setLendStatusDesc(String lendStatusDesc) {
        this.lendStatusDesc = lendStatusDesc;
    }

    public String getLendTime() {
        return lendTime;
    }

    public void setLendTime(String lendTime) {
        this.lendTime = lendTime;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

}
