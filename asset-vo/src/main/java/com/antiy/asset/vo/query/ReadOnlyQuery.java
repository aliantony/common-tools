package com.antiy.asset.vo.query;

import com.antiy.common.base.QueryCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: lvliang
 * @Date: 2020/5/13 14:53
 */
@ApiModel
public class ReadOnlyQuery extends QueryCondition {
    @ApiModelProperty("是否是查看true/false")
    private boolean readOnly = false;

    public boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
