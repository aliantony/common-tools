package com.antiy.asset.vo.enums;

public enum ReportFormType {
                            ALL("ALL", "总数统计报表"), NEW("NEW", "新增趋势报表"), TABLE("TABLE", "表格报表");

    private String code;

    private String message;

    ReportFormType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
