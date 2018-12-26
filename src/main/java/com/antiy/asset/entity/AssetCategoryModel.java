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
 * 品类型号表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_category_model")
public class AssetCategoryModel extends Model<AssetCategoryModel> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 名称
     */
	private String name;
    /**
     * 类型:1-品类，2-型号
     */
	private Integer type;
    /**
     * 资产类型:1软件，2硬件
     */
	@TableField("asset_type")
	private Integer assetType;
    /**
     * 父ID
     */
	@TableField("parent_id")
	private Integer parentId;
    /**
     * 描述
     */
	private String description;
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
	private Integer status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAssetType() {
		return assetType;
	}

	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public static final String NAME = "name";

	public static final String TYPE = "type";

	public static final String ASSET_TYPE = "asset_type";

	public static final String PARENT_ID = "parent_id";

	public static final String DESCRIPTION = "description";

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
		return "AssetCategoryModel{" +
			"id=" + id +
			", name=" + name +
			", type=" + type +
			", assetType=" + assetType +
			", parentId=" + parentId +
			", description=" + description +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", memo=" + memo +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", status=" + status +
			"}";
	}
}
