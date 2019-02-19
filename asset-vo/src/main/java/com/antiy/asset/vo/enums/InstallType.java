package com.antiy.asset.vo.enums;

public enum InstallType {
    MANUAL(1, "人工"),
    AUTOMATIC(2, "自动")
    ;

    private Integer code;

    private String status;

    InstallType(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
