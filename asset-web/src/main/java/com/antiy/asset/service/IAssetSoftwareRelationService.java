package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 资产软件关系信息 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetSoftwareRelationService extends IBaseService<AssetSoftwareRelation> {

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
     * 已软件安装列表
     * @param query
     * @return
     * @throws Exception
     */
    List<AssetSoftwareInstallResponse> queryInstalledList(QueryCondition query) throws Exception;

    /**
     * 可安装软件列表
     * @param query
     * @return
     */
    PageResult<AssetSoftwareInstallResponse> queryInstallableList(InstallQuery query);

    /**
     * 批量关联软件
     * @param softwareReportRequest
     * @return
     */
    Integer batchRelation(AssetSoftwareReportRequest softwareReportRequest);
}
