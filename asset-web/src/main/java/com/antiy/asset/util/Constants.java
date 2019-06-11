package com.antiy.asset.util;

/**
 * @auther: zhangbing
 * @date: 2019/2/22 16:08
 * @description: 常量类
 */
public class Constants {

    /**
     * 最大资产关联的资产组个数
     */
    public static final Integer MAX_ASSET_RELATION_GROUP_COUNT     = 10;

    /**
     * 品类根节点类名
     */
    public static final String  ROOT_CATEGORY_NAME                 = "品类型号";
    /**
     * 硬件第一级节点品类名
     */
    public static final String  FIRST_LEVEL_ASSET_CATEGORY_NAME    = "硬件";
    /**
     * 软件第一级节品类名
     */
    public static final String  FIRST_LEVEL_SOFTWARE_CATEGORY_NAME = "软件";
    /**
     * 软件第一级ID
     */
    public static final int     FIRST_LEVEL_SOFTWARE_CATEGORY_ID   = 1;
    /**
     * 硬件第一级ID
     */
    public static final int     FIRST_LEVEL_ASSET_CATEGORY_ID      = 2;
    /**
     * 设置pageSize为ALL_PAGE时，查所有数据，不需要分页
     */
    public static final int     ALL_PAGE                           = -1;
    /**
     * 存在状态常量
     */
    public static final int     EXISTENCE_STATUS                   = 1;
    /**
     * 品类型号非系统默认常量
     */
    public static final int     NOT_SYSTEM_DEFAULT_CATEGORY        = 1;

    /**
     * redis 存放操作系统redis key
     */
    public static final String  SYSTEM_OS_REDIS_KEY                = "system:Baseline:os";

    /**
     * EXCEL 导入最大多少条记录
     */
    public static final int     EXCEL_MAX_ROWS                     = 100;

    /**
     * EXCEL 模板下拉导出开始行数
     */
    public static final int     EXCEL_SELECT_START_ROWS            = 1;

    public static final String  XLS                                = ".xls";
    public static final String  XLSX                               = ".xlsx";

    /**
     * 隐藏列
     */
    public static final String  HIDDEN_SHEET_HEAD                  = "hidden";

    /**
     * EXCEL 下拉框导出最大长度
     */
    public static final Integer MAX_EXCEL_SELECT_LENGTH            = 300;

    public static final String  WINDOWS                            = "Windows";

    public static final String  LINUX                              = "Linux";

    public static final String  OTHER                              = "其它";

}
