package com.antiy.asset.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusLogResponse extends AssetPreStatusInfoResponse {
	private String describe;
	private Long gmtCreate;

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "StatusLogResponse{" +
				"describe='" + describe + '\'' +
				", gmtCreate=" + gmtCreate +
				'}';
	}
}
