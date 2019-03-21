package com.antiy.asset.vo.enums;

/**
 * 内存类型枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum TransferTypeMemoryEnum implements CodeEnum {
                                                        DDR2(2, "DDR2"), DDR3(3, "DDR3"), DDR4(4, "DDR4");

    TransferTypeMemoryEnum(Integer code, String msg) {
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
