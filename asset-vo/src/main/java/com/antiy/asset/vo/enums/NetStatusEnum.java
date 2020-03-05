package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 通用网络状态枚举
 *
 * @author zhangyajun
 * @since 2020/3/3
 */
public enum NetStatusEnum {
                           ONLINE("1", "在线"), OFFLINE("2", "离线"), UNKNOWN("3", "未知");

    public static NetStatusEnum getEnumByCode(String code) {
        return Arrays.stream(NetStatusEnum.values()).filter(v -> v.getCode().equals(code)).collect(Collectors.toList())
            .get(0);
    }

    NetStatusEnum(String code, String name) {
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
