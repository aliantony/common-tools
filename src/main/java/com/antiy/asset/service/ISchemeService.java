package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.vo.query.SchemeQuery;
import com.antiy.asset.entity.vo.request.SchemeRequest;
import com.antiy.asset.entity.vo.response.SchemeResponse;
import com.antiy.asset.entity.Scheme;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface ISchemeService extends IBaseService<Scheme> {

        /**
         * 保存
         * @param schemeRequest
         * @return
         */
        Integer saveScheme(SchemeRequest schemeRequest) throws Exception;

        /**
         * 修改
         * @param schemeRequest
         * @return
         */
        Integer updateScheme(SchemeRequest schemeRequest) throws Exception;

        /**
         * 查询对象集合
         * @param schemeQuery
         * @return
         */
        List<SchemeResponse> findListScheme(SchemeQuery schemeQuery) throws Exception;

        /**
         * 批量查询
         * @param schemeQuery
         * @return
         */
        PageResult<SchemeResponse> findPageScheme(SchemeQuery schemeQuery) throws Exception;

}
