package com.antiy.asset.vo.enums;

/**
 * 硬件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum AssetSchemeTypeEnum implements CodeEnum {
                                                     PERMIT_IMPLEMENTATION(1,
                                                                           "准入实施"), EFFECT_CHECK(2,
                                                                                                 "效果检查"), WAIT_RETIRE_SCHEMA(3,
                                                                                                                             "制定待退役方案"), VALIDATE_RETIRE_RESULT(4,
                                                                                                                                                                "实施退役");

    AssetSchemeTypeEnum(Integer code, String msg) {
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
