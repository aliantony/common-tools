package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/25
 */
@ApiModel("资产回滚信息类")
public class AssetRollbackRequest extends BaseRequest {

    @ApiModelProperty("资产id")
    private String assetId;
    @ApiModelProperty("回滚信息")
    List<RollBack> rollBackInfo;
    @ApiModelProperty("创建时间")
    private long gmtCreate;
    @ApiModelProperty("修改时间")
    private long gmtModified;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("修改人")
    private String modifyUser;

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public List<RollBack> getRollBackInfo() {
        return rollBackInfo;
    }

    public void setRollBackInfo(List<RollBack> rollBackInfo) {
        this.rollBackInfo = rollBackInfo;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public static class RollBack {
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

}


