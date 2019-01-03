package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SchemeResponse 响应对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

@Data
public class SchemeResponse {
    /**
     * 类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.资产退役、4.验证退役方案、5.实施退役方案）")
    private Integer type;
    /**
     * 结果
     */
    @ApiModelProperty("结果")
    private Integer result;
    /**
     * 实施用户主键
     */
    @ApiModelProperty("实施用户主键")
    private Integer putintoUserId;
    /**
     * 实施时间
     */
    @ApiModelProperty("实施时间")
    private Long putintoTime;
    /**
     * 实施人
     */
    @ApiModelProperty("实施人")
    private String putintoUser;
    /**
     * 附件路径
     */
    @ApiModelProperty("附件路径")
    private String filepath;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

}