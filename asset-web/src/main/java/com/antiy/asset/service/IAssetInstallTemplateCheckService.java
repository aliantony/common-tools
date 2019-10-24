package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetInstallTemplateCheck;
import com.antiy.asset.vo.query.AssetInstallTemplateCheckQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateCheckRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateCheckResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 装机模板审核表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
public interface IAssetInstallTemplateCheckService extends IBaseService<AssetInstallTemplateCheck> {


    /**
     * 通过装机模板ID查询
     * @param queryCondition
     * @return
     * @throws Exception
     */
    List<AssetInstallTemplateCheckResponse> queryTemplateCheckByTemplateId(QueryCondition queryCondition) throws Exception;

}
