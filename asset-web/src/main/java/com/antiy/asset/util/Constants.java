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

}
