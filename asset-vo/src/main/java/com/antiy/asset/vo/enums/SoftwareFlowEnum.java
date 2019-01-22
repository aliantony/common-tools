package com.antiy.asset.vo.enums;

/**
 * 软件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum SoftwareFlowEnum implements CodeEnum {
    SOFTWARE_REGISTER("软件资产登记"),
    SOFTWARE_BASELINE_ANALYZE("软件基准分析"),
    SOFTWARE_INSTALL("软件安装");

    SoftwareFlowEnum(String msg) {
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

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
