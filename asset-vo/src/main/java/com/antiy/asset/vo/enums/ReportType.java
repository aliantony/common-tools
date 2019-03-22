package com.antiy.asset.vo.enums;

public enum ReportType {
    MANUAL(1, "自动上报"),
    AUTOMATIC(2, "人工上报"),
    FREESOFT(0, "免费软件"),
    BUSINESSSOFT(1, "商业软件")
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


    public String getStatus() {
        return status;
    }

}
