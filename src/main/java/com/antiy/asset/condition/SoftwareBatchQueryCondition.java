package com.antiy.asset.condition;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.antiy.asset.base.ObjectQuery;
import com.antiy.asset.enums.SoftwareStatusEnum;
import com.antiy.asset.enums.SoftwareTypeEnum;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.utils.ParamterExceptionUtils;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/22 09:28
 * @Description: 软件列表和导出查询条件
 */
@ApiModel(value = "软件列表查询条件")
public class SoftwareBatchQueryCondition extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty(value = "IT资产ID")
    @NotNull(message = "IT资产Id不能为空")
    private Integer assetId;

    @ApiModelProperty(value = "软件类型,UN_IDENTIFIED(未识别),EXCLUDED(排除),FREE_WARE(免费),MANAGED(管理),PROHIBITED(禁止),SHARE_WARE(共享)")
    private String  softwareType;

    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    private String  softwareName;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private String  softwareVersion;

    @ApiModelProperty(value = "状态,ACTIVE(生效),IN_ACTIVE(失效)")
    private String  status;

    @ApiModelProperty(value = "是否需要查询license,默认false 不查询，true为查询")
    private Boolean isQueryLicense = false;

    public Boolean getQueryLicense() {
        return isQueryLicense;
    }

    public void setQueryLicense(Boolean queryLicense) {
        isQueryLicense = queryLicense;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getSoftwareType() {
        return softwareType;
    }

    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (StringUtils.isNotBlank(softwareType)) {
            ParamterExceptionUtils.isNull(SoftwareTypeEnum.getSoftwateByName(softwareType), "软件类型参数错误");
        }
        if (StringUtils.isNotBlank(status)) {
            ParamterExceptionUtils.isNull(SoftwareStatusEnum.getSoftStatusByCode(status), "软件状态参数错误");
        }
    }
}
