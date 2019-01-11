package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetUserRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetUserRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer           id;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String            name;
    /**
     * 姓名
     */
    @ApiModelProperty("备注")
    private String            memo;
    /**
     * 部门主键
     */
    @ApiModelProperty("部门主键")
    private Integer           departmentId;
    /**
     * 电子邮箱
     */
    @ApiModelProperty("电子邮箱")
    private String            email;
    /**
     * qq号
     */
    @ApiModelProperty("qq号")
    private String            qq;
    /**
     * 微信
     */
    @ApiModelProperty("微信")
    private String            weixin;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String            mobile;
    /**
     * 住址
     */
    @ApiModelProperty("住址")
    private String            address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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

}