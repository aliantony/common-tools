package com.antiy.asset.vo.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 13:42
 * @description:
 */
@ApiModel(value = "硬件资产状态变更")
public class AssetStatusReqeust extends BasicRequest implements ObjectValidator {

    @ApiModelProperty(value = "方案信息")
    @Valid
    private SchemeRequest              schemeRequest;

    @ApiModelProperty(value = "工单信息")
    @Valid
    private WorkOrderVO                workOrderVO;

    @ApiModelProperty(value = "流程引擎")
    private ActivityHandleRequest      activityHandleRequest;

    @ApiModelProperty(value = "资产变更流程信息")
    @Valid
    private ManualStartActivityRequest manualStartActivityRequest;

    /**
     * 同意/拒绝
     */
    @ApiModelProperty("同意(true)/拒绝(false)")
    @NotNull(message = "同意或拒绝不能为空")
    private Boolean                    agree;

    @ApiModelProperty(value = "是否为软件,false为硬件，true为软件，默认false", allowableValues = "false,true")
    @NotNull(message = "软件或者硬件判断条件不能为空")
    private Boolean                    software = false;

    /**
     * 资产主键
     */
    @Encode
    @ApiModelProperty("资产主键")
    @NotBlank(message = "资产主键不能为空")
    private String                     assetId;
    /**
     * 资产主键
     */
    @ApiModelProperty("硬件资产当前状态")
    private AssetStatusEnum            assetStatus;

    /**
     * 是否资产变更流程
     */
    @ApiModelProperty("资产流程类别")
    @NotNull(message = "资产流程类别不能为空")
    private AssetFlowCategoryEnum      assetFlowCategoryEnum;

    @ApiModelProperty(value = "软件资产当前状态")
    private SoftwareStatusEnum         softwareStatusEnum;

    public ManualStartActivityRequest getManualStartActivityRequest() {
        return manualStartActivityRequest;
    }

    public void setManualStartActivityRequest(ManualStartActivityRequest manualStartActivityRequest) {
        this.manualStartActivityRequest = manualStartActivityRequest;
    }

    public AssetFlowCategoryEnum getAssetFlowCategoryEnum() {
        return assetFlowCategoryEnum;
    }

    public void setAssetFlowCategoryEnum(AssetFlowCategoryEnum assetFlowCategoryEnum) {
        this.assetFlowCategoryEnum = assetFlowCategoryEnum;
    }

    public SoftwareStatusEnum getSoftwareStatusEnum() {
        return softwareStatusEnum;
    }

    public void setSoftwareStatusEnum(SoftwareStatusEnum softwareStatusEnum) {
        this.softwareStatusEnum = softwareStatusEnum;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public AssetStatusEnum getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatusEnum assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Boolean getSoftware() {
        return software;
    }

    public void setSoftware(Boolean software) {
        this.software = software;
    }

    public SchemeRequest getSchemeRequest() {
        return schemeRequest;
    }

    public void setSchemeRequest(SchemeRequest schemeRequest) {
        this.schemeRequest = schemeRequest;
    }

    public WorkOrderVO getWorkOrderVO() {
        return workOrderVO;
    }

    public void setWorkOrderVO(WorkOrderVO workOrderVO) {
        this.workOrderVO = workOrderVO;
    }

    public ActivityHandleRequest getActivityHandleRequest() {
        return activityHandleRequest;
    }

    public void setActivityHandleRequest(ActivityHandleRequest activityHandleRequest) {
        this.activityHandleRequest = activityHandleRequest;
    }

    public Boolean getAgree() {
        return this.agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    @Override
    public void validate() throws RequestParamValidateException {

        if (activityHandleRequest == null && manualStartActivityRequest == null) {
            ParamterExceptionUtils.isTrue(false, "请求流程不能为空");
        }
        if (!software) {
            ParamterExceptionUtils.isNull(assetStatus, "硬件资产状态不能为空");
        }

        if (software) {
            ParamterExceptionUtils.isNull(softwareStatusEnum, "软件资产状态不能为空");
        }

    }

    @Override
    public String toString() {
        return "AssetStatusReqeust{" + "schemeRequest=" + schemeRequest + ", workOrderVO=" + workOrderVO
               + ", activityHandleRequest=" + activityHandleRequest + ", manualStartActivityRequest="
               + manualStartActivityRequest + ", agree=" + agree + ", software=" + software + ", assetId='" + assetId
               + '\'' + ", assetStatus=" + assetStatus + ", assetFlowCategoryEnum=" + assetFlowCategoryEnum
               + ", softwareStatusEnum=" + softwareStatusEnum + '}';
    }
}
