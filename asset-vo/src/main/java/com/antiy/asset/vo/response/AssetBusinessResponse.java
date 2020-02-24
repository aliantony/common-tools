package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

/**
 * <p>
 * AssetBusinessResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetBusinessResponse extends BaseResponse{
    /**
     * 业务id
     */
    private String id;
    /**
     *  业务名称（中文字符，去重）
     */
    private String name;
    /**
     *  业务重要性：1-高，2-中，3-低
     */
    private Integer importance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }
}