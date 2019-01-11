package com.antiy.asset.util;

import java.util.List;

/**
 * @description Excel sheet信息
 * @author lvliang
 * @param <T>
 */
public class ExcelSheet<T> {

    /**
     * sheet名称
     */
    private String name;
    /**
     * sheet英文名
     */
    private String englishName;

    /**
     * sheet标题
     */
    private String sheetTitle;
    /**
     * sheet表头
     */
    private List<String> header;
    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]> annotationList;
    /**
     * sheet数据
     */
    private List<T> dataList;

    /**
     * 是否为父sheet
     */
    private int isParent;

    /**
     * 外键名称
     */
    private String foreignerName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getSheetTitle() {
        return sheetTitle;
    }

    public void setSheetTitle(String sheetTitle) {
        this.sheetTitle = sheetTitle;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<Object[]> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<Object[]> annotationList) {
        this.annotationList = annotationList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getIsParent() {
        return isParent;
    }

    public void setIsParent(int isParent) {
        this.isParent = isParent;
    }

    public String getForeignerName() {
        return foreignerName;
    }

    public void setForeignerName(String foreignerName) {
        this.foreignerName = foreignerName;
    }

    public ExcelSheet(String name, String englishName, String sheetTitle, List<String> header, List<T> dataList, int isParent, String foreignerName) {
        this.name = name;
        this.englishName = englishName;
        this.sheetTitle = sheetTitle;
        this.header = header;
        this.dataList = dataList;
        this.isParent = isParent;
        this.foreignerName = foreignerName;
    }
}
