package com.antiy.asset.manage.builder.on.line;

import com.antiy.asset.entity.AssetStatusDetail;
import com.antiy.asset.manage.builder.Builder;
import com.antiy.asset.manage.builder.under.line.UnderValidateBuilder;
import com.antiy.asset.vo.enums.AssetChangeDetailEnum;

import java.util.List;
/**
 * @author zhouye
 * 线上资产-退役流程
 */
public class OnRetireBuilder extends Builder {
    @Override
    public void builder() {
        List<AssetStatusDetail> res = next(new UnderValidateBuilder(), AssetChangeDetailEnum.OPERATION_WAIT_RETIRE, true, true,"实施退役。退役情况：");
        products.setProducts(res);
    }
}
