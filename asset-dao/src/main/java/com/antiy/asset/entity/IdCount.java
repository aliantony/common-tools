package com.antiy.asset.entity;

public class IdCount {
    /**
     * id
     */
    private String id;
    /**
     * 数量
     */
    private String count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public IdCount() {
    }

    public IdCount(String id, String count) {
        this.id = id;
        this.count = count;
    }
}
