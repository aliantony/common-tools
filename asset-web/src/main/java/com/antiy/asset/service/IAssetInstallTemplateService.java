package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 装机模板 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface IAssetInstallTemplateService extends IBaseService<AssetInstallTemplate> {

    /**
     * 模板查询-操作系统(去重)
     *
     * @return
     */
    List<AssetInstallTemplateOsResponse> queryTemplateOs();

    /**
     * 模板查询-状态(去重)
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
     * 模板创建/编辑-查询操作系统
     *
     * @return
     */
    List<AssetInstallTemplateOsResponse> queryOs();

    /**
     * 保存
     *
     * @param request
     * @return
     */
    String saveAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    String updateAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetInstallTemplateResponse> queryListAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception;

    /**
     * 通过ID查询
     *
     * @param queryCondition
     * @return
     */
    AssetInstallTemplateResponse queryAssetInstallTemplateById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     *
     * @param request
     * @return
     */
    String deleteAssetInstallTemplateById(BatchQueryRequest request) throws Exception;

    /**
     * 通过资产ID查询
     *
     * @param queryCondition
     * @return
     * @throws Exception
     */
    AssetTemplateRelationResponse queryTemplateByAssetId(QueryCondition queryCondition) throws Exception;

    /**
     * 模板软件查询
     *
     * @param query
     * @return
     */
    PageResult<AssetHardSoftLibResponse> querySoftPage(PrimaryKeyQuery query);

    /**
     * 补丁列表查询
     *
     * @param query
     * @return
     */
    PageResult<PatchInfoResponse> queryPatchPage(PrimaryKeyQuery query);

    /**
     * 启用禁用
     *
     * @param request
     * @return
     */
    int enableInstallTemplate(AssetInstallTemplateRequest request) throws Exception;

    /**
     * 补丁列表
     *
     * @param query
     * @return
     */
    List<PatchInfoResponse> queryPatchs(PrimaryKeyQuery query);
}
