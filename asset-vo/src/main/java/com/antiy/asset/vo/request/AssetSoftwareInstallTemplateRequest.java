package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;

/**
 * <p> AssetSoftwareInstallTemplateRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetSoftwareInstallTemplateRequest extends BaseRequest implements ObjectValidator {

    /**
     * 模板主键
     */
    @ApiModelProperty("模板主键")
    private Integer installTemplateId;
    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 状态：1-正常，0-删除
     */
    @ApiModelProperty("状态：1-正常，0-删除")
    private Integer status;

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
    public void validate() throws RequestParamValidateException {

    }

}