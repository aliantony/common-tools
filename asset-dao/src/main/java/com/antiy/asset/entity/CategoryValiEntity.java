package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * Created with IntelliJ IDEA. User: 钊钊 Date: 2019/5/7 Time: 13:38 Description: No Description
 */
public class CategoryValiEntity extends BaseEntity {

    private String name;

    private int    parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
