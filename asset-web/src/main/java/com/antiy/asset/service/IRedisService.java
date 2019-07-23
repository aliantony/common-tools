package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.vo.response.BaselineCategoryModelResponse;

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
    List<BaselineCategoryModelResponse> getAllSystemOs() throws Exception;
}
