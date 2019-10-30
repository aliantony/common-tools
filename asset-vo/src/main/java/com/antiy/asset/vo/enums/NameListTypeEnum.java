package com.antiy.asset.vo.enums;

/**
 * 基准模板名单类型
 * @Author: lvliang
 * @Date: 2019/10/16 10:30
 */
public enum NameListTypeEnum {
                              NO(0, "无"), BLACK(1, "黑名单"), WHITE(2, "白名单");

    private Integer code;
    private String  name;

    NameListTypeEnum(Integer code, String name) {
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
