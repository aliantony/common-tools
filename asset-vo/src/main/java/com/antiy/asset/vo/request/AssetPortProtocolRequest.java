package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetPortProtocolRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetPortProtocolRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产软件关系表主键
     */
    @ApiModelProperty("资产软件关系表主键")
    @Encode
    private String    assetSoftId;
    /**
     * id
     */
    @ApiModelProperty("id")
    @Encode
    private String    id;
    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private Integer[] port;
    /**
     * 协议
     */
    @ApiModelProperty("协议")
    private String    protocol;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String    description;

    public String getAssetSoftId() {
        return assetSoftId;
    }

    public void setAssetSoftId(String assetSoftId) {
        this.assetSoftId = assetSoftId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer[] getPort() {
        return port;
    }

    public void setPort(Integer[] port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}