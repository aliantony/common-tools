package com.antiy.asset.vo.request;

import java.util.Arrays;

import javax.validation.constraints.Size;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetGroupRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

@ApiModel("资产组请求对象")
public class AssetGroupRequest extends BasicRequest implements ObjectValidator {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String   id;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String   memo;
    /**
     * 资产组名称
     */
    @ApiModelProperty("资产组名称")
//    @NotBlank(message = "资产组名称不能为空")
    @Size(message = "资产组名称长度不能超过32位", max = 32)
    private String   name;
    /**
     * 资产组ID数组
     */
    @ApiModelProperty("资产组ID数组")
    @Encode
    @Size(max = 1000, message = "资产组Id数组长度不能超过1000")
    private String[] assetIds;

    /**
     * 被移除的资产
     */
    @ApiModelProperty("被移除的资产数组")
    @Encode
    @Size(max = 1000, message = "资产组Id数组长度不能超过1000")
    private String[] deleteAssetIds;

    /**
     * 新增的关联资产(不包含已关联资产)
     */
    @ApiModelProperty("新增的关联资产ID数组")
    @Encode
    private String[] addAssociateIds;

    public String[] getAddAssociateIds() {
        return addAssociateIds;
    }

    public void setAddAssociateIds(String[] addAssociateIds) {
        this.addAssociateIds = addAssociateIds;
    }

    public String[] getDeleteAssetIds() {
        return deleteAssetIds;
    }

    public void setDeleteAssetIds(String[] deleteAssetIds) {
        this.deleteAssetIds = deleteAssetIds;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String[] getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String[] assetIds) {
        this.assetIds = assetIds;
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
    public String toString() {
        return "AssetGroupRequest{" + "id='" + id + '\'' + ", memo='" + memo + '\'' + ", name='" + name + '\''
               + ", assetIds=" + Arrays.toString(assetIds) + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}