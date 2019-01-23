package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetUser;
import org.springframework.stereotype.Component;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.BaseConverter;

@Component
public class UserSelectResponseConverter extends BaseConverter<AssetUser, SelectResponse> {
    @Override
    protected void convert(AssetUser assetUser, SelectResponse selectResponse) {
        selectResponse.setId(assetUser.getId() + "");
        selectResponse.setValue(assetUser.getName());
    }
}
