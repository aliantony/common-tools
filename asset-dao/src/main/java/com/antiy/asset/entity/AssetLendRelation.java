package com.antiy.asset.entity;


import com.antiy.common.base.BaseEntity;
/**
 * <p></p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */

public class AssetLendRelation extends BaseEntity {


private static final long serialVersionUID = 1L;

    /**
    *  唯一键
    */
    private Long uniqueId;
    /**
    *  资产id
    */
    private Integer assetId;
    /**
    *  用户id
    */
    private Integer useId;
    /**
    *  订单id
    */
    private Integer orderId;
    /**
    *  归还日期
    */
    private Long lendPeriods;
    /**
    *  出借目的
    */
    private String lendPurpose;
    /**
    *  出借状态 1 已借出 2 已归还
    */
    private Integer lendStatus;
    /**
    *  创建时间
    */
    private Long gmtCreate;
    /**
    *  修改时间
    */
    private Long gmtModified;
    /**
    *  创建人
    */
    private Integer createUser;
    /**
    *  修改人
    */
    private Integer modifyUser;
    /**
    *  状态
    */
    private Integer status;

    /**
     *  出借日期
     */
    private Long lendTime;

    /**
     * 归还时间
     *
     */
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


    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }


    public Integer getUseId() {
        return useId;
    }

    public void setUseId(Integer useId) {
        this.useId = useId;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
            return "AssetLendRelation{" +
                        ", uniqueId=" + uniqueId +
                        ", assetId=" + assetId +
                        ", useId=" + useId +
                        ", orderId=" + orderId +
                        ", lendPeriods=" + lendPeriods +
                        ", lendPurpose=" + lendPurpose +
                        ", lendStatus=" + lendStatus +
                        ", gmtCreate=" + gmtCreate +
                        ", gmtModified=" + gmtModified +
                        ", createUser=" + createUser +
                        ", modifyUser=" + modifyUser +
                        ", status=" + status +
            "}";
    }
}