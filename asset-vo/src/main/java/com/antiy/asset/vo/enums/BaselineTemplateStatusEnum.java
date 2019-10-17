package com.antiy.asset.vo.enums;

/**
 * @author liulusheng
 * @since 2019/10/17
 */
public enum BaselineTemplateStatusEnum {
    NONE(0,"无"),

    BLACK_ITEM(1,"黑名单"),

    WHITE_ITEM(2,"白名单");

    private Integer code;
    private String name;

    BaselineTemplateStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
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
