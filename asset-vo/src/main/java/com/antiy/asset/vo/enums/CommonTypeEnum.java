package com.antiy.asset.vo.enums;

import java.util.Objects;

/**
 * @Author: zhangyajun
 * @Date: 2019/8/1 8:50
 */
public enum CommonTypeEnum {

    //数据库定义类型：a-应用软件 h-硬件 o-操作系统 m-中间件 d-数据库 f-固件 e-其他
    OS("o","操作系统"),
    SOFT("a","应用软件"),
    DATABASE("d","数据库"),
    MIDDLEWARE("m","中间件"),
    FIRMWARE("f","固件"),
    OTHER("e","其他"),
    HARD("h","硬件")
    ;
    private String code;
    private String name;

    CommonTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        CommonTypeEnum[] commonTypeEnums = CommonTypeEnum.values();
        for (CommonTypeEnum commonTypeEnum : commonTypeEnums) {
            if (Objects.equals(code, commonTypeEnum.code)) {
                return commonTypeEnum.name;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
