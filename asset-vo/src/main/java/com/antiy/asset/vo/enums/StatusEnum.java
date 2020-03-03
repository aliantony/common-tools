package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 通用状态枚举
 *
 * @author zhangyajun
 * @since 2020/3/3
 */
public enum StatusEnum {
                        ENABLE("1", "启用"),

                        FOBIDDEN("2", "禁用");

    public static StatusEnum getEnumByCode(String code) {
        return Arrays.stream(StatusEnum.values()).filter(v -> v.getCode().equals(code)).collect(Collectors.toList())
            .get(0);
    }

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
