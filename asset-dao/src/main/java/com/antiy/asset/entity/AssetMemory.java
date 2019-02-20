package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 内存表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetMemory extends BaseEntity {

    /**
     * 资产主键
     */
    private String  assetId;
    /**
     * 内存容量
     */
    private Integer capacity;
    /**
     * 内存类型：1未知，2-ddr2,3-ddr3,4-ddr4
     */
    private Integer transferType;
    /**
     * 内存品牌
     */
    private String  brand;
    /**
     * 内存序列号
     */
    private String  serial;

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    /**
     * 内存主频(MHz)
     */
    private Double  frequency;
    /**
     * 插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM
     */
    private Integer slotType;
    /**
     * 是否带散热片:0-不带，1-带
     */
    private Integer heatsink;
    /**
     * 针脚数
     */
    private Integer stitch;
    /**
     * 购买日期
     */
    private Long    buyDate;
    /**
     * 保修期
     */
    private Long    warrantyDate;
    /**
     * 联系电话
     */
    private String  telephone;
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

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getSlotType() {
        return slotType;
    }

    public void setSlotType(Integer slotType) {
        this.slotType = slotType;
    }

    public Integer getHeatsink() {
        return heatsink;
    }

    public void setHeatsink(Integer heatsink) {
        this.heatsink = heatsink;
    }

    public Integer getStitch() {
        return stitch;
    }

    public void setStitch(Integer stitch) {
        this.stitch = stitch;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Long warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
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
        return "AssetMemory{" + ", assetId=" + assetId + ", capacity=" + capacity + ", frequency=" + frequency
               + ", slotType=" + slotType + ", heatsink=" + heatsink + ", stitch=" + stitch + ", buyDate=" + buyDate
               + ", warrantyDate=" + warrantyDate + ", telephone=" + telephone + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser=" + createUser + ", modifyUser="
               + modifyUser + ", status=" + status + "}";
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }
}