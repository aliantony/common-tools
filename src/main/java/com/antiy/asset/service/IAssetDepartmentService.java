package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetDepartmentDTO;;
import com.antiy.asset.entity.vo.query.AssetDepartmentQuery;
import com.antiy.asset.entity.vo.request.AssetDepartmentRequest;
import com.antiy.asset.entity.vo.response.AssetDepartmentResponse;
import com.antiy.asset.entity.AssetDepartment;


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
         * @param request
         * @return
         */
        Integer saveAssetDepartment(AssetDepartmentRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetDepartment(AssetDepartmentRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery query) throws Exception;

}
