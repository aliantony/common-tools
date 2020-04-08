package com.antiy.asset.vo.enums;

import com.antiy.common.exception.BusinessException;

/**
 * @author chenchaowu
 */

public enum KeyStatusEnum {
    KEY_NO_RECIPIENTS(0, "未领用"),
    KEY_RECIPIENTS(1, "领用"),
    KEY_FREEZE(2, "冻结");

    private Integer status;
    private String name;

    KeyStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String getEnumName(Integer status){
        KeyStatusEnum[] enums = values();
        for (KeyStatusEnum keyStatusEnum : enums){
            if (keyStatusEnum.getStatus().equals(status)){
                return keyStatusEnum.getName();
            }
        }
        throw new BusinessException("类型不存在!");
    }

    public static Integer getEnumStatus(String name){
        KeyStatusEnum[] enums = values();
        for (KeyStatusEnum keyStatusEnum : enums){
            if (keyStatusEnum.getStatus().equals(name)){
                return keyStatusEnum.getStatus();
            }
        }
        throw new BusinessException("类型不存在!");
    }

    public static KeyStatusEnum getEnum(Integer status){
        KeyStatusEnum[] enums = values();
        for (KeyStatusEnum keyStatusEnum : enums){
            if (keyStatusEnum.getStatus().equals(status)){
                return keyStatusEnum;
            }
        }
        throw new BusinessException("类型不存在!");
    }

    public static KeyStatusEnum getEnum(String name){
        KeyStatusEnum[] enums = values();
        for (KeyStatusEnum keyStatusEnum : enums){
            if (keyStatusEnum.getStatus().equals(name)){
                return keyStatusEnum;
            }
        }
        throw new BusinessException("类型不存在!");
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
