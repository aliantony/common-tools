package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class KeyPullDownResponse {
    /**
     * 主键ID
     */
    @ApiModelProperty("资产ID or 用户ID")
    private Integer id;

    /**
     * 资产编号 or 用户名
     */
    @ApiModelProperty("资产编号 or 用户名")
    private String numOrName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumOrName() {
        return numOrName;
    }

    public void setNumOrName(String numOrName) {
        this.numOrName = numOrName;
    }


    @Override
    public String toString() {
        return "AreaKeyManageResponse{" +
                "id='" + id + '\'' +
                ", numOrName=" + numOrName +
                '}';
    }
}