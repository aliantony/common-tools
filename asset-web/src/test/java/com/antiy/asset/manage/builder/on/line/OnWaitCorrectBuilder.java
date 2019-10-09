package com.antiy.asset.manage.builder.on.line;


import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.request.AssetChangeDetailEnum;

import java.util.List;

/**
 * @author zhouye
 * 线上-安全整改 通过
 */
public class OnWaitCorrectBuilder extends Builder {
	@Override
	public void builder() {
		//安全检查不通过
		List<AssetStatusDetail> res = next(new OnWaitCheckBuilder(), AssetChangeDetailEnum.WAIT_CORRECT, false, true);
		products.setProducts(res);
	}
}
