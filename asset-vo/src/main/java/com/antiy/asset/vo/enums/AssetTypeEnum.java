package com.antiy.asset.vo.enums;

import java.util.Objects;

/**
 * @Author: lvliang
 * @Date: 2019/7/25 15:10
 */
public enum AssetTypeEnum {
    HARD(1, "h"), SOFT(2, "a"), OS(3, "o"), ALL(4, "all");
    private Integer code;
    private String name;

    AssetTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        AssetTypeEnum[] assetTypeEnums = AssetTypeEnum.values();
        for (AssetTypeEnum assetTypeEnum : assetTypeEnums) {
            if (Objects.equals(code, assetTypeEnum.code)) {
                return assetTypeEnum.name;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
