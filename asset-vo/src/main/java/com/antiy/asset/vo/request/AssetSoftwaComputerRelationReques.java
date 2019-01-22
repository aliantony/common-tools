package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Filename: AssetSoftwaComputerReques Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2019/1/18
 */
public class AssetSoftwaComputerRelationReques extends BasicRequest implements ObjectValidator {

    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String licenseSecretKey;
    /**
     * 软件ID
     */
    @ApiModelProperty("softwareid")
    @Encode
    private String softwareid;
    /**
     * 软件名称
     */
    @ApiModelProperty("softwareName")
    private String softwareName;

    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private String port;
    /**
     * 协议
     */
    @ApiModelProperty("协议")
    private String protocol;
    /**
     * /** 许可密钥
     */
    @ApiModelProperty("端口描述")
    private String portMemo;

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    public String getSoftwareid() {
        return softwareid;
    }

    public void setSoftwareid(String softwareid) {
        this.softwareid = softwareid;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPortMemo() {
        return portMemo;
    }

    public void setPortMemo(String portMemo) {
        this.portMemo = portMemo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
