package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetChangeRecordResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetChangeRecordResponse extends BaseEntity {
    /**
     * 修改对象的JSON串
     */
    @ApiModelProperty("修改对象的JSON串")
    private String changeVal;

    public String getChangeVal() {
        return changeVal;
    }

    public void setChangeVal(String changeVal) {
        this.changeVal = changeVal;
    }
}