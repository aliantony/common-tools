package com.antiy.asset.entity;

import javax.validation.constraints.NotNull;

import com.antiy.asset.base.BaseEntity;


/**
 * 
 * @ClassName Event
 * @Description 众包事件系统-事件类型定义表
 * @author chenxinchong
 * @Date 2018年1月17日 上午10:14:39
 * @version 1.0.0
 */
public class Event extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
     * 事件名称
     */
	@NotNull(message="事件名称不能为空")
    private String name;

    /**
     * 赏金金额
     */
	@NotNull(message="赏金金额不能为空")
    private Integer reward;

    /**
     * 抢单方式(1:众包平台抢单,2:指派特定人员)
     */
	@NotNull(message="抢单方式不能为空")
    private Byte processMethod;

    /**
     * 事件难度星级,来自zb_event_star.id
     */
	@NotNull(message="事件难度星级不能为空")
    private Integer idStars;
	
	/**
     * 积分/经验值
     */
    @NotNull(message="经验值不能为空")
    private Integer integral;
	
    /**
     * 期望-事件名称
     */
	private String editName;
	
	/**
	 * 期望-赏金金额
	 */
	private Integer editReward;
	
	/**
	 * 期望-抢单方式(1:众包平台抢单,2:指派特定人员
	 */
	private Byte editProcessMethod;
	
	/**
	 * 期望-事件难度星级,来自zb_event_star.id
	 */
	private Integer editIdStars;
	
	/**
	 * 期望-积分/经验值
	 */
	private Integer editIntegral;
	
    /**
     * 审核状态(0:审核中,1:审核通过,2:审核失败)
     */
    private Byte auditStatus;

    /**
     * 审核通过时间
     */
    private Integer auditTime;

    /**
     * 创建人id,来自user.openid
     */
    private String creatorId;

    /**
     * 创建人姓名,来自user.user_name
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 更新人id,来自user.openid
     */
    private String updateUserId;

    /**
     * 更新人姓名,来自user.user_name
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private Integer updateTime;

    /**
     * 是否删除(0:否,1:是)
     */
    private Byte isDelete;
    
    /**
     * 事件开启状态(0:未开启，1:开启)
     */
    private Byte isActive;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public Byte getProcessMethod() {
		return processMethod;
	}

	public void setProcessMethod(Byte processMethod) {
		this.processMethod = processMethod;
	}

	public Integer getIdStars() {
		return idStars;
	}

	public void setIdStars(Integer idStars) {
		this.idStars = idStars;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getEditName() {
		return editName;
	}

	public void setEditName(String editName) {
		this.editName = editName;
	}

	public Integer getEditReward() {
		return editReward;
	}

	public void setEditReward(Integer editReward) {
		this.editReward = editReward;
	}

	public Byte getEditProcessMethod() {
		return editProcessMethod;
	}

	public void setEditProcessMethod(Byte editProcessMethod) {
		this.editProcessMethod = editProcessMethod;
	}

	public Integer getEditIdStars() {
		return editIdStars;
	}

	public void setEditIdStars(Integer editIdStars) {
		this.editIdStars = editIdStars;
	}

	public Integer getEditIntegral() {
		return editIntegral;
	}

	public void setEditIntegral(Integer editIntegral) {
		this.editIntegral = editIntegral;
	}

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public Byte getIsActive() {
		return isActive;
	}

	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Event [name=" + name + ", reward=" + reward
				+ ", processMethod=" + processMethod + ", idStars=" + idStars
				+ ", integral=" + integral + ", editName=" + editName
				+ ", editReward=" + editReward + ", editProcessMethod="
				+ editProcessMethod + ", editIdStars=" + editIdStars
				+ ", editIntegral=" + editIntegral + ", auditStatus="
				+ auditStatus + ", auditTime=" + auditTime + ", creatorId="
				+ creatorId + ", creatorName=" + creatorName + ", createTime="
				+ createTime + ", updateUserId=" + updateUserId
				+ ", updateUserName=" + updateUserName + ", updateTime="
				+ updateTime + ", isDelete=" + isDelete + ", isActive="
				+ isActive + "]";
	}

}