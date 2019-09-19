package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetSoftwareInstallTemplate;
import com.antiy.asset.dao.AssetSoftwareInstallTemplateDao;
import com.antiy.asset.service.IAssetSoftwareInstallTemplateService;
import com.antiy.asset.vo.request.AssetSoftwareInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallTemplateResponse;
import com.antiy.asset.vo.query.AssetSoftwareInstallTemplateQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 装机模板与软件关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetSoftwareInstallTemplateServiceImpl extends BaseServiceImpl<AssetSoftwareInstallTemplate>
                                                     implements IAssetSoftwareInstallTemplateService {

    private Logger                                                                            logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetSoftwareInstallTemplateDao                                                   assetSoftwareInstallTemplateDao;
    @Resource
    private BaseConverter<AssetSoftwareInstallTemplateRequest, AssetSoftwareInstallTemplate>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftwareInstallTemplate, AssetSoftwareInstallTemplateResponse> responseConverter;

    @Override
    public String saveAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateRequest request) throws Exception {
        AssetSoftwareInstallTemplate assetSoftwareInstallTemplate = requestConverter.convert(request,
            AssetSoftwareInstallTemplate.class);
        assetSoftwareInstallTemplateDao.insert(assetSoftwareInstallTemplate);
        return assetSoftwareInstallTemplate.getStringId();
    }

    @Override
    public String updateAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateRequest request) throws Exception {
        AssetSoftwareInstallTemplate assetSoftwareInstallTemplate = requestConverter.convert(request,
            AssetSoftwareInstallTemplate.class);
        return assetSoftwareInstallTemplateDao.update(assetSoftwareInstallTemplate).toString();
    }

    @Override
    public List<AssetSoftwareInstallTemplateResponse> queryListAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateQuery query) throws Exception {
        List<AssetSoftwareInstallTemplate> assetSoftwareInstallTemplateList = assetSoftwareInstallTemplateDao
            .findQuery(query);
        // TODO
        return responseConverter.convert(assetSoftwareInstallTemplateList, AssetSoftwareInstallTemplateResponse.class);
    }

    @Override
    public PageResult<AssetSoftwareInstallTemplateResponse> queryPageAssetSoftwareInstallTemplate(AssetSoftwareInstallTemplateQuery query) throws Exception {
        return new PageResult<AssetSoftwareInstallTemplateResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetSoftwareInstallTemplate(query));
    }

    @Override
    public AssetSoftwareInstallTemplateResponse queryAssetSoftwareInstallTemplateById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetSoftwareInstallTemplateResponse assetSoftwareInstallTemplateResponse = responseConverter.convert(
            assetSoftwareInstallTemplateDao.getById(queryCondition.getPrimaryKey()),
            AssetSoftwareInstallTemplateResponse.class);
        return assetSoftwareInstallTemplateResponse;
    }

    @Override
    public String deleteAssetSoftwareInstallTemplateById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetSoftwareInstallTemplateDao.deleteById(baseRequest.getStringId()).toString();
    }
}
