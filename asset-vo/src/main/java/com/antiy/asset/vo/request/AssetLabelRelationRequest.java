package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLabelRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLabelRelationRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer           assetId;
    /**
     * 标签主键
     */
    @ApiModelProperty("标签主键")
    private Integer           assetLabelId;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getAssetLabelId() {
        return assetLabelId;
    }

    public void setAssetLabelId(Integer assetLabelId) {
        this.assetLabelId = assetLabelId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}