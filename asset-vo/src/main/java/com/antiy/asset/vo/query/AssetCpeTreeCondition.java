package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCpeTree 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

@ApiModel
public class AssetCpeTreeCondition {

    @ApiModelProperty("唯一键")
    private String uniqueId;

    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}