package com.antiy.asset.vo.enums;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 16:20
 * @description:
 */
public enum AssetLogOperationType {
                                   ADD("add"), UPDATE("update"), QUERY("query"), DELETED("deleted"), CREATE("create"), VERIFY("verify"), OTHER("other"), RANK("RANK");
    private String code;

    AssetLogOperationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * 根据code获取枚举
     *
     * @param code
     * @return
     */
    public static AssetLogOperationType getByCode(String code) {
        if (null == code || "".equals(code)) {
            return null;
        }

        for (AssetLogOperationType at : AssetLogOperationType.values()) {
            if (at.name().equals(code)) {
                return at;
            }
        }

        return null;
    }
}
