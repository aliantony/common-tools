package com.antiy.asset.vo.request;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/26 14:03
 * @description:
 */
@ApiModel(value = "资产ID请求")
public class AssetIdRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty("资产ID集合不能为空")
    @Encode
    private List<String> assetIds;

    private String userId;

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (CollectionUtils.isEmpty(assetIds)) {
            throw new BusinessException("资产ID集合不能为空");
        }
    }
}
