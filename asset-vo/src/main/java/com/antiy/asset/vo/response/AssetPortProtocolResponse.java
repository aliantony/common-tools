package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetPortProtocolResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetPortProtocolResponse extends BaseResponse {
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
     * 描述
     */
    @ApiModelProperty("描述")
    private String  description;
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
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    @ApiModelProperty("状态,1未删除,0已删除")
    private Integer status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}