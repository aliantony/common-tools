package com.antiy.asset.entity;


/**
 * 报表统计结果
 */
public class AssetCategoryEntity {
    // 统计数
    private Integer categoryCount;
    // 品类型号ID
    private Integer categoryModel;
    // 日期
    private String  date;

    // 父类主键
    private String  parentId;

    // 品类名称
    private String  categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AssetCategoryEntity{" + "categoryCount=" + categoryCount + ", categoryModel=" + categoryModel
               + ", date='" + date + '\'' + ", parentId='" + parentId + '\'' + '}';
    }

}
