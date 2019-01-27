package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.query.AssetIDAndSchemeTypeQuery;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

/**
 * * <p> 方案 服务实现类 </p>
 *
 * @author zhangyajun
 * @create 2019-01-27 11:05
 **/
@Service
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService {

    @Resource
    SchemeDao                                     schemeDao;
    @Resource
    private AesEncoder                            aesEncoder;
    @Resource
    private BaseConverter<Scheme, SchemeResponse> responseBaseConverter;

    @Override
    public String saveScheme(AssetStatusReqeust assetStatusReqeust) throws Exception {
        return null;
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        return null;
    }

    @Override
    public List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception {
        return responseBaseConverter.convert(schemeDao.findQuery(query), SchemeResponse.class);
    }

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), schemeDao.findCount(query), query.getCurrentPage(),
            this.findListScheme(query));
    }

    /**
     * 根据方案ID和类型查询方案信息
     * @param query
     * @return
     * @throws Exception
     */
    @Override
    public SchemeResponse findSchemeByAssetIdAndType(AssetIDAndSchemeTypeQuery query) throws Exception {
        query.setAssetId(aesEncoder.decode(query.getAssetId(), LoginUserUtil.getLoginUser().getUsername()));
        return responseBaseConverter.convert(schemeDao.findSchemeByAssetIdAndType(query), SchemeResponse.class);
    }
}
