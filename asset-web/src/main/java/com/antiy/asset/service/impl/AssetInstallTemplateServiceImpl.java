package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateOsResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateResponse;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

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
    public List<AssetInstallTemplateOsResponse> queryTemplateOs() {
        return assetInstallTemplateDao.queryTemplateOs();
    }

    @Override
    public List<Integer> queryTemplateStatus() {
        // List<Integer> statusCode = assetInstallTemplateDao.queryTemplateStatus();
        //
        // Stream.of(AssetInstallTemplateStatusEnum.values()).filter(v->v.getCode()==statusCode);
        return assetInstallTemplateDao.queryTemplateStatus();
    }

    @Override
    public Integer queryNumberCode(String numberCode) {
        return assetInstallTemplateDao.queryNumberCode(numberCode);
    }

    @Override
    public List<AssetInstallTemplateOsResponse> queryOs() {
        return assetInstallTemplateDao.queryOs();
    }

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
        return responseConverter.convert(assetInstallTemplateList, AssetInstallTemplateResponse.class);
    }

    @Override
    public PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        Integer count = this.findCount(query);
        if (count == 0 || count == null) {
            return null;
        }
        return new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), count, query.getCurrentPage(),
            this.queryListAssetInstallTemplate(query));
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

    @Override
    public AssetTemplateRelationResponse queryTemplateByAssetId(QueryCondition queryCondition) throws Exception {
        AssetTemplateRelationResponse templateRelationResponse = assetInstallTemplateDao
            .queryTemplateByAssetId(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey()));
        return templateRelationResponse;
    }

    @Override
    public PageResult<AssetHardSoftLibResponse> querySoftPage(PrimaryKeyQuery query) {
        Integer count = assetInstallTemplateDao.querySoftCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        List<AssetHardSoftLibResponse> assetHardSoftLibResponseList = BeanConvert
            .convert(assetInstallTemplateDao.querySoftList(query), AssetHardSoftLibResponse.class);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetHardSoftLibResponseList);
    }

    @Override
    public PageResult<AssetHardSoftLibResponse> queryPatchPage(PrimaryKeyQuery query) {
        return null;
    }
}
