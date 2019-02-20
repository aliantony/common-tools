package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 处理器表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetCpu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    private String           assetId;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String            serial;
    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    private String            brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String            model;
    /**
     * CPU主频
     */
    @ApiModelProperty("CPU主频")
    private Float             mainFrequency;
    /**`1
     * 线程数
     */
    @ApiModelProperty("线程数")
    private Integer           threadSize;
    /**
     * 核心数
     */
    @ApiModelProperty("处理器核心数")
    private Integer           coreSize;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getMainFrequency() {
        return mainFrequency;
    }

    public void setMainFrequency(Float mainFrequency) {
        this.mainFrequency = mainFrequency;
    }

    public Integer getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(Integer threadSize) {
        this.threadSize = threadSize;
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
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

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
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
        return "AssetCpu{" +
                "assetId='" + assetId + '\'' +
                ", serial='" + serial + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", mainFrequency=" + mainFrequency +
                ", threadSize=" + threadSize +
                ", coreSize=" + coreSize +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", status=" + status +
                '}';
    }
}