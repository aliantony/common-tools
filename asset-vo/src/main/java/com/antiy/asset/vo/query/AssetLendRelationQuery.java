package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * AssetLendRelation 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLendRelationQuery extends ObjectQuery {
    @ApiModelProperty("导出开始条数")
    private Integer       start;

    @ApiModelProperty("导出结束条数")
    private Integer       end;
    @ApiModelProperty("综合查询")
    private String multiQuery;
    @ApiModelProperty("类型")
    private List<String> categoryModels;
    @ApiModelProperty("状态")
    private List<Integer> lendStatus;
    @ApiModelProperty("当前使用者id")
    private Integer currentUseId;
    @ApiModelProperty("出借日期")
    private Long  lendTime;
    @ApiModelProperty("归还日期")
    private  Long  lendPeriods;
    @ApiModelProperty("区域")
    @Encode
    private List<String> areaIds;

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
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

    public Integer getCurrentUseId() {
        return currentUseId;
    }

    public void setCurrentUseId(Integer currentUseId) {
        this.currentUseId = currentUseId;
    }

    public Long getLendTime() {
        return lendTime;
    }

    public void setLendTime(Long lendTime) {
        this.lendTime = lendTime;
    }

    public Long getLendPeriods() {
        return lendPeriods;
    }

    public void setLendPeriods(Long lendPeriods) {
        this.lendPeriods = lendPeriods;
    }

    public String getMultiQuery() {
        return multiQuery;
    }

    public void setMultiQuery(String multiQuery) {
        this.multiQuery = multiQuery;
    }

    public List<String> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<String> categoryModels) {
        this.categoryModels = categoryModels;
    }

    public List<Integer> getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(List<Integer> lendStatus) {
        this.lendStatus = lendStatus;
    }
}