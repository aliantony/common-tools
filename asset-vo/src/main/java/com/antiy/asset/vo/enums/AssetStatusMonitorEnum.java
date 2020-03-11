package com.antiy.asset.vo.enums;

public enum AssetStatusMonitorEnum {

    PROCESS(1, "进程"), SOFT(2, "软件"), SERVICE(3, "服务"), BASE_PERFORMANCE(4, "性能");
    private int code;
    private String name;

    AssetStatusMonitorEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
