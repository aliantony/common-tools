package com.antiy.asset.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 资产主表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
public class Asset extends Model<Asset> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 资产编号
     */
	private String number;
    /**
     * 资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS,
     */
	private Integer type;
    /**
     * 资产名称
     */
	private String name;
    /**
     * 序列号
     */
	private String serial;
    /**
     * 品类
     */
	private Integer category;
    /**
     * 资产型号
     */
	private Integer model;
    /**
     * 制造商
     */
	private String manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
	@TableField("asset_status")
	private Integer assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
	@TableField("operation_system")
	private String operationSystem;
    /**
     * 系统位数
     */
	@TableField("system_bit")
	private Integer systemBit;
    /**
     * 物理位置
     */
	private String location;
    /**
     * 纬度
     */
	private String latitude;
    /**
     * 经度
     */
	private String longitude;
    /**
     * 固件版本
     */
	@TableField("firmware_version")
	private String firmwareVersion;
    /**
     * 责任人主键
     */
	@TableField("responsible_user_id")
	private Integer responsibleUserId;
    /**
     * 联系电话
     */
	@TableField("contact_tel")
	private String contactTel;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 硬盘
     */
	@TableField("hard_disk")
	private String hardDisk;
    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
	private String memory;
    /**
     * 上报来源,1-自动上报，2-人工上报
     */
	@TableField("asset_source")
	private Integer assetSource;
    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
	@TableField("importance_degree")
	private Integer importanceDegree;
    /**
     * 描述
     */
	private String describle;
    /**
     * CPUJSON数据{ID:1,name:intel,coresize:8}
     */
	private String cpu;
    /**
     * 网卡JSON数据{ID:1,name:intel,speed:1900M}
     */
	@TableField("network_card")
	private String networkCard;
    /**
     * 父类资源Id
     */
	@TableField("parent_id")
	private Integer parentId;
    /**
     * 所属标签ID和名称列表JSON串
     */
	private String tags;
    /**
     * 是否入网,0表示未入网,1表示入网
     */
	@TableField("is_innet")
	private Boolean isInnet;
    /**
     * 使用到期时间
     */
	@TableField("service_life")
	private Long serviceLife;
    /**
     * 制造日期
     */
	@TableField("buy_date")
	private Long buyDate;
    /**
     * 保修期
     */
	private Long warranty;
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
     * 状态,0 未删除,1已删除
     */
	@TableField("is_delete")
	private Integer isDelete;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(Integer assetStatus) {
		this.assetStatus = assetStatus;
	}

	public String getOperationSystem() {
		return operationSystem;
	}

	public void setOperationSystem(String operationSystem) {
		this.operationSystem = operationSystem;
	}

	public Integer getSystemBit() {
		return systemBit;
	}

	public void setSystemBit(Integer systemBit) {
		this.systemBit = systemBit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public Integer getResponsibleUserId() {
		return responsibleUserId;
	}

	public void setResponsibleUserId(Integer responsibleUserId) {
		this.responsibleUserId = responsibleUserId;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHardDisk() {
		return hardDisk;
	}

	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public Integer getAssetSource() {
		return assetSource;
	}

	public void setAssetSource(Integer assetSource) {
		this.assetSource = assetSource;
	}

	public Integer getImportanceDegree() {
		return importanceDegree;
	}

	public void setImportanceDegree(Integer importanceDegree) {
		this.importanceDegree = importanceDegree;
	}

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getNetworkCard() {
		return networkCard;
	}

	public void setNetworkCard(String networkCard) {
		this.networkCard = networkCard;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Boolean getInnet() {
		return isInnet;
	}

	public void setInnet(Boolean isInnet) {
		this.isInnet = isInnet;
	}

	public Long getServiceLife() {
		return serviceLife;
	}

	public void setServiceLife(Long serviceLife) {
		this.serviceLife = serviceLife;
	}

	public Long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Long buyDate) {
		this.buyDate = buyDate;
	}

	public Long getWarranty() {
		return warranty;
	}

	public void setWarranty(Long warranty) {
		this.warranty = warranty;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public static final String ID = "id";

	public static final String NUMBER = "number";

	public static final String TYPE = "type";

	public static final String NAME = "name";

	public static final String SERIAL = "serial";

	public static final String CATEGORY = "category";

	public static final String MODEL = "model";

	public static final String MANUFACTURER = "manufacturer";

	public static final String ASSET_STATUS = "asset_status";

	public static final String OPERATION_SYSTEM = "operation_system";

	public static final String SYSTEM_BIT = "system_bit";

	public static final String LOCATION = "location";

	public static final String LATITUDE = "latitude";

	public static final String LONGITUDE = "longitude";

	public static final String FIRMWARE_VERSION = "firmware_version";

	public static final String RESPONSIBLE_USER_ID = "responsible_user_id";

	public static final String CONTACT_TEL = "contact_tel";

	public static final String EMAIL = "email";

	public static final String HARD_DISK = "hard_disk";

	public static final String MEMORY = "memory";

	public static final String ASSET_SOURCE = "asset_source";

	public static final String IMPORTANCE_DEGREE = "importance_degree";

	public static final String DESCRIBLE = "describle";

	public static final String CPU = "cpu";

	public static final String NETWORK_CARD = "network_card";

	public static final String PARENT_ID = "parent_id";

	public static final String TAGS = "tags";

	public static final String IS_INNET = "is_innet";

	public static final String SERVICE_LIFE = "service_life";

	public static final String BUY_DATE = "buy_date";

	public static final String WARRANTY = "warranty";

	public static final String GMT_CREATE = "gmt_create";

	public static final String GMT_MODIFIED = "gmt_modified";

	public static final String MEMO = "memo";

	public static final String CREATE_USER = "create_user";

	public static final String MODIFY_USER = "modify_user";

	public static final String IS_DELETE = "is_delete";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Asset{" +
			"id=" + id +
			", number=" + number +
			", type=" + type +
			", name=" + name +
			", serial=" + serial +
			", category=" + category +
			", model=" + model +
			", manufacturer=" + manufacturer +
			", assetStatus=" + assetStatus +
			", operationSystem=" + operationSystem +
			", systemBit=" + systemBit +
			", location=" + location +
			", latitude=" + latitude +
			", longitude=" + longitude +
			", firmwareVersion=" + firmwareVersion +
			", responsibleUserId=" + responsibleUserId +
			", contactTel=" + contactTel +
			", email=" + email +
			", hardDisk=" + hardDisk +
			", memory=" + memory +
			", assetSource=" + assetSource +
			", importanceDegree=" + importanceDegree +
			", describle=" + describle +
			", cpu=" + cpu +
			", networkCard=" + networkCard +
			", parentId=" + parentId +
			", tags=" + tags +
			", isInnet=" + isInnet +
			", serviceLife=" + serviceLife +
			", buyDate=" + buyDate +
			", warranty=" + warranty +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", memo=" + memo +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", isDelete=" + isDelete +
			"}";
	}
}
