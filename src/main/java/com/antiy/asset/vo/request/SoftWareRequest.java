package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.antiy.asset.base.BasicRequest;
import com.antiy.asset.encoder.Encode;
import com.antiy.asset.enums.SoftwareTypeEnum;
import com.antiy.asset.enums.Status;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.utils.ParamterExceptionUtils;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zhangbing
 * @Date: 2018/11/21 13:18
 * @Description: 软件信息VO对象
 */
@ApiModel(value = "软件对象")
public class SoftWareRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty(value = "资产Id")
    @NotNull(message = "资产Id不能为空")
    @Encode
    private String  assetId;

    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    @NotBlank(message = "软件名称不能为空")
    private String  softwareName;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    @NotBlank(message = "软件版本不能为空")
    private String  softwareVersion;

    /**
     * 制造商名字
     */
    @ApiModelProperty("制造商名字")
    private String  manufacturer;

    /**
     * 软件描述
     */
    @ApiModelProperty("软件描述")
    private String  softwareDescribe;

    /**
     * 软件类型: 0-未识别的(unIdentified),1-排除在外(excluded),2-免费软件(freeware),3-管理级(managed),4-禁止(prohibited),5-共享软件(shareware)
     */
    @ApiModelProperty("软件类型")
    @NotBlank(message = "软件类型不能为空")
    private String  softwareType;

    /**
     * 标签ID
     */
    @ApiModelProperty("标签ID")
    private Integer softLabel;

    /**
     * 状态:0-inactive,1-active
     */
    @ApiModelProperty("状态")
    @NotBlank(message = "软件状态不能为空")
    private String  status;

    @ApiModelProperty(value = "软件Id")
    @Encode
    private String  softWareId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSoftWareId() {
        return softWareId;
    }

    public void setSoftWareId(String softWareId) {
        this.softWareId = softWareId;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSoftwareDescribe() {
        return softwareDescribe;
    }

    public void setSoftwareDescribe(String softwareDescribe) {
        this.softwareDescribe = softwareDescribe;
    }

    public SoftwareTypeEnum getSoftwareType() {
        return SoftwareTypeEnum.getSoftwateByName(softwareType);
    }

    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    public Integer getSoftLabel() {
        return softLabel;
    }

    public void setSoftLabel(Integer softLabel) {
        this.softLabel = softLabel;
    }

    public Status getStatus() {
        return Status.getStatus(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        if (StringUtils.isNotBlank(softwareType)) {
            ParamterExceptionUtils.isNull(SoftwareTypeEnum.getSoftwateByName(softwareType), "软件类型错误");
        }

        if (StringUtils.isNotBlank(status)) {
            ParamterExceptionUtils.isNull(Status.getStatus(status), "软件状态错误");
        }
    }
}
