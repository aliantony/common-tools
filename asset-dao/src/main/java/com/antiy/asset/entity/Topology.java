package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/3 13:15
 * @Description:
 */
public class Topology extends BaseEntity {
    /**
     * 资产类型:1台式办公机,2便携式办公机,3服务器虚拟终,4移动设备,4ATM机,5工控上位机,6路由器,7交换机,8防火墙,9IDS,10IPS
     */
    private Integer type;
    /**
     * 资产名称
     */
    private String  name;

    /**
     * 父类资源Id
     */
    private Integer parentId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
