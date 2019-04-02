package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLinkRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLinkRelationRequest extends BaseRequest implements ObjectValidator {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode(message = "子资产主键错误")
    private String  assetId;
    /**
     * 资产IP
     */
    @ApiModelProperty("资产IP")
    private String  assetIp;
    /**
     * 资产端口
     */
    @ApiModelProperty("资产端口")
    private String  assetPort;
    /**
     * 父级设备主键
     */
    @ApiModelProperty("父级设备主键")
    @Encode(message = "父级设备主键错误")
    private String  parentAssetId;
    /**
     * 父级设备IP
     */
    @ApiModelProperty("父级设备IP")
    private String  parentAssetIp;
    /**
     * 父级设备端口
     */
    @ApiModelProperty("父级设备端口")
    private String  parentAssetPort;
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

    public String getAssetIp() {
        return assetIp;
    }

    public void setAssetIp(String assetIp) {
        this.assetIp = assetIp;
    }

    public String getAssetPort() {
        return assetPort;
    }

    public void setAssetPort(String assetPort) {
        this.assetPort = assetPort;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(String parentAssetId) {
        this.parentAssetId = parentAssetId;
    }

    public String getParentAssetIp() {
        return parentAssetIp;
    }

    public void setParentAssetIp(String parentAssetIp) {
        this.parentAssetIp = parentAssetIp;
    }

    public String getParentAssetPort() {
        return parentAssetPort;
    }

    public void setParentAssetPort(String parentAssetPort) {
        this.parentAssetPort = parentAssetPort;
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

    @Override
    public void validate() throws RequestParamValidateException {

    }

}