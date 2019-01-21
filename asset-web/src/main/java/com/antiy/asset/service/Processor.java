package com.antiy.asset.service;

import com.antiy.asset.vo.request.SchemeRequest;

/**
 * @author zhangyajun
 * @create 2019-01-16 17:41
 **/
public interface Processor {
    /**
     * 统一处理流程操作历史和工单
     * @param schemeRequest
     */
    Integer process(SchemeRequest schemeRequest) throws Exception;
}
