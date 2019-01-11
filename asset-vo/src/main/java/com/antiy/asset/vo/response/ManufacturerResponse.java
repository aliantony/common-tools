package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> ManufacturerResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class ManufacturerResponse {

    /**
     * 厂商
     */
    @ApiModelProperty("主键")
    private String manufacturer;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}