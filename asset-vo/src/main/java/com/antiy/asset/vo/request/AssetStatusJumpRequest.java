package com.antiy.asset.vo.request;

import com.antiy.asset.dto.StatusJumpAssetInfo;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxin
 * @date 2019/9/10
 */
public class AssetStatusJumpRequest extends BasicRequest implements ObjectValidator {


    /**
     * 报废组件
     */
    @ApiModelProperty("组件报废")
    private List<AssetAssemblyRequest> assetAssemblyRequest;
    /**
     * 审批人id
     */
    @ApiModelProperty("审批人id")
    @Encode
    private String checkUserId;

    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    private String  checkUserName;
    /**
     * 执行人id
     */
    @ApiModelProperty("执行人id")
    @Encode
    private String executeUserId;
    /**
     * 执行人名称
     */
    @ApiModelProperty("执行人姓名")
    private String executeUserName;

    @ApiModelProperty("资产信息")
    @NotEmpty(message = "资产数据格式不正确")
    @Valid
    private List<StatusJumpAssetInfo>  assetInfoList;

    @ApiModelProperty(value = "流程表单数据,JSON串")
    private Map                        formData;

    @ApiModelProperty(value = "是否发起退役流程")
    private Boolean                    isAssetPlanRetire;

    @ApiModelProperty("资产当前操作流程:REGISTER登记资产;TEMPLATE_IMPL实施;VALIDATE验证;NET_IN入网;CHECK检查;CORRECT整改;TO_WAIT_RETIRE拟退役;RETIRE退役;CHANGE变更资产;CHANGE_COMPLETE变更完成")
    @NotNull(message = "当前操作类型不正确")
    private AssetFlowEnum              assetFlowEnum;

    @ApiModelProperty(value = "资产变更流程信息")
    private ManualStartActivityRequest manualStartActivityRequest;

    /**
     * 本次处理结果:同意true,不同意false
     */
    @ApiModelProperty(value = "执行意见")
    private Boolean                    agree=true;



    /**
     * 方案内容:输入的备注信息
     */
    @ApiModelProperty(value = "备注内容")
    private String                     note;

    @ApiModelProperty(value = "上传的文件JSON串")
    private String                     fileInfo;

    /**
     * 从待登记到待检查
     */
    @ApiModelProperty(value = "从整改到待登记true;其他情况可不传")
    private Boolean                    waitCorrectToWaitRegister = Boolean.FALSE;

    public List<AssetAssemblyRequest> getAssetAssemblyRequest() {
        return assetAssemblyRequest;
    }

    public void setAssetAssemblyRequest(List<AssetAssemblyRequest> assetAssemblyRequest) {
        this.assetAssemblyRequest = assetAssemblyRequest;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getExecuteUserId() {
        return executeUserId;
    }

    public void setExecuteUserId(String executeUserId) {
        this.executeUserId = executeUserId;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }

    public Boolean getIsAssetPlanRetire() {
        return isAssetPlanRetire;
    }

    public void setIsAssetPlanRetire(Boolean assetPlanRetire) {
        isAssetPlanRetire = assetPlanRetire;
    }

    public List<StatusJumpAssetInfo> getAssetInfoList() {
        return assetInfoList;
    }

    public void setAssetInfoList(List<StatusJumpAssetInfo> assetInfoList) {
        this.assetInfoList = assetInfoList;
    }

    public Map getFormData() {
        return formData;
    }

    public void setFormData(Map formData) {
        this.formData = formData;
    }

    public ManualStartActivityRequest getManualStartActivityRequest() {
        return manualStartActivityRequest;
    }

    public void setManualStartActivityRequest(ManualStartActivityRequest manualStartActivityRequest) {
        this.manualStartActivityRequest = manualStartActivityRequest;
    }

    public AssetFlowEnum getAssetFlowEnum() {
        return assetFlowEnum;
    }

    public void setAssetFlowEnum(AssetFlowEnum assetFlowEnum) {
        this.assetFlowEnum = assetFlowEnum;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public Boolean getWaitCorrectToWaitRegister() {
        return waitCorrectToWaitRegister;
    }

    public void setWaitCorrectToWaitRegister(Boolean waitCorrectToWaitRegister) {
        this.waitCorrectToWaitRegister = waitCorrectToWaitRegister;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (!(assetFlowEnum.equals(AssetFlowEnum.CHANGE_COMPLETE)) && formData == null
            && manualStartActivityRequest == null) {
            ParamterExceptionUtils.isTrue(false, "请求流程数据不能为空");
        }
        if(assetFlowEnum.equals(AssetFlowEnum.RETIRE_APPLICATION)

            || assetFlowEnum.equals(AssetFlowEnum.SCRAP_APPLICATION)
            ){

            ParamterExceptionUtils.isBlank(note,"退役方案不能为空");

        }
        /*if (assetFlowEnum.equals(AssetFlowEnum.TO_WAIT_RETIRE) || assetFlowEnum.equals(AssetFlowEnum.CHANGE_COMPLETE)) {
            agree = Boolean.TRUE;
        } else {
            ParamterExceptionUtils.isNull(agree, "执行意见必填");
        }

        // 通过:入网/退役/检查/变更完成不校验,其他校验下一步执行人;不通过:备注信息不能为空
        boolean checkConfigUser = !(assetFlowEnum.equals(AssetFlowEnum.RETIRE)
                                    || assetFlowEnum.equals(AssetFlowEnum.NET_IN)
                                    || assetFlowEnum.equals(AssetFlowEnum.CHECK)
                                    || assetFlowEnum.equals(AssetFlowEnum.CHANGE_COMPLETE));
        if (Boolean.TRUE.equals(agree) && checkConfigUser) {
            ParamterExceptionUtils.isNull(getFormData(), "formData参数错误");
            ParamterExceptionUtils.isNull(formData.get(assetFlowEnum.getActivityKey()), "下一步执行人员错误");
        } else if (Boolean.FALSE.equals(agree)) {
            ParamterExceptionUtils.isTrue(StringUtils.isNotBlank(getNote()),
                assetFlowEnum.equals(AssetFlowEnum.TO_WAIT_RETIRE) ? "退役方案信息必填" : "备注信息必填");
        }*/
    }

    @Override
    public String toString() {
        return "AssetStatusJumpRequest{" + "assetInfos=" + assetInfoList + ", assetFlowEnum=" + assetFlowEnum
               + ", formData=" + formData + ", manualStartActivityRequest=" + manualStartActivityRequest + ", agree="
               + agree + ", note='" + note + '\'' + ", fileInfo='" + fileInfo + '\'' + ", waitCorrectToWaitRegister="
               + waitCorrectToWaitRegister + '}';
    }
}
