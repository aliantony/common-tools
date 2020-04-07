package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;

/**
 * <p>
 * AssetNettypeManageResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetNettypeManageResponse extends BaseResponse {
    /**
     *  网络类型名称
     */
    private String netTypeName;
    /**
     *  描述
     */
    private String description;

    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}