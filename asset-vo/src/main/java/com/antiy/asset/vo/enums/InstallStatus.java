package com.antiy.asset.vo.enums;

public enum InstallStatus {
    ALL(0, "全部"),
    FAIL(1, "失败"),
    SUCCESS(2, "成功"),
    INSTALLING(3, "安装中"),
    UNINSTALLED(4, "未安装"),
    ;

    private Integer code;

    private String status;

    InstallStatus(int code, String status) {
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
