package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;

/**
 * <p> AssetIpRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCustomizeRequest extends BaseRequest {

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}