package com.antiy.asset.vo.enums;

/**
 * 软件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum SoftwareFlowEnum implements CodeEnum {
    SOFTWARE_REGISTER(SoftwareStatusEnum.WATI_REGSIST.getCode(),"软件资产登记"),
    SOFTWARE_BASELINE_ANALYZE(SoftwareStatusEnum.WAIT_ANALYZE.getCode(),"软件基准分析"),
    SOFTWARE_INSTALL(SoftwareStatusEnum.ALLOW_INSTALL.getCode(),"软件安装");

    SoftwareFlowEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String  msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
