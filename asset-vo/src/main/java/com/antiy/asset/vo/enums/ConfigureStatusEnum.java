package com.antiy.asset.vo.enums;

/**
 * 配置状态
 * @author lvliang
 */
public enum ConfigureStatusEnum {
                                 NOCONFIGURE(1, "未配置"), CONFIGURING(2, "配置中"), CONFIGURED(3, "已配置");
    private Integer code;
    private String  name;

    public Integer getCode() {
        return code;
    }

    ConfigureStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
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

    public static ConfigureStatusEnum getConfigureStatusByCode(Integer code) {
        if (code != null) {
            for (ConfigureStatusEnum configureStatusEnum : ConfigureStatusEnum.values()) {
                if (configureStatusEnum.getCode().equals(code)) {
                    return configureStatusEnum;
                }
            }
        }
        return null;
    }

}
