package com.antiy.asset.vo.response;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;

/**
 * @author zhouye
 * 资产-检查-结果验证流程关联资产是否已上传结果附件
 */
public class AssetBaselineFileResponse extends BasicRequest {
	@Encode
	private String assetId;
	private boolean result;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "AssetBaselineFileResponse{" +
				"assetId='" + assetId + '\'' +
				", result=" + result +
				'}';
	}
}
