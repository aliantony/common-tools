package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>变更记录表 </p>
 *
 * @author why
 * @since 2019-02-19
 */

public class AssetChangeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 1--硬件资产，2--软件资产
     */
    private Integer           type;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 是否存储,1--已经存储,0--未存储
     */
    private Integer           isStore;
    /**
     * 修改对象的JSON串
     */
    private String            changeVal;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 业务主键Id
     */
    private Integer           businessId;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getIsStore() {
        return isStore;
    }

    public void setIsStore(Integer isStore) {
        this.isStore = isStore;
    }

    public String getChangeVal() {
        return changeVal;
    }

    public void setChangeVal(String changeVal) {
        this.changeVal = changeVal;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "AssetChangeRecord{" + "type=" + type + ", status=" + status + ", createUser=" + createUser
               + ", isStore=" + isStore + ", changeVal='" + changeVal + '\'' + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + ", memo='" + memo + '\'' + ", businessId=" + businessId + '}';
    }
}