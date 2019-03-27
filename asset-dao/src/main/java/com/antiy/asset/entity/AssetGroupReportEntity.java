package com.antiy.asset.entity;

/**
 * 根据资产组查询资产数据实体
 *
 * @author zhangxin
 * @date 2019/3/27 9:48
 */
public class AssetGroupReportEntity {

    /**
     * 资产组名字
     */
    private String  assetGroupName;

    /**
     * 资产组Id
     */
    private Integer assetGroupId;

    /**
     * 统计数量
     */
    private Integer countNum;

    /**
     * 统计时间串
     */
    private String  date;

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public Integer getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(Integer assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AssetGroupReportEntity{" + "assetGroupName='" + assetGroupName + '\'' + ", assetGroupId=" + assetGroupId
               + ", countNum=" + countNum + ", date='" + date + '\'' + '}';
    }
}
