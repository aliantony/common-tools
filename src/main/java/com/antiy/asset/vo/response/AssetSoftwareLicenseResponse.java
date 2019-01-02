package com.antiy.asset.vo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * AssetSoftwareLicenseResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class AssetSoftwareLicenseResponse {

    /**
     * 许可名称
     */
    @ApiModelProperty("许可名称")
    private String name;
    /**
     * 厂商名称
     */
    @ApiModelProperty("厂商名称")
    private String manufacturer;
    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 购买日期
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("购买日期")
    private Long busyDate;
    /**
     * 有效期限
     */
    @ApiModelProperty("有效期限")
    private Long expiryDate;
    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String licenseSecretKey;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    /**
     * 状态,0 未删除,1已删除
     */
    @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;
}