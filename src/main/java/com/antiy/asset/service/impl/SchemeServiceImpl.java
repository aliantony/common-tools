package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.entity.vo.request.SchemeRequest;
import com.antiy.asset.entity.vo.response.SchemeResponse;
import com.antiy.asset.entity.vo.query.SchemeQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService{


        @Resource
        private SchemeDao schemeDao;

        private BaseConverter<SchemeRequest, Scheme>  requestConverter;
        
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
            return schemeDao.findListScheme(query);
        }

        public Integer findCountScheme(SchemeQuery query) throws Exception {
            return schemeDao.findCount(query);
        }

        @Override
        public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountScheme(query),query.getCurrentPage(), this.findListScheme(query));
        }
}
