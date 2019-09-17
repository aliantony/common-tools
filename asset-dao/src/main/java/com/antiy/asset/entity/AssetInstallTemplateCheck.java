package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>装机模板审核表</p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */

public class AssetInstallTemplateCheck extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 装机模板主键
     */
    private Integer           installTemplateId;
    /**
     * 用户主键
     */
    private Integer           userId;
    /**
     * 审核意见
     */
    private String            advice;
    /**
     * 是否通过：0-拒绝，1-通过
     */
    private Integer           result;

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
    public String toString() {
        return "AssetInstallTemplateCheck{" + ", installTemplateId=" + installTemplateId + ", userId=" + userId
               + ", advice=" + advice + ", result=" + result + "}";
    }
}