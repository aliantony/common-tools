package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;

/**
 * @Filename: NumberMac Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2019/10/10
 */
public class NumberMac extends BaseRequest {
    private String mac;
    private String number;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
