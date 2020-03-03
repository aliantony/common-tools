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

public class AssetBusinessResponse extends BaseResponse {
    /**
     * 业务id
     */
    private Integer id;
    /**
     *  业务名称（中文字符，去重）
     */
    private String name;
    /**
     *  业务重要性：1-高，2-中，3-低
     */
    private Integer importance;

    private String importanceDesc;
    /**
     *  描述
     */
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImportanceDesc() {
        if(importance==1)
            return "高";
        if(importance==2)
            return "中";
        if(importance==3)
            return "低";
        return null;
    }

    /**
     *  创建时间
     */
    private Long gmtCreate;
    /**
     *  更新时间
     */
    private Long gmtModified;

    private Integer assetCount;

    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(Integer assetCount) {
        this.assetCount = assetCount;
    }
}