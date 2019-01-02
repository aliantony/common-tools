package com.antiy.asset.dao;

import com.antiy.asset.dto.SchemeDTO;
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
 * @since 2018-12-29
 */
public interface SchemeDao extends IBaseDao<Scheme> {

    List<SchemeDTO> findListScheme(SchemeQuery query) throws Exception;
}
