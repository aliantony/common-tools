package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * <p> AssetGroupRelation 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupRelationQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 资产组ID
     */
    @ApiModelProperty("资产组ID")
    @Encode
    @NotBlank(message = "资产组ID不能为空")
    private String  assetGroupId;

    public String getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(String assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    @Override
    public String toString() {
        return "AssetGroupRelationQuery{" +
                "assetGroupId='" + assetGroupId + '\'' +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}