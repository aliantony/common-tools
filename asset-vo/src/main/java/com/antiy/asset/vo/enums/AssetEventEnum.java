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
    ASSET_CATEGORY_UPDATE(6,"品类型号修改"),
    ASSET_DELETE(7,"硬件资产删除"),
    ASSET_EXPORT_COMPUTER(8,"导入计算设备"),
    ASSET_EXPORT_NET(9,"导入网络设备"),
    ASSET_EXPORT_OTHERS(10,"导入其他设备"),
    ASSET_EXPORT_SAFETY(11,"导入安全设备"),
    ASSET_EXPORT_STORAGE(12,"导入存储设备"),
    ASSET_START_ACTIVITY(13,"启动流程"),
    ASSET_CPU_INSERT(14,"硬件资产CPU新增"),
    ASSET_CPU_UPDATE(15,"硬件资产CPU修改"),
    ASSET_CPU_DELETE(16,"硬件资产CPU删除");


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
