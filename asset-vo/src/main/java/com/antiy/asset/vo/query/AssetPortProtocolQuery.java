package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetPortProtocol 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetPortProtocolQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 资产软件关系表主键
     */
    @ApiModelProperty("资产软件关系表主键")
    @Encode
    private String  assetSoftId;
    /**
     * 端口
     */
    @ApiModelProperty("端口")
    private Integer port;
    /**
     * 协议
     */
    @ApiModelProperty("协议")
    private String  protocol;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Long    gmtModified;

    public String getAssetSoftId() {
        return assetSoftId;
    }

    public void setAssetSoftId(String assetSoftId) {
        this.assetSoftId = assetSoftId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    @Override
    public void validate() throws RequestParamValidateException {

    }
}