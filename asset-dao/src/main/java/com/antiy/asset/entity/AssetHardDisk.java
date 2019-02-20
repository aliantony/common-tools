package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 硬盘表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetHardDisk extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    private String           assetId;
    /**
     * 硬盘品牌
     */
    @ApiModelProperty("品牌")
    private String            brand;
    /**
     * 硬盘型号
     */
    @ApiModelProperty("型号")
    private String            model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String            serial;
    /**
     * 接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道
     */
    @ApiModelProperty("接口类型")
    private Integer           interfaceType;
    /**
     * 容量 (MB)
     */
    @ApiModelProperty("容量")
    private Integer           capacity;
    /**
     * 磁盘类型,1 HDD,2,SSD
     */
    @ApiModelProperty("磁盘类型")
    private Integer           diskType;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long              buyDate;
    /**
     * 使用次数
     */
    private Integer           useTimes;
    /**
     * 累计小时
     */
    private Integer           cumulativeHour;
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
     * 状态,1 未删除,0已删除
     */
    private Integer           status;


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

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getDiskType() {
        return diskType;
    }

    public void setDiskType(Integer diskType) {
        this.diskType = diskType;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public Integer getCumulativeHour() {
        return cumulativeHour;
    }

    public void setCumulativeHour(Integer cumulativeHour) {
        this.cumulativeHour = cumulativeHour;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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

    @Override
    public String toString() {
        return "AssetHardDisk{" +
                "assetId='" + assetId + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", serial='" + serial + '\'' +
                ", interfaceType=" + interfaceType +
                ", capacity=" + capacity +
                ", diskType=" + diskType +
                ", buyDate=" + buyDate +
                ", useTimes=" + useTimes +
                ", cumulativeHour=" + cumulativeHour +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", status=" + status +
                '}';
    }
}