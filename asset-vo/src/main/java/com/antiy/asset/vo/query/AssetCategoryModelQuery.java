package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * <p> AssetCategoryModel 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCategoryModelQuery extends ObjectQuery implements ObjectValidator {

    /**
     * id
     */
    @ApiModelProperty("id")
    @Encode
    private String   id;

    /**
     * 品类型号id列表
     */
    @Encode
    @ApiModelProperty("ids")
    private String[] ids;

    /**
     * 名称
     */
    @ApiModelProperty("类型名称")
    private String   name;
    /**
     * 类型:1-品类，2-型号
     */
    @ApiModelProperty("类型:1-品类，2-型号")
    private Integer  type;
    /**
     * 资产类型:1软件，2硬件
     */
    @ApiModelProperty("资产类型:1软件，2硬件")
    private Integer  assetType;
    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    @Encode
    private String   parentId;

    @ApiModelProperty("status")
    private Integer  status;
    @ApiModelProperty("是否来源于借用管理：true是,false否")
    private boolean sourceOfLend;

    public boolean isSourceOfLend() {
        return sourceOfLend;
    }

    public void setSourceOfLend(boolean sourceOfLend) {
        this.sourceOfLend = sourceOfLend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getParentId() {
        return parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetCategoryModelQuery{" +
                "id='" + id + '\'' +
                ", ids=" + Arrays.toString(ids) +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", assetType=" + assetType +
                ", parentId='" + parentId + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}