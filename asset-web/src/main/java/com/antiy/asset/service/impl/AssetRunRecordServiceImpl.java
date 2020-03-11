package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetRunRecordDao;
import com.antiy.asset.entity.AssetRunRecord;
import com.antiy.asset.service.IAssetRunRecordService;
import com.antiy.asset.vo.query.AssetRunRecordQuery;
import com.antiy.asset.vo.request.AssetRunRecordRequest;
import com.antiy.asset.vo.response.AssetRunRecordResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 系统运行记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-10
 */
@Service
public class AssetRunRecordServiceImpl extends BaseServiceImpl<AssetRunRecord> implements IAssetRunRecordService {

    private Logger                                                logger = LogUtils.get(this.getClass());

    @Resource
    private AssetRunRecordDao                                     assetRunRecordDao;
    @Resource
    private BaseConverter<AssetRunRecordRequest, AssetRunRecord>  requestConverter;
    @Resource
    private BaseConverter<AssetRunRecord, AssetRunRecordResponse> responseConverter;

    @Override
    public String saveAssetRunRecord(AssetRunRecordRequest request) throws Exception {
        AssetRunRecord assetRunRecord = requestConverter.convert(request, AssetRunRecord.class);
        assetRunRecordDao.insert(assetRunRecord);
        return assetRunRecord.getStringId();
    }

    @Override
    public String updateAssetRunRecord(AssetRunRecordRequest request) throws Exception {
        AssetRunRecord assetRunRecord = requestConverter.convert(request, AssetRunRecord.class);
        return assetRunRecordDao.update(assetRunRecord).toString();
    }

    @Override
    public List<AssetRunRecordResponse> queryListAssetRunRecord(AssetRunRecordQuery query) throws Exception {
        List<AssetRunRecord> assetRunRecordList = assetRunRecordDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetRunRecordList, AssetRunRecordResponse.class);
    }

    @Override
    public PageResult<AssetRunRecordResponse> queryPageAssetRunRecord(AssetRunRecordQuery query) throws Exception {
        return new PageResult<AssetRunRecordResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetRunRecord(query));
    }

    @Override
    public AssetRunRecordResponse queryAssetRunRecordById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetRunRecordResponse assetRunRecordResponse = responseConverter
            .convert(assetRunRecordDao.getById(queryCondition.getPrimaryKey()), AssetRunRecordResponse.class);
        return assetRunRecordResponse;
    }

    @Override
    public String deleteAssetRunRecordById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetRunRecordDao.deleteById(baseRequest.getStringId()).toString();
    }
}
