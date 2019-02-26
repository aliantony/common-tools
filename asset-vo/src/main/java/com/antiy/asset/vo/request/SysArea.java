package com.antiy.asset.vo.request;

import java.io.Serializable;
import java.util.List;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 行政区划信息
 *
 * @author wangqian
 * @since 2019-01-10
 */
public class SysArea extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 父行政区划id */
    @ApiModelProperty("父行政区划id")
    private String            parentId;
    /** 所属层级 1、省级 2、市级 3、县级 */
    @ApiModelProperty("所属层级 1、省级 2、市级 3、县级")
    private Integer           levelType;
    /** 行政区划全称 */
    @ApiModelProperty("行政区划全称")
    private String            fullName;
    /** 行政区划简称 */
    @ApiModelProperty("行政区划简称")
    private String            shortName;
    /** 行政区划全拼 */
    @ApiModelProperty("行政区划全拼")
    private String            fullSpell;
    /** 行政区划简拼 */
    @ApiModelProperty("行政区划简拼")
    private String            shortSpell;
    /** 状态,1、未删除 0、已删除 */
    @ApiModelProperty("状态,1、未删除 0、已删除")
    private Integer           status;

    @ApiModelProperty("子区域")
    private List<com.antiy.common.base.SysArea>     child;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullSpell() {
        return fullSpell;
    }

    public void setFullSpell(String fullSpell) {
        this.fullSpell = fullSpell;
    }

    public String getShortSpell() {
        return shortSpell;
    }

    public void setShortSpell(String shortSpell) {
        this.shortSpell = shortSpell;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<com.antiy.common.base.SysArea> getChild() {
        return child;
    }

    public void setChild(List<com.antiy.common.base.SysArea> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "SysArea{" + ", parentId=" + parentId + ", levelType=" + levelType + ", fullName=" + fullName
               + ", shortName=" + shortName + ", fullSpell=" + fullSpell + ", shortSpell=" + shortSpell + ", status="
               + status + "}";
    }
}
