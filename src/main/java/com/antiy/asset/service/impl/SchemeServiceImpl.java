package com.antiy.asset.service.impl;

import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 方案表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Slf4j
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService {


    @Resource
    private SchemeDao schemeDao;
    @Resource
    private BaseConverter<SchemeRequest, Scheme> requestConverter;
    @Resource
    private BaseConverter<Scheme, SchemeResponse> responseConverter;

    @Override
    public Integer saveScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        return schemeDao.insert(scheme);
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        return schemeDao.update(scheme);
    }

    @Override
    public List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception {
        List<Scheme> scheme = schemeDao.findListScheme(query);
        //TODO
        List<SchemeResponse> schemeResponse = new ArrayList<SchemeResponse>();
        return schemeResponse;
    }

    public Integer findCountScheme(SchemeQuery query) throws Exception {
        return schemeDao.findCount(query);
    }

    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountScheme(query), query.getCurrentPage(), this.findListScheme(query));
    }
}
