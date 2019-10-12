package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetInstallTemplateCheckResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateCheckResponse extends BaseResponse {


    @ApiModelProperty("用户主键")
    private Integer           userId;

    @ApiModelProperty("审核意见")
    private String            advice;

    @ApiModelProperty("当前状态 1-待审核，2-拒绝，3-禁用，4-启用")
    private Integer           result;

    @ApiModelProperty("当前状态名称")
    private String  resultStr;

    @ApiModelProperty("创建时间")
    private String gmtCreate;

    @ApiModelProperty("用户名")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
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

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplateCheckResponse{" +
                "installTemplateId=" +
                ", userId=" + userId +
                ", advice='" + advice + '\'' +
                ", result=" + result +
                ", resultStr='" + resultStr + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", name='" + name + '\'' +
                '}';
    }
}