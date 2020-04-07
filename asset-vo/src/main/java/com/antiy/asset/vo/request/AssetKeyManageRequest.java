package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author chenchaowu
 */
public class AssetKeyManageRequest extends BaseRequest implements ObjectValidator {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Integer id;

    /**
     * key编号
     */
    @ApiModelProperty("key编号")
    private String keyNum;

    /**
     * 领用状态 0:未领用 1:领用 2:冻结
     */
    @ApiModelProperty("领用状态")
    private Integer recipState;

    /**
     * 使用者类型 0:设备 1:用户
     */
    @ApiModelProperty("使用者类型")
    private Integer keyUserType;

    /**
     * 使用者--资产ID or 用户ID
     */
    @ApiModelProperty("使用者 --资产ID or 用户ID")
    private Integer keyUserId;

    /**
     * 使用者 -- 资产编号 or 用户名
     */
    @ApiModelProperty("使用者 -- 资产编号 or 用户名")
    private String userNumName;

    /**
     * 领用时间
     */
    @ApiModelProperty("领用时间")
    private Long recipTime;

    @Override
    public void validate() throws RequestParamValidateException {

    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum;
    }

    public Integer getRecipState() {
        return recipState;
    }

    public void setRecipState(Integer recipState) {
        this.recipState = recipState;
    }

    public Integer getKeyUserType() {
        return keyUserType;
    }

    public void setKeyUserType(Integer keyUserType) {
        this.keyUserType = keyUserType;
    }

    public Integer getKeyUserId() {
        return keyUserId;
    }

    public void setKeyUserId(Integer keyUserId) {
        this.keyUserId = keyUserId;
    }

    public String getUserNumName() {
        return userNumName;
    }

    public void setUserNumName(String userNumName) {
        this.userNumName = userNumName;
    }

    public Long getRecipTime() {
        return recipTime;
    }

    public void setRecipTime(Long recipTime) {
        this.recipTime = recipTime;
    }

    @Override
    public String toString() {
        return "AssetKeyManageRequest{" +
                "id='" + id + '\'' +
                ", keyNum=" + keyNum +
                ", recipState=" + recipState +
                ", keyUserType=" + keyUserType +
                ", keyUserId=" + keyUserId +
                ", userNumName=" + userNumName +
                ", recipTime=" + recipTime +
                '}';
    }
}
