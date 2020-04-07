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