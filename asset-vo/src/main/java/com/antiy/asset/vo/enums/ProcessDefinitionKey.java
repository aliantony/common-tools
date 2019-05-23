package com.antiy.asset.vo.enums;

/**
 * @author zhangyajun
 * @create 2019-05-22 14:25
 **/
public enum ProcessDefinitionKey {
                                  HARDWARE_ADMITTANCE("hardwareAdmittance",
                                                      "登记或导入硬件"), HARDWARE_RETIRE("hardwareRetire", "硬件退役");

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    private String key;
    private String msg;

    ProcessDefinitionKey(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
}
