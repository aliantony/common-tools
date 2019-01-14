package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetDepartmentRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetDepartmentRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ApiModelProperty("主键")
    private Integer           id;

    /**
     * 部门名
     */
    @ApiModelProperty("部门名")
    private String            name;
      /**
     * 上级部门
     */
    @ApiModelProperty("上级部门")
    private Integer           parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}