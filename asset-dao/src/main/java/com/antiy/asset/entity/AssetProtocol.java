package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>协议表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetProtocol extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务主键
     */
    private Long              businessId;
    /**
     * 协议
     */
    private String            name;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 是否入库：1-已入库，2-未入库
     */
    private Integer           isStorage;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 关联的遏制漏洞编号
     */
    private String            linkVuls;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;
    /**
     * 状态：1-正常，0-删除
     */
    private Integer           status;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getLinkVuls() {
        return linkVuls;
    }

    public void setLinkVuls(String linkVuls) {
        this.linkVuls = linkVuls;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetProtocol{" + ", businessId=" + businessId + ", name=" + name + ", memo=" + memo + ", isStorage="
               + isStorage + ", gmtModified=" + gmtModified + ", linkVuls=" + linkVuls + ", gmtCreate=" + gmtCreate
               + ", createUser=" + createUser + ", modifiedUser=" + modifiedUser + ", status=" + status + "}";
    }
}