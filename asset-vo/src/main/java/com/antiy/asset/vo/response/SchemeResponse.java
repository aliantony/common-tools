package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> SchemeResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class SchemeResponse {
    @Encode
    private String  id;
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
     * 方案内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}