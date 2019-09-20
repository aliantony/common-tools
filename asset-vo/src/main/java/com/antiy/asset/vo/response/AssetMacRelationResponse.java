package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

/**
 * <p> AssetMacRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMacRelationResponse extends BaseResponse {
    private String mac;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}