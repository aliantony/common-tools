package com.antiy.asset.vo.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.base.BasicRequest;
import com.antiy.asset.exception.RequestParamValidateException;
import com.antiy.asset.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: zhangyajun
 * @Date: 2018/11/21 13:18
 * @Description: 资产信息VO对象
 */
@Data
@ApiModel(value = "资产对象")
public class AssetRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    @NotBlank(message = "资产名称不能为空")
    private String  name;

    /**
     * 资产类型
     */
    @ApiModelProperty("资产型号")
    @NotBlank(message = "资产型号不能为空")
    private String  model;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    @NotBlank(message = "操作系统不能为空")
    private String  system;

    /**
     * 系统位数
     */
    @ApiModelProperty("系统位数")
    @NotNull(message = "系统位数不能为空")
    private Integer systemBit;

    /**
     * 物理位置
     */
    @ApiModelProperty("物理位置")
    @NotBlank(message = "物理位置不能为空")
    private String  location;

    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    @NotNull(message = "责任人主键不能为空")
    private Integer personId;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String  contactTel;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String  email;

    /**
     * 内存
     */
    @ApiModelProperty("内存JSON数据")
    // @NotBlank(message = "内存JSON数据不能为空")
    private String  memory;

    /**
     * 设备来源
     */
    @ApiModelProperty("设备来源")
    @NotNull(message = "设备来源不能为空")
    private Integer assetSource;

    /**
     * 重要程度标签
     */
    @ApiModelProperty("重要程度标签")
    @NotNull(message = "重要程度标签不能为空")
    private Integer majorType;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    // @NotBlank(message = "描述不能为空")
    private String  describle;

    /**
     * 磁盘信息JSON 串
     */
    @ApiModelProperty("磁盘信息JSON 串")
    // @NotBlank(message = "磁盘信息JSON串不能为空")
    private String  disk;

    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    // @NotNull(message = "IP地址不能为空")
    private Integer ipAddress;

    /**
     * 父类资源Id
     */
    @NotNull(message = "父类资源Id不能为空")
    private Integer parentId;

    /**
     * 链接类型
     */
    @ApiModelProperty("链接类型,1-光纤，2-双绞线")
    @NotBlank(message = "链接类型不能为空")
    private Integer parentNetType;

    /**
     * 所属标签ID和名称列表JSON串
     */
    @ApiModelProperty("所属标签ID和名称列表JSON串")
    // @NotBlank(message = "所属标签ID和名称列表JSON串不能为空")
    private String  tags;

    /**
     * 是否入网,0表示未入网,1表示入网
     */
    @ApiModelProperty("是否入网,0表示未入网,1表示入网")
    @NotNull(message = "是否入网不能为空")
    private Integer isInNet;

    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    // @NotNull(message = "保修期不能为空")
    private Date    endWarranty;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
