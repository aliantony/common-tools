package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program asset
 * @description 软件信息
 * @author wangqian
 * created on 2019-09-20
 * @version  1.0.0
 */
@ApiModel(value = "软件信息")
public class SoftwareResponse {

    @Encode
    @ApiModelProperty("软件id")
    private String softwareId;

    @ApiModelProperty("软件名称")
    private String productName;

    @ApiModelProperty("厂商")
    private String supplier;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("关联时间")
    private Long linkTime;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(Long linkTime) {
        this.linkTime = linkTime;
    }
}
