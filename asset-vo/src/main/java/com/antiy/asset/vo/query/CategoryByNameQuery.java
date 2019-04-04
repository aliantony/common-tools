package com.antiy.asset.vo.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: CategoryByNameQuery
 * @Auther: chenguoming
 * @Date: 2019/4/4 17:21
 * @Description:
 */
public class CategoryByNameQuery {

    @ApiModelProperty(value = "分类名称",required = true)
    @NotNull(message = "分类名称不能为空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
