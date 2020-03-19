package com.antiy.asset.entity;

import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p></p>
 *
 * @author why
 * @since 2020-02-24
 */

public class AssetCompositionReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private AssetCompositionReportQuery queryCondition;
    /**
     * 查询模板名称
     */
    private String            name;
    /**
     * 用户id
     */
    private Integer           userId;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 0 删除 1存在
     */
    private Integer           status;

    public void setQueryCondition(AssetCompositionReportQuery queryCondition) {
        this.queryCondition = queryCondition;
    }

    public AssetCompositionReportQuery getQueryCondition() {
        return queryCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetCompositionReport{" + ", queryCondition=" + queryCondition + ", name=" + name + ", userId="
               + userId + ", gmtCreate=" + gmtCreate + ", status=" + status + "}";
    }
}