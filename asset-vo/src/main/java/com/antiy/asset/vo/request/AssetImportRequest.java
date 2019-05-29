package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
     * 品类型号
     */
    @ApiModelProperty("品类型号：1.计算设备2，安全设备3，网络设备，4，存储设备5，其他设备")
    @Encode
    @NotBlank(message = "品类型号不能为空")
    private String   category;
    /**
     * 用户
     */
    @ApiModelProperty("下一步操作人员")
    @Encode
//    @NotNull(message = "操作人员不能为空")
    private String[] userId;
    /**
     * 序列号
     */
    @ApiModelProperty("备注")
    @Size(message = "备注不能超过300个字符",max = 300, min = 5)
    private String  memo;

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String[] getUserId() {
        return userId;
    }

    public void setUserId(String[] userId) {
        this.userId = userId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}