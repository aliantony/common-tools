package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetAssemblyDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.query.AssetAssemblyScrapRequest;
import com.antiy.asset.vo.query.AssetSchemeQuery;
import com.antiy.asset.vo.request.AssetAssemblyRequest;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.*;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p> 资产组件关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetAssemblyServiceImpl extends BaseServiceImpl<AssetAssembly> implements IAssetAssemblyService {

    private Logger                                              logger = LogUtils.get(this.getClass());

    @Resource
    private AssetAssemblyDao                                    assetAssemblyDao;

    @Resource
    private AssetOperationRecordDao  assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetAssemblyRequest, AssetAssembly>  requestConverter;
    @Resource
    private BaseConverter<AssetAssembly, AssetAssemblyResponse> responseConverter;

    @Override
    public String saveAssetAssembly(AssetAssemblyRequest request) throws Exception {
        AssetAssembly assetAssembly = requestConverter.convert(request, AssetAssembly.class);
        assetAssemblyDao.insert(assetAssembly);
        return assetAssembly.getStringId();
    }

    @Override
    public String updateAssetAssembly(AssetAssemblyRequest request) throws Exception {
        AssetAssembly assetAssembly = requestConverter.convert(request, AssetAssembly.class);
        Integer update = assetAssemblyDao.update(assetAssembly);
        return update.toString();
    }

    @Override
    public List<AssetAssemblyResponse> queryListAssetAssembly(AssetAssemblyQuery query) throws Exception {
        List<AssetAssembly> assetAssemblyList = assetAssemblyDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetAssemblyList, AssetAssemblyResponse.class);
    }

    @Override
    public PageResult<AssetAssemblyResponse> queryPageAssetAssembly(AssetAssemblyQuery query) throws Exception {
        return new PageResult<AssetAssemblyResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
                this.queryListAssetAssembly(query));
    }

    @Override
    public AssetAssemblyResponse queryAssetAssemblyById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetAssemblyResponse assetAssemblyResponse = responseConverter
                .convert(assetAssemblyDao.getById(queryCondition.getPrimaryKey()), AssetAssemblyResponse.class);
        return assetAssemblyResponse;
    }

    @Override
    public String deleteAssetAssemblyById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetAssemblyDao.deleteById(baseRequest.getStringId()).toString();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer scrapUpdate(AssetAssemblyScrapRequest assetAssemblyScrapRequest) throws Exception {
        if(StringUtils.isNotBlank(assetAssemblyScrapRequest.getTemporaryInfo())){
            if(assetAssemblyScrapRequest.getTemporaryInfo().length()>300){
                 throw new BusinessException("备注不能超过300字符");
            }
        }
        //把备注信息写入到该条资产报废申请的 temporary_info 列
        AssetSchemeQuery assetSchemeQuery=new AssetSchemeQuery();
        assetSchemeQuery.setAssetIds(Arrays.asList(assetAssemblyScrapRequest.getAssetId()));
        assetSchemeQuery.setTargetStatus(AssetStatusEnum.WAIT_SCRAP.getCode());
        assetSchemeQuery.setOrginStatusOne(AssetStatusEnum.RETIRE.getCode());
        assetSchemeQuery.setOrginStatusTwo(AssetStatusEnum.RETIRE.getCode());
        List<AssetResponse> assetResponseList = assetOperationRecordDao.queryAssetSchemListByAssetIds(assetSchemeQuery);
        if(CollectionUtils.isNotEmpty(assetResponseList) && StringUtils.isNotBlank(assetAssemblyScrapRequest.getTemporaryInfo())){
            AssetOperationRecord assetOperationRecord=new AssetOperationRecord();
            assetOperationRecord.setId(DataTypeUtils.stringToInteger(assetResponseList.get(0).getAssetOperationRecordId()));
            assetOperationRecord.setTemporaryInfo(assetAssemblyScrapRequest.getTemporaryInfo());
             assetOperationRecordDao.update(assetOperationRecord);
        }
        //组件报废信息
       return assetAssemblyDao.updateByUniqueId(assetAssemblyScrapRequest.getAssetAssemblyRequestList());
    }
}
