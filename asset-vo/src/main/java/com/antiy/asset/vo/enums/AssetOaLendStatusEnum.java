package com.antiy.asset.vo.enums;

/**
 * @author shenliang
 * @since 2020-05-29
 */
public enum AssetOaLendStatusEnum {
    /**
     * 同意出借
     */
    YES(1,"同意出借"),
    /**
     * 拒绝出借
     */
    NO(2,"拒绝出借");

    private Integer code;
    private String msg;

    AssetOaLendStatusEnum(Integer code, String msg){
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
    public static AssetOaLendStatusEnum getValueByCode(Integer code) {
        for (AssetOaLendStatusEnum assetOaLendStatusEnum : AssetOaLendStatusEnum.values()) {
            if (assetOaLendStatusEnum.code.equals(code)) {
                return assetOaLendStatusEnum;
            }
        }
        return null;
    }
}
