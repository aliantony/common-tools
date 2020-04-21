package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-04-21
 */
public enum AssetLendStatusEnum {
    /**
     * 出借
     */
    YES(1,"出借"),
    /**
     * 不出借
     */
    NO(2,"不出借");

    private Integer code;
    private String msg;

    AssetLendStatusEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过code获取枚举信息
     *
     * @param code
     * @return
     */
    public static AssetLendStatusEnum getValueByCode(Integer code) {
        for (AssetLendStatusEnum assetLendStatusEnum : AssetLendStatusEnum.values()) {
            if (assetLendStatusEnum.code.equals(code)) {
                return assetLendStatusEnum;
            }
        }
        return null;
    }
}
