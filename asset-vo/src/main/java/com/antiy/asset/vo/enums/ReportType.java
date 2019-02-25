package com.antiy.asset.vo.enums;

public enum ReportType {
    MANUAL(1, "自动上报"),
    AUTOMATIC(2, "人工上报")
    ;

    private Integer code;

    private String status;

    ReportType(int code, String status) {
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
