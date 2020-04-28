package com.antiy.asset.vo.request;

import com.antiy.asset.vo.query.AssetCompositionReportReQuery;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCompositionReportRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCompositionReportRequest extends BasicRequest implements ObjectValidator {

    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private AssetCompositionReportReQuery query;

    /**
     * 查询模板名称
     */
    @ApiModelProperty("查询模板名称")
    private String  name;
    /**
     * 查询模板名称
     */
    @ApiModelProperty("id")
    private Integer                     id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 0 删除 1存在
     */
    @ApiModelProperty("0 删除 1存在")
    private Integer status;
    @ApiModelProperty("1 是 0 否  默认那个")
    private Integer                     identification;

    public AssetCompositionReportReQuery getQuery() {
        return query;
    }

    public void setQuery(AssetCompositionReportReQuery query) {
        this.query = query;
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
    public void validate() throws RequestParamValidateException {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdentification() {
        return identification;
    }

    public void setIdentification(Integer identification) {
        this.identification = identification;
    }
}