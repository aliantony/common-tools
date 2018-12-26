package com.antiy.asset.vo.response;

import java.util.Date;

import com.antiy.asset.encoder.Encode;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p> 资产 </p>
 *
 * @author zhangyajun
 * @since 2018-11-21
 */
@Data
@ApiModel(value = "资产信息")
public class AssetResponse {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  id;

    public String getId() {
        return id;
    }

    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String  name;

    /**
     * 资产类型
     */
    @ApiModelProperty("资产型号")
    private String  model;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String  system;

    /**
     * 系统位数
     */
    @ApiModelProperty("系统位数")
    private Integer systemBit;

    /**
     * 物理位置
     */
    @ApiModelProperty("物理位置")
    private String  location;

    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人")
    private String  personName;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String  contactTel;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String  email;

    /**
     * 内存
     */
    @ApiModelProperty("内存JSON数据")
    private String  memory;

    /**
     * 设备来源
     */
    @ApiModelProperty("设备来源")
    private Integer assetSource;

    /**
     * 设备类型:1-台式机，2-服务器,3-IDS资产,4-IPS资产,5-路由器,6-防火墙,7-交换机
     */
    @ApiModelProperty("设备类型:1-台式机，2-服务器,3-IDS资产,4-IPS资产,5-路由器,6-防火墙,7-交换机")
    private Integer type;

    /**
     * 重要程度标签
     */
    @ApiModelProperty("重要程度标签")
    private Integer majorType;

    /**
     * 状态
     */
    @ApiModelProperty(" 状态")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String  describle;

    /**
     * 磁盘信息JSON 串
     */
    @ApiModelProperty("磁盘信息JSON 串")
    private String  disk;

    /**
     * 父类资源Id
     */
    private Integer parentId;

    /**
     * 链接类型
     */
    @ApiModelProperty("链接类型,1-光纤，2-双绞线")
    private Integer parentNetType;

    /**
     * 所属标签ID和名称列表JSON串
     */
    @ApiModelProperty("所属标签ID和名称列表JSON串")
    private String  tags;

    /**
     * 是否入网,0表示未入网,1表示入网
     */
    @ApiModelProperty("是否入网,0表示未入网,1表示入网")
    private Integer isInNet;

    /**
     * 保修期
     */
    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("保修期")

    private Date    endWarranty;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;

}
