package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;

/**
 * <p> AssetIpRelationRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCustomizeRequest {
    //@NotBlank(message = "自定义字段名称不能为空")
    private String name;
    //@NotBlank(message = "自定义字段值不能为空")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}