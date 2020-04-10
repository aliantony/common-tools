package com.antiy.asset.vo.enums;

public enum AssetNetStatusEnum implements CodeEnum {
                                                    ONLINE(1, "在线"), OFFLINE(2, "离线"), UNKONWN(3, "未知");
    private Integer code;
    private String  msg;

    AssetNetStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static String getMsgByCode(Integer code) {
        for (AssetNetStatusEnum assetNetStatusEnum : AssetNetStatusEnum.values()) {
            if (assetNetStatusEnum.code.equals(code)) {
                return assetNetStatusEnum.msg;
            }
        }
        return "";
    }
}
