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
 * 内存表
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-24
 */
@TableName("asset_memory")
public class AssetMemory extends Model<AssetMemory> {

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
     * 内存容量
     */
	private Integer capacity;
    /**
     * 内存主频(MHz)
     */
	private Integer frequency;
    /**
     * 插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM
     */
	@TableField("slot_type")
	private Integer slotType;
    /**
     * 是否带散热片:0-不带，1-带
     */
	@TableField("is_heatsink")
	private Boolean isHeatsink;
    /**
     * 针脚数
     */
	private Integer stitch;
    /**
     * 购买日期
     */
	@TableField("buy_date")
	private Long buyDate;
    /**
     * 保修期
     */
	@TableField("warranty_date")
	private Long warrantyDate;
    /**
     * 联系电话
     */
	private String telephone;
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

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getSlotType() {
		return slotType;
	}

	public void setSlotType(Integer slotType) {
		this.slotType = slotType;
	}

	public Boolean getHeatsink() {
		return isHeatsink;
	}

	public void setHeatsink(Boolean isHeatsink) {
		this.isHeatsink = isHeatsink;
	}

	public Integer getStitch() {
		return stitch;
	}

	public void setStitch(Integer stitch) {
		this.stitch = stitch;
	}

	public Long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Long buyDate) {
		this.buyDate = buyDate;
	}

	public Long getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Long warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public static final String CAPACITY = "capacity";

	public static final String FREQUENCY = "frequency";

	public static final String SLOT_TYPE = "slot_type";

	public static final String IS_HEATSINK = "is_heatsink";

	public static final String STITCH = "stitch";

	public static final String BUY_DATE = "buy_date";

	public static final String WARRANTY_DATE = "warranty_date";

	public static final String TELEPHONE = "telephone";

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
		return "AssetMemory{" +
			"id=" + id +
			", assetId=" + assetId +
			", capacity=" + capacity +
			", frequency=" + frequency +
			", slotType=" + slotType +
			", isHeatsink=" + isHeatsink +
			", stitch=" + stitch +
			", buyDate=" + buyDate +
			", warrantyDate=" + warrantyDate +
			", telephone=" + telephone +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", memo=" + memo +
			", createUser=" + createUser +
			", modifyUser=" + modifyUser +
			", status=" + status +
			"}";
	}
}
