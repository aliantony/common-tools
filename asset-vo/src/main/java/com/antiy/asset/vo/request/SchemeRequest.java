package com.antiy.asset.vo.request;

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
    @ApiModelProperty("资产主键")
    private String  assetId;

    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）")
    private Integer type;
    /**
     * 结果
     */
    @ApiModelProperty("结果")
    private Integer result;
    /**
     * 实施用户主键
     */
    @ApiModelProperty("实施用户主键")
    private Integer putintoUserId;
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
     * 工单级别(1提示2紧急2重要3次要)
     */
    private Integer orderLevel;
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
     * 目标状态
     */
    @ApiModelProperty("目标状态")
    private Integer targetStatus;

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

    public Integer getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(Integer orderLevel) {
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

    public Integer getPutintoUserId() {
        return putintoUserId;
    }

    public void setPutintoUserId(Integer putintoUserId) {
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

    public Integer getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Integer targetStatus) {
        this.targetStatus = targetStatus;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}