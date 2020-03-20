package com.antiy.asset.vo.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("资产类型枚举")
public enum AssetCategoryEnum implements CodeEnum{
    COMPUTER(1, "计算设备",null),
    NETWORK(2, "网络设备",null),
    SAFETY(3, "安全设备",null),
    STORAGE(4, "存储设备",null),
    OTHER(5, "其它设备",null),
    GENERAL_COMPUTER(6,"一般计算设备",COMPUTER),
    PORTABLE_COMPUTER(7,"便携机",COMPUTER),
    RIAD(8,"磁盘阵列",STORAGE),
    HARDDISK(9,"硬盘",STORAGE),
    MOBILE_HARDISK(10,"移动硬盘",STORAGE),
    USB_DISK(11,"U盘",STORAGE),
    SD_CARD(12,"SD卡",STORAGE),


        ;
    private Integer code;
    private String  name;
    @ApiModelProperty("上级节点")
    private AssetCategoryEnum preCategory;

    AssetCategoryEnum(Integer code, String name, AssetCategoryEnum preCategory) {
        this.code = code;
        this.name = name;
        this.preCategory = preCategory;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return name;
    }

    public String getName() {
        return name;
    }

    public AssetCategoryEnum getPreCategory() {
        return preCategory;
    }

    public static String getNameByCode(Integer code) {
        for (AssetCategoryEnum assetCategoryEnum : AssetCategoryEnum.values()) {
            if (assetCategoryEnum.code.equals(code)) {
                return assetCategoryEnum.name;
            }
        }
        return null;
    }

}
