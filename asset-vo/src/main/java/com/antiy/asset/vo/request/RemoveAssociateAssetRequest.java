package com.antiy.asset.vo.request;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 关联资产请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

@ApiModel("关联资产请求对象")
public class RemoveAssociateAssetRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty("资产组Id")
    @NotNull(message = "资产组Id不能为空")
    private String       groupId;

    @Encode
    @NotNull(message = "移除资产不能为空")
    @ApiModelProperty("被移除资产")
    private String[] removeAsset;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getRemoveAsset() {
        return removeAsset;
    }

    public void setRemoveAsset(String[] removeAsset) {
        this.removeAsset = removeAsset;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}