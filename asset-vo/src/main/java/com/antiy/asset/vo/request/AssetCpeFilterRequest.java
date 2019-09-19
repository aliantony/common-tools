package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetCpeFilterRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCpeFilterRequest extends BaseRequest implements ObjectValidator {

    /**
     * 业务主键
     */
    @ApiModelProperty("业务主键")
    private Long    businessId;
    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    private String  productName;
    /**
     * 系列名称
     */
    @ApiModelProperty("系列名称")
    private String  serialName;
    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String  type;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long    gmtModified;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String  createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String  modifiedUser;
    /**
     * 状态：1-正常，0-删除
     */
    @ApiModelProperty("状态：1-正常，0-删除")
    private Integer status;

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
    public void validate() throws RequestParamValidateException {

    }

}