package com.antiy.asset.vo.enums;

/**
 * @Author xiaoqianyong
 * @DATE 2020/4/28 14:39
 * @Description
 */
public enum ExportType {

    PDF("pdf"),WORD("doc");

    private String typeName;

    ExportType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
