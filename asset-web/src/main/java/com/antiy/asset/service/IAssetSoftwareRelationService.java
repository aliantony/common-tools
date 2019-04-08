package com.antiy.asset.service;

import java.util.List;
import java.util.Map;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.entity.AssetSoftwareRelationMapper;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

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
     * 通过资产ID分页查询关联软件简要信息 id,名称,品类型号,软件大小,厂商,发布时间,端口,许可秘钥
     *
     * @param query
     * @return
     */
    PageResult<AssetSoftwareRelationResponse> getSimpleSoftwarePageByAssetId(AssetSoftwareRelationQuery query);

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
    List<String> findOS() throws Exception;

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
    void installSoftware(AssetSoftwareRelationList assetSoftwareRelationList);

    /**
     * 软件安装列表
     * @param query
     * @return
     * @throws Exception
     */
    PageResult<AssetSoftwareInstallResponse> queryInstallList(InstallQuery query) throws Exception;
}
