package com.antiy.asset.manage.builder.under.line;


import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;

import java.util.List;

/**
 * @author zhouye
 * 入网流程-结果验证通过-> 入网通过
 */
public class UnderInNetBuilder extends Builder {
	@Override
	public void builder() {

		List<AssetStatusDetail> res = next(new UnderValidateBuilder(),AssetChangeDetailEnum.OPERATION_WAIT_NET, true, true, AssetFlowEnum.NET_IN.getMsg());

		products.setProducts(res);
	}
}
