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
 * 通联关系表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_link_relation")
public class AssetLinkRelation extends Model<AssetLinkRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 资产组主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 通联名称
     */
	private String name;
    /**
     * 通联类型:1光纤，2双绞线
     */
	@TableField("link_type")
	private Integer linkType;
    /**
     * 头节点资产
     */
	@TableField("head_asset")
	private Integer headAsset;
    /**
     * 尾节点资产
     */
	@TableField("tail_asse")
	private Integer tailAsse;
    /**
     * 头节点类型
     */
	@TableField("head_type")
	private Integer headType;
    /**
     * 尾节点类型
     */
	@TableField("tail_type")
	private Integer tailType;
    /**
     * 状态
     */
	@TableField("link_status")
	private Integer linkStatus;
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

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public Integer getHeadAsset() {
		return headAsset;
	}

	public void setHeadAsset(Integer headAsset) {
		this.headAsset = headAsset;
	}

	public Integer getTailAsse() {
		return tailAsse;
	}

	public void setTailAsse(Integer tailAsse) {
		this.tailAsse = tailAsse;
	}

	public Integer getHeadType() {
		return headType;
	}

	public void setHeadType(Integer headType) {
		this.headType = headType;
	}

	public Integer getTailType() {
		return tailType;
	}

	public void setTailType(Integer tailType) {
		this.tailType = tailType;
	}

	public Integer getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
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

	public static final String LINK_TYPE = "link_type";

	public static final String HEAD_ASSET = "head_asset";

	public static final String TAIL_ASSE = "tail_asse";

	public static final String HEAD_TYPE = "head_type";

	public static final String TAIL_TYPE = "tail_type";

	public static final String LINK_STATUS = "link_status";

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
		return "AssetLinkRelation{" +
			"id=" + id +
			", name=" + name +
			", linkType=" + linkType +
			", headAsset=" + headAsset +
			", tailAsse=" + tailAsse +
			", headType=" + headType +
			", tailType=" + tailType +
			", linkStatus=" + linkStatus +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", memo=" + memo +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", status=" + status +
			"}";
	}
}
