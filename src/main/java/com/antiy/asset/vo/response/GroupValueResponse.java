package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * GroupValueResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class GroupValueResponse {
    /**
     * 资产组名称
     */
    @ApiModelProperty("资产组名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}