package com.antiy.asset.vo.enums;

/**
 * 信息标签
 * @author zhangyajun
 * @create 2019-02-20 10:52
 **/
public enum InfoLabelEnum implements CodeEnum {
    COMMONINFO(1,"通用信息"),
    BUSINESSINFO(2,"业务信息"),
    CPU(3,"CPU"),
    MEMORY(4,"内存"),
    HARDDISK(5,"硬盘"),
    MAINBORAD(6,"主板"),
    NETWORKCARD(7,"网卡"),
    RELATESOFTWARE(8,"关联软件"),
    ;
    // code
    private Integer code;

    // msg
    private String  msg;

    InfoLabelEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
