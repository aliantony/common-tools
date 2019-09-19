package com.antiy.asset.entity;

import com.antiy.asset.vo.enums.AssetChangeDetailEnum;

/**
 * @author zhouye
 * 资产状态动态详细描述
 */
public class AssetStatusDetail extends AssetStatusNote {
	/**
	 * 当前状态
	 */
	private AssetChangeDetailEnum originStatus;
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
	 * 文件json
	 */
	private String fileInfo;
	/**
	 * 创建时间
	 */
	private Long gmtCreate;

	public AssetChangeDetailEnum getOriginStatus() {
		return originStatus;
	}

	public void setOriginStatus(AssetChangeDetailEnum originStatus) {
		this.originStatus = originStatus;
	}

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

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
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
		return "AssetStatusDetail{" +
				"originStatus=" + originStatus +
				", operateUserId=" + operateUserId +
				", operateUserName='" + operateUserName + '\'' +
				", processResult='" + processResult + '\'' +
				", fileInfo='" + fileInfo + '\'' +
				", gmtCreate=" + gmtCreate +
				'}';
	}
}
