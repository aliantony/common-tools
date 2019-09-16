package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetInstallTemplateResponse;
import com.antiy.asset.entity.AssetInstallTemplate;

/**
 * <p> 装机模板 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface IAssetInstallTemplateService extends IBaseService<AssetInstallTemplate> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetInstallTemplateResponse> queryListAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetInstallTemplateResponse queryAssetInstallTemplateById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetInstallTemplateById(BaseRequest baseRequest) throws Exception;

}
