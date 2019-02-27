package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: zhangyajun
 * @Date: 2019/1/19 10:21
 * @Description:
 */
public enum SoftwareStatusEnum {
    WATI_REGSIST(1, "待登记"),
    WAIT_ANALYZE(2, "登记待分析"),
    ALLOW_INSTALL(3, "可安装"),
    RETIRE(4, "已退役"),
    NOT_REGSIST(5, "不予登记"),
    WAIT_RETIRE(6,"待退役"),
    WAIT_RETIRE_ANALYZE(8,"退役待分析"),
    UNINSTALL_WAIT_ANALYZE(7,"卸载待分析");

    SoftwareStatusEnum(Integer code, String msg) {
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
    public static SoftwareStatusEnum getAssetByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (SoftwareStatusEnum softwareType : SoftwareStatusEnum.values()) {
                if (softwareType.name().equals(name)) {
                    return softwareType;
                }
            }
        }
        return null;
    }

    public static SoftwareStatusEnum getAssetByCode(Integer code) {
        if (code != null) {
            for (SoftwareStatusEnum softwareType : SoftwareStatusEnum.values()) {
                if (softwareType.getCode().equals(code)) {
                    return softwareType;
                }
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
