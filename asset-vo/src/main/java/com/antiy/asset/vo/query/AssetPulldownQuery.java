package com.antiy.asset.vo.query;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;


public class AssetPulldownQuery extends BasicRequest {
    @ApiModelProperty("厂商")
    private String supplier;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("版本")
    private String version;

    private int pos;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
