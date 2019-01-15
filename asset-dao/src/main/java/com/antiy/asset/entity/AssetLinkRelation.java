package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 通联关系表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetLinkRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 通联名称
     */
    private String            name;
    /**
     * 通联类型:1光纤，2双绞线
     */
    private Integer           linkType;
    /**
     * 头节点资产
     */
    private Integer           headAsset;
    /**
     * 尾节点资产
     */
    private Integer           tailAsse;
    /**
     * 头节点类型
     */
    private Integer           headType;
    /**
     * 尾节点类型
     */
    private Integer           tailType;
    /**
     * 状态
     */
    private Integer           linkStatus;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
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

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Integer getHeadAsset() {
        return headAsset;
    }

    public void setHeadAsset(Integer headAsset) {
        this.headAsset = headAsset;
    }

    public Integer getTailAsse() {
        return tailAsse;
    }

    public void setTailAsse(Integer tailAsse) {
        this.tailAsse = tailAsse;
    }

    public Integer getHeadType() {
        return headType;
    }

    public void setHeadType(Integer headType) {
        this.headType = headType;
    }

    public Integer getTailType() {
        return tailType;
    }

    public void setTailType(Integer tailType) {
        this.tailType = tailType;
    }

    public Integer getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(Integer linkStatus) {
        this.linkStatus = linkStatus;
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
        return "AssetLinkRelation{" + ", name=" + name + ", linkType=" + linkType + ", headAsset=" + headAsset
               + ", tailAsse=" + tailAsse + ", headType=" + headType + ", tailType=" + tailType + ", linkStatus="
               + linkStatus + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo
               + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}