package com.antiy.asset.vo.enums;

public enum AssetCategoryEnum {
    COMPUTER(1, "计算设备"),
    NETWORK(2, "网络设备"),
    SAFETY(3, "安全设备"),
    STORAGE(4, "存储设备"),
    OTHER(5, "其他设备");

    private Integer code;
    private String  name;

    AssetCategoryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
