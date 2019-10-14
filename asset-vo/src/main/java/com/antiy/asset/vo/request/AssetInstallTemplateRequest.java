package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p> AssetInstallTemplateRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateRequest extends BaseRequest implements ObjectValidator {
    /**
     * 模板名称
     */
    @ApiModelProperty("模板名称")
    @Size(max = 80, min = 1)
    private String name;
    /**
     * 模板编号
     */
    @ApiModelProperty("模板编号")
    @Size(max = 30, min = 1)
    private String numberCode;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private Integer categoryModel;
    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private Integer checkStatus;
    /**
     * 适用操作系统
     */
    @ApiModelProperty("适用操作系统")
    private Long operationSystem;
    /**
     * 操作系统名称
     */
    @ApiModelProperty("操作系统名称")
    private String operationSystemName;
    /**
     * 0-启用/禁用
     * 1-模板编辑
     */
    @ApiModelProperty("0-启用/禁用,1-模板编辑")
    // @NotNull
    private Integer isUpdateStatus;
    @ApiModelProperty("要更新的状态")
    @Range(message = "状态不合法", max = 4, min = 1)
    private Integer updateStatus;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Size(max = 500, min = 0)
    private String description;
    @ApiModelProperty("关联的软件业务id集合")
    private Set<String> softBussinessIds;
    @ApiModelProperty("关联的补丁主键id集合")
    private Set<String> patchIds;
    @ApiModelProperty("模板审核的用户主键id集合")
    private Set<String> nextExecutor;
    @ApiModelProperty("创建时间")
    private long gmtCreate;
    @ApiModelProperty("修改时间")
    private long gmtModified;
    @ApiModelProperty("创建用户")
    private String createUser;
    @ApiModelProperty("修改用户")
    private String modifiedUser;
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("执行人id")
    private Map formData;

    public Set<String> getSoftBussinessIds() {
        return softBussinessIds;
    }

    public void setSoftBussinessIds(Set<String> softBussinessIds) {
        this.softBussinessIds = softBussinessIds;
    }

    public Set<String> getPatchIds() {
        return patchIds;
    }

    public void setPatchIds(Set<String> patchIds) {
        this.patchIds = patchIds;
    }

    public Set<String> getNextExecutor() {
        return nextExecutor;
    }

    public void setNextExecutor(Set<String> nextExecutor) {
        this.nextExecutor = nextExecutor;
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

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getOperationSystem() {
        return operationSystem;
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

    public Integer getIsUpdateStatus() {
        return isUpdateStatus;
    }

    public void setIsUpdateStatus(Integer isUpdateStatus) {
        this.isUpdateStatus = isUpdateStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getCurrentStatus() {
        return updateStatus;
    }

    public void setCurrentStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
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

    public Map getFormData() {
        StringBuilder builder = new StringBuilder();
        this.getNextExecutor().forEach(v -> {
            builder.append(v);
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);
        Map<String, String> map = new HashMap<>();
        map.put("checkUser", builder.toString());
        return map;
    }

    public void setFormData(Map formData) {
        this.formData = formData;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "AssetInstallTemplateRequest{" +
                "name='" + name + '\'' +
                ", numberCode='" + numberCode + '\'' +
                ", categoryModel=" + categoryModel +
                ", checkStatus=" + checkStatus +
                ", operationSystem=" + operationSystem +
                ", operationSystemName='" + operationSystemName + '\'' +
                ", isUpdateStatus=" + isUpdateStatus +
                ", updateStatus=" + updateStatus +
                ", description='" + description + '\'' +
                ", softBussinessIds=" + softBussinessIds +
                ", patchIds=" + patchIds +
                ", nextExecutor=" + nextExecutor +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", createUser='" + createUser + '\'' +
                ", modifiedUser='" + modifiedUser + '\'' +
                ", taskId='" + taskId + '\'' +
                ", formData=" + formData +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }

}