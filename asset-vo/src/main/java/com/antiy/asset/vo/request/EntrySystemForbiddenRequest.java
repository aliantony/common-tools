package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/5/20
 */
public class EntrySystemForbiddenRequest extends EntrySystemRequest {

    @ApiModelProperty("描述信息")
    private String msg;
    @ApiModelProperty("终端ip")
    private String ip;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "EntrySystemRequest{" +
                "token='" + super.getToken() + '\'' +
                ", mac='" + super.getMac() + '\'' +
                ", msg='" + msg + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
