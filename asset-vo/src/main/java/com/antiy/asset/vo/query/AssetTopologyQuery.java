package com.antiy.asset.vo.query;

import java.util.List;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetTopology 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetTopologyQuery extends ObjectQuery {

    /**
     * 资产id
     */
    @Encode
    @ApiModelProperty("资产id")
    private String        assetId;

    /**
     * 拓扑类型
     */
    private String        topologyType;

    /**
     * 登录用户所属区域ids
     */
    private List<Integer> userAreaIds;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getTopologyType() {
        return topologyType;
    }

    public void setTopologyType(String topologyType) {
        this.topologyType = topologyType;
    }

    public List<Integer> getUserAreaIds() {
        return userAreaIds;
    }

    public void setUserAreaIds(List<Integer> userAreaIds) {
        this.userAreaIds = userAreaIds;
    }
}