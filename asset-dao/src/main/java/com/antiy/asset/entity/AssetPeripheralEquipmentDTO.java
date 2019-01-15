package com.antiy.asset.dto;

import java.util.Date;
import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 输入设备表 数据对象 </p>
 *
 * @author zhangyajun
 * @since 2019-01-11
 */

public class AssetPeripheralEquipmentDTO extends BaseEntity {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     * 设备类型：1鼠标2键盘3打印机
     */
    @ApiModelProperty("设备类型：1鼠标2键盘3打印机")
    private Integer type;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String  manufacturer;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Long    gmtModified;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
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
     * 状态,0 已删除,1未删除
     */
    @ApiModelProperty("状态,0 已删除,1未删除")
    private Integer status;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        return "AssetPeripheralEquipment{" + ", assetId=" + assetId + ", type=" + type + ", manufacturer="
               + manufacturer + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo
               + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}