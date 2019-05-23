package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetStatusTaskRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetStatusTaskRequest extends BaseRequest implements ObjectValidator {

    /**
     * 流程任务主键
     */
    @ApiModelProperty("流程任务主键")
    private Integer taskId;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}