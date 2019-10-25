package com.antiy.asset.manage.builder.under.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;
import com.antiy.asset.vo.enums.AssetFlowEnum;

import java.util.List;

/**
 * @author zhouye
 * 线下资产-退役流程
 */
public class UnderRetireBuilder extends Builder {
    @Override
    public void builder() {
        List<AssetStatusDetail> res = next(new UnderValidateBuilder(), AssetChangeDetailEnum.OPERATION_WAIT_RETIRE, true, true, AssetFlowEnum.RETIRE.getMsg());
        products.setProducts(res);
    }
}
