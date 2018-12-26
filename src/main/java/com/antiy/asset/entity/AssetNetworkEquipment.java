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
 * 网络设备详情表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_network_equipment")
public class AssetNetworkEquipment extends Model<AssetNetworkEquipment> {

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
     * 接口数目
     */
	@TableField("interface_size")
	private Integer interfaceSize;
    /**
     * 是否无线:0-否,1-是
     */
	@TableField("is_wireless")
	private Boolean isWireless;
    /**
     * 内网IP
     */
	@TableField("inner_ip")
	private String innerIp;
    /**
     * 外网IP
     */
	@TableField("outer_ip")
	private String outerIp;
    /**
     * MAC地址
     */
	@TableField("mac_address")
	private String macAddress;
    /**
     * 子网掩码
     */
	@TableField("subnet_mask")
	private String subnetMask;
    /**
     * 预计带宽(M)
     */
	@TableField("expect_bandwidth")
	private Integer expectBandwidth;
    /**
     * 配置寄存器(GB)
     */
	private Integer register;
    /**
     * DRAM大小
     */
	@TableField("dram_size")
	private Float dramSize;
    /**
     * FLASH大小
     */
	@TableField("flash_size")
	private Float flashSize;
    /**
     * NCRM大小
     */
	@TableField("ncrm_size")
	private Float ncrmSize;
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
     * 创建时间
     */
	@TableField("gmt_create")
	private Long gmtCreate;
    /**
     * 备注
     */
	private byte[] memo;
    /**
     * 更新时间
     */
	@TableField("gmt_modified")
	private Long gmtModified;
    /**
     * 状态,0 未删除,1已删除
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

	public Integer getInterfaceSize() {
		return interfaceSize;
	}

	public void setInterfaceSize(Integer interfaceSize) {
		this.interfaceSize = interfaceSize;
	}

	public Boolean getWireless() {
		return isWireless;
	}

	public void setWireless(Boolean isWireless) {
		this.isWireless = isWireless;
	}

	public String getInnerIp() {
		return innerIp;
	}

	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}

	public String getOuterIp() {
		return outerIp;
	}

	public void setOuterIp(String outerIp) {
		this.outerIp = outerIp;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public Integer getExpectBandwidth() {
		return expectBandwidth;
	}

	public void setExpectBandwidth(Integer expectBandwidth) {
		this.expectBandwidth = expectBandwidth;
	}

	public Integer getRegister() {
		return register;
	}

	public void setRegister(Integer register) {
		this.register = register;
	}

	public Float getDramSize() {
		return dramSize;
	}

	public void setDramSize(Float dramSize) {
		this.dramSize = dramSize;
	}

	public Float getFlashSize() {
		return flashSize;
	}

	public void setFlashSize(Float flashSize) {
		this.flashSize = flashSize;
	}

	public Float getNcrmSize() {
		return ncrmSize;
	}

	public void setNcrmSize(Float ncrmSize) {
		this.ncrmSize = ncrmSize;
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

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public byte[] getMemo() {
		return memo;
	}

	public void setMemo(byte[] memo) {
		this.memo = memo;
	}

	public Long getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static final String ID = "id";

	public static final String ASSET_ID = "asset_id";

	public static final String INTERFACE_SIZE = "interface_size";

	public static final String IS_WIRELESS = "is_wireless";

	public static final String INNER_IP = "inner_ip";

	public static final String OUTER_IP = "outer_ip";

	public static final String MAC_ADDRESS = "mac_address";

	public static final String SUBNET_MASK = "subnet_mask";

	public static final String EXPECT_BANDWIDTH = "expect_bandwidth";

	public static final String REGISTER = "register";

	public static final String DRAM_SIZE = "dram_size";

	public static final String FLASH_SIZE = "flash_size";

	public static final String NCRM_SIZE = "ncrm_size";

	public static final String CREATE_USER = "create_user";

	public static final String MODIFY_USER = "modify_user";

	public static final String GMT_CREATE = "gmt_create";

	public static final String MEMO = "memo";

	public static final String GMT_MODIFIED = "gmt_modified";

	public static final String STATUS = "status";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AssetNetworkEquipment{" +
			"id=" + id +
			", assetId=" + assetId +
			", interfaceSize=" + interfaceSize +
			", isWireless=" + isWireless +
			", innerIp=" + innerIp +
			", outerIp=" + outerIp +
			", macAddress=" + macAddress +
			", subnetMask=" + subnetMask +
			", expectBandwidth=" + expectBandwidth +
			", register=" + register +
			", dramSize=" + dramSize +
			", flashSize=" + flashSize +
			", ncrmSize=" + ncrmSize +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", gmtCreate=" + gmtCreate +
			", memo=" + memo +
			", gmtModified=" + gmtModified +
			", status=" + status +
			"}";
	}
}
