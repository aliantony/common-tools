package com.antiy.asset.vo.enums;

/**
 * @author zhangyajun
 * @create 2020-03-03 14:12
 **/
public enum TimeEnum {
                      HOUR("HOUR", "时"), DAY("DAY", "天");

    TimeEnum(String name, String code) {
        this.name = name;
        this.msg = code;
    }

    private String name;
    private String msg;

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }
}
