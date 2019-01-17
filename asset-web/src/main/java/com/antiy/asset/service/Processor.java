package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.vo.request.SchemeRequest;

/**
 * @author zhangyajun
 * @create 2019-01-16 17:41
 **/
public interface Processor {
    /**
     * 保存流程操作历史
     * @param schemeRequest
     */
    void saveOperationHistory(SchemeRequest schemeRequest) throws Exception;
}
