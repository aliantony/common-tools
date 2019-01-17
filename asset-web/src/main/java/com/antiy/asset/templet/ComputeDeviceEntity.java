package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import lombok.Data;

/**
 * 计算设备
 */
@Data
public class ComputeDeviceEntity {
    /**
     * 序号
     */
    @ExcelField(value = "order_number", align = 1, title = "序号",type =1 )
    private Integer orderNumber;

    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称")
    private String  name;

    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String  manufacturer;

    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String  serial;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String  user;

    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话")
    private String  telephone;

    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱")
    private String  email;

    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String  location;

    /**
     * 机房位置
     */
    @ExcelField(value = "house_location", align = 1, title = "机房位置")
    private String  houseLocation;

    /**
     * 固件版本
     */
    @ExcelField(value = "firmware_version", align = 1, title = "固件版本")
    private String  firmwareVersion;

    /**
     * 操作系统
     */
    @ExcelField(value = "operation_system", align = 1, title = "操作系统")
    private String  operationSystem;

    /**
     * 重要程度
     */
    @ExcelField(value = "importance_degree", align = 1, title = "重要程度", dictType = "major_type")
    private String  importanceDegree;

    /**
     * 购买时间
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买时间", isDate = true)
    private Long  buyDate;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "due_time", align = 1, title = "到期时间", isDate = true)
    private Long  dueTime;
    /**
     * 保修
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期",isDate = true)
    private Long    warranty;

    /**
     * memo
     */
    @ExcelField(value = "description", align = 1, title = "描述", isDate = true)
    private String  description;
    /**
     * 内存数量
     */
    @ExcelField(value = "memory_num", align = 1, title = "内存数量")
    private Integer  memoryNum;
    /**
     * 内存品牌
     */
    @ExcelField(value = "memory_brand", align = 1, title = "内存品牌")
    private String  memoryBrand;

    /**
     * 内存序列号
     */
    @ExcelField(value = "memory_serial", align = 1, title = "内存序列号")
    private String  memorySerial;
    /**
     * 内存容量
     */
    @ExcelField(value = "memory_capacity", align = 1, title = "内存容量")
    private Integer  memoryCapacity;


    /**
     * 内存主频
     */
    @ExcelField(value = "memory_frequency", align = 1, title = "内存主频")
    private Double  memoryFrequency;

    /**
     * 内存插槽类型
     */
    @ExcelField(value = "slot_type", align = 1, title = "内存插槽类型", dictType = "slot_type")
    private Integer  slotType;

    /**
     * 是否带散热
     */
    @ExcelField(value = "cpu_is_heatsink", align = 1, title = "内存是否带散热", dictType = "yesorno")
    private Integer  cpuIsHeatsink;
    /**
     * 针脚数
     */
    @ExcelField(value = "cpu_stitch", align = 1, title = "内存针脚数")
    private Integer  cpuStitch;
    /**
     * 硬盘数量
     */
    @ExcelField(value = "hard_disk_num", align = 1, title = "硬盘数量")
    private Integer  hardDiskNum;
    /**
     * 硬盘品牌
     */
    @ExcelField(value = "hard_disk_brand", align = 1, title = "硬盘品牌")
    private String  hardDiskBrand;
    /**
     * 硬盘型号
     */
    @ExcelField(value = "hard_disk_model", align = 1, title = "硬盘型号")
    private String  hardDiskModel;

    /**
     * 硬盘序列号
     */
    @ExcelField(value = "hard_disk_serial", align = 1, title = "硬盘序列号")
    private String  hardDiskSerial;

    /**
     * 硬盘容量
     */
    @ExcelField(value = "hard_disk_capacity", align = 1, title = "硬盘容量")
    private Integer  hardDisCapacityl;
    /**
     * 硬盘接口类型
     */
    @ExcelField(value = "hard_disk_interface_type", align = 1, title = "硬盘接口类型", dictType = "interface_type")
    private Integer  hardDiskInterfaceType;
    /**
     * 硬盘磁盘类型
     */
    @ExcelField(value = "hard_disk_type", align = 1, title = "硬盘磁盘类型", dictType = "disk_type")
    private Integer  hardDiskType;
    /**
     * 硬盘购买时间
     */
    @ExcelField(value = "hard_disk_buy_date", align = 1, title = "硬盘购买时间", isDate = true)
    private Long    hardDiskBuyDate;
    /**
     * 主板品牌
     */
    @ExcelField(value = "mainborad_brand", align = 1, title = "主板品牌")
    private String  mainboradBrand;
    /**
     * 主板型号
     */
    @ExcelField(value = "mainborad_model", align = 1, title = "主板型号")
    private String  mainboradModel;
    /**
     * 主板序列号
     */
    @ExcelField(value = "mainborad_serial", align = 1, title = "主板序列号")
    private String  mainboradSerial;
    /**
     * 主板bios版本
     */
    @ExcelField(value = "mainborad_bios_version", align = 1, title = "主板bios版本")
    private String  mainboradBiosVersion;
    /**
     * 主板bios日期
     */
    @ExcelField(value = "mainborad_bios_date", align = 1, title = "主板bios日期",isDate = true)
    private Long  mainboradBiosDate;
    /**
     * cpu数量
     */
    @ExcelField(value = "cpu_num", align = 1, title = "cpu数量")
    private Integer  cpuNum;
    /**
     * cpu品牌
     */
    @ExcelField(value = "cpu_brand", align = 1, title = "cpu品牌")
    private String  cpuBrand;
    /**
     * cpu型号
     */
    @ExcelField(value = "cpu_model", align = 1, title = "cpu型号")
    private String  cpuModel;
    /**
     * cpu序列号
     */
    @ExcelField(value = "cpu_serial", align = 1, title = "cpu序列号")
    private String  cpuSerial;
    /**
     * cpu主频
     */
    @ExcelField(value = "cpu_main_frequency", align = 1, title = "cpu主频")
    private Float  cpuMainFrequency;
    /**
     * cpu线程数
     */
    @ExcelField(value = "cpu_thread_size", align = 1, title = "cpu线程数")
    private Integer  cpuThreadSize;
    /**
     * cpu核心数
     */
    @ExcelField(value = "cpu_core_size", align = 1, title = "cpu核心数")
    private Integer  cpuCoreSize;
    /**
     * 网卡数量
     */
    @ExcelField(value = "network_num", align = 1, title = "网卡数量")
    private Integer  networkNum;
    /**
     * 网卡品牌
     */
    @ExcelField(value = "network_brand", align = 1, title = "网卡品牌")
    private String  networkBrand;
    /**
     * 网卡型号
     */
    @ExcelField(value = "network_model", align = 1, title = "网卡型号")
    private String  networkModel;
    /**
     * 网卡序列号
     */
    @ExcelField(value = "network_serial", align = 1, title = "网卡序列号")
    private String  networkSerial;
    /**
     * 网卡ip地址
     */
    @ExcelField(value = "network_ip_address", align = 1, title = "网卡ip地址")
    private String  networkIpAddress;
    /**
     * 网卡mac地址
     */
    @ExcelField(value = "network_mac_address", align = 1, title = "网卡mac地址")
    private String  networkMacAddress;
    /**
     * 网卡子网掩码
     */
    @ExcelField(value = "network_subnet_mask", align = 1, title = "网卡子网掩码")
    private String  networkSubnetMask;
    /**
     * 网卡默认网关
     */
    @ExcelField(value = "network_default_gateway", align = 1, title = "网卡默认网关")
    private String  networkDefaultGateway;


}
