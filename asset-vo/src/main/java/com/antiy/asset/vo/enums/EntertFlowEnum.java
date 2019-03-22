package com.antiy.asset.vo.enums;

/**
 * 准入流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum EntertFlowEnum implements CodeEnum {
    ENTER_MANAGE(1,"准入管理");

    EntertFlowEnum(Integer code, String msg) {
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
