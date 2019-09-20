package com.antiy.asset.entity;

/**
 * @author zhouye
 * 资产状态上一步变更备注
 */
public class AssetStatusNote {
	public AssetStatusNote() {
	}

	public AssetStatusNote(String id, String assetId, String note, String fileInfo) {
		this.id = id;
		this.assetId = assetId;
		this.note = note;
		this.fileInfo = fileInfo;
	}

	private String id;
	private String assetId;
	private String note;
	/**
	 * 文件json
	 */
	private String fileInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

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

	@Override
	public String toString() {
		return "AssetStatusNote{" +
				"id='" + id + '\'' +
				", assetId='" + assetId + '\'' +
				", note='" + note + '\'' +
				", fileInfo='" + fileInfo + '\'' +
				'}';
	}
}
