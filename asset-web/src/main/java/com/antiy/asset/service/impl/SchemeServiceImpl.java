package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * * <p> 方案 服务实现类 </p>
 *
 * @author zhangyajun
 * @create 2019-01-27 11:05
 **/
@Service
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService {

    @Resource
    private SchemeDao                                     schemeDao;
    @Resource
    private AesEncoder                            aesEncoder;
    @Resource
    private BaseConverter<Scheme, SchemeResponse> responseBaseConverter;
    @Resource
    private BaseConverter<List<Scheme>, SchemeResponse> schemeListToResponseConverter;

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

    @Override
    public SchemeResponse queryMemoById(SchemeQuery query) {
        ParamterExceptionUtils.isNull(query.getAssetId(), "资产ID不能为空");
        ParamterExceptionUtils.isNull(query.getAssetStatus(), "资产状态不能为空");
        ParamterExceptionUtils.isNull(query.getAssetTypeEnum(), "类型不能为空");
        // 上一步状态的备注
        query.setAssetStatus(query.getAssetStatus() - 1);
        Scheme scheme = schemeDao.findMemoById(query);
        if (scheme != null) {
            scheme.setFileInfo(HtmlUtils.htmlUnescape(scheme.getFileInfo()));
        }
        return responseBaseConverter.convert(scheme, SchemeResponse.class);
    }
}
