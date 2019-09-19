package com.antiy.asset.vo.response;

/**
 * 上一步操作信息
 */
public class AssetPreStatusInfoResponse {
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

	@Override
	public String toString() {
		return "AssetPreStatusInfoResponse{" +
				"note='" + note + '\'' +
				", fileInfo='" + fileInfo + '\'' +
				'}';
	}
}
