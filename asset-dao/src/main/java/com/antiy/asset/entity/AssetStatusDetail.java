package com.antiy.asset.entity;


/**
 * @author zhouye
 * 资产状态动态详细描述
 */
public class AssetStatusDetail extends AssetStatusNote {

	/**
	 * 操作人id
	 */
	private Integer operateUserId;
	/**
	 * 操作人姓名
	 */
	private String operateUserName;
	/**
	 * 操作结果 通过-1 不通过-0
	 */
	private String processResult;

	/**
	 * 创建时间
	 */
	private Long gmtCreate;



	public Integer getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(Integer operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}



	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	@Override
	public String toString() {
		return super.toString()+"AssetStatusDetail{" +
				"operateUserId=" + operateUserId +
				", operateUserName='" + operateUserName + '\'' +
				", processResult='" + processResult + '\'' +
				", gmtCreate=" + gmtCreate +
				'}';
	}
}
