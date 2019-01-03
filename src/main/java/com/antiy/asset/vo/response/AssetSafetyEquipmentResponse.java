package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * AssetSafetyEquipmentResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class AssetSafetyEquipmentResponse {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     * 特征库配置
     */
    @ApiModelProperty("特征库配置")
    private String featureLibrary;
    /**
     * 策略配置
     */
    @ApiModelProperty("策略配置")
    private String strategy;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Long gmtModified;

}