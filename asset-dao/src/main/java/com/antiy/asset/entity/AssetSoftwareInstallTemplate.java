package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>装机模板与软件关系表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetSoftwareInstallTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模板主键
     */
    private Integer           installTemplateId;
    /**
     * 软件主键
     */
    private Integer           softwareId;
    /**
     * 状态：1-正常，0-删除
     */
    private Integer           status;

    public Integer getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(Integer installTemplateId) {
        this.installTemplateId = installTemplateId;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetSoftwareInstallTemplate{" + ", installTemplateId=" + installTemplateId + ", softwareId="
               + softwareId + ", status=" + status + "}";
    }
}