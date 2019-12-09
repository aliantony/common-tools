package com.antiy.asset.vo.user;

import java.util.List;

/**
 * SysMenuResponse 响应对象
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class OauthMenuResponse {
    /** 父菜单id */
    private String                  parentId;

    /** 菜单标识 */
    private String                  tag;
    /** 菜单名 */
    private String                  name;
    /** 菜单类型 */
    private Integer                 menuType;

    private String                  stringId;

    private List<OauthMenuResponse> childrenNode;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public List<OauthMenuResponse> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<OauthMenuResponse> childrenNode) {
        this.childrenNode = childrenNode;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }
}
