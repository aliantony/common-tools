package com.antiy.asset.vo.redis;

import com.antiy.asset.vo.response.BaseResponse;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCategoryModelResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class CategoryOsResponse extends BaseResponse {

    /**
     * 名称
     */
    @ApiModelProperty("系统名称")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}