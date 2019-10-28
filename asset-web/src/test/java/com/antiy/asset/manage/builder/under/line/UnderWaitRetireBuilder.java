package com.antiy.asset.manage.builder.under.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.manage.builder.on.line.OnInNetBuilder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;

import java.util.List;

/**
 * 线下资产拟退役流程
 */
public class UnderWaitRetireBuilder extends Builder {
    @Override
    public void builder() {
        List<AssetStatusDetail> res = next(new UnderInNetBuilder(), AssetChangeDetailEnum.OPERATION_NET_IN, true, true, AssetFlowEnum.TO_WAIT_RETIRE.getMsg());
    products.setProducts(res);
    }
}
