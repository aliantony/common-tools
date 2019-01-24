package com.antiy.asset.vo.response;

import java.util.List;

import com.antiy.common.encoder.Encode;

/**
 * <p> AssetGroupResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupResponse extends BaseResponse {
    /**
     * 资产组ID
     */
    @Encode
    private String id;
    /**
     * 资产组名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Long   gmtCreate;
    /**
     * 备注
     */
    private String memo;

    /**
     * 创建人名字
     */
    private String createUserName;

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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<String> getAssetList() {
        return assetList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAssetList(List<String> assetList) {
        this.assetList = assetList;
    }

    @Override
    public String toString() {
        return "AssetGroupResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", memo='" + memo + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", assetList=" + assetList +
                '}';
    }
}