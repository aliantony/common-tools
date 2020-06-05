package com.antiy.asset.vo.enums;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用告警枚举
 *
 * @author zhangyajun
 * @since 2020/3/3
 */
public enum AlarmLevelEnum {
                            EMERGENCY("1", "紧急"),

                            SECONDARY("2", "次要"), MPORTANT("3", "重要"), PROMPT("4", "提示");

    public static AlarmLevelEnum getEnumByCode(String code) {
        return Arrays.stream(AlarmLevelEnum.values()).filter(v -> v.getCode().equals(code)).collect(Collectors.toList())
            .get(0);
    }
    public static String getCodeByMsg(String code){
        if(StringUtils.isBlank(code)) {
            return  null;
        }
        List<String> collect = Arrays.stream(AlarmLevelEnum.values()).filter(v -> v.getCode().equals(code)).map(t -> t.getName()).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(collect)) {
            return  collect.get(0);
        }
        return null;
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
