package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.util.List;
import com.antiy.asset.vo.query.AssetStorageMediumQuery;
import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import com.antiy.asset.vo.response.AssetStorageMediumResponse;
import com.antiy.asset.entity.AssetStorageMedium;

/**
 * <p> 服务类 </p>
 *
 * @author lvliang
 * @since 2019-01-17
 */
public interface IAssetStorageMediumService extends IBaseService<AssetStorageMedium> {

    /**
     * 保存
     * @param request
     * @return
     */
    Integer saveAssetStorageMedium(AssetStorageMediumRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    Integer updateAssetStorageMedium(AssetStorageMediumRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetStorageMediumResponse> findListAssetStorageMedium(AssetStorageMediumQuery query) throws Exception;

    /**
     * 批量查询
     * @param query
     * @return
     */
    PageResult<AssetStorageMediumResponse> findPageAssetStorageMedium(AssetStorageMediumQuery query) throws Exception;

}
