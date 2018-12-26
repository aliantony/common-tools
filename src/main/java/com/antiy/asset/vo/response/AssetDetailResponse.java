package com.antiy.asset.vo.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 软件表,名字和版本为唯一建 </p>
 *
 * @author zhangyajun
 * @since 2018-11-21
 */
@ApiModel(value = "软件信息")
public class AssetDetailResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer           softwareId;

    /**
     * 软件名称
     */
    @ApiModelProperty("软件名称")
    private String            softwareName;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private String            softwareVersion;

    /**
     * 制造商名字
     */
    @ApiModelProperty("制造商名字")
    private String            manufacturer;

    /**
     * 软件描述
     */
    @ApiModelProperty("软件描述")
    private String            softwareDescribe;

    /**
     * 软件类型: 0-未识别的(unIdentified),1-排除在外(excluded),2-免费软件(freeware),3-管理级(managed),4-禁止(prohibited),5-共享软件(shareware)
     */
    @ApiModelProperty("软件类型: 0-未识别的(unIdentified),1-排除在外(excluded),2-免费软件(freeware),3-管理级(managed),4-禁止(prohibited),5-共享软件(shareware)")
    private String            softwareTypeCode;

    @ApiModelProperty(value = "软件类型msg")
    private String            softwareTypeMsg;

    /**
     * 标签ID
     */
    @ApiModelProperty("标签ID")
    private Integer           softLabel;

    /**
     * 状态:0-inactive,1-active
     */
    @ApiModelProperty("状态code")
    private String            statusCode;

    @ApiModelProperty(value = "状态msg")
    private String            statusMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date              gmtCreate;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date              gmtModified;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String            memo;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer           createUser;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer           modifyUser;

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

    public Integer getSoftLabel() {
        return softLabel;
    }

    public void setSoftLabel(Integer softLabel) {
        this.softLabel = softLabel;
    }

    public String getSoftwareTypeCode() {
        return softwareTypeCode;
    }

    public void setSoftwareTypeCode(String softwareTypeCode) {
        this.softwareTypeCode = softwareTypeCode;
    }

    public String getSoftwareTypeMsg() {
        return softwareTypeMsg;
    }

    public void setSoftwareTypeMsg(String softwareTypeMsg) {
        this.softwareTypeMsg = softwareTypeMsg;
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

}
