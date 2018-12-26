package com.antiy.asset.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 硬盘表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_hard_disk")
public class AssetHardDisk extends Model<AssetHardDisk> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 资产主键
     */
	@TableField("asset_id")
	private Integer assetId;
    /**
     * 硬盘品牌
     */
	private String brand;
    /**
     * 硬盘型号
     */
	private String model;
    /**
     * 序列号
     */
	private String serial;
    /**
     * 接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道
     */
	@TableField("interface_type")
	private Integer interfaceType;
    /**
     * 容量 (MB)
     */
	private Integer capacity;
    /**
     * 磁盘类型,1 HDD,2,SSD
     */
	@TableField("disk_type")
	private Integer diskType;
    /**
     * 购买日期
     */
	@TableField("buy_date")
	private Long buyDate;
    /**
     * 使用次数
     */
	@TableField("use_times")
	private Integer useTimes;
    /**
     * 累计小时
     */
	@TableField("cumulative_hour")
	private Integer cumulativeHour;
    /**
     * 创建时间
     */
	@TableField("gmt_create")
	private Long gmtCreate;
    /**
     * 更新时间
     */
	@TableField("gmt_modified")
	private Long gmtModified;
    /**
     * 备注
     */
	private String memo;
    /**
     * 创建人
     */
	@TableField("create_user")
	private Integer createUser;
    /**
     * 修改人
     */
	@TableField("modify_user")
	private Integer modifyUser;
    /**
     * 状态,1 未删除,0已删除
     */
	private Integer status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public static final String ID = "id";

	public static final String ASSET_ID = "asset_id";

	public static final String BRAND = "brand";

	public static final String MODEL = "model";

	public static final String SERIAL = "serial";

	public static final String INTERFACE_TYPE = "interface_type";

	public static final String CAPACITY = "capacity";

	public static final String DISK_TYPE = "disk_type";

	public static final String BUY_DATE = "buy_date";

	public static final String USE_TIMES = "use_times";

	public static final String CUMULATIVE_HOUR = "cumulative_hour";

	public static final String GMT_CREATE = "gmt_create";

	public static final String GMT_MODIFIED = "gmt_modified";

	public static final String MEMO = "memo";

	public static final String CREATE_USER = "create_user";

	public static final String MODIFY_USER = "modify_user";

	public static final String STATUS = "status";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AssetHardDisk{" +
			"id=" + id +
			", assetId=" + assetId +
			", brand=" + brand +
			", model=" + model +
			", serial=" + serial +
			", interfaceType=" + interfaceType +
			", capacity=" + capacity +
			", diskType=" + diskType +
			", buyDate=" + buyDate +
			", useTimes=" + useTimes +
			", cumulativeHour=" + cumulativeHour +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", memo=" + memo +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", status=" + status +
			"}";
	}
}
