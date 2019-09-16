package com.antiy.asset.vo.enums;

/**
 * 工作流节点与资产状态的映射枚举
 * @author zhangyajun
 * @create 2019-05-22 14:25
 **/
public enum ActivityNodeStatusEnum {
                                    WAIT_REGISTER("资产登记",
                                                 AssetStatusEnum.WAIT_REGISTER), WAIT_SETTING("基准配置",
                                                                                             AssetStatusEnum.WAIT_SETTING), WAIT_NET("实施入网",
                                                                                                                                     AssetStatusEnum.WAIT_NET), WAIT_CHECK("效果检查",
                                                                                                                                                                           AssetStatusEnum.WAIT_CHECK);

    private String          name;
    private AssetStatusEnum statusEnum;

    ActivityNodeStatusEnum(String name, AssetStatusEnum statusEnum) {
        this.name = name;
        this.statusEnum = statusEnum;
    }

    public String getName() {
        return name;
    }

    public AssetStatusEnum getStatusEnum() {
        return statusEnum;
    }

    public static ActivityNodeStatusEnum getNodeStatus(String name) {
        if (name != null) {
            for (ActivityNodeStatusEnum nodeStatusEnum : ActivityNodeStatusEnum.values()) {
                if (nodeStatusEnum.getName().equals(name)) {
                    return nodeStatusEnum;
                }
            }
        }
        return null;
    }
}
