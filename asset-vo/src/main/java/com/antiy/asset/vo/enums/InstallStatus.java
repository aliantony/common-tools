package com.antiy.asset.vo.enums;

public enum InstallStatus {

    FAIL(0, "失败"),
    SUCCESS(1, "成功"),
    INSTALLING(2, "安装中"),
    UNINSTALLED(3, "未安装"),
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
