package com.antiy.asset.vo.enums;

/**
 * @author zhangyajun
 * @create 2019-01-15 16:13
 **/
public enum ProcessTypeEnum implements CodeEnum {
                                                 YES(1, "同意"), NO(0, "拒绝");
    // code
    private Integer code;

    // msg
    private String  msg;

    ProcessTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
