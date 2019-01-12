package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetGroup;
import org.springframework.stereotype.Component;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;

/**
 * 对象转换器
 *
 * @author zhangyajun
 * @create 2019-01-12 13:21
 **/
@Component
public class AssetGroupResponseConverter extends BaseConverter<AssetGroup, SelectResponse> {
    @Override
    protected void convert(AssetGroup assetGroup, SelectResponse selectResponse) {
        super.convert(assetGroup, selectResponse);
        selectResponse.setId(assetGroup.getId().toString());
        selectResponse.setValue(assetGroup.getName());
    }
}
