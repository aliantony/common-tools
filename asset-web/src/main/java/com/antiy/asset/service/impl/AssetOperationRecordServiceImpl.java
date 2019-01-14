package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.entity.AssetOperationRecordMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.request.AssetOperationRecordRequest;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;

/**
 * <p> 资产操作记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
@Service
public class AssetOperationRecordServiceImpl extends BaseServiceImpl<AssetOperationRecord>
                                             implements IAssetOperationRecordService {

    private static final Logger                                               logger = LogUtils.get();

    @Resource
    private AssetOperationRecordDao                                           assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetOperationRecordMapper, AssetOperationRecordResponse> responseConverter;

    @Override
    public List<AssetOperationRecordResponse> findAssetOperationRecordByAssetId(Integer assetId) {
        List<AssetOperationRecordMapper> assetOperationRecordList = assetOperationRecordDao.findAssetOperationRecordByAssetId(assetId);
        List<AssetOperationRecordResponse> assetOperationRecordResponse = responseConverter
                .convert(assetOperationRecordList, AssetOperationRecordResponse.class);
        return assetOperationRecordResponse;
    }
}
