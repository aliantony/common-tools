package com.antiy.asset.vo.enums;

public enum AssetBaseLineEnum {
    SUCCESS("1","配置成功"),
    FALI("2","配置失败"),
    OTHER("3","其他");
    private String code;
    private String msg;

    AssetBaseLineEnum(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

