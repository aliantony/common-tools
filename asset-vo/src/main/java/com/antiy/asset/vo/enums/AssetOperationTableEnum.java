package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @auther: zhangbing
 * @date: 2019/1/14 13:10
 * @description:操作流程表类型
 */
public enum AssetOperationTableEnum {
                                     ASSET("ASSET", "硬件资产"), SOFTWARE("SOFTWARE", "软件资产");

    AssetOperationTableEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private String code;

    // msg
    private String msg;

    /**
     * 通过code获取表名
     * @param code
     * @return
     */
    public static AssetOperationTableEnum getAssetOperationTableByCode(String code) {
        if (code != null) {
            for (AssetOperationTableEnum operationTable : AssetOperationTableEnum.values()) {
                if (operationTable.getCode().equals(code)) {
                    return operationTable;
                }
            }
        }
        return null;
    }

    /**
     * 通过code获取枚举
     *
     * @param name name
     * @return
     */
    public static AssetOperationTableEnum getAssetOperationTableByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (AssetOperationTableEnum operationTable : AssetOperationTableEnum.values()) {
                if (operationTable.name().equals(name)) {
                    return operationTable;
                }
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
