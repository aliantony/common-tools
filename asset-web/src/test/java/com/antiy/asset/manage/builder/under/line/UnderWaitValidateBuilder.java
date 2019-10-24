package com.antiy.asset.manage.builder.under.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;

import java.util.List;

/**
 * 线下-结果验证通过
 */
public class UnderWaitValidateBuilder extends Builder {
	@Override
	public void builder() {
		List<AssetStatusDetail> res = next(new UnderTemplateImplBuilder(),AssetChangeDetailEnum.OPERATION_WAIT_VALIDATE, true, true,"结果验证。验证情况：");
		products.setProducts(res);
	}
}
