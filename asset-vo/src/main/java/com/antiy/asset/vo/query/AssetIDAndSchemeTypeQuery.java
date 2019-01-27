package com.antiy.asset.vo.query;

import javax.validation.constraints.NotNull;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * 资产ID和方案类型查询条件
 *
 * @author zhangyajun
 * @create 2019-01-27 12:36
 **/
public class AssetIDAndSchemeTypeQuery {
    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）")
    @NotNull(message = "类型不能为空")
    private Integer type;
    /**
     * 实施人
     */
    @Encode
    @ApiModelProperty("资产id")
    @NotNull(message = "资产id不能为空")
    private String  assetId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
