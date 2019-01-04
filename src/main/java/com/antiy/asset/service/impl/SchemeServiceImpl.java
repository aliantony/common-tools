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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        //TODO 添加创建人信息
        scheme.setGmtCreate(System.currentTimeMillis());
        return schemeDao.insert(scheme);
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        //TODO 添加修改人信息
        return schemeDao.update(scheme);
    }

    @Override
    public List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception {
        List<Scheme> schemeList = schemeDao.findQuery(query);
        //TODO
        List<SchemeResponse> schemeResponse = responseConverter.convert(schemeList, SchemeResponse.class);
        return schemeResponse;
    }

    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListScheme(query));
    }
}
