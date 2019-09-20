package com.antiy.asset.service;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetRelationSoftRequest;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p> 资产软件关系信息 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetSoftwareRelationService extends IBaseService<AssetSoftwareRelation> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;

    /**
     * 通过资产ID查询关联软件信息
     *
     * @param assetId
     * @return
     */
    List<AssetSoftwareResponse> getSoftByAssetId(Integer assetId);


    /**
     * 通过软件ID统计资产数量
     *
     * @param id
     * @return
     */
    Integer countAssetBySoftId(Integer id);

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return
     */
    List<SelectResponse> findOS() throws Exception;

    /**
     * 批量修改软件状态
     *
     * @param map
     * @return
     */
    Integer changeSoftwareStatus(Map<String, Object> map) throws Exception;

    /**
     * 人工安装软件
     * @param assetSoftwareRelationList
     * @return
     */
    Integer installArtificial(List<AssetSoftwareRelationRequest> assetSoftwareRelationList);

    /**
     * 自动安装软件
     * @param assetSoftwareRelationList
     * @return
     */
    Integer installAauto(List<AssetSoftwareRelationRequest> assetSoftwareRelationList);

    /**
     * 软件安装
     * @param assetSoftwareRelationList
     * @return
     */
    ActionResponse installSoftware(AssetSoftwareRelationList assetSoftwareRelationList) throws Exception;

    /**
     * 软件安装列表
     * @param query
     * @return
     * @throws Exception
     */
    PageResult<AssetSoftwareInstallResponse> queryInstallList(InstallQuery query) throws Exception;

    /**
     * 修改软件和硬件安装的状态
     * @param assetRelationSoftRequest
     * @throws Exception
     */
    Integer updateAssetReleation(AssetRelationSoftRequest assetRelationSoftRequest) throws Exception;

    /**
     * 分页查询资产关联的软件信息列表
     * @param queryCondition
     * @return
     */
    PageResult<SoftwareResponse> getPageSoftWareList(AssetSoftwareRelationQuery queryCondition);
}
