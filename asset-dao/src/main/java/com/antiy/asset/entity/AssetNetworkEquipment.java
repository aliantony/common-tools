package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import lombok.Data;

/**
 * <p> 网络设备详情表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Data
public class AssetNetworkEquipment extends BaseEntity {

    /**
     * 资产主键
     */
    private Integer assetId;
    /**
     * 接口数目
     */
    private Integer interfaceSize;
    /**
     * 端口数目
     */
    private Integer portSize;

    /**
     * 端口数目
     */
    private String ios;

    /**
     * 端口数目
     */
    private String cpuVersion;
    /**
     * 端口数目
     */
    private Integer cpu;
    /**
     * 是否无线:0-否,1-是
     */
    private Integer isWireless;
    /**
     * 内网IP
     */
    private String  innerIp;
    /**
     * 外网IP
     */
    private String  outerIp;
    /**
     * MAC地址
     */
    private String  macAddress;
    /**
     * 子网掩码
     */
    private String  subnetMask;
    /**
     * 预计带宽(M)
     */
    private Integer expectBandwidth;
    /**
     * 配置寄存器(GB)
     */
    private Integer register;
    /**
     * DRAM大小
     */
    private Float   dramSize;
    /**
     * FLASH大小
     */
    private Float   flashSize;
    /**
     * NCRM大小
     */
    private Float   ncrmSize;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;


}