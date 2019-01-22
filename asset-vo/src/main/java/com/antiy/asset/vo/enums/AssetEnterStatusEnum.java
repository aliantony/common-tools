package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangyajun
 * @Date: 2019/1/20 23:20
 * @Description:
 */
public enum AssetEnterStatusEnum {
    WATI_SETTING(1, "待设置"),
    ENTERED(2, "已允许"),
    NO_ENTER(3, "已禁止");

    AssetEnterStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String msg;

    /**
     * 通过code获取资产
     * @param code
     * @return
     */
    public static AssetEnterStatusEnum getAssetByCode(Integer code){
        if(code != null){
            for (AssetEnterStatusEnum softwareType : AssetEnterStatusEnum.values()){
                if(softwareType.getCode().equals(code)){
                    return softwareType;
                }
            }
        }
        return null;
    }

    /**
     * 通过code获取枚举
     *
     * @param name name
     * @return
     */
    public static AssetEnterStatusEnum getAssetByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (AssetEnterStatusEnum softwareType : AssetEnterStatusEnum.values()) {
                if (softwareType.name().equals(name)) {
                    return softwareType;
                }
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
