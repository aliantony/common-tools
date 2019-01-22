package com.antiy.asset.vo.enums;

/**
 * 软件流程枚举
 *
 * @Auther: zhangyajun
 * @Date: 2019/1/14 11:36
 * @Description:
 */
public enum SoftwareSchemeTypeEnum implements CodeEnum {
                                                        SOFT_REGISTER(1,
                                                                      "软件登记"), SOFT_UPDATE(2,
                                                                                           "软件变更"), RULE_ANALYZE(3,
                                                                                                                 "基准分析"), AUTO_INSTALL(3,
                                                                                                                                       "自动安装"), MANUAL_INSTALL(4,
                                                                                                                                                               "人工安装");

    SoftwareSchemeTypeEnum(Integer code, String msg) {
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
