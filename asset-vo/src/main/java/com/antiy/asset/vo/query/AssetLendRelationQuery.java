package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
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