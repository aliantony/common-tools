package com.antiy.asset.vo.query;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program asset
 * @description 
 * @author wangqian
 * created on 2020-05-11
 * @version  1.0.0
 */
public class QxTagQuery {
    @ApiModelProperty("权限tags")
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "QxTagQuery{" +
                "tags=" + tags.toString() +
                '}';
    }
}
