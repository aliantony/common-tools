package com.antiy.asset.service;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.vo.enums.CodeEnum;

/**
 * @author zhangyajun
 * @create 2019-01-16 17:41
 **/
public interface Processor {
    /**
     * 保存流程操作历史
     * @param scheme
     */
    // AssetOperationRecord saveOperationRecord(Scheme scheme) throws Exception;

    void process(String assetOperationEnum, Scheme scheme) throws Exception;
}
