package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SelectConvert extends BaseConverter<AssetGroup, SelectResponse> {
    @Override
    protected void convert(AssetGroup assetGroup, SelectResponse selectResponse) {
        selectResponse.setValue(assetGroup.getName());
        selectResponse.setId(Objects.toString(assetGroup.getId()));
        super.convert(assetGroup, selectResponse);
    }
}
