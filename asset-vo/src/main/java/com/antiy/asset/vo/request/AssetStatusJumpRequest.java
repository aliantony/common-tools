package com.antiy.asset.vo.request;

import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangxin
 * @date 2019/9/10
 */
public class AssetStatusJumpRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty("资产Id数组")
    @NotEmpty(message = "资产数据格式不正确")
    private List<String> assetIdList;

    @ApiModelProperty("资产当前操作流程")
    @NotNull(message = "当前操作类型不正确")
    private AssetFlowEnum assetFlowEnum;

    @ApiModelProperty(value = "流程引擎数据")
    private ActivityHandleRequest activityHandleRequest;

    @ApiModelProperty(value = "资产变更流程信息")
    private ManualStartActivityRequest manualStartActivityRequest;

    /**
     * 本次处理结果:同意true,不同意false
     */
    @NotNull(message = "执行意见必填")
    @ApiModelProperty(value = "执行意见")
    private Boolean agree;

    /**
     * 方案内容:输入的备注信息
     */
    @ApiModelProperty(value = "备注内容")
    private String note;

    @ApiModelProperty(value = "上传的文件JSON串")
    private String fileInfo;

    // @ApiModelProperty(value = "扩展字段,JSON串")
    // private String extension;

    /**
     * 从待登记到待检查
     */
    @ApiModelProperty(value = "从整改到待登记true;")
    private Boolean waitCorrectToWaitRegister = Boolean.FALSE;

    public ActivityHandleRequest getActivityHandleRequest() {
        return activityHandleRequest;
    }

    public void setActivityHandleRequest(ActivityHandleRequest activityHandleRequest) {
        this.activityHandleRequest = activityHandleRequest;
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

    public List<String> getAssetIdList() {
        return assetIdList;
    }

    public void setAssetIdList(List<String> assetIdList) {
        this.assetIdList = assetIdList;
    }

    public Boolean getWaitCorrectToWaitRegister() {
        return waitCorrectToWaitRegister;
    }

    public void setWaitCorrectToWaitRegister(Boolean waitCorrectToWaitRegister) {
        this.waitCorrectToWaitRegister = waitCorrectToWaitRegister;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (activityHandleRequest == null && manualStartActivityRequest == null) {
            ParamterExceptionUtils.isTrue(false, "请求流程不能为空");
        }
        // 通过:校验下一步执行人;不通过:备注信息不能为空
        if (getAgree() != null && getAgree()) {
            ParamterExceptionUtils.isTrue(CollectionUtils.isNotEmpty(getManualStartActivityRequest().getConfigUserIds()), "下一步执行人员错误");
        } else {
            ParamterExceptionUtils.isTrue(StringUtils.isNotBlank(getNote()), "备注信息不能为空");
        }
    }

    @Override
    public String toString() {
        return "AssetStatusJumpRequest{" +
                "assetIdList=" + assetIdList +
                ", assetFlowEnum=" + assetFlowEnum +
                ", activityHandleRequest=" + activityHandleRequest +
                ", manualStartActivityRequest=" + manualStartActivityRequest +
                ", agree=" + agree +
                ", note='" + note + '\'' +
                ", fileInfo='" + fileInfo + '\'' +
                ", waitCorrectToWaitRegister=" + waitCorrectToWaitRegister +
                '}';
    }
}
