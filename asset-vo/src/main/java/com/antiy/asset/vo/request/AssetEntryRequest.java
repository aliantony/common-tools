package com.antiy.asset.vo.request;

import com.antiy.asset.vo.enums.AssetEntrySourceEnum;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@ApiModel(description = "准入资产请求类")
public class AssetEntryRequest extends BaseRequest implements ObjectValidator {
    @ApiModelProperty("资产id集合，加密id")
    @NotEmpty
    private List<String> assetIds;
    @ApiModelProperty("准入状态：1-允许，2-禁止")
    @Pattern(regexp = "^[12]$",message = "准入状态参数错误，只能为1或2")
    private int updateStatus;
    @ApiModelProperty("准入指令来源")
    private AssetEntrySourceEnum entrySource;

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

    public AssetEntrySourceEnum getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(AssetEntrySourceEnum entrySource) {
        this.entrySource = entrySource;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
