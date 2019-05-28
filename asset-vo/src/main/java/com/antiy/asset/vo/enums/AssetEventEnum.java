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
    ASSET_CATEGORY_DELETE(4,"删除品类型号"),
    ASSET_CATEGORY_INSERT(5,"登记品类型号"),
    ASSET_CATEGORY_UPDATE(6,"编辑品类型号"),
    ASSET_DELETE(7,"硬件资产删除"),
    ASSET_EXPORT_COMPUTER(8,"导入计算设备"),
    ASSET_EXPORT_NET(9,"导入网络设备"),
    ASSET_EXPORT_OTHERS(10,"导入其它设备"),
    ASSET_EXPORT_SAFETY(11,"导入安全设备"),
    ASSET_EXPORT_STORAGE(12,"导入存储设备"),
    ASSET_START_ACTIVITY(13,"启动流程"),
    ASSET_CPU_INSERT(14,"硬件资产CPU新增"),
    ASSET_CPU_UPDATE(15,"硬件资产CPU修改"),
    ASSET_CPU_DELETE(16,"硬件资产CPU删除"),
    ASSET_DEPARTMENT_INSERT(17,"资产部门新增"),
    ASSET_DEPAETMENT_UPDATE(18,"资产部门修改"),
    ASSET_DEPAETMENT_DELETE(19,"资产部门删除"),
                            ASSET_GROUP_DELETE(20,
                                               "资产组注销"),
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
    ASSET_NETWORK_DETAIL_DELETE(38,"资产网络设备删除"),
    ASSET_NETWORK_DETAIL_UPDATE(39,"资产网络设备修改"),
    ASSET_NETWORK_DETAIL_INSERT(40,"资产网络设备新增"),
    ASSET_PORT_DELETE(41,"资产端口删除"),
    ASSET_PORT_UPDATE(42,"资产端口修改"),
    ASSET_PORT_INSERT(43,"资产端口新增"),
    ASSET_SAFE_DETAIL_DELETE(44,"资产安全设备删除"),
    ASSET_SAFE_DETAIL_UPDATE(45,"资产安全设备修改"),
    ASSET_SAFE_DETAIL_INSERT(46,"资产安全设备新增"),
    SOFT_DELETE(47,"软件资产删除"),
    SOFT_UPDATE(48,"软件变更"),
    SOFT_INSERT(49,"软件登记"),
                            SOFT_EXPORT(50,
                                        "软件导出"),
    SOFT_INSTALL(51,"软件安装"),
    SOFT_LICENSE_DELETE(52,"软件资产许可删除"),
    SOFT_LICENSE_UPDATE(53,"软件资产许可修改"),
    SOFT_LICENSE_INSERT(54,"软件资产许可新增"),
    SOFT_ASSET_RELATION_DELETE(55,"软件资产和硬件关系删除"),
    SOFT_ASSET_RELATION_UPDATE(56,"软件资产和硬件关系修改"),
    SOFT_ASSET_RELATION_INSERT(57,"软件资产和硬件关系新增"),
    SOFT_ASSET_STATUS_CHANGE(58,"资产状态跃迁"),
    ASSET_USER_DELETE(59,"注销人员身份"),
    ASSET_USER_UPDATE(60,"变更人员身份"),
    ASSET_USER_INSERT(61,"登记人员身份"),
    ASSET_SCHEME_DELETE(62,"资产方案删除"),
    ASSET_SCHEME_UPDATE(63,"资产方案修改"),
    ASSET_SCHEME_INSERT(64,"资产方案新增"),
    ASSET_STORAGE_INSERT(65,"存储介质新增"),
    ASSET_STORAGE_UPDATE(66,"存储介质修改"),
    ASSET_STORAGE_DELETE(67,"存储介质删除"),
    ASSET_OPERATION_RECORD_INSERT(68,"历史纪录新增"),
    ASSET_OTHERS_INSERT(69,"资产其它设备新增"),
    ASSET_OTHERS_UPDATE(70,"资产其它设备修改"),
    ASSET_OTHERS_DELETE(71,"资产其它设备删除"),
    ASSET_LABEL_RELATION_INSERT(72,"资产标签关系插入"),
    ASSET_LABEL_RELATION_UPDATE(73,"资产标签关系更新"),
    ASSET_LABEL_RELATION_DELETE(74,"资产标签关系删除"),
    ASSET_LABEL_INSERT(75,"资产标签插入"),
    ASSET_LABEL_UPDATE(76,"资产标签更新"),
    ASSET_LABEL_DELETE(77,"资产标签删除"),
    ASSET_LINK_RELATION_INSERT(78,"资产通联关系插入"),
    ASSET_LINK_RELATION_UPDATE(79,"资产通联关系更新"),
    ASSET_LINK_RELATION_DELETE(80,"资产通联关系删除"),
    ASSET_ADMITTANCE_ALLOW(107,"资产允许准入"),
    ASSET_ADMITTANCE_REFUSE(108,"资产禁止准入"),
                            ASSET_REPORT_EXPORT(82,
                                                "资产报表导出"), ASSET_REPORT_IMPORT(83,
                                                                               "资产报表导入"), ASSET_SAFETY_EQUIPMENT_INSERT(84,
                                                                                                                        "硬件资产安全设备新增"), ASSET_SAFETY_EQUIPMENT_UPDATE(85,
                                                                                                                                                                     "硬件资产安全设备修改"), ASSET_SAFETY_EQUIPMENT_DELETE(86,
                                                                                                                                                                                                                  "硬件资产安全设备删除"), SOFTWARE_LICENSE_INSERT(87,
                                                                                                                                                                                                                                                         "软件许可新增"), SOFTWARE_LICENSE_DELETE(88,
                                                                                                                                                                                                                                                                                            "软件许可删除"), SOFTWARE_LICENSE_UPDATE(89,
                                                                                                                                                                                                                                                                                                                               "软件许可修改"), SOFT_INSTALL_MANUAL(90,
                                                                                                                                                                                                                                                                                                                                                              "软件资产人工安装"), SOFT_INSTALL_AUTO(91,
                                                                                                                                                                                                                                                                                                                                                                                             "软件资产自动安装"), FILE_UPLOAD(92,
                                                                                                                                                                                                                                                                                                                                                                                                                      "文件上传"), FILE_DOWNLOAD(93,
                                                                                                                                                                                                                                                                                                                                                                                                                                             "文件下载"), HARDWARE_CONFIG(94,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                      "硬件资产配置"), ASSET_ADMITTANCE_EXPORT(101,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         "资产准入管理导出"), SOFT_CONFIG(100,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  "软件配置"), RETIRE_REGISTER(101,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             "硬件退役再登记"), GET_USER_INOF(102,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       "获取登录用户"), ASSET_STATUS_SCHEDULE_TASK(103,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             "执行资产状态定时任务"), NO_REGISTER(104,
            "硬件不予登记再登记"),
    SETTING_REGISTER(105,
            "硬件配置待再登记"), SOFT_NO_REGISTER(106,
            "软件不予登记"),
    HARD_EXPORT(109,"硬件资产导出");


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
