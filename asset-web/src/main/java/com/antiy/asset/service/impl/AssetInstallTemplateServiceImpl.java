package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetInstallTemplateStatusEnum;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateCheckRequest;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.asset.vo.request.SysArea;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.*;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p> 装机模板 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-16
 */
@Service
public class AssetInstallTemplateServiceImpl extends BaseServiceImpl<AssetInstallTemplate>
        implements IAssetInstallTemplateService {

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetInstallTemplateDao assetInstallTemplateDao;
    @Resource
    private BaseConverter<AssetInstallTemplateRequest, AssetInstallTemplate> requestConverter;
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
        if (numberCode == null || numberCode.isEmpty()) {
            return 0;
        }
        return assetInstallTemplateDao.queryNumberCode(numberCode);
    }

    @Override
    public List<AssetInstallTemplateOsResponse> queryOs(String osBusinessId) {
        return assetInstallTemplateDao.queryOs(osBusinessId);
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
        assetInstallTemplate.setGmtModified(System.currentTimeMillis());
        assetInstallTemplate.setModifiedUser(LoginUserUtil.getLoginUser().getName());
        if (assetInstallTemplateDao.update(assetInstallTemplate).toString().equals("1")) {
            return "更新成功，请刷新界面";
        }
        return "更新失败";
    }

    @Override
    public List<AssetInstallTemplateResponse> queryListAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        List<AssetInstallTemplate> assetInstallTemplateList = assetInstallTemplateDao.findQuery(query);
        return responseConverter.convert(assetInstallTemplateList, AssetInstallTemplateResponse.class);
    }

    @Override
    public PageResult<AssetInstallTemplateResponse> queryPageAssetInstallTemplate(AssetInstallTemplateQuery query) throws Exception {
        String baselineId = query.getBaselineId();
        PageResult<AssetInstallTemplateResponse> responsePageResult = null;

        Integer count = baselineId == null ? this.findCount(query) : assetInstallTemplateDao.findFilteredCount(query);
        if (count == 0 || count == null) {

            return new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), 0, query.getCurrentPage(), new ArrayList<AssetInstallTemplateResponse>());
        }
        Integer type = assetInstallTemplateDao.queryBaselineTemplateType(query);
        if (baselineId == null || (baselineId != null && (type == null || type != 2))) {
            responsePageResult = new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), count, query.getCurrentPage(),
                    assetInstallTemplateDao.queryTemplateInfo(query));
        }
        //根据配置模板id过滤包含黑名单软件的装机模板
        else {
            responsePageResult = new PageResult<AssetInstallTemplateResponse>(query.getPageSize(), count, query.getCurrentPage(),
                    assetInstallTemplateDao.queryFilteredTemplate(query));
        }
        return responsePageResult;
    }

    @Override
    public AssetInstallTemplateResponse queryAssetInstallTemplateById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetInstallTemplateResponse assetInstallTemplateResponse = responseConverter.convert(
                assetInstallTemplateDao.getById(queryCondition.getPrimaryKey()), AssetInstallTemplateResponse.class);
        return assetInstallTemplateResponse;
    }

    @Override
    public String deleteAssetInstallTemplateById(BatchQueryRequest request) throws Exception {
        ParamterExceptionUtils.isEmpty(request.getIds(), "主键Id不能为空");
        int count = assetInstallTemplateDao.batchDeleteTemplate(request.getIds(), System.currentTimeMillis(),
                LoginUserUtil.getLoginUser().getName());
        if (count < request.getIds().size()) {
            return "状态为拒绝的模板才能被删除";
        }
        return "删除成功，请刷新界面";
    }

    @Override
    public AssetTemplateRelationResponse queryTemplateByAssetId(QueryCondition queryCondition) throws Exception {
        AssetTemplateRelationResponse templateRelationResponse = assetInstallTemplateDao
                .queryTemplateById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey()));
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
    public PageResult<PatchInfoResponse> queryPatchPage(PrimaryKeyQuery query) {
        Integer count = assetInstallTemplateDao.queryPatchCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        List<PatchInfoResponse> patchInfoResponseList = BeanConvert
                .convert(assetInstallTemplateDao.queryPatchList(query), PatchInfoResponse.class);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), patchInfoResponseList);
    }

    @Override
    public int enableInstallTemplate(AssetInstallTemplateRequest request) throws Exception {
        AssetInstallTemplate assetInstallTemplate = this.getById(request.getStringId());
        if (Objects.equals(request.getEnable(), 1)) {
            BusinessExceptionUtils.isTrue(Objects.equals(assetInstallTemplate.getCurrentStatus(),
                    AssetInstallTemplateStatusEnum.FOBIDDEN.getCode()), "模板状态错误");
            assetInstallTemplate.setCurrentStatus(AssetInstallTemplateStatusEnum.ENABLE.getCode());
        }
        if (Objects.equals(request.getEnable(), 0)) {
            BusinessExceptionUtils.isTrue(Objects.equals(assetInstallTemplate.getCurrentStatus(),
                    AssetInstallTemplateStatusEnum.ENABLE.getCode()), "模板状态错误");
            assetInstallTemplate.setCurrentStatus(AssetInstallTemplateStatusEnum.FOBIDDEN.getCode());
        }
        assetInstallTemplate.setGmtModified(System.currentTimeMillis());
        assetInstallTemplate.setModifiedUser(LoginUserUtil.getLoginUser().getUsername());
        return assetInstallTemplateDao.update(assetInstallTemplate);
    }

    @Override
    public List<PatchInfoResponse> queryPatchs(PrimaryKeyQuery query) {
        Integer count = assetInstallTemplateDao.queryPatchCount(query);
        if (count <= 0) {
            return Lists.newArrayList();
        }
        return BeanConvert
                .convert(assetInstallTemplateDao.queryPatchList(query), PatchInfoResponse.class);
    }

    @Override
    @Transactional
    public String submitTemplateInfo(AssetInstallTemplateRequest request) throws Exception {
        if (request.getStringId() == null) {
            String unknowId = "0";
            request.setStringId(unknowId);
        }
        request.setCreateUser(LoginUserUtil.getLoginUser().getId().toString());
        request.setGmtCreate(System.currentTimeMillis());
        AssetInstallTemplate template = requestConverter.convert(request, AssetInstallTemplate.class);
        template.setCategoryModel(1);
        template.setCurrentStatus(AssetInstallTemplateStatusEnum.NOTAUDIT.getCode());
        assetInstallTemplateDao.insert(template);
        request.setStringId(template.getStringId());
        assetInstallTemplateDao.insertBatchPatch(request);
        assetInstallTemplateDao.insertBatchSoft(request);
        assetInstallTemplateDao.insertBatchUser(request);
        return "提交成功";
    }

    @Override
    @Transactional
    public String checkTemplate(AssetInstallTemplateCheckRequest request) throws Exception {
        request.setGmtModified(System.currentTimeMillis());
        Integer loginUserId = LoginUserUtil.getLoginUser().getId();
        if (!assetInstallTemplateDao.queryCheckTemplateUserId(request.getInstallTemplateId()).contains(loginUserId)) {
            throw new BusinessException("非法操作");
        }
        Integer currentStatus = request.getResult() == 0 ? AssetInstallTemplateStatusEnum.REJECT.getCode() : AssetInstallTemplateStatusEnum.ENABLE.getCode();
        request.setModifiedUser(loginUserId.toString());
        //更新模板检查表
        request.setResult(currentStatus);
        assetInstallTemplateDao.checkTemplate(request);
        //更新模板表状态
        AssetInstallTemplate template = new AssetInstallTemplate();
        template.setId(request.getInstallTemplateId());
        template.setCurrentStatus(currentStatus);
        template.setModifiedUser(loginUserId.toString());
        template.setGmtModified(System.currentTimeMillis());
        assetInstallTemplateDao.update(template);
        return "审核结果提交成功";
    }

}
