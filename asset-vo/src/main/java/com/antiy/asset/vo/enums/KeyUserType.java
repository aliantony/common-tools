package com.antiy.asset.vo.enums;

import com.antiy.common.exception.BusinessException;

/**
 * @author chenchaowu
 */

public enum KeyUserType {
    KEY_EQUIPMENT(1, "设备"),
    KEY_USER(2, "用户");

    private Integer status;
    private String name;

    KeyUserType(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String getEnumName(Integer status){
        KeyUserType[] enums = values();
        for (KeyUserType keyUserType : enums){
            if (keyUserType.getStatus().equals(status)){
                return keyUserType.getName();
            }
        }
        return null;
//        throw new BusinessException("类型不存在!");
    }

    public static Integer getEnumStatus(String name){
        KeyUserType[] enums = values();
        for (KeyUserType keyUserType : enums){
            if (keyUserType.getStatus().equals(name)){
                return keyUserType.getStatus();
            }
        }
        return null;
//        throw new BusinessException("类型不存在!");
    }

    public static KeyUserType getEnum(Integer status){
        KeyUserType[] enums = values();
        for (KeyUserType keyUserType : enums){
            if (keyUserType.getStatus().equals(status)){
                return keyUserType;
            }
        }
        return null;
//        throw new BusinessException("类型不存在!");
    }

    public static KeyUserType getEnum(String name){
        KeyUserType[] enums = values();
        for (KeyUserType keyUserType : enums){
            if (keyUserType.getStatus().equals(name)){
                return keyUserType;
            }
        }
        return null;
//        throw new BusinessException("类型不存在!");
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
