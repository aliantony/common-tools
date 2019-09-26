package com.antiy.asset.vo.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 装机模板状态
 *
 * @author liulusheng
 * @since 2019/9/25
 */
public enum AssetInstallTemplateStatusEnum {
    NOTAUDIT(1, "待审核"),

    REJECT(2, "拒绝"),

    ENABLE(3, "启用"),

    FOBIDDEN(4, "禁用");


    public static AssetInstallTemplateStatusEnum getEnumByCode(Integer code) {
        return Arrays.stream(AssetInstallTemplateStatusEnum.values()).filter(v -> v.getCode() == code).collect(Collectors.toList()).get(0);
    }

    AssetInstallTemplateStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    Integer code;
    String status;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
