package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMonitorRule 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

@ApiModel
public class AssetBaseQuery extends ObjectQuery {
    @Encode
    @ApiModelProperty("区域")
    private List<String>  areaList;

    @ApiModelProperty("资产状态")
    private List<Integer> statusList;

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }
}