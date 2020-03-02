package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.vo.response.AssetCountIncludeResponse;
import com.antiy.asset.vo.response.AssetOnlineChartResponse;
import com.antiy.asset.vo.response.EnumCountResponse;

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

    /**
     * 资产在线情况
     *
     * @return
     */
    AssetOnlineChartResponse assetOnlineChart() throws Exception;

    /**
     * 资产重要程度分布
     *
     * @return
     */
    List<EnumCountResponse> assetImportanceDegreePie() throws Exception;

}
