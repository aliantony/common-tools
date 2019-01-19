package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetGroupResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupResponse extends BaseResponse {
    /**
     * 用途
     */
    @ApiModelProperty("用途")
    private String  purpose;
    /**
     * 重要程度(0-不重要(not_major),1- 一般(general),3-重要(major),)
     */
    @ApiModelProperty("重要程度(0-不重要(not_major),1- 一般(general),3-重要(major),)")
    private Integer importantDegree;
    /**
     * 资产组名称
     */
    @ApiModelProperty("资产组名称")
    private String  name;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getImportantDegree() {
        return importantDegree;
    }

    public void setImportantDegree(Integer importantDegree) {
        this.importantDegree = importantDegree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}