package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * <p> AssetCpuRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetImportRequest extends BasicRequest implements ObjectValidator {

    /**
     * 区域
     */
    @ApiModelProperty("区域")
    @Encode
    private String  areaId;
    /**
     * 用户
     */
    @ApiModelProperty("下一步操作人员")
    @Encode
    @NotNull(message = "操作人员不能为空")
    private String[] userId;
    /**
     * 序列号
     */
    @ApiModelProperty("备注")
    private String  memo;

    @Override
    public String toString() {
        return "AssetImportRequest{" +
                "areaId='" + areaId + '\'' +
                ", userId=" + Arrays.toString (userId) +
                ", memo='" + memo + '\'' +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String[] getUserId() {
        return userId;
    }

    public void setUserId(String[] userId) {
        this.userId = userId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}