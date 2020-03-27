package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/3/27
 */
public class RollBack {
    @ApiModelProperty("回滚字段名")
    private String filedName;
    @ApiModelProperty("回滚字段值")
    private String filedValue;
    @ApiModelProperty("回滚表名")
    private String rollbackTableName;
    @ApiModelProperty("回滚前的操作类型:1-add,2-delete,3-update")
    private int operationType;
    @ApiModelProperty("filedValue为关联表的id时，此字段展示该id所在主表的名称，适用于组件表和软件表")
    private String name;

    public RollBack() {
    }

    public RollBack(String filedName, String filedValue, String rollbackTableName, int operationType) {
        this.filedName = filedName;
        this.filedValue = filedValue;
        this.rollbackTableName = rollbackTableName;
        this.operationType = operationType;
    }

    public RollBack(String filedName, String filedValue, String rollbackTableName, int operationType, String name) {
        this.filedName = filedName;
        this.filedValue = filedValue;
        this.rollbackTableName = rollbackTableName;
        this.operationType = operationType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getFiledValue() {
        return filedValue;
    }

    public void setFiledValue(String filedValue) {
        this.filedValue = filedValue;
    }

    public String getRollbackTableName() {
        return rollbackTableName;
    }

    public void setRollbackTableName(String rollbackTableName) {
        this.rollbackTableName = rollbackTableName;
    }
}
