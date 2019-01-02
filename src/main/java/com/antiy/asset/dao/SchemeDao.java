package com.antiy.asset.dao;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 方案表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface SchemeDao extends IBaseDao<Scheme> {

    List<Scheme> findListScheme(SchemeQuery query) throws Exception;
}
