package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.entity.vo.query.SchemeQuery;
import com.antiy.asset.entity.vo.response.SchemeResponse;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface SchemeDao extends IBaseDao<Scheme> {

    List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception;
}
