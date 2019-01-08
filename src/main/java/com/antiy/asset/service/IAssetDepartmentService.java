package com.antiy.asset.service;

import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import io.swagger.models.auth.In;

import java.util.List;


/**
 * <p>
 * 资产部门信息 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetDepartmentService extends IBaseService<AssetDepartment> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetDepartment(AssetDepartmentRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetDepartment(AssetDepartmentRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetDepartmentResponse> findListAssetDepartment(AssetDepartmentQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetDepartmentResponse> findPageAssetDepartment(AssetDepartmentQuery query) throws Exception;
    /**
     * 根据id查询所有部门及子部门
     *
     * @param id
     * @return
     */
    List<AssetDepartmentResponse> findAssetDepartmentById(Integer id) throws Exception;


}
