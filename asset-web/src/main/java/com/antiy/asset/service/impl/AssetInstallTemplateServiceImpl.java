package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetInstallTemplateResponse;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 装机模板 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
@Service
public class AssetInstallTemplateServiceImpl extends BaseServiceImpl<AssetInstallTemplate>
                                             implements IAssetInstallTemplateService {

    private Logger                                                            logger = LogUtils.get(this.getClass());

    @Resource
    private AssetInstallTemplateDao                                           assetInstallTemplateDao;
    @Resource
    private BaseConverter<AssetInstallTemplateRequest, AssetInstallTemplate>  requestConverter;
    @Resource
    private BaseConverter<AssetInstallTemplate, AssetInstallTemplateResponse> responseConverter;

    @Override
    public String saveAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception {
        AssetInstallTemplate assetInstallTemplate = requestConverter.convert(request, AssetInstallTemplate.class);
        assetInstallTemplateDao.insert(assetInstallTemplate);
        return assetInstallTemplate.getStringId();
    }

    @Override
    public String updateAssetInstallTemplate(AssetInstallTemplateRequest request) throws Exception {
        AssetInstallTemplate assetInstallTemplate = requestConverter.convert(request, AssetInstallTemplate.class);
        return assetInstallTemplateDao.update(assetInstallTemplate).toString();
    }

    @Override
    public List<AssetInstallTemplateResponse> queryListAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        List<AssetInstallTemplate> assetInstallTemplateList = assetInstallTemplateDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetInstallTemplateList, AssetInstallTemplateResponse.class);
    }

    @Override
    public PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        return new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetInstallTemplate(query));
    }

    @Override
    public AssetInstallTemplateResponse queryAssetInstallTemplateById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetInstallTemplateResponse assetInstallTemplateResponse = responseConverter.convert(
            assetInstallTemplateDao.getById(queryCondition.getPrimaryKey()), AssetInstallTemplateResponse.class);
        return assetInstallTemplateResponse;
    }

    @Override
    public String deleteAssetInstallTemplateById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetInstallTemplateDao.deleteById(baseRequest.getStringId()).toString();
    }
}
