package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetManufactureRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetManufactureRelationRequest extends BaseRequest implements ObjectValidator {

    /**
     * 唯一键
     */
    @ApiModelProperty("唯一键")
    private Long uniqueId;
    /**
     * 业务主键
     */
    @ApiModelProperty("业务主键")
    private Long businessId;
    /**
     * 资产业务主键（来源asset_hard_soft_lib）
     */
    @ApiModelProperty("资产业务主键（来源asset_hard_soft_lib）")
    private Long assetIdHardsoftLib;

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getAssetIdHardsoftLib() {
        return assetIdHardsoftLib;
    }

    public void setAssetIdHardsoftLib(Long assetIdHardsoftLib) {
        this.assetIdHardsoftLib = assetIdHardsoftLib;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}