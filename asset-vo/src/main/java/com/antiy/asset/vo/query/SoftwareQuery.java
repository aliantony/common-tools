package com.antiy.asset.vo.query;

import com.antiy.common.base.QueryCondition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/14 13:32
 * @description:
 */
@ApiModel(value = "软件详情查询")
public class SoftwareQuery extends QueryCondition {

    @ApiModelProperty(value = "是否查询软件license,默认为false不查询", allowableValues = "true,false")
    private Boolean queryLicense = false;

    @ApiModelProperty(value = "是否查询软件端口,默认为false不查询", allowableValues = "true,false")
    private Boolean queryPort    = false;

    public Boolean getQueryLicense() {
        return queryLicense;
    }

    public void setQueryLicense(Boolean queryLicense) {
        this.queryLicense = queryLicense;
    }

    public Boolean getQueryPort() {
        return queryPort;
    }

    public void setQueryPort(Boolean queryPort) {
        this.queryPort = queryPort;
    }
}
