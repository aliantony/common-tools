package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetTopologyRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetTopologyRequest extends BasicRequest implements ObjectValidator {

    /**
     * 拓扑类型
     */
    @ApiModelProperty("拓扑类型")
    private Integer topologyType;
    /**
     * 拓扑关系
     */
    @ApiModelProperty("拓扑关系")
    private String relation;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;


    public Integer getTopologyType() {
        return topologyType;
    }

    public void setTopologyType(Integer topologyType) {
        this.topologyType = topologyType;
    }


    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }


    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

}