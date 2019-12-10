package com.antiy.asset.vo.user;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * SysRoleResponse 响应对象
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class OauthRoleResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色标识")
    private String            code;

    @ApiModelProperty("角色名")
    private String            name;

    @ApiModelProperty("角色备注")
    private String            description;

    @ApiModelProperty("角色拥有的功能菜单id")
    private List<String>      menuIds;

    private String            stringId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }
}
