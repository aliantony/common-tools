package com.antiy.asset.entity;

/**
 * @author zhouye
 * 资产状态上一步变更备注
 */
public class AssetStatusNote {


	private String id;
	private String assetId;
	private String note;


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
	@Override
	public String toString() {
		return "AssetStatusNote{" +
				"id='" + id + '\'' +
				", assetId='" + assetId + '\'' +
				", note='" + note + '\'' +
				'}';
	}
}
