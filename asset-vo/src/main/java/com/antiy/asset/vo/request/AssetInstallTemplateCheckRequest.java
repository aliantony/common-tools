package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.ParamterExceptionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p> AssetInstallTemplateCheckRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel("模板审核请求对象")
public class AssetInstallTemplateCheckRequest extends BaseRequest implements ObjectValidator {

    /**
     * 装机模板主键
     */
    @ApiModelProperty(value = "装机模板主键", hidden = true)
    private Integer installTemplateId;
    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
//    @NotNull(message = "用户id不能为空")
//    @Encode
    private Integer userId;
    /**
     * 审核意见
     */
    @ApiModelProperty("审核意见")
    private String advice;
    /**
     * 是否通过：0-拒绝，1-通过
     */
    @ApiModelProperty("审核结果：0-拒绝，1-通过")
    @NotNull(message = "审核结果不能为空")
    private Integer result;

    @ApiModelProperty("修改时间")
    private long gmtModified;
    @ApiModelProperty("修改用户")
    private String modifiedUser;

    public Integer getInstallTemplateId() {
        return getId();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (result == 0 && (advice == null || advice.isEmpty())) {
            throw new RequestParamValidateException("拒绝理由不能为空");
        } else if (!(result == 0 || result == 1)) {
            throw new RequestParamValidateException("审核结果只能为0和1");
        }

    }

}