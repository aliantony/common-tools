package com.antiy.asset.manage.builder.on.line;


import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;

import java.util.List;

/**
 * @author zhouye
 * 入网流程-结果验证通过-> 入网通过
 */
public class OnInNetBuilder extends Builder {
	@Override
	public void builder() {

		List<AssetStatusDetail> res = next(new OnCheckBuilder(),AssetChangeDetailEnum.OPERATION_WAIT_NET, true, true, AssetFlowEnum.NET_IN.getMsg());
		products.setProducts(res);
	}
}
