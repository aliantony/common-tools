package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>软件与协议表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetSoftProtocolRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 软件id
     */
    private Long              softId;
    /**
     * 协议id
     */
    private Long              protocolId;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 修改时间
     */
    private Long              gmtModified;
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

    public Long getSoftId() {
        return softId;
    }

    public void setSoftId(Long softId) {
        this.softId = softId;
    }

    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
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
        return "AssetSoftProtocolRelation{" + ", softId=" + softId + ", protocolId=" + protocolId + ", memo=" + memo
               + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate + ", createUser=" + createUser
               + ", modifiedUser=" + modifiedUser + ", status=" + status + "}";
    }
}