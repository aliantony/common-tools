package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.entity.dto.SchemeDTO;
import com.antiy.asset.entity.vo.query.SchemeQuery;

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
