package com.antiy.asset.vo.enums;

/**
 * 重要程度
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetImportanceDegreeEnum implements CodeEnum {
                                                           CORE(1, "核心"), IMPORTANCE(2, "重要"), COMMON(3, "一般");

    AssetImportanceDegreeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String  msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static AssetImportanceDegreeEnum getByCode(Integer code) {
        for (AssetImportanceDegreeEnum assetImportanceDegreeEnum : AssetImportanceDegreeEnum.values()) {
            if (assetImportanceDegreeEnum.getCode().equals(code)) {
                return assetImportanceDegreeEnum;
            }
        }
        return null;
    }

}
