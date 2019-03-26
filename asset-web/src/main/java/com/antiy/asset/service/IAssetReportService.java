package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetCategoryModelResponse;

/**
 * <p> 资产报表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-03-26
 */
public interface IAssetReportService {

    AssetCategoryModelResponse queryCategoryCountByTime();

}
