package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.asset.util.DataTypeUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.SchemaTypeEnum;
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
        // TODO 添加创建人信息
        scheme.setGmtCreate(System.currentTimeMillis());
        schemeDao.insert(scheme);
        // 修改资产状态
        Map<String, Integer[]> assetIdMap = new HashMap();
        assetIdMap.put("ids", new Integer[] { DataTypeUtils.stringToInteger(request.getAssetId()) });
        assetIdMap.put("assetStatus", new Integer[] { request.getTargetStatus() });
        assetDao.changeStatus(assetIdMap);

        // TODO 调用工作流

        // 记录操作历史
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        // 判断请求的方案类型
        SchemaTypeEnum codeEnum = EnumUtil.getByCode(SchemaTypeEnum.class, scheme.getType());
        assetOperationRecord.setTargetObjectId(DataTypeUtils.stringToInteger(request.getAssetId()));
        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        assetOperationRecord.setTargetStatus(request.getTargetStatus());
        assetOperationRecord.setSchemaId(scheme.getId());
        assetOperationRecord.setContent(codeEnum != null ? codeEnum.getMsg() : null);
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        // TODO 操作者
        assetOperationRecord.setOperateUserId(1);
        assetOperationRecord.setOperateUserName("张三");

        operationRecordDao.insert(assetOperationRecord);

        return scheme.getId();
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        scheme.setGmtModified(System.currentTimeMillis());
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
