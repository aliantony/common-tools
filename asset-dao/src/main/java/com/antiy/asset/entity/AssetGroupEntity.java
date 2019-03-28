package com.antiy.asset.entity;

/**
 * 报表统计结果
 */
public class AssetGroupEntity {
    // 统计数
    private Integer groupCount;

    private Integer groupId;
    // 名字
    private String  name;
    // 日期
    private String  date;

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
