package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class GroupSelectResponseConverter extends BaseConverter<AssetGroup, SelectResponse> {
    @Override
    protected void convert(AssetGroup assetGroup, SelectResponse selectResponse) {
        selectResponse.setId(assetGroup.getId() + "");
        selectResponse.setValue(assetGroup.getName());
    }
}
