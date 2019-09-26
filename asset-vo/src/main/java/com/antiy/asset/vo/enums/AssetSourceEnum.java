package com.antiy.asset.vo.enums;

/**
 * 资产来源枚举
 */
public enum AssetSourceEnum {
                             ASSET_DETECTION(1, "资产探测"), MANUAL_REGISTRATION(2, "人工登记"), AGENCY_REPORT(3, "代理上报");
    private Integer code;
    private String  name;

    public static String getNameByCode(Integer code) {
        for (AssetSourceEnum assetSourceEnum : AssetSourceEnum.values()) {
            if (assetSourceEnum.code.equals(code)) {
                return assetSourceEnum.name;
            }
        }
        return "";
    }

    AssetSourceEnum(Integer code, String name) {
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
