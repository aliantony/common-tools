package com.antiy.asset.vo.enums;

/**
 * @author chenchaowu
 */

public enum KeyStatusEnum {
    KEY_NO_RECIPIENTS(0, "未领用"),
    KEY_RECIPIENTS(1, "领用"),
    KEY_FREEZE(2, "冻结");

    private Integer status;
    private String name;

    KeyStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
