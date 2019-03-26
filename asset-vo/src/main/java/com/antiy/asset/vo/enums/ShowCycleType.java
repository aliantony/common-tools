package com.antiy.asset.vo.enums;

/**
 * 展示周期类型
 *
 * @author zhangyajun
 * @create 2019-03-06 10:30
 **/
public enum ShowCycleType {
                           THIS_WEEK("THIS_WEEK",
                                     "本周"), THIS_MONTH("THIS_MONTH",
                                                       "本月"), THIS_QUARTER("THIS_QUARTER",
                                                                           "本季度"), THIS_YEAR("THIS_YEAR",
                                                                                             "本年"), ASSIGN_TIME("ASSIGN_TIME",
                                                                                                                "指定日期");

    private String code;

    private String message;

    ShowCycleType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
