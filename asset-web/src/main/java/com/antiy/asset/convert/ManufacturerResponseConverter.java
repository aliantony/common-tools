package com.antiy.asset.convert;

import org.springframework.stereotype.Component;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;

/**
 * 对象转换器
 *
 * @author zhangyajun
 * @create 2019-01-12 13:21
 **/
@Component
public class ManufacturerResponseConverter extends BaseConverter<Asset, SelectResponse> {
    @Override
    protected void convert(Asset asset, SelectResponse selectResponse) {
        super.convert(asset, selectResponse);
        selectResponse.setValue(asset.getManufacturer());
    }
}
