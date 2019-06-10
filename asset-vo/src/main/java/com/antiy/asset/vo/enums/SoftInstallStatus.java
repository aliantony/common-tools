package com.antiy.asset.vo.enums;

public enum SoftInstallStatus {
                               ALL(0,
                                   "全部"), WAIT_CONFIGURE(1,
                                                         "待配置"), CONFIGURING(2,
                                                                             "配置中"), UNINSTALLED(3,
                                                                                                 "未安装"), FAIL(4,
                                                                                                              "失败"), SUCCESS(5,
                                                                                                                             "成功"), INSTALLING(6,
                                                                                                                                               "安装中");

    private Integer code;

    private String  status;

    SoftInstallStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public static SoftInstallStatus getInstallStatusByCode(Integer code) {
        if (code != null) {
            for (SoftInstallStatus installStatus : SoftInstallStatus.values()) {
                if (installStatus.getCode().equals(code)) {
                    return installStatus;
                }
            }
        }
        return null;
    }
}
