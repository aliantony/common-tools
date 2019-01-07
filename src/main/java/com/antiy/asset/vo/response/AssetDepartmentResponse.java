package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetDepartmentResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetDepartmentResponse {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer id;
    /**
     * 部门名
     */
    @ApiModelProperty("部门名")
    private String name;
    /**
     * 负责人
     */
    @ApiModelProperty("负责人")
    private String responsibleUser;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String telephone;
    /**
     * 上级部门
     */
    @ApiModelProperty("上级部门")
    private Integer parentId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
    public String toString() {
        return "AssetDepartment{" +
                ", name=" + name +
                ", responsibleUser=" + responsibleUser +
                ", telephone=" + telephone +
                ", parentId=" + parentId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo=" + memo +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                "}";
    }
}