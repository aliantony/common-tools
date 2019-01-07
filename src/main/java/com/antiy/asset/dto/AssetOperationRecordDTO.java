package com.antiy.asset.dto;


import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 资产操作记录表 数据对象
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */

public class AssetOperationRecordDTO extends BaseEntity {

        /**
    *  被操作的对象ID
    */
        @ApiModelProperty("被操作的对象ID")
    private Integer targetObject;
        /**
    *  被修改的表名称
    */
        @ApiModelProperty("被修改的表名称")
    private String targetTableName;
        /**
    *  操作内容
    */
        @ApiModelProperty("操作内容")
    private String content;
        /**
    *  创建人
    */
        @ApiModelProperty("创建人")
    private Integer createUser;
        /**
    *  创建时间
    */
        @ApiModelProperty("创建时间")
    private Long gmtCreate;

                    
                                    
    public Integer getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Integer targetObject) {
        this.targetObject = targetObject;
    }
    
                                    
    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }
    
                                    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
                                    
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    
                                    
    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    
    
        @Override
    public String toString() {
            return "AssetOperationRecord{" +
                                                                                            ", targetObject=" + targetObject +
                                                                                        ", targetTableName=" + targetTableName +
                                                                                        ", content=" + content +
                                                                                        ", createUser=" + createUser +
                                                                                        ", gmtCreate=" + gmtCreate +
                                                "}";
    }
    }