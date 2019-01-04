package com.antiy.asset.service;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 资产主表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetService extends IBaseService<Asset> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAsset(AssetRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAsset(AssetRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetResponse> findListAsset(AssetQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception;

    /**
     * 保存上报数据
     * @param assetOuterRequestList
     * @throws Exception
     */
    void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception;

    /**
     * 判断资产是否重复
     * @param uuid
     * @param ipMac
     * @return
     */
    boolean checkRepeatAsset(String uuid, List<String[]> ipMac);
    /**
     * 批量修改
     *
     * @param idArrays
     * @param assetStatus
     * @return
     */
    Integer changeStatus(Integer[] idArrays, Integer assetStatus) throws Exception;

    /**
     * 批量保存
     *
     * @param assets
     * @return
     */
    Integer batchSave(List<Asset> assets) throws Exception;

}
