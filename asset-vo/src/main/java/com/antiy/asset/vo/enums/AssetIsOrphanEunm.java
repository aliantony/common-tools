package com.antiy.asset.vo.enums;

public enum AssetIsOrphanEunm {

    ALLOW(1,"孤岛"),REFUSE(2,"非孤岛");
    private Integer status;
    private String name;

    AssetIsOrphanEunm(Integer status, String name) {
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
