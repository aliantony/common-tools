package com.antiy.asset.vo.request;

import com.antiy.asset.vo.enums.AssetEntrySourceEnum;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@ApiModel(description = "准入资产请求类")
public class AssetEntryRequest extends BasicRequest  implements ObjectValidator {
    @Valid
    @ApiModelProperty("资产集合")
    @NotEmpty(message = "资产不能为空")
    private List<ActivityHandleRequest> assetActivityRequests;
    @ApiModelProperty("准入状态：1-允许，2-禁止")
    @Pattern(regexp = "^[12]$", message = "准入状态参数错误，只能为1或2")
    private String updateStatus;
    @ApiModelProperty("准入指令来源:  ASSET_ENTER_NET(资产入网)," +
            "ASSET_RETIRE(资产退回)," +
            "VUL_SCAN(漏洞扫描)," +
            "CONFIG_SCAN(配置扫描)," +
            "PATCH_INSTALL(补丁安装)," +
            "ENTRY_MANAGE(准入管理)," +
            "ASSET_SCRAP(资产报废)," +
            "ASSET_CHANGE(资产变更)")
    private AssetEntrySourceEnum entrySource;

    public List<ActivityHandleRequest> getAssetActivityRequests() {
        return assetActivityRequests;
    }

    public void setAssetActivityRequests(List<ActivityHandleRequest> assetActivityRequests) {
        this.assetActivityRequests = assetActivityRequests;
    }


    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public AssetEntrySourceEnum getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(AssetEntrySourceEnum entrySource) {
        this.entrySource = entrySource;
    }

    @Override
    public String toString() {
        return "AssetEntryRequest{" +
                "assetActivityRequests=" + assetActivityRequests +
                ", updateStatus='" + updateStatus + '\'' +
                ", entrySource=" + entrySource +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
