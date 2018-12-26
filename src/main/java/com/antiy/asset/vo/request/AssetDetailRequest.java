package com.antiy.asset.vo.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.base.BasicRequest;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/21 13:18
 * @Description: 资产详情信息VO对象
 */
@Data
@ApiModel(value = "资产详情对象")
public class AssetDetailRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @NotNull(message = "资产主键不能为空")
    private Integer           assetId;

    /**
     * 资产UUID
     */
    @ApiModelProperty("资产UUID")
    private String  uuid;

    /**
     * 设备厂商
     */
    @ApiModelProperty("设备厂商")
    private String  manufacturer;

    /**
     * 主板
     */
    @ApiModelProperty("主板")
    private String  mainBoard;

    /**
     * 光驱名字
     */
    @ApiModelProperty("光驱名字")
    private String  cdRomName;

    /**
     * 声卡名字
     */
    @ApiModelProperty("声卡名字")
    private String  sourdCardName;

    /**
     * 显卡名字JSON数据{name:英伟达}
     */
    @ApiModelProperty("显卡名字JSON数据{name:英伟达}")
    private String  videoCard;

    /**
     * 网口信息JSON数据{num:2,type= 1000Mbps}
     */
    @ApiModelProperty("网口信息JSON数据{num:2,type= 1000Mbps}")
    private String  ethernetInterface;

    /**
     * 串口信息JSON数据{num:2,type= usb3.0}
     */
    @ApiModelProperty("串口信息JSON数据{num:2,type= usb3.0}")
    private String  serialPort;

    /**
     * 购买的日期
     */
    @ApiModelProperty("购买的日期")
    @NotNull(message = "购买日期不能为空")
    private Date    purchaseDate;

    /**
     * 购买的日期
     */
    @ApiModelProperty("保修期")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "保修期不能为空")
    private Date    endWarranty;

    /**
     * 设备使用有效期
     */
    @ApiModelProperty("设备使用有效期")
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date    endUseDate;

    /**
     * 采购单号
     */
    @ApiModelProperty("采购单号")
    private String  purchaseNumber;

    /**
     * 计算机域
     */
    @ApiModelProperty("计算机域")
    private String  region;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    @NotBlank(message = "经度不能为空")
    private String  longitude;

    /**
     * 预计带宽(路由器和交换机特有)
     */
    @ApiModelProperty("预计带宽(路由器和交换机特有)")
    private String  expectTape;

    /**
     * FLASH大小,单位MB(路由器和交换机特有)
     */
    @ApiModelProperty("FLASH大小,单位MB(路由器和交换机特有)")
    private Integer flashSize;

    /**
     * NCRM大小,单位MB(路由器和交换机特有)
     */
    @ApiModelProperty("NCRM大小,单位MB(路由器和交换机特有)")
    private Integer ncrmSize;

    /**
     * 维度
     */
    @ApiModelProperty("维度")
    @NotBlank(message = "维度不能为空")
    private String  latitude;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
