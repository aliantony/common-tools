package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.AssetEnterStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.ArrayUtils;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@ApiModel("准入资产查询")
public class AssetEntryQuery extends ObjectQuery implements ObjectValidator {
    @ApiModelProperty("综合查询条件")
    @Size(max = 30, message = "综合查询条件不能超过30")
    private String multipleQuery;
    @ApiModelProperty("资产组集合")
    @Encode
    private List<String> assetGroups;
    @ApiModelProperty("准入状态")
    private int[] entryStatus;
    @ApiModelProperty(value = "资产状态集合", hidden = true)
    private int[] assetStatus;

    @Override
    public void validate() throws RequestParamValidateException {
        if (ArrayUtils.isEmpty(entryStatus)) {
            int length = AssetEnterStatusEnum.values().length;
            entryStatus = new int[length];
            for (int i = 0; i < length; i++) {
                entryStatus[i] = AssetEnterStatusEnum.values()[i].getCode();
            }
        }
        if (ArrayUtils.isEmpty(assetStatus)) {
            assetStatus = new int[]{AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.IN_CHANGE.getCode(),
                    AssetStatusEnum.WAIT_RETIRE_CHECK.getCode(), AssetStatusEnum.RETIRE_DISAGREE.getCode(),
                    AssetStatusEnum.WAIT_RETIRE.getCode(), AssetStatusEnum.RETIRE.getCode()};
        }

    }

    public int[] getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(int[] assetStatus) {
        this.assetStatus = assetStatus;
    }


    public String getMultipleQuery() {
        return multipleQuery;
    }

    public void setMultipleQuery(String multipleQuery) {
        this.multipleQuery = multipleQuery;
    }

    public List<String> getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(List<String> assetGroups) {
        this.assetGroups = assetGroups;
    }

    public int[] getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(int[] entryStatus) {
        this.entryStatus = entryStatus;
    }
}
