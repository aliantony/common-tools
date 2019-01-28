package com.antiy.asset.vo.response;

import java.util.List;

/**
 * <p> AssetGroupResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupResponse extends BaseResponse {
    /**
     * 资产组名称
     */
    private String name;

    /**
     * 备注
     */
    private String memo;

    /**
     * 资产信息
     */
    List<String>   assetList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<String> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<String> assetList) {
        this.assetList = assetList;
    }

    @Override
    public String toString() {
        return "AssetGroupResponse{" + ", name='" + name + '\'' + ", gmtCreate=" + memo + '\'' + ", createUserName='"
               + ", assetList=" + assetList + '}';
    }
}