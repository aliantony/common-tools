package com.antiy.asset.convert;

import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.request.AssetGroupRequest;
import org.springframework.stereotype.Component;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;

/**
 * request对象转换器
 *
 * @author zhangyajun
 * @create 2019-01-12 13:21
 **/
@Component
public class AssetGroupRequestConverter extends BaseConverter<AssetGroupRequest, AssetGroup> {
    @Override
    protected void convert(AssetGroupRequest request, AssetGroup assetGroup) {
        super.convert(request, assetGroup);
        assetGroup.setId(DataTypeUtils.stringToInteger(request.getId()));
    }
}
