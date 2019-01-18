package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 方案表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService {

    @Resource
    private SchemeDao                             schemeDao;
    @Resource
    private AesEncoder                            aesEncoder;
    @Resource
    private BaseConverter<SchemeRequest, Scheme>  requestConverter;
    @Resource
    private BaseConverter<Scheme, SchemeResponse> responseConverter;

    @Override
    public String saveScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        if (request.getTopCategory().equals(AssetOperationTableEnum.ASSET.getCode())) {
            AssetStatusProcessor assetStatusProcessor = new AssetStatusProcessor();
            assetStatusProcessor.changeStatus(request);
        } else if (request.getTopCategory().equals(AssetOperationTableEnum.SOFTWARE.getCode())) {
            SoftwarStatusProcessor softwarStatusProcessor = new SoftwarStatusProcessor();
        }
        schemeDao.insert(scheme);
        return aesEncoder.decode(scheme.getId().toString(), LoginUserUtil.getLoginUser().getPassword());
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        // scheme.setModifyUser(LoginUserUtil.getLoginUser().getId());
        scheme.setGmtModified(System.currentTimeMillis());
        return schemeDao.update(scheme);
    }

    @Override
    public List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception {
        List<Scheme> schemeList = schemeDao.findQuery(query);
        // TODO
        List<SchemeResponse> schemeResponse = responseConverter.convert(schemeList, SchemeResponse.class);
        return schemeResponse;
    }

    @Override
    public SchemeResponse findSchemeById(String id) throws Exception {
        return responseConverter.convert(super.getById(id), SchemeResponse.class);
    }

    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListScheme(query));
    }
}
