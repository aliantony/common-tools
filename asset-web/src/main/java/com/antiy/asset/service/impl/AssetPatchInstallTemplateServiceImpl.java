package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetPatchInstallTemplate;
import com.antiy.asset.dao.AssetPatchInstallTemplateDao;
import com.antiy.asset.service.IAssetPatchInstallTemplateService;
import com.antiy.asset.vo.request.AssetPatchInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetPatchInstallTemplateResponse;
import com.antiy.asset.vo.query.AssetPatchInstallTemplateQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 装机模板与补丁关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetPatchInstallTemplateServiceImpl extends BaseServiceImpl<AssetPatchInstallTemplate>
                                                  implements IAssetPatchInstallTemplateService {

    private Logger                                                                      logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetPatchInstallTemplateDao                                                assetPatchInstallTemplateDao;
    @Resource
    private BaseConverter<AssetPatchInstallTemplateRequest, AssetPatchInstallTemplate>  requestConverter;
    @Resource
    private BaseConverter<AssetPatchInstallTemplate, AssetPatchInstallTemplateResponse> responseConverter;

    @Override
    public String saveAssetPatchInstallTemplate(AssetPatchInstallTemplateRequest request) throws Exception {
        AssetPatchInstallTemplate assetPatchInstallTemplate = requestConverter.convert(request,
            AssetPatchInstallTemplate.class);
        assetPatchInstallTemplateDao.insert(assetPatchInstallTemplate);
        return assetPatchInstallTemplate.getStringId();
    }

    @Override
    public String updateAssetPatchInstallTemplate(AssetPatchInstallTemplateRequest request) throws Exception {
        AssetPatchInstallTemplate assetPatchInstallTemplate = requestConverter.convert(request,
            AssetPatchInstallTemplate.class);
        return assetPatchInstallTemplateDao.update(assetPatchInstallTemplate).toString();
    }

    @Override
    public List<AssetPatchInstallTemplateResponse> queryListAssetPatchInstallTemplate(AssetPatchInstallTemplateQuery query) throws Exception {
        List<AssetPatchInstallTemplate> assetPatchInstallTemplateList = assetPatchInstallTemplateDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetPatchInstallTemplateList, AssetPatchInstallTemplateResponse.class);
    }

    @Override
    public PageResult<AssetPatchInstallTemplateResponse> queryPageAssetPatchInstallTemplate(AssetPatchInstallTemplateQuery query) throws Exception {
        return new PageResult<AssetPatchInstallTemplateResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetPatchInstallTemplate(query));
    }

    @Override
    public AssetPatchInstallTemplateResponse queryAssetPatchInstallTemplateById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetPatchInstallTemplateResponse assetPatchInstallTemplateResponse = responseConverter.convert(
            assetPatchInstallTemplateDao.getById(queryCondition.getPrimaryKey()),
            AssetPatchInstallTemplateResponse.class);
        return assetPatchInstallTemplateResponse;
    }

    @Override
    public String deleteAssetPatchInstallTemplateById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetPatchInstallTemplateDao.deleteById(baseRequest.getStringId()).toString();
    }
}
