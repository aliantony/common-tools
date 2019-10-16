package com.antiy.asset.vo.response;

import com.antiy.common.base.SysUser;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * <p> AssetInstallTemplateResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateResponse {

    @ApiModelProperty("模板id")
    @Encode
    private String stringId;
    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板编号")
    private String numberCode;

    @ApiModelProperty("品类型号")
    private Integer categoryModel;

    @ApiModelProperty("模板状态")
    private Integer currentStatus;

    @ApiModelProperty("适用操作系统编号")
    private Long operationSystem;

    @ApiModelProperty("适用操作系统名称")
    private String operationSystemName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建时间")
    private Long gmtCreate;

    @ApiModelProperty("修改时间")
    private Long gmtModified;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("修改人")
    private String modifiedUser;
    @ApiModelProperty("补丁数量")
    private Integer patchNum;
    @ApiModelProperty("软件数量")
    private Integer softNum;
    @ApiModelProperty("配合基准那边给与明文id")
    private String templateId;
    @ApiModelProperty("执行人信息")
    private List<AssetSysUserResponse> executor;
    @ApiModelProperty("代办任务")
    private WaitingTaskReponse waitingTask;
    @ApiModelProperty("软件业务ids")
    private List<String> softBusinessIds;


    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public Integer getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(Integer categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getOperationSystem() {
        return String.valueOf(operationSystem);
    }

    public void setOperationSystem(Long operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getPatchNum() {
        return patchNum;
    }

    public void setPatchNum(Integer patchNum) {
        this.patchNum = patchNum;
    }

    public Integer getSoftNum() {
        return softNum;
    }

    public void setSoftNum(Integer softNum) {
        this.softNum = softNum;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<AssetSysUserResponse> getExecutor() {
        return executor;
    }

    public void setExecutor(List<AssetSysUserResponse> executor) {
        this.executor = executor;
    }

    public WaitingTaskReponse getWaitingTask() {
        return waitingTask;
    }

    public void setWaitingTask(WaitingTaskReponse waitingTask) {
        this.waitingTask = waitingTask;
    }

    public List<String> getSoftBusinessIds() {
        return softBusinessIds;
    }

    public void setSoftBusinessIds(List<String> softBusinessIds) {
        this.softBusinessIds = softBusinessIds;
    }
}