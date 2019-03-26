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
        return "CategoryCountVO{" +
                "categoryCount=" + categoryCount +
                ", categoryModel=" + categoryModel +
                ", date='" + date + '\'' +
                '}';
    }
}
