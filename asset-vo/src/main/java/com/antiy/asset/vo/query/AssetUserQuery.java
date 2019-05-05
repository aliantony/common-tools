package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

/**
 * <p> AssetUser 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetUserQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @Size(message = "姓名应在1~30字符", min = 1, max = 30)
    private String  name;
    /**
     * 姓名
     */
    @ApiModelProperty("导入使用姓名")
    private String exportName;

    /**
     * 部门主键
     */
    @Encode
    @ApiModelProperty("部门主键")
    private String departmentId;
    /**
     * 部门主键
     */
    @Encode
    @ApiModelProperty("部门主键")
    private String[] departmentIds;
    /**
     * 电子邮箱
     */
    @ApiModelProperty("电子邮箱")
    private String  email;
    /**
     * qq号
     */
    @ApiModelProperty("qq号")
    private String  qq;
    /**
     * 微信
     */
    @ApiModelProperty("微信")
    private String  weixin;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @Length(message = "手机号长度应为11位", min = 11, max = 11)
    private String  mobile;
    /**
     * 住址
     */
    @ApiModelProperty("住址")
    private String  address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String[] getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String[] departmentIds) {
        this.departmentIds = departmentIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }
}