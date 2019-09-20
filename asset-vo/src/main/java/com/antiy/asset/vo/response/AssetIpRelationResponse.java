package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

/**
 * <p> AssetIpRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetIpRelationResponse extends BaseResponse {
    /**
     * IP
     */
    private String  ip;
    /**
     * 网口
     */
    private Integer net;

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AssetIpMacResponse{" + "ip='" + ip + '\'' + ", net=" + net + '}';
    }
}