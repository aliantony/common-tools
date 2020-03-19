package com.antiy.asset.vo.enums;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用状态枚举
 *
 * @author zhangyajun
 * @since 2020/3/3
 */
public enum StatusEnum {
                        ENABLE("1", "启用"),

                        FOBIDDEN("0", "禁用");

    public static StatusEnum getEnumByCode(String code) {
        return Arrays.stream(StatusEnum.values()).filter(v -> v.getCode().equals(code)).collect(Collectors.toList())
            .get(0);
    }

    public static String getCodeByMsg(String code){
        if(StringUtils.isBlank(code))
            return  null;
        List<String> collect = Arrays.stream(StatusEnum.values()).filter(v -> v.getCode().equals(code)).map(t -> t.getName()).collect(Collectors.toList());
       if(CollectionUtils.isNotEmpty(collect))
           return  collect.get(0);
        return null;
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
