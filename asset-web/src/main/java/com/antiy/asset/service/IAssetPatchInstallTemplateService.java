package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetPatchInstallTemplateQuery;
import com.antiy.asset.vo.request.AssetPatchInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetPatchInstallTemplateResponse;
import com.antiy.asset.entity.AssetPatchInstallTemplate;

/**
 * <p> 装机模板与补丁关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetPatchInstallTemplateService extends IBaseService<AssetPatchInstallTemplate> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetPatchInstallTemplate(AssetPatchInstallTemplateRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetPatchInstallTemplate(AssetPatchInstallTemplateRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetPatchInstallTemplateResponse> queryListAssetPatchInstallTemplate(AssetPatchInstallTemplateQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetPatchInstallTemplateResponse> queryPageAssetPatchInstallTemplate(AssetPatchInstallTemplateQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetPatchInstallTemplateResponse queryAssetPatchInstallTemplateById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetPatchInstallTemplateById(BaseRequest baseRequest) throws Exception;

}
