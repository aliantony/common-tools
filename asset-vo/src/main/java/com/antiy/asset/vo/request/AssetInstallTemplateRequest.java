package com.antiy.asset.vo.request;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p> AssetInstallTemplateRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetInstallTemplateRequest implements ObjectValidator {
    @ApiModelProperty("主键Id")
    // @Encode
    private String stringId;

    /**
     * 模板名称
     */
    @ApiModelProperty("模板名称")
    @Size(max = 80, min = 1)
    @NotBlank(message = "模板名称不能为空")
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
     * 是否启用：0-禁用，1-启用
     */
    @ApiModelProperty("是否启用：0-禁用，1-启用")
    // @Min(value = 0, message = "是否启用只能为0，1")
    // @Max(value = 1, message = "是否启用只能为0，1")
    private Integer enable;
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
    private List<Long> softBussinessIds;
    @ApiModelProperty("关联的补丁主键id集合")
    private List<Integer> patchIds;
    @ApiModelProperty("模板审核的用户主键id集合")
    @Encode
    private List<Integer> nextExecutor;
    @ApiModelProperty("创建时间")
    private long gmtCreate;
    @ApiModelProperty("修改时间")
    private long gmtModified;
    @ApiModelProperty("创建用户")
    private String createUser;
    @ApiModelProperty("修改用户")
    private String modifiedUser;

    public List<Long> getSoftBussinessIds() {
        return softBussinessIds;
    }

    public void setSoftBussinessIds(List<Long> softBussinessIds) {
        this.softBussinessIds = softBussinessIds;
    }

    public List<Integer> getPatchIds() {
        return patchIds;
    }

    public void setPatchIds(List<Integer> patchIds) {
        this.patchIds = patchIds;
    }

    public List<Integer> getNextExecutor() {
        return nextExecutor;
    }

    public void setNextExecutor(List<Integer> nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
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

    public Integer getId() {
        return Integer.valueOf(stringId);
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

    @Override
    public void validate() throws RequestParamValidateException {
    }

}