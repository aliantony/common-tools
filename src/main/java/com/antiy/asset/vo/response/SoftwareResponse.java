package com.antiy.asset.vo.response;

import java.util.Date;

import com.antiy.asset.encoder.Encode;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 软件表,名字和版本为唯一建 </p>
 *
 * @author zhangyajun
 * @since 2018-11-21
 */
@ApiModel(value = "软件信息")
public class SoftwareResponse {

    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    @Encode
    private Integer softwareId;

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
    @ApiModelProperty("软件类型code")
    private String  softwareTypeCode;

    @ApiModelProperty(value = "软件类型msg")
    private String  softwareTypemsg;

    /**
     * 状态:0-inactive,1-active
     */
    @ApiModelProperty("状态")
    private String  statusCode;

    @ApiModelProperty(value = "软件状态msg")
    private String  statusMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date    gmtCreate;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date    gmtModified;

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

    @ApiModelProperty(value = "资产Id")
    private Integer assetId;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
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

    public String getSoftwareTypeCode() {
        return softwareTypeCode;
    }

    public void setSoftwareTypeCode(String softwareTypeCode) {
        this.softwareTypeCode = softwareTypeCode;
    }

    public String getSoftwareTypemsg() {
        return softwareTypemsg;
    }

    public void setSoftwareTypemsg(String softwareTypemsg) {
        this.softwareTypemsg = softwareTypemsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}
