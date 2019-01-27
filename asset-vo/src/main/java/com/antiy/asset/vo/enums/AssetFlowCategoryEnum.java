package com.antiy.asset.vo.enums;

/**
 * 硬件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetFlowCategoryEnum {
                                   HARDWARE_REGISTER("HARDWARE_REGISTER",
                                                  "硬件资产登记流程"), SOFTWARE_REGISTER("SOFTWARE_REGISTER",
                                                                            "软件资产登记流程"), HARDWARE_CHANGE("HARDWARE_CHANGE",
                                                                                                    "硬件资产变更流程"),HARDWARE_RETIRE("HARDWARE_RETIRE",
            "硬件资产退役流程");

    AssetFlowCategoryEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private String code;

    // msg
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
