package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetInstallTemplateCheckRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateCheckRequest extends BaseRequest implements ObjectValidator {

    /**
     * 装机模板主键
     */
    @ApiModelProperty("装机模板主键")
    private Integer installTemplateId;
    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Integer userId;
    /**
     * 审核意见
     */
    @ApiModelProperty("审核意见")
    private String  advice;
    /**
     * 是否通过：0-拒绝，1-通过
     */
    @ApiModelProperty("是否通过：0-拒绝，1-通过")
    private Integer result;

    public Integer getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(Integer installTemplateId) {
        this.installTemplateId = installTemplateId;
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

    @Override
    public void validate() throws RequestParamValidateException {

    }

}