package com.antiy.asset.manage.builder.under.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.manage.builder.RegisterBuilder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;

import java.util.List;

/**
 * @author zhouye
 * 线下-模板实施通过
 */
public class UnderTemplateImplBuilder extends Builder {
	@Override
	public void builder() {
		List<AssetStatusDetail> res = next(new RegisterBuilder(), AssetChangeDetailEnum.WAIT_TEMPLATE_IMPL, true, true);
		products.setProducts(res);
	}
}
