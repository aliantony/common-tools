package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>过滤显示表（筛选指定的数据给用户）</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetCpeFilter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务主键
     */
    private Long              businessId;
    /**
     * 产品名称
     */
    private String            productName;
    /**
     * 系列名称
     */
    private String            serialName;
    /**
     * 类型
     */
    private String            type;
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

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "AssetCpeFilter{" + ", businessId=" + businessId + ", productName=" + productName + ", serialName="
               + serialName + ", type=" + type + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate
               + ", createUser=" + createUser + ", modifiedUser=" + modifiedUser + ", status=" + status + "}";
    }
}