package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 主板表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetMainborad extends BaseEntity {

    /**
     * 资产主键
     */
    private Integer assetId;
    /**
     * 品牌
     */
    private String  brand;
    /**
     * 型号
     */
    private String  model;
    /**
     * 序列号
     */
    private String  serial;
    /**
     * BIOS版本
     */
    private String  biosVersion;
    /**
     * BIOS日期
     */
    private Long    biosDate;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 修改时间
     */
    private Long    gmtModified;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBiosVersion() {
        return biosVersion;
    }

    public void setBiosVersion(String biosVersion) {
        this.biosVersion = biosVersion;
    }

    public Long getBiosDate() {
        return biosDate;
    }

    public void setBiosDate(Long biosDate) {
        this.biosDate = biosDate;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetMainborad{" + ", assetId=" + assetId + ", brand=" + brand + ", model=" + model + ", serial="
               + serial + ", biosVersion=" + biosVersion + ", biosDate=" + biosDate + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser=" + createUser + ", modifyUser="
               + modifyUser + ", status=" + status + "}";
    }
}