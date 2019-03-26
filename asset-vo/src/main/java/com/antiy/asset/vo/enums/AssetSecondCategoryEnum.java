package com.antiy.asset.vo.enums;

public enum AssetSecondCategoryEnum {
                                     COMPUTE_DEVICE("compute_device",
                                                    "计算设备"), STORAGE_DEVICE("storage_device",
                                                                            "存储设备"), OTHER_DEVICE("other_device",
                                                                                                  "其它设备"), SAFETY_DEVICE("safety_device",
                                                                                                                         "安全设备"), NETWORK_DEVICE("network_device",
                                                                                                                                                 "网络设备");

    AssetSecondCategoryEnum(String code, String msg) {
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
