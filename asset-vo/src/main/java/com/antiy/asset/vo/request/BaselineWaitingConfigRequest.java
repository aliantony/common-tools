package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * BaselineWaitingConfigRequest 请求对象
 * </p>
 *
 * @author shenliang
 * @since 2019-09-17
 */

public class BaselineWaitingConfigRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产id
     */
    @ApiModelProperty("资产id")
    private Integer assetId;
    /**
     * 来源 1资产检查 2资产变更3漏洞修复 4补丁安装
     */
    @ApiModelProperty("来源 1资产检查 2资产变更 3漏洞修复 4补丁安装 5模板变更")
    private Integer source;
    /**
     * 状态：1待配置，2待核查，3核查中，4核查待确认，5核查失败，6待加固，7加固中，8加固待确认，9加固失败
     */
    @ApiModelProperty("状态：1待配置，2待核查，3核查中，4核查待确认，5核查失败，6待加固，7加固中，8加固待确认，9加固失败")
    private Integer configStatus;
    /**
     * 核查方式 1人工 2自动
     */
    @ApiModelProperty("核查方式 1人工 2自动")
    private Integer checkType;
    /**
     * 配置操作人员id
     */
    @ApiModelProperty("配置操作人员id")
    private Integer operator;
    /**
     * 配置失败状态 1核查失败 2加固失败
     */
    @ApiModelProperty("配置失败状态 1核查失败 2加固失败")
    private Integer statusPage;
    /**
     * 配置建议
     */
    @ApiModelProperty("配置建议")
    private String advice;
    /**
     * 配置原因
     */
    @ApiModelProperty("配置原因")
    private String reason;
    /**
     * 记录创建时间
     */
    @ApiModelProperty("记录创建时间")
    private Long gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    /**
     * 记录创建人员id
     */
    @ApiModelProperty("记录创建人员id")
    private Integer createUser;
    /**
     * 修改人id
     */
    @ApiModelProperty("修改人id")
    private Integer modifiedUser;


    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }


    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }


    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }


    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }


    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }


    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }


    public Integer getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatusPage() {
        return statusPage;
    }

    public void setStatusPage(Integer statusPage) {
        this.statusPage = statusPage;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}