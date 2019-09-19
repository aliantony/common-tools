package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetAssemblyLibRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyLibRequest extends BaseRequest implements ObjectValidator {

    /**
     * 业务主键
     */
    @ApiModelProperty("业务主键")
    private Long    businessId;
    /**
     * 组件类型
     */
    @ApiModelProperty("组件类型")
    private String  type;
    /**
     * 供应商
     */
    @ApiModelProperty("供应商")
    private String  supplier;
    /**
     * 产品名
     */
    @ApiModelProperty("产品名")
    private String  productName;
    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private String  version;
    /**
     * 系统版本
     */
    @ApiModelProperty("系统版本")
    private String  sysVersion;
    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String  language;
    /**
     * 其他
     */
    @ApiModelProperty("其他")
    private String  other;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long    gmtModified;
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
     * 是否入库：1已入库、2未入库
     */
    @ApiModelProperty("是否入库：1已入库、2未入库")
    private Integer isStorage;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
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

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
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