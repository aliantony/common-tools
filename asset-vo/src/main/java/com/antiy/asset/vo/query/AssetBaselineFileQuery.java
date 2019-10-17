package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * '资产-检查-结果验证流程关联资产是否已上传结果附件vo
 */
public class AssetBaselineFileQuery extends BatchQueryRequest implements ObjectValidator {
	@ApiModelProperty("流程类型：待实施-WAIT_TEMPLATE_IMPL 待验证-WAIT_VALIDATE")
	@NotNull(message = "资产状态不能为空")
	private AssetStatusEnum type;

	public AssetStatusEnum getType() {
		return type;
	}

	public void setType(AssetStatusEnum type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AssetBaselineFileQuery{" +
				"type=" + type +
				'}';
	}

	@Override
	public void validate() throws RequestParamValidateException {

	}
}
