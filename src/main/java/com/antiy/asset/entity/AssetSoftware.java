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
 * 软件信息表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_software")
public class AssetSoftware extends Model<AssetSoftware> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 软件大小(KB)
     */
	private Integer size;
    /**
     * 操作系统(WINDOWS7-32-64,WINDOWS8-64)
     */
	@TableField("operation_system")
	private String operationSystem;
    /**
     * 软件品类
     */
	private Integer category;
    /**
     * 软件型号
     */
	private Integer model;
    /**
     * 软件名称
     */
	private String name;
    /**
     * 上传的软件名称
     */
	@TableField("upload_software_name")
	private String uploadSoftwareName;
    /**
     * 安装包路径
     */
	private String path;
    /**
     * 版本
     */
	private String version;
    /**
     * 制造商
     */
	private String manufacturer;
    /**
     * 软件描述
     */
	private String description;
    /**
     * 软件标签
     */
	@TableField("software_label")
	private Integer softwareLabel;
    /**
     * 1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
	@TableField("software_status")
	private Integer softwareStatus;
    /**
     * 0-免费软件，1-商业软件
     */
	private Integer authorization;
    /**
     * 上报来源:1-自动上报，2-人工上报
     */
	@TableField("report_source")
	private Integer reportSource;
    /**
     * 端口
     */
	private String port;
    /**
     * 语言
     */
	private String language;
    /**
     * 发布时间
     */
	@TableField("release_time")
	private Long releaseTime;
    /**
     * 发布者
     */
	private String publisher;
    /**
     * 创建时间
     */
	@TableField("gmt_create")
	private Long gmtCreate;
    /**
     * 备注
     */
	private String memo;
    /**
     * 更新时间
     */
	@TableField("gmt_modified")
	private Long gmtModified;
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
	private Integer status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getOperationSystem() {
		return operationSystem;
	}

	public void setOperationSystem(String operationSystem) {
		this.operationSystem = operationSystem;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploadSoftwareName() {
		return uploadSoftwareName;
	}

	public void setUploadSoftwareName(String uploadSoftwareName) {
		this.uploadSoftwareName = uploadSoftwareName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSoftwareLabel() {
		return softwareLabel;
	}

	public void setSoftwareLabel(Integer softwareLabel) {
		this.softwareLabel = softwareLabel;
	}

	public Integer getSoftwareStatus() {
		return softwareStatus;
	}

	public void setSoftwareStatus(Integer softwareStatus) {
		this.softwareStatus = softwareStatus;
	}

	public Integer getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Integer authorization) {
		this.authorization = authorization;
	}

	public Integer getReportSource() {
		return reportSource;
	}

	public void setReportSource(Integer reportSource) {
		this.reportSource = reportSource;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Long releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
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

	public static final String SIZE = "size";

	public static final String OPERATION_SYSTEM = "operation_system";

	public static final String CATEGORY = "category";

	public static final String MODEL = "model";

	public static final String NAME = "name";

	public static final String UPLOAD_SOFTWARE_NAME = "upload_software_name";

	public static final String PATH = "path";

	public static final String VERSION = "version";

	public static final String MANUFACTURER = "manufacturer";

	public static final String DESCRIPTION = "description";

	public static final String SOFTWARE_LABEL = "software_label";

	public static final String SOFTWARE_STATUS = "software_status";

	public static final String AUTHORIZATION = "authorization";

	public static final String REPORT_SOURCE = "report_source";

	public static final String PORT = "port";

	public static final String LANGUAGE = "language";

	public static final String RELEASE_TIME = "release_time";

	public static final String PUBLISHER = "publisher";

	public static final String GMT_CREATE = "gmt_create";

	public static final String MEMO = "memo";

	public static final String GMT_MODIFIED = "gmt_modified";

	public static final String CREATE_USER = "create_user";

	public static final String MODIFY_USER = "modify_user";

	public static final String STATUS = "status";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AssetSoftware{" +
			"id=" + id +
			", size=" + size +
			", operationSystem=" + operationSystem +
			", category=" + category +
			", model=" + model +
			", name=" + name +
			", uploadSoftwareName=" + uploadSoftwareName +
			", path=" + path +
			", version=" + version +
			", manufacturer=" + manufacturer +
			", description=" + description +
			", softwareLabel=" + softwareLabel +
			", softwareStatus=" + softwareStatus +
			", authorization=" + authorization +
			", reportSource=" + reportSource +
			", port=" + port +
			", language=" + language +
			", releaseTime=" + releaseTime +
			", publisher=" + publisher +
			", gmtCreate=" + gmtCreate +
			", memo=" + memo +
			", gmtModified=" + gmtModified +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", status=" + status +
			"}";
	}
}
