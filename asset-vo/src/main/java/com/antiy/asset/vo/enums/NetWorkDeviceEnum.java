package com.antiy.asset.vo.enums;

public enum NetWorkDeviceEnum {
    Router("Router", "路由器"),Switch("Switch","交换机");
    NetWorkDeviceEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private String code;

    // msg
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
