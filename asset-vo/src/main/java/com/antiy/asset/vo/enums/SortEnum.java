package com.antiy.asset.vo.enums;

/**
 * 排序枚举接口
 * @author 吕梁
 * @create 2019-05-09 17:11
 **/
public enum SortEnum {
                      ASC(0, "ASC"), DESC(1, "DESC");
    /**
     * code
     */
    private Integer code;

    /**
     * 排序
     */
    private String  sort;

    SortEnum(Integer code, String sort) {
        this.code = code;
        this.sort = sort;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
