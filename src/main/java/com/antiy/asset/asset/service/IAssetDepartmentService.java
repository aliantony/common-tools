package com.antiy.asset.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetDepartmentQuery;
import com.antiy.asset.asset.entity.vo.request.AssetDepartmentRequest;
import com.antiy.asset.asset.entity.vo.response.AssetDepartmentResponse;
import com.antiy.asset.asset.entity.AssetDepartment;


/**
 * <p>
 * 资产部门信息 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetDepartmentService extends IBaseService<AssetDepartment> {

        /**
         * 保存
         * @param assetDepartmentRequest
         * @return
         */
        Integer saveAssetDepartment(AssetDepartmentRequest assetDepartmentRequest) throws Exception;

        /**
         * 修改
         * @param assetDepartmentRequest
         * @return
         */
        Integer updateAssetDepartment(AssetDepartmentRequest assetDepartmentRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetDepartmentQuery
         * @return
         */
        List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery assetDepartmentQuery) throws Exception;

        /**
         * 批量查询
         * @param assetDepartmentQuery
         * @return
         */
        PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery assetDepartmentQuery) throws Exception;

}
