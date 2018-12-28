package com.antiy.asset.entity;


import java.util.Date;
import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 资产部门信息
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */

public class AssetDepartment extends BaseEntity {


private static final long serialVersionUID = 1L;

        /**
    *  部门名
    */
        @ApiModelProperty("部门名")
    private String name;
        /**
    *  负责人
    */
        @ApiModelProperty("负责人")
    private String responsibleUser;
        /**
    *  联系电话
    */
        @ApiModelProperty("联系电话")
    private String telephone;
        /**
    *  上级部门
    */
        @ApiModelProperty("上级部门")
    private Integer parentId;
        /**
    *  创建时间
    */
        @ApiModelProperty("创建时间")
    private Long gmtCreate;
        /**
    *  修改时间
    */
        @ApiModelProperty("修改时间")
    private Long gmtModified;
        /**
    *  备注
    */
        @ApiModelProperty("备注")
    private String memo;
        /**
    *  创建人
    */
        @ApiModelProperty("创建人")
    private Integer createUser;
        /**
    *  修改人
    */
        @ApiModelProperty("修改人")
    private Integer modifyUser;
        /**
    *  状态,0 未删除,1已删除
    */
        @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;

                    
                                    
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