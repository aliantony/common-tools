package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.ArrayUtils;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@ApiModel(value = "AssetEntryQuery",description = "准入资产查询")
public class AssetEntryQuery extends ObjectQuery implements ObjectValidator {
    @ApiModelProperty("综合查询条件")
    @Size(max = 30, message = "综合查询条件不能超过30")
    private String multipleQuery;
    @ApiModelProperty("资产组集合")
    @Encode
    private List<String> assetGroups;
    @ApiModelProperty("准入状态：1已允许，2已禁止")
    @Pattern(regexp = "^\\s*[12]?$",message = "准入状态参数只能为1或2")
    private String entryStatus;
    @ApiModelProperty(value = "资产状态集合")
    private int[] assetStatus;
    @ApiModelProperty("资产id,未加密")
    private String assetId;
    @ApiModelProperty("资产ids,未加密")
    private String[] assetIds;
    @ApiModelProperty(value = "区域id集合",hidden = true)
    private List<String> areaIds;
    @ApiModelProperty(value = "资产类型",hidden = true)
    private List<String>  assetCategorys;
    @ApiModelProperty("导出开始条数")
    private Integer start;
    @ApiModelProperty("导出结束条数")
    private Integer end;




    @Override
    public void validate() throws RequestParamValidateException {
        if (ArrayUtils.isEmpty(assetStatus)) {
            assetStatus = new int[]{AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.IN_CHANGE.getCode(),
                    AssetStatusEnum.WAIT_RETIRE.getCode(), AssetStatusEnum.RETIRE.getCode(),
                    AssetStatusEnum.WAIT_SCRAP.getCode(), AssetStatusEnum.SCRAP.getCode()};
        }

    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
    public List<String> getAssetCategorys() {
        return assetCategorys;
    }

    public void setAssetCategorys(List<String> assetCategorys) {
        this.assetCategorys = assetCategorys;
    }

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
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

    public String getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(String entryStatus) {
        this.entryStatus = entryStatus;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String[] getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String[] assetIds) {
        this.assetIds = assetIds;
    }
}
