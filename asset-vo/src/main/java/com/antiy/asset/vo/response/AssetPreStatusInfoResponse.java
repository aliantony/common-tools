package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

/**
 * 上一步操作信息
 */
public class AssetPreStatusInfoResponse {
	public AssetPreStatusInfoResponse() {
	}

	public AssetPreStatusInfoResponse(String assetId, String note, String fileInfo) {
		this.assetId = assetId;
		this.note = note;
		this.fileInfo = fileInfo;
	}

	/**
	 * 资产id
	 */
	@Encode
	private String assetId;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 文件信息
	 */
	private String fileInfo;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Override
	public String toString() {
		return "AssetPreStatusInfoResponse{" +
				"assetId='" + assetId + '\'' +
				", note='" + note + '\'' +
				", fileInfo='" + fileInfo + '\'' +
				'}';
	}
}
