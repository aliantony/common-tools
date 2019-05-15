package com.antiy.asset.vo.request;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @program csom
 * @description 合并或迁移区域请求
 * @author wangqian created on 2019-01-18
 * @version 1.0.0
 */
public class AreaOperationRequest {

    @ApiModelProperty("源区域id数组")
    @NotEmpty(message = "源区域id数组不能为空")
    @Encode
    private List<String> sourceAreaIds;

    @ApiModelProperty("目标区域id")
    @NotBlank(message = "目标区域id不能为空")
    @Encode
    private String targetAreaId;

    public List<String> getSourceAreaIds() {
        return sourceAreaIds;
    }

    public void setSourceAreaIds(List<String> sourceAreaIds) {
        this.sourceAreaIds = sourceAreaIds;
    }

    public String getTargetAreaId() {
        return targetAreaId;
    }

    public void setTargetAreaId(String targetAreaId) {
        this.targetAreaId = targetAreaId;
    }
}
