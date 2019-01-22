package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> SchemeRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SchemeRequest extends BasicRequest implements ObjectValidator {

    @Encode
    @ApiModelProperty(value = "主键")
    private String  id;
    /**
     * 资产主键
     */
    @Encode
    @ApiModelProperty("资产主键")
    @NotBlank(message = "资产主键不能为空")
    private String  assetId;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产状态")
    @NotNull(message = "资产状态不能为空")
    private Integer assetStatus;

    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.制定待退役方案、4.实施退役方案）")
    @NotNull(message = "类型不能为空")
    private Integer type;
    /**
     * 结果
     */
    @ApiModelProperty("结果")
    @NotNull(message = "结果不能为空")
    private Integer result;
    /**
     * 实施用户主键
     */
    @ApiModelProperty("实施用户主键")
    private String  putintoUserId;
    /**
     * 实施时间
     */
    @ApiModelProperty("实施时间")
    private Long    putintoTime;
    /**
     * 实施人
     */
    @ApiModelProperty("实施人")
    private String  putintoUser;
    /**
     * 方案来源
     */
    @ApiModelProperty("方案来源")
    private Integer schemeSource;
    /**
     * 工单级别(1提示2紧急2重要3次要)
     */
    private String  orderLevel;
    /**
     * 预计开始时间
     */
    private Long    expecteStartTime;
    /**
     * 预计结束时间
     */
    private Long    expecteEndTime;
    /**
     * 附件路径
     */
    @ApiModelProperty("附件路径")
    private String  fileInfo;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

    /**
     * 同意/拒绝
     */
    @ApiModelProperty("同意/拒绝")
    @NotNull(message = "同意或拒绝不能为空")
    private Integer isAgree;

    /**
     * 方案内容
     */
    private String  content;
    /**
     * 硬件或软件
     */
    @ApiModelProperty("硬件：ASSET 或软件: SOFTWARE")
    @NotNull(message = "硬件或软件不能为空")
    private String  topCategory;

    @ApiModelProperty("业务Id不能为空")
    @NotBlank(message = "业务Id不能为空")
    private String  businessId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Integer getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Integer isAgree) {
        this.isAgree = isAgree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(String orderLevel) {
        this.orderLevel = orderLevel;
    }

    public Long getExpecteStartTime() {
        return expecteStartTime;
    }

    public void setExpecteStartTime(Long expecteStartTime) {
        this.expecteStartTime = expecteStartTime;
    }

    public Long getExpecteEndTime() {
        return expecteEndTime;
    }

    public void setExpecteEndTime(Long expecteEndTime) {
        this.expecteEndTime = expecteEndTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getPutintoUserId() {
        return putintoUserId;
    }

    public void setPutintoUserId(String putintoUserId) {
        this.putintoUserId = putintoUserId;
    }

    public Long getPutintoTime() {
        return putintoTime;
    }

    public void setPutintoTime(Long putintoTime) {
        this.putintoTime = putintoTime;
    }

    public String getPutintoUser() {
        return putintoUser;
    }

    public void setPutintoUser(String putintoUser) {
        this.putintoUser = putintoUser;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSchemeSource() {
        return schemeSource;
    }

    public void setSchemeSource(Integer schemeSource) {
        this.schemeSource = schemeSource;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}