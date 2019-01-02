package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

/**
 * <p>
 * AssetDepartment 查询条件
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetDepartmentQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 部门名
     */
    private String name;
    /**
     * 负责人
     */
    private String responsibleUser;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 上级部门
     */
    private Integer parentId;

    @Override
    public void validate() throws RequestParamValidateException {

    }

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
}