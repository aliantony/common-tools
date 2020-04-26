package com.antiy.asset.vo.request;

public class ApplicantRequest {
    /**
     * 综合查询字段
     */
    private String multiply;

    public String getMultiply() {
        return multiply;
    }

    public void setMultiply(String multiply) {
        this.multiply = multiply;
    }

    @Override
    public String toString() {
        return "ApplicantRequest{" +
                "multiply='" + multiply + '\'' +
                '}';
    }
}
