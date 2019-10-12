package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>装机模板审核表</p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */

public class AssetInstallTemplateCheck extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    private Integer           userId;
    /**
     * 审核意见
     */
    private String            advice;
    /**
     *创建时间
     */
    private Long gmtCreate;
    /**
     * 是否通过：0-拒绝，1-通过
     */
    private Integer           result;

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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplateCheck{" +
                "installTemplateId=" +
                ", userId=" + userId +
                ", advice='" + advice + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", result=" + result +
                '}';
    }
}