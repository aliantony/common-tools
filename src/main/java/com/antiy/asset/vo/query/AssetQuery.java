package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * Asset 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class AssetQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String number;
    /**
     * 资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS,
     */
    @ApiModelProperty("资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS")
    private Integer type;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String name;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String serial;
    /**
     * 品类
     */
    @ApiModelProperty("品类")
    private Integer category;
    /**
     * 资产型号
     */
    @ApiModelProperty("资产型号")
    private Integer model;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String manufacturer;
    /**
     * 资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役
     */
    @ApiModelProperty("资产状态：1-待登记，2-不予登记，3-待配置，4-待验证，5-待入网，6-已入网，7-待退役，8-已退役")
    private Integer assetStatus;
    /**
     * 操作系统,如果type为IDS或者IPS则此字段存放软件版本信息
     */
    @ApiModelProperty("操作系统,如果type为IDS或者IPS则此字段存放软件版本信息")
    private String operationSystem;
    /**
     * 系统位数
     */
    @ApiModelProperty("系统位数")
    private Integer systemBit;


    /**
     * 固件版本
     */
    @ApiModelProperty("固件版本")
    private String firmwareVersion;
    /**
     * 设备uuid
     */
    @ApiModelProperty("设备uuid")
    private String uuid;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    private Integer responsibleUserId;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;

    /**
     * 上报来源,1-自动上报，2-人工上报
     */
    @ApiModelProperty("上报来源,1-自动上报，2-人工上报")
    private Integer assetSource;
    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    @ApiModelProperty("0-不重要(not_major),1- 一般(general),3-重要(major),")
    private Integer importanceDegree;

    /**
     * 父类资源Id
     */
    @ApiModelProperty("父类资源Id")
    private Integer parentId;
    /**
     * 所属标签ID和名称列表JSON串
     */
    @ApiModelProperty("所属标签ID和名称列表JSON串")
    private String tags;
    /**
     * 是否入网,0表示未入网,1表示入网
     */
    @ApiModelProperty("是否入网,0表示未入网,1表示入网")
    private Boolean isInnet;
    /**
     * 使用到期时间
     */
    @ApiModelProperty("使用到期时间")
    private Long serviceLife;
    /**
     * 制造日期
     */
    @ApiModelProperty("制造日期")
    private Long buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private Long warranty;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}