package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * AssetSoftwareRelation 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
@Data
public class AssetSoftwareRelationQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 软件资产状态：1待登记，2不予登记，3待配置，4待验证，5待入网，6已入网，7待退役，8已退役
     */
    @ApiModelProperty("软件资产状态：1待登记，2不予登记，3待配置，4待验证，5待入网，6已入网，7待退役，8已退役")
    private Integer softwareStatus;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    @Override
    public void validate() throws RequestParamValidateException {

    }
}