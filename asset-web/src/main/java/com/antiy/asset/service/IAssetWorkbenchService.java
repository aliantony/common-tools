package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetWorkBenchResponse;
import org.springframework.stereotype.Service;

@Service
public interface IAssetWorkbenchService {


    /**
     * 功能描述 : 查询工作台各资产数量
     * @author ygh
     * @date 19:37
     */
    public AssetWorkBenchResponse queryWorkBench();
}
