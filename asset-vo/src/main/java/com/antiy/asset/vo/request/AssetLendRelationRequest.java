package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetLendRelationRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLendRelationRequest extends BaseRequest implements ObjectValidator{

    /**
     *  唯一键
     */
    @ApiModelProperty("唯一键")
    private Long uniqueId;
    /**
     *  资产id
     */
    @ApiModelProperty("资产id")
    @Encode
    private String assetId;
    /**
     *  用户id
     */
    @ApiModelProperty("用户id")
    private Integer useId;
    /**
     *  订单id
     */
    @ApiModelProperty("订单编号")
    private String orderNumber;
    /**
     *  归还日期
     */
    @ApiModelProperty("归还日期")
    private Long lendPeriods;
    /**
     *  出借目的
     */
    @ApiModelProperty("出借目的")
    private String lendPurpose;
    /**
     *  出借状态 1 已借出 2 已归还
     */
    @ApiModelProperty("出借状态 1 已借出 2 已归还")
    private Integer lendStatus;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  修改时间
     */
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    /**
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     *  状态
     */
    @ApiModelProperty("状态")
    private Integer status;


    /**
     *  出借日期
     */
    @ApiModelProperty("出借日期")
    private Long lendTime;

    /**
     * 归还时间
     *
     */
    @ApiModelProperty("归还日期")
    private Long returnTime;

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }
    public Long getLendTime() {
        return lendTime;
    }

    public void setLendTime(Long lendTime) {
        this.lendTime = lendTime;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
    this.uniqueId = uniqueId;
    }


    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
    this.useId = useId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getLendPeriods() {
        return lendPeriods;
    }

    public void setLendPeriods(Long lendPeriods) {
    this.lendPeriods = lendPeriods;
    }


    public String getLendPurpose() {
        return lendPurpose;
    }

    public void setLendPurpose(String lendPurpose) {
    this.lendPurpose = lendPurpose;
    }


    public Integer getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(Integer lendStatus) {
    this.lendStatus = lendStatus;
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


    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
    this.modifyUser = modifyUser;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
    this.status = status;
    }

    @Override
    public String toString() {
        return "AssetLendRelationRequest{" +
                "uniqueId=" + uniqueId +
                ", assetId='" + assetId + '\'' +
                ", useId=" + useId +
                ", orderNumber='" + orderNumber + '\'' +
                ", lendPeriods=" + lendPeriods +
                ", lendPurpose='" + lendPurpose + '\'' +
                ", lendStatus=" + lendStatus +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                ", lendTime=" + lendTime +
                ", returnTime=" + returnTime +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}