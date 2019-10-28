package com.antiy.asset.manage.builder.on.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;

import java.util.List;

/**
 * 线上资产拟退役流程
 */
public class OnWaitRetireBuilder extends Builder {
    @Override
    public void builder() {
        List<AssetStatusDetail> res = next(new OnInNetBuilder(), AssetChangeDetailEnum.OPERATION_NET_IN, true, true,"待退役资产。");
    products.setProducts(res);
    }
}
