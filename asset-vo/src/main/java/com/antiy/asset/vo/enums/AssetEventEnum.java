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
    ASSET_CPU_DELETE(16,"硬件资产CPU删除"),
    ASSET_DEPARTMENT_INSERT(17,"资产部门新增"),
    ASSET_DEPAETMENT_UPDATE(18,"资产部门修改"),
    ASSET_DEPAETMENT_DELETE(19,"资产部门删除"),
    ASSET_GROUP_DELETE(20,"资产组删除"),
    ASSET_GROUP_INSERT(21,"资产组新增"),
    ASSET_GROUP_UPDATE(22,"资产组修改"),
    ASSET_GROUP_RELATION_UPDATE(23,"资产组关联修改"),
    ASSET_GROUP_RELATION_DELETE(24,"资产组关联删除"),
    ASSET_GROUP_RELATION_INSERT(25,"资产组关联新增"),
    ASSET_DISK_INSERT(26,"资产硬盘新增"),
    ASSET_DISK_UPDATE(27,"资产硬盘修改"),
    ASSET_DISK_DELETE(28,"资产硬盘删除"),
    ASSET_MAINBORAD_DELETE(29,"资产主板删除"),
    ASSET_MAINBORAD_UPDATE(30,"资产主板修改"),
    ASSET_MAINBORAD_INSERT(31,"资产主板新增"),
    ;


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
