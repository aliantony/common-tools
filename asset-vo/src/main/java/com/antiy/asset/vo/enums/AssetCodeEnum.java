package com.antiy.asset.vo.enums;

/**
 * @description 资产模块码表枚举类
 * @author lvliang
 * @date 2019-01-17
 */
public enum AssetCodeEnum {

                           VALIDATE_STATUS("validate_status",
                                           "验证状态"), HARDWARE_STATUS("hardware_status",
                                                                    "硬件资产状态"), HARDWARE_TYPE("hardware_type",
                                                                                             "硬件资产类型"), CATEGORY_TYPE("category_type",
                                                                                                                      "品类型号类型"), SOFTWARE_STATUS("software_status",
                                                                                                                                                 "软件资产状态"), ASSET_SOURCE("asset_source",
                                                                                                                                                                         "资产来源"), LABLE_TYPE("lable_type",
                                                                                                                                                                                             "标签类型"), ENTER_NETWORK_STATUS("enter_network_status",
                                                                                                                                                                                                                           "入网状态"), REPORT_SOURCE("report_source",
                                                                                                                                                                                                                                                  "上报来源"), DELETE_FLAG("delete_flag",
                                                                                                                                                                                                                                                                       "删除标识"), AUTHORIZATION("authorization",
                                                                                                                                                                                                                                                                                              "授权"), DISK_TYPE("disk_type",
                                                                                                                                                                                                                                                                                                               "磁盘"), INTERFACE_TYPE("interface_type",
                                                                                                                                                                                                                                                                                                                                     "接口类型"), TRANSFER_TYPE("transfer_type",
                                                                                                                                                                                                                                                                                                                                                        "内存类型"), SLOT_TYPE("slot_type",
                                                                                                                                                                                                                                                                                                                                                                           "插槽类型"), MAJOR_TYPE("major_type",
                                                                                                                                                                                                                                                                                                                                                                                               "重要程度");

    ;
    /**
     * 码表类型
     */
    private String codeType;

    /**
     * 码表名称
     */
    private String codeName;

    AssetCodeEnum(String codeType, String codeName) {
        this.codeType = codeType;
        this.codeName = codeName;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
