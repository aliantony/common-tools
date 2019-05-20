package com.antiy.asset.vo.enums;

/**
 * 资产流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetFlowCategoryEnum {
    HARDWARE_REGISTER("HARDWARE_REGISTER", "硬件资产登记流程"),
                                   HARDWARE_NO_REGISTER("HARDWARE_REGISTER",
                                                        "硬件资产登记流程"),
    SOFTWARE_REGISTER("SOFTWARE_REGISTER","软件资产登记流程"),
    HARDWARE_CHANGE("HARDWARE_CHANGE","硬件资产变更流程"),
    HARDWARE_RETIRE("HARDWARE_RETIRE","硬件资产退役流程"),
    SOFTWARE_RETIRE("SOFTWARE_RETIRE","软件资产退役流程"),
    HARDWARE_IMPL_RETIRE("HARDWARE_COMPLETE_RETIRE","硬件资产实施退役流程"),
    SOFTWARE_IMPL_RETIRE("SOFTWARE_IMPL_RETIRE","软件资产实施退役流程"),
    SOFTWARE_IMPL_UNINSTALL("SOFTWARE_IMPL_UNINSTALL","软件资产实施卸载流程"),
    SOFTWARE_UNINSTALL("SOFTWARE_UNINSTALL","软件卸载流程");

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
