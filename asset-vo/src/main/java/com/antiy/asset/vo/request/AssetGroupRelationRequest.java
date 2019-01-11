package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetGroupRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupRelationRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;

    /**
     * 资产组主键
     */
    @ApiModelProperty("主键")
    private Integer           id;
    /**
     * 资产组主键
     */
    @ApiModelProperty("资产组主键")
    private Integer           assetGroupId;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer           assetId;

    public Integer getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(Integer assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}