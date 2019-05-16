package com.antiy.asset.vo.enums;

/**
 * @author: zhangbing
 * @date: 2019/4/9 13:39
 * @description:
 */
public enum ApiCommandType {
                            vul("vul",
                                "漏洞"), patch("patch",
                                             "补丁"), asset("asset",
                                                          "资产"), baseline("baseline",
                                                                          "基准校验"), patchInstall("patchInstall",
                                                                                                "补丁安装"), vulRepair("vulRepair",
                                                                                                                   "漏洞修复"), patchUnistall("patchUnistall",
                                                                                                                                          "补丁卸载"), softwareInstall("softwareInstall",
                                                                                                                                                                   "软件安装"), softwareUpdate("softwareUpdate",
                                                                                                                                                                                           "软件升级"), softwareUninstall("softwareUninstall",
                                                                                                                                                                                                                      "软件卸载");
    private String code;
    private String msg;

    ApiCommandType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
