package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import lombok.Data;

/**
 * 网络设备
 */
@Data
public class NetworkDeviceEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    @ExcelField(value = "id", align = 1, title = "序号",type = 1)
    private String            id;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称")
    private String            name;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String            manufacturer;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String            serial;
    /**
     * 使用者
     */
    @ExcelField(value = "area", align = 1, title = "所属区域")
    private String            area;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String            user;

    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话")
    private String            telephone;

    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱")
    private String            email;
    /**
     * 资产组
     */
    @ExcelField(value = "asset_group", align = 1, title = "资产组")
    private String            assetGroup;

    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String            location;
    /**
     * 机房位置
     */
    @ExcelField(value = "house_location", align = 1, title = "机房位置")
    private String            houseLocation;
    /**
     * 端口数目
     */
    @ExcelField(value = "port_size", align = 1, title = "端口数目")
    private Integer            portSize;
    /**
     * 接口数目
     */
    @ExcelField(value = "interface_size", align = 1, title = "接口数目")
    private Integer            interfaceSize;
    /**
     * IOS
     */
    @ExcelField(value = "IOS", align = 1, title = "IOS")
    private String            ios;

    /**
     * 固件版本
     */
    @ExcelField(value = "firmware_version", align = 1, title = "固件版本")
    private String            firmwareVersion;
    /**
     * 是否无线
     */
    @ExcelField(value = "is_wireless", align = 1, title = "是否无线",dictType = "yesorno")
    private Integer            isWireless;
    /**
     * 内网IP
     */
    @ExcelField(value = "inner_ip", align = 1, title = "内网IP")
    private String            innerIp;
    /**
     * 外网IP
     */
    @ExcelField(value = "outer_ip", align = 1, title = "外网IP")
    private String            outerIp;
    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址")
    private String            mac;
    /**
     * 子网掩码
     */
    @ExcelField(value = "subnet_mask", align = 1, title = "子网掩码")
    private String            subnetMask;
    /**
     * 预计带宽
     */
    @ExcelField(value = "expect_bandwidth", align = 1, title = "预计带宽")
    private Integer            expectBandwidth;

    /**
     * 配置寄存器
     */
    @ExcelField(value = "register", align = 1, title = "配置寄存器")
    private Integer            register;

    /**
     * cpu大小
     */
    @ExcelField(value = "cpu", align = 1, title = "cpu大小")
    private Integer            cpuSize;

    /**
     * cpu版本
     */
    @ExcelField(value = "cpu_version", align = 1, title = "cpu版本")
    private String            cpuVersion;

    /**
     * dram大小
     */
    @ExcelField(value = "dram_size", align = 1, title = "dram大小")
    private Float            dramSize;

    /**
     * flash大小
     */
    @ExcelField(value = "flash_size", align = 1, title = "flash大小")
    private Float            flashSize;

    /**
     * NCRM大小
     */
    @ExcelField(value = "ncrm_size", align = 1, title = "NCRM大小")
    private Float            ncrmSize;
    /**
     * 购买日期
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买日期",isDate = true)
    private Long            butDate;
    /**
     * 到期时间
     */
    @ExcelField(value = "due_date", align = 1, title = "到期时间",isDate = true)
    private Long            dueDate;
    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期",isDate = true)
    private Long            warranty;
    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述")
    private String            memo;


}
