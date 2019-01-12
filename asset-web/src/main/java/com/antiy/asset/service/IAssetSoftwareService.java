package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.asset.vo.response.OsResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 软件信息表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetSoftwareService extends IBaseService<AssetSoftware> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception;

    /**
     * 批量保存
     *
     * @param assetSoftwareList
     * @return
     */
    Integer batchSave(List<AssetSoftware> assetSoftwareList) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception;

    /**
     * 查询操作系统
     *
     * @return
     */
    List<OsResponse> findOS() throws Exception;

    /**
     * 查询厂商
     * @param manufacturerName
     * @return
     * @throws Exception
     */
    List<String> getManufacturerName(String manufacturerName) throws Exception;

}
