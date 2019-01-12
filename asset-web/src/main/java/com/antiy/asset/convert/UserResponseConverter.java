package com.antiy.asset.convert;

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
public class UserResponseConverter extends BaseConverter<AssetUser, SelectResponse> {
    @Override
    protected void convert(AssetUser assetUser, SelectResponse selectResponse) {
        super.convert(assetUser, selectResponse);
        selectResponse.setId(assetUser.getId().toString());
        selectResponse.setValue(assetUser.getName());
    }
}
