package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetSoftwareRelationRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSoftwareRelationRequest extends BasicRequest implements ObjectValidator {

    private Integer id;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 软件资产状态：1待登记，2不予登记，3待配置，4待验证，5待入网，6已入网，7待退役，8已退役
     */
    @ApiModelProperty("软件资产状态：1待登记，2不予登记，3待配置，4待验证，5待入网，6已入网，7待退役，8已退役")
    private Integer softwareStatus;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getSoftwareStatus() {
        return softwareStatus;
    }

    public void setSoftwareStatus(Integer softwareStatus) {
        this.softwareStatus = softwareStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}