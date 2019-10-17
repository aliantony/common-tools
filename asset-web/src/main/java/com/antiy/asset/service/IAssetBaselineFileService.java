package com.antiy.asset.service;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.response.AssetBaselineFileResponse;
import com.antiy.common.base.ActionResponse;

import java.util.List;

public interface IAssetBaselineFileService {
	/**
	 * 资产-检查流程关联资产是否已上传结果附件
	 * @param ids 资产列表
	 * @param type 资产流程类型
	 * @return 数据
	 */
	ActionResponse<List<AssetBaselineFileResponse>> queryBaselineFileIsExist(List<String> ids, AssetStatusEnum type);


}
