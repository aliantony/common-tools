package com.antiy.asset.vo.enums;

/**
 * 操作日志枚举
 * @auther: zhangyajun
 * @date: 2019/5/30 14:22
 * @description:
 */
public enum AssetOperateLogEnum {
                                 ASSET_REGISTER(AssetStatusEnum.WATI_REGSIST.getCode(),
                                                "资产登记"), ASSET_NET_IN(AssetStatusEnum.WAIT_NET.getCode(),
                                                                      "资产入网"), ASSET_NET_IN_CHECK(AssetStatusEnum.WAIT_CHECK
                                                                          .getCode(), "入网检查");

    private Integer status;
    private String  name;

    AssetOperateLogEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
