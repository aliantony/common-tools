package com.antiy.asset.vo.request;

import com.antiy.asset.dto.StatusJumpAssetInfo;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxin
 * @date 2019/9/10
 */
public class AssetStatusJumpRequest extends BasicRequest implements ObjectValidator {

    // @ApiModelProperty("资产Id数组")
    // @NotEmpty(message = "资产数据格式不正确")
    // private List<String> assetIdList;

    @ApiModelProperty("资产信息")
    @NotEmpty(message = "资产数据格式不正确")
    private List<StatusJumpAssetInfo> assetInfoList;

    @ApiModelProperty(value = "流程表单数据,JSON串")
    private Map formData;


    @ApiModelProperty("资产当前操作流程:REGISTER登记资产;TEMPLATE_IMPL实施;VALIDATE验证;NET_IN入网;CHECK检查;CORRECT整改;TO_WAIT_RETIRE拟退役;RETIRE退役;CHANGE变更资产;CHANGE_COMPLETE变更完成")
    @NotNull(message = "当前操作类型不正确")
    private AssetFlowEnum assetFlowEnum;

    //
    // @ApiModelProperty(value = "流程引擎数据")
    // private ActivityHandleRequest activityHandleRequest;

    @ApiModelProperty(value = "资产变更流程信息")
    private ManualStartActivityRequest manualStartActivityRequest;

    /**
     * 本次处理结果:同意true,不同意false
     */
    @ApiModelProperty(value = "执行意见")
    private Boolean agree;

    /**
     * 方案内容:输入的备注信息
     */
    @ApiModelProperty(value = "备注内容")
    private String note;

    @ApiModelProperty(value = "上传的文件JSON串")
    private String fileInfo;

    /**
     * 从待登记到待检查
     */
    @ApiModelProperty(value = "从整改到待登记true;其他情况可不传")
    private Boolean waitCorrectToWaitRegister = Boolean.FALSE;

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
        if (formData == null && manualStartActivityRequest == null) {
            ParamterExceptionUtils.isTrue(false, "请求流程不能为空");
        }

        // 通过:入网/退役/检查不校验,其他校验下一步执行人;不通过:备注信息不能为空
        boolean needCheckAgree = !(assetFlowEnum.equals(AssetFlowEnum.RETIRE) || assetFlowEnum.equals(AssetFlowEnum.NET_IN) || assetFlowEnum.equals(AssetFlowEnum.CHECK));
        if (needCheckAgree) {
            ParamterExceptionUtils.isNull(agree, "执行意见必填");
        }
        if (Boolean.FALSE.equals(agree)) {
            ParamterExceptionUtils.isTrue(StringUtils.isNotBlank(getNote()), "备注信息不能为空");
        }
    }

    @Override
    public String toString() {
        return "AssetStatusJumpRequest{" +
                "assetInfos=" + assetInfoList +
                ", assetFlowEnum=" + assetFlowEnum +
                ", formData=" + formData +
                ", manualStartActivityRequest=" + manualStartActivityRequest +
                ", agree=" + agree +
                ", note='" + note + '\'' +
                ", fileInfo='" + fileInfo + '\'' +
                ", waitCorrectToWaitRegister=" + waitCorrectToWaitRegister +
                '}';
    }
}
