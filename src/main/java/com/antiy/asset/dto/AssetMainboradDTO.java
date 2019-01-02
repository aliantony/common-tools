package com.antiy.asset.dto;


import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 主板表 数据对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetMainboradDTO extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    private String brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String serial;
    /**
     * BIOS版本
     */
    @ApiModelProperty("BIOS版本")
    private String biosVersion;
    /**
     * BIOS日期
     */
    @ApiModelProperty("BIOS日期")
    private Long biosDate;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     * 状态,0 未删除,1已删除
     */
    @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;


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


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public Long getGmtModified() {
        return gmtModified;
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
        return "AssetMainborad{" +
                ", assetId=" + assetId +
                ", brand=" + brand +
                ", model=" + model +
                ", serial=" + serial +
                ", biosVersion=" + biosVersion +
                ", biosDate=" + biosDate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo=" + memo +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                "}";
    }
}