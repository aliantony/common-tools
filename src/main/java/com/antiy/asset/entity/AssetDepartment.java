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
 * 资产部门信息
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_department")
public class AssetDepartment extends Model<AssetDepartment> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 部门名
     */
	private String name;
    /**
     * 负责人
     */
	@TableField("responsible_user")
	private String responsibleUser;
    /**
     * 联系电话
     */
	private String telephone;
    /**
     * 上级部门
     */
	@TableField("parent_id")
	private Integer parentId;
    /**
     * 创建时间
     */
	@TableField("gmt_create")
	private Long gmtCreate;
    /**
     * 修改时间
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

	public String getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public static final String RESPONSIBLE_USER = "responsible_user";

	public static final String TELEPHONE = "telephone";

	public static final String PARENT_ID = "parent_id";

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
		return "AssetDepartment{" +
			"id=" + id +
			", name=" + name +
			", responsibleUser=" + responsibleUser +
			", telephone=" + telephone +
			", parentId=" + parentId +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", memo=" + memo +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", status=" + status +
			"}";
	}
}
