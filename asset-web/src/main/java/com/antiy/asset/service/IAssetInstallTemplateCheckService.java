package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetInstallTemplateCheckQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateCheckRequest;
import com.antiy.asset.vo.response.AssetInstallTemplateCheckResponse;
import com.antiy.asset.entity.AssetInstallTemplateCheck;

/**
 * <p> 装机模板审核表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface IAssetInstallTemplateCheckService extends IBaseService<AssetInstallTemplateCheck> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetInstallTemplateCheck(AssetInstallTemplateCheckRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetInstallTemplateCheck(AssetInstallTemplateCheckRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetInstallTemplateCheckResponse> queryListAssetInstallTemplateCheck(AssetInstallTemplateCheckQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetInstallTemplateCheckResponse> queryPageAssetInstallTemplateCheck(AssetInstallTemplateCheckQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetInstallTemplateCheckResponse queryAssetInstallTemplateCheckById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetInstallTemplateCheckById(BaseRequest baseRequest) throws Exception;

}
