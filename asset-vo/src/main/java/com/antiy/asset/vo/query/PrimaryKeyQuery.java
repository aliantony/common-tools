package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: lvliang
 * @Date: 2019/9/27 15:46
 */
public class PrimaryKeyQuery extends ObjectQuery {
    @ApiModelProperty("业务主键")
    @Encode
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
