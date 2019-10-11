package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.entity.PatchInfo;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateCheckRequest;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetInstallTemplateOsResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateResponse;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.common.base.IBaseDao;
import com.antiy.common.base.ObjectQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p> 装机模板 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface AssetInstallTemplateDao extends IBaseDao<AssetInstallTemplate> {

    public List<AssetInstallTemplate> findByAssetIds(List<String> ids);

    /**
     * 通过资产ID查询
     *
     * @param templateId
     * @return
     * @throws Exception
     */
    AssetTemplateRelationResponse queryTemplateById(Integer templateId) throws Exception;

    /**
     * 模板查询-所有装机模板操作系统名称(去重)
     *
     * @return
     */
    List<AssetInstallTemplateOsResponse> queryTemplateOs();

    /**
     * 模板查询-所有装机模板操作系统状态(去重)
     *
     * @return
     */
    List<Integer> queryTemplateStatus();

    /**
     * 查询模板编号是否存在
     *
     * @param numberCode
     * @return
     */
    Integer queryNumberCode(String numberCode);

    /**
     * 模板创建/编辑-查询操作系统 匹配知识库
     *
     * @return
     */
    List<AssetInstallTemplateOsResponse> queryOs(@Param("osBusinessId") String osBusinessId);

    /**
     * 查询装机模板的软件数量
     *
     * @param query
     * @return
     */
    Integer querySoftCount(PrimaryKeyQuery query);

    /**
     * 查询装机模板的软件列表
     *
     * @param query
     * @return
     */
    List<AssetHardSoftLib> querySoftList(PrimaryKeyQuery query);

    /**
     * 查询装机模板的补丁数量
     *
     * @param query
     * @return
     */
    Integer queryPatchCount(PrimaryKeyQuery query);


    List<PatchInfo> queryPatchList(PrimaryKeyQuery query);

    /**
     * 批量删除
     */
    int batchDeleteTemplate(@Param("ids") List<String> ids, @Param("gmtModified") Long gmtModified,
                            @Param("modifiedUser") String modifiedUser);

    Integer queryBaselineTemplateType(ObjectQuery query);

    Integer findFilteredCount(ObjectQuery query);

    List<AssetInstallTemplateResponse> queryTemplateInfo(ObjectQuery query);

    List<AssetInstallTemplateResponse> queryFilteredTemplate(ObjectQuery query);

    /**
     * 批量插入补丁与装机模板的关系
     *
     * @param request
     * @return
     */
    Integer insertBatchPatch(AssetInstallTemplateRequest request);

    /**
     * 批量插入软件与装机模板的关系
     *
     * @param request
     * @return
     */
    Integer insertBatchSoft(AssetInstallTemplateRequest request);

    /**
     * 批量插入审核用户与装机模板的关系
     *
     * @param request
     * @return
     */
    Integer insertBatchUser(AssetInstallTemplateRequest request);

    Set<String> queryCheckTemplateUserId(@Param("installTemplateId") Integer installTemplateId);

    Integer checkTemplate(AssetInstallTemplateCheckRequest request);

    Integer updateStatus(AssetInstallTemplate template);

    Integer deleteBatchSoft(AssetInstallTemplateRequest request);

    Integer deleteBatchPatch(AssetInstallTemplateRequest request);

    Integer deleteBatchUser(AssetInstallTemplateRequest request);

    Set<String> queryPatchIds(PrimaryKeyQuery query);

    List<PatchInfo> queryPatchRelations(@Param("installTemplateId") String installTemplateId);
}
