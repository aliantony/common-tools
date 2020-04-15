package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/4/14
 */
@ApiModel(description = "资产类型下拉树判断借用来源")
public class LendSourceQuery {
    @ApiModelProperty("是否来源于借用管理：true是,false否")
    private boolean sourceOfLend;

    public boolean isSourceOfLend() {
        return sourceOfLend;
    }

    public void setSourceOfLend(boolean sourceOfLend) {
        this.sourceOfLend = sourceOfLend;
    }
}
