package com.antiy.asset.service;

import com.antiy.asset.entity.AssetBusiness;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.query.AssetBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
public interface IAssetBusinessService extends IBaseService<AssetBusiness> {

        /**
         * 保存
         * @param request
         * @return
         */
        String saveAssetBusiness(AssetBusinessRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        String updateAssetBusiness(AssetBusinessRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetBusinessResponse> queryListAssetBusiness(AssetBusinessQuery query) throws Exception;

        /**
         * 分页查询
         * @param query
         * @return
         */
        PageResult<AssetBusinessResponse> queryPageAssetBusiness(AssetBusinessQuery query) throws Exception;

        /**
         * 通过ID查询
         * @param queryCondition
         * @return
         */
        AssetBusinessResponse queryAssetBusinessById(QueryCondition queryCondition) throws Exception;

        /**
         * 通过ID删除
         * @param baseRequest
         * @return
         */
        String deleteAssetBusinessById(BaseRequest baseRequest) throws Exception;

        PageResult<AssetResponse> queryAsset(AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception;

        List<AssetBusinessRelationResponse> queryAssetByBusinessId(AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception;

        AssetBusinessResponse getByUniqueId(String uniqueId);

    Integer updateStatusByUniqueId(String uniqueId);
}
