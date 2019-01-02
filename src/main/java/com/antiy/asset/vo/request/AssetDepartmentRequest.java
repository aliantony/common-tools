package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * AssetDepartmentRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetDepartmentRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;


    /**
     * 部门名
     */
    @ApiModelProperty("部门名")
    private String name;
    /**
     * 负责人
     */
    @ApiModelProperty("负责人")
    private String responsibleUser;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String telephone;
    /**
     * 上级部门
     */
    @ApiModelProperty("上级部门")
    private Integer parentId;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

}