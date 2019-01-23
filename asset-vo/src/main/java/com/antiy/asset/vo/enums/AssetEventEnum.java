package com.antiy.asset.vo.enums;

/**
 * @auther: zhangbing
 * @date: 2019/1/23 10:06
 * @description:
 */
public enum AssetEventEnum {
    ASSET_INSERT(1,"硬件资产登记"),
    ASSET_MODIFY(2,"硬件资产修改"),
    ASSET_STATUS_CHANGE(3,"硬件资产状态修改"),
    ASSET_CATEGORY_DELETE(4,"品类型号删除"),
    ASSET_CATEGORY_INSERT(5,"品类型号新增"),
    ASSET_CATEGORY_UPDATE(6,"品类型号修改");


    private Integer status;
    private String  name;

    AssetEventEnum(Integer status, String name) {
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
