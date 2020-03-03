package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * AssetBusiness 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetBusinessQuery extends ObjectQuery {
    /**
     *  更新时间
     */
    @ApiModelProperty("更新时间起始")
    private Long gmtModifiedStart;
    @ApiModelProperty("更新时间结束")
    private Long gmtModifiedEnd;
    /**
     *  业务名称（中文字符，去重）
     */
    @ApiModelProperty("业务名称（中文字符，去重）")
    @Length(max = 50,message = "业务名称不能超过50个字符")
    private String name;
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getGmtModifiedStart() {
        return gmtModifiedStart;
    }

    public void setGmtModifiedStart(Long gmtModifiedStart) {
        this.gmtModifiedStart = gmtModifiedStart;
    }

    public Long getGmtModifiedEnd() {
        return gmtModifiedEnd;
    }

    public void setGmtModifiedEnd(Long gmtModifiedEnd) {
        this.gmtModifiedEnd = gmtModifiedEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}