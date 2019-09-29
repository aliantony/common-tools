package com.antiy.asset.vo.enums;

import java.util.Objects;

public enum AssetServiceType {
                              SYSTEM_SERVICE(1, "系统服务"), SOFTWARE_SERVICE(2, "软件服务");
    private Integer code;
    private String  name;

    AssetServiceType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(Integer code) {
        AssetServiceType[] assetServiceTypes = AssetServiceType.values();
        for (AssetServiceType assetServiceType : assetServiceTypes) {
            if (Objects.equals(code, assetServiceType.code)) {
                return assetServiceType.name;
            }
        }
        return null;
    }

}
