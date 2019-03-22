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
     * 创建人ID
     */
    private Integer createUser;

    /**
     * 资产信息
     */
    private List<String>   assetList;

    /**
     * 资产明细
     */
    private String   assetDetail;

    public String getAssetDetail() {
        return assetDetail;
    }

    public void setAssetDetail(String assetDetail) {
        this.assetDetail = assetDetail;
    }

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

    public void setAssetList(List<String> assetList) {
        this.assetList = assetList;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "AssetGroupResponse{" + ", name='" + name + '\'' + ", gmtCreate=" + gmtCreate + ", memo='" + memo + '\''
               + ", createUserName='" + createUserName + '\'' + ", assetList=" + assetList + '}';
    }
}