package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@ApiModel("准入资产请求类")
public class AssetEntryRequest extends BaseRequest {
    @ApiModelProperty("资产id集合，加密id")
    @NotEmpty
    private List<String> assetIds;
    @ApiModelProperty("准入状态：1-允许，2-禁止")
    @Pattern(regexp = "^[12]$",message = "准入状态参数错误，只能为1或2")
    private int updateStatus;

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }
}
