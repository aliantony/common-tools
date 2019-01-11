package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/7 13:11
 * @Description: 拓扑类型
 */
public enum TopologyTypeEnum {
    PHYSICS(0, "物理拓扑"),
    LOGIC(1, "逻辑拓扑"),
    CONNECT(3, "通联关系");

    TopologyTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // code
    private Integer code;

    // msg
    private String  msg;

    /**
     * 通过code获取枚举
     *
     * @param name name
     * @return
     */
    public static TopologyTypeEnum getTopologyByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (TopologyTypeEnum topologyTypeEnum : TopologyTypeEnum.values()) {
                if (topologyTypeEnum.name().equals(name)) {
                    return topologyTypeEnum;
                }
            }
        }
        return null;
    }

    /**
     * 通过code获取枚举
     * @param code
     * @return
     */
    public static TopologyTypeEnum getTopologyByCode(Integer code) {
        if (null != code) {
            for (TopologyTypeEnum topologyTypeEnum : TopologyTypeEnum.values()) {
                if (topologyTypeEnum.code.equals(code)) {
                    return topologyTypeEnum;
                }
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
