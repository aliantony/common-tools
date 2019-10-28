package com.antiy.asset.manage.builder.on.line;


import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;

import java.util.List;

/**
 * @author zhouye
 * 线上-安全整改 通过
 */
public class OnCorrectBuilder extends Builder {
	@Override
	public void builder() {
		//安全检查不通过
		List<AssetStatusDetail> res = next(new OnCheckBuilder(), AssetChangeDetailEnum.WAIT_CORRECT, false, true, AssetFlowEnum.CORRECT.getMsg());
		products.setProducts(res);
	}
}
