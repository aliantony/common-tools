package com.antiy.asset.vo.enums;

public enum AssetIsBorrowEnum {

    ALLOW(1,"可借用"),REFUSE(2,"不可借用");
    private Integer status;
    private String name;

    AssetIsBorrowEnum(Integer status, String name) {
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
