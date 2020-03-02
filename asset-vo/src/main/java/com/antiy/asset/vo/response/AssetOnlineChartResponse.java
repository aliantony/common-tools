package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应对象
 * @author zhangyajun
 * @create 2020-02-28 11:19
 **/
@ApiModel("资产在线情况")
public class AssetOnlineChartResponse {
    @ApiModelProperty("横坐标数据")
    private List<String> xData;
    @ApiModelProperty("折线点数据 ")
    private NameValueVo  coordinate;

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public NameValueVo getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(NameValueVo coordinate) {
        this.coordinate = coordinate;
    }
}
