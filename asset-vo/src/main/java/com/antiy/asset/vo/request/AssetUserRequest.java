package com.antiy.asset.vo.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * <p> AssetUserRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetUserRequest extends BasicRequest implements ObjectValidator {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String id;
    /**
     * 姓名
     */
    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty("姓名")
    private String name;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 部门主键
     */
    @ApiModelProperty("部门主键")
    @Encode
    @NotBlank(message = "部门主键不能为空")
    private String departmentId;
    /**
     * 部门名
     */
    @ApiModelProperty("部门名")
    private String departmentName;
    /**
     * 电子邮箱
     */
    @ApiModelProperty("电子邮箱")
    private String email;
    /**
     * qq号
     */
    @ApiModelProperty("qq号")
    private String qq;
    /**
     * 微信
     */
    @ApiModelProperty("微信")
    @Pattern(message = "只能是英文字母和数字", regexp = "^[a-zA-Z0-9]+$")
    private String weixin;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;
    /**
     * 住址
     */
    @ApiModelProperty("住址")
    private String address;
    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String detailAddress;
    /**
     * 职位
     */
    @ApiModelProperty("职位")
    private String position;

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @ApiModelProperty(value = "详细住址")
    @Length(message = "最大长度不能超过255", max = 255)
    private String detailAddress;

    @ApiModelProperty(value = "职位")
    @Pattern(regexp = "^[\u4e00-\u9fa5a-zA-Z]+$", message = "职位只能是中英文")
    @Length(message = "最大长度不能超过64", max = 64)
    private String position;

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
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

    @Override
    public String toString() {
        return "AssetUserRequest{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", memo='" + memo + '\''
               + ", departmentId='" + departmentId + '\'' + ", email='" + email + '\'' + ", qq='" + qq + '\''
               + ", weixin='" + weixin + '\'' + ", mobile='" + mobile + '\'' + ", address='" + address + '\'' + '}';
    }
}