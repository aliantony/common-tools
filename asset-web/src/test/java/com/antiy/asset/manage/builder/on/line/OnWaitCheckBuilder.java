package com.antiy.asset.manage.builder.on.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.manage.builder.RegisterBuilder;
import com.antiy.asset.vo.request.AssetChangeDetailEnum;

import java.util.List;

/***
 * 线上登记资产-安全检查成功
 */
public class OnWaitCheckBuilder extends Builder {

	@Override
	public void builder() {
		List<AssetStatusDetail> res = next(new RegisterBuilder(),AssetChangeDetailEnum.OPERATION_WAIT_CHECK,true,true);
		products.setProducts(res);

	}
}
