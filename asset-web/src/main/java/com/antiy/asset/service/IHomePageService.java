package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetCountIncludeResponse;

/**
 * <p> 首页接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IHomePageService {

    /**
     * 统计纳入资产
     *
     * @return
     */
    AssetCountIncludeResponse countIncludeManage() throws Exception;

}
