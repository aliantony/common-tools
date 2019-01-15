package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.biz.util.LoginUserUtil;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.SchemeTypeEnum;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

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
    private AssetDao                              assetDao;
    @Resource
    private AssetOperationRecordDao               operationRecordDao;
    @Resource
    private BaseConverter<SchemeRequest, Scheme>  requestConverter;
    @Resource
    private BaseConverter<Scheme, SchemeResponse> responseConverter;

    @Override
    public Integer saveScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        scheme.setCreateUser(LoginUserUtil.getLoginUser().getId());
        scheme.setGmtCreate(System.currentTimeMillis());
        schemeDao.insert(scheme);
        // 修改资产状态
        Map<String, Object> assetIdMap = new HashMap<String, Object>(2);
        assetIdMap.put("ids", request.getAssetId());
        assetIdMap.put("assetStatus", request.getTargetStatus().toString());
        assetDao.changeStatus(assetIdMap);

        // TODO 调用工作流
        Integer type = scheme.getType();
        if (EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType()) != null){
            switch (type){
                case 1: ;
                break;
                case 2:
                    ;
                case 3://制定待退役方案-由入网变为“待退役”
                    assetDao.changeStatusById(request.getAssetId(),request.getTargetStatus(), AssetStatusEnum.NET_IN.getCode()) ;
                    break;
                case 4:;
                case 5:;
                default:break;
            }
        }else {
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        }


        // 记录操作历史
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        // 判断请求的方案类型
        SchemeTypeEnum codeEnum = EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType());
        assetOperationRecord.setTargetObjectId(DataTypeUtils.stringToInteger(request.getAssetId()));
        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        assetOperationRecord.setTargetStatus(request.getTargetStatus());
        assetOperationRecord.setSchemaId(scheme.getId());
        assetOperationRecord.setContent(codeEnum != null ? codeEnum.getMsg() : null);
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setOperateUserId(request.getPutintoUserId());
        assetOperationRecord.setOperateUserName(request.getPutintoUser());

        operationRecordDao.insert(assetOperationRecord);

        return scheme.getId();
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        // TODO 添加修改人信息
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
    public SchemeResponse findSchemeById(Integer id) throws Exception {
        return responseConverter.convert(super.getById(id), SchemeResponse.class);
    }

    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListScheme(query));
    }
}
