package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 方案表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface ISchemeService extends IBaseService<Scheme> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveScheme(SchemeRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateScheme(SchemeRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception;

}
