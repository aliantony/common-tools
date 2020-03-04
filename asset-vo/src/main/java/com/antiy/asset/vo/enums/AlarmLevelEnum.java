package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 通用告警枚举
 *
 * @author zhangyajun
 * @since 2020/3/3
 */
public enum AlarmLevelEnum {
                            EMERGENCY("1", "紧急"),

                            IMPORTANT("2", "重要"), PROMPT("3", "提示");

    public static AlarmLevelEnum getEnumByCode(String code) {
        return Arrays.stream(AlarmLevelEnum.values()).filter(v -> v.getCode().equals(code)).collect(Collectors.toList())
            .get(0);
    }

    AlarmLevelEnum(String code, String name) {
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
