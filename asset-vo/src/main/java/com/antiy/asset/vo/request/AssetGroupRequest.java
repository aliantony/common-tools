package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p> AssetGroupRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupRequest extends BasicRequest implements ObjectValidator {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String id;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 资产组名称
     */
    @ApiModelProperty("资产组名称")
    @NotBlank(message = "资产组名称不能为空")
    private String name;
    /**
     * 资产组ID数组
     */
    @ApiModelProperty("资产组ID数组")
    @Encode
    private List<String> assetIdList;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<String> getAssetIdList() {
        return assetIdList;
    }

    public void setAssetIdList(List<String> assetIdList) {
        this.assetIdList = assetIdList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}