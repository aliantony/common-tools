package com.antiy.asset.vo.enums;

/**
 * @author chenchaowu
 */

public enum KeyUserType {
    KEY_RECIPIENTS(1, "设备"),
    KEY_FREEZE(2, "用户");

    private Integer status;
    private String name;

    KeyUserType(Integer status, String name) {
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
