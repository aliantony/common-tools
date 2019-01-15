package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 端口协议 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetPortProtocol extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产软件关系表主键
     */
    private Integer           assetSoftId;
    /**
     * 端口
     */
    private Integer           port;
    /**
     * 协议
     */
    private String            protocol;
    /**
     * 描述
     */
    private String            description;
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
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;

    public Integer getAssetSoftId() {
        return assetSoftId;
    }

    public void setAssetSoftId(Integer assetSoftId) {
        this.assetSoftId = assetSoftId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
        return "AssetPortProtocol{" + ", assetSoftId=" + assetSoftId + ", port=" + port + ", protocol=" + protocol
               + ", description=" + description + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
               + ", memo=" + memo + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status
               + "}";
    }
}