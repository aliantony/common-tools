package com.antiy.asset.service;

import java.util.List;

/**
 * @author: zhangbing
 * @date: 2019/5/5 15:08
 * @description:
 */
public interface IAssetTemplateService {

    /**
     * 获取所有的品类和型号
     * @return
     */
    List<String> queryAllCategoryModels() throws Exception;

    /**
     * 获取所有的区域信息
     * @return
     * @throws Exception
     */
    List<String> queryAllArea() throws Exception;

    /**
     * 获取所有的使用者
     * @return
     * @throws Exception
     */
    List<String> getAllUser() throws Exception;

    /**
     * 获取所有的操作系统
     * @return
     * @throws Exception
     */
    List<String> getAllSystemOs() throws Exception;
}
