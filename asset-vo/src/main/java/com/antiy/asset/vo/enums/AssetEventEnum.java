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
    ASSET_MEMORY_DELETE(32,"资产内存删除"),
    ASSET_MEMORY_UPDATE(33,"资产内存修改"),
    ASSET_MEMORY_INSERT(34,"资产内存新增"),
    ASSET_NETWORK_DELETE(35,"资产网卡删除"),
    ASSET_NETWORK_UPDATE(36,"资产网卡修改"),
    ASSET_NETWORK_INSERT(37,"资产网卡新增"),
    ASSET_NETWORK_DETAIL_DELETE(38,"资产网络设备详情删除"),
    ASSET_NETWORK_DETAIL_UPDATE(39,"资产网络设备详情修改"),
    ASSET_NETWORK_DETAIL_INSERT(40,"资产网络设备详情新增"),
    ASSET_PORT_DELETE(41,"资产端口删除"),
    ASSET_PORT_UPDATE(42,"资产端口修改"),
    ASSET_PORT_INSERT(43,"资产端口新增"),
    ASSET_SAFE_DETAIL_DELETE(44,"资产安全设备删除"),
    ASSET_SAFE_DETAIL_UPDATE(45,"资产安全设备修改"),
    ASSET_SAFE_DETAIL_INSERT(46,"资产安全设备新增"),
    SOFT_DELETE(47,"软件资产删除"),
    SOFT_UPDATE(48,"软件资产修改"),
    SOFT_INSERT(49,"软件资产新增"),
    SOFT_EXPORT(50,"软件资产导入"),
    SOFT_INSTALL(51,"软件资产安装"),
    SOFT_LICENSE_DELETE(52,"软件资产许可删除"),
    SOFT_LICENSE_UPDATE(53,"软件资产许可修改"),
    SOFT_LICENSE_INSERT(54,"软件资产许可新增"),
    SOFT_ASSET_RELATION_DELETE(55,"软件资产和硬件关系删除"),
    SOFT_ASSET_RELATION_UPDATE(56,"软件资产和硬件关系修改"),
    SOFT_ASSET_RELATION_INSERT(57,"软件资产和硬件关系新增"),
    SOFT_ASSET_STATUS_CHANGE(58,"资产状态跃迁"),
    ASSET_USER_DELETE(59,"资产用户删除"),
    ASSET_USER_UPDATE(60,"资产用户修改"),
    ASSET_USER_INSERT(61,"资产用户新增"),
    ASSET_SCHEME_DELETE(62,"资产方案删除"),
    ASSET_SCHEME_UPDATE(63,"资产方案修改"),
    ASSET_SCHEME_INSERT(64,"资产方案新增"),
    ASSET_STORAGE_INSERT(65,"存储介质新增"),
    ASSET_STORAGE_UPDATE(66,"存储介质修改"),
    ASSET_STORAGE_DELETE(67,"存储介质删除"),
    ASSET_OPERATION_RECORD_INSERT(68,"历史纪录新增"),
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
