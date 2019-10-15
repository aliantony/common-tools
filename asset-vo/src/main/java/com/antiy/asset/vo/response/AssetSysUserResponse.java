package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

/**
 * @author liulusheng
 * @since 2019/10/14
 */
public class AssetSysUserResponse extends BaseResponse {
    private String username;
    private String name;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AssetSysUserResponse{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
