package com.antiy.asset.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhouye
 * @date 2019-01-14 工单类型枚举 1巡检，2预警，3重保，4应急，5清查,6基准配置，7基准验证，8准入实施，9准入效果检查， 10其他，11告警，12固件升级，13特征库升级
 */
public enum WorkOrderTypeEnum {
    /**
     * 异常
     */
    EMPTY(-1, ""),
    /**
     * 默认 查询条件用
     */
    DEFAULT(0, "默认"),
    /**
     * 巡检
     */
    INSPECTION(1, "巡检"),
    /**
     * 预警
     */
    WARNING(2, "预警"),
    /**
     * 重保
     */
    KEYPROTECTION(3, "重保"),
    /**
     * 应急
     */
    EMERGENCY(4, "应急"),
    /**
     * 清查
     */
    INVENTRY(5, "清查"),
    /**
     * 基准配置
     */
    BENCHMARK_CONFIGUARTION(6, "基准配置"),
    /**
     * 基准验证
     */
    BENCHMARK_VERIFICATION(7, "基准验证"),
    /**
     * 准入实施
     */
    ADMISSION_IMPLEMENTATION(8, "准入实施"),
    /**
     * 准入效果检查
     */
    ADMISSION_EFFECT_CHECK(9, "准入效果检查"),
    /**
     * 其他
     */
    OTHER(10, "其他"),
    /**
     * 告警
     */
    ALARM(11, "告警"),
    /**
     * 固件升级
     */
    FIRMWARE_UPGRADE(12, "固件升级"),
    /**
     * 特征库升级
     */
    FEATURE_LIBRARY_UPGRADE(13, "特征库升级"),
    /**
     * 硬件待验证
     */
    HARDWARE_TO_VERIFY(14, "硬件待验证"),

    /**
     * 硬件待检查
     */
    HARDWARE_TO_BE_CHECKED(15, "硬件待检查"),
    /**
     * 硬件实施入网
     */
    HARDWARE_ENTRY_INTO_NETWORK(16, "硬件实施入网"),
    /**
     * 软件退役分析
     */
    SOFTWARE_DECOMMISSIONING_ANALYSIS(17, "软件退役分析"),
    /**
     * 类型硬件待退役
     */
    HARDWARE_TO_BE_DECOMMISSIONED(18, "类型硬件待退役");
    /**
     * code
     */
    private Integer code;

    /**
     * msg
     */
    private String msg;

    WorkOrderTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过code获取枚举
     *
     * @param code
     * @return Enum
     */
    public static WorkOrderTypeEnum getEnumByCode(Integer code) {
        if (code != null) {
            for (WorkOrderTypeEnum type : WorkOrderTypeEnum.values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
        }
        return EMPTY;
    }

    /**
     * 通过name获取枚举
     *
     * @param name name
     * @return
     */
    public static WorkOrderTypeEnum getEnumByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (WorkOrderTypeEnum type : WorkOrderTypeEnum.values()) {
                if (type.name().equals(name)) {
                    return type;
                }
            }
        }
        return EMPTY;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    }
