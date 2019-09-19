package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetSoftwareInstallTemplateQuery;
import com.antiy.asset.vo.request.AssetSoftwareInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallTemplateResponse;
import com.antiy.asset.entity.AssetSoftwareInstallTemplate;

/**
 * <p> 装机模板与软件关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetSoftwareInstallTemplateService extends IBaseService<AssetSoftwareInstallTemplate> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetSoftwareInstallTemplateResponse> queryListAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetSoftwareInstallTemplateResponse> queryPageAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetSoftwareInstallTemplateResponse queryAssetSoftwareInstallTemplateById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetSoftwareInstallTemplateById(BaseRequest baseRequest) throws Exception;

}
