package com.antiy.asset.vo.query;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetOperationRecord 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@ApiModel(value = "资产流程查询条件")
public class AssetOperationRecordQuery extends ObjectQuery {

    /**
     * 被操作的对象ID
     */
    @Encode
    @ApiModelProperty(value = "资产Id")
    @NotBlank(message = "资产Id不能为空")
    private String                  targetObjectId;

    /**
     * 被操作对象类型
     */
    @NotNull(message = "操作对象类型不能为空")
    @ApiModelProperty(value = "资产类型")
    private AssetOperationTableEnum targetType;

    @ApiModelProperty(value = "当前资产的状态")
    private String                  originStatus;

    public String getOriginStatus() {
        return originStatus;
    }

    public void setOriginStatus(String originStatus) {
        this.originStatus = originStatus;
    }

    public String getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(String targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public AssetOperationTableEnum getTargetType() {
        return targetType;
    }

    public void setTargetType(AssetOperationTableEnum targetType) {
        this.targetType = targetType;
    }
}