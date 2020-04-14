package com.antiy.asset.service;

import com.antiy.asset.entity.AssetNettypeManage;
import com.antiy.asset.vo.query.AssetNettypeManageQuery;
import com.antiy.asset.vo.request.AssetNettypeManageRequest;
import com.antiy.asset.vo.response.AssetNettypeManageResponse;
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
 * @author wangqian
 * @since 2020-04-07
 */
public interface IAssetNettypeManageService extends IBaseService<AssetNettypeManage> {

        /**
         * 保存
         * @param request
         * @return
         */
        String saveAssetNettypeManage(AssetNettypeManageRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        String updateAssetNettypeManage(AssetNettypeManageRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetNettypeManageResponse> queryListAssetNettypeManage(AssetNettypeManageQuery query) throws Exception;

        /**
         * 分页查询
         * @param query
         * @return
         */
        PageResult<AssetNettypeManageResponse> queryPageAssetNettypeManage(AssetNettypeManageQuery query) throws Exception;

        /**
         * 通过ID查询
         * @param queryCondition
         * @return
         */
        AssetNettypeManageResponse queryAssetNettypeManageById(QueryCondition queryCondition) throws Exception;

        /**
         * 通过ID删除
         * @param baseRequest
         * @return
         */
        String deleteAssetNettypeManageById(BaseRequest baseRequest) throws Exception;

        /**
         * 查询全部
         * @return
         */
        List<AssetNettypeManageResponse> getAllList() throws Exception;

        /**
         * 根据网络类型名称查询id
         * @param name
         * @return
         */
        List<String> getIdsByName(String name);
}
