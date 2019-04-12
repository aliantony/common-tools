package com.antiy.asset.service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: zhangbing
 * @date: 2019/4/12 14:58
 * @description:
 */
public interface IRedisService {

    /**
     * 获取所有的操作系统信息
     * @return
     */
    List<LinkedHashMap> getAllSystemOs() throws Exception;
}
