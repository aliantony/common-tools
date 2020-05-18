package com.antiy.asset.vo.enums;

/**
 * @author liulusheng
 * @since 2020/3/3
 */

public enum AssetEntrySourceEnum implements CodeEnum {
    ASSET_ENTER_NET(1,"资产入网"),
    ASSET_RETIRE(2,"资产退回"),
    VUL_SCAN(3,"漏洞管理"),
    CONFIG_SCAN(4,"配置扫描"),
    PATCH_INSTALL(5,"补丁安装"),
    ENTRY_MANAGE(6,"准入管理"),
    ASSET_CHANGE(7,"资产变更"),
    ASSET_SCRAP(8,"资产报废"),
    UNKNOWN_ASSET_REGISTER(9,"未知资产登记"),

    ;
    private int code;
    private String msg;

    AssetEntrySourceEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
