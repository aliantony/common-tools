package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetOperationRecordMapper;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;

/**
 * <p> 资产操作记录表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
@Service
public class AssetOperationRecordServiceImpl extends BaseServiceImpl<AssetOperationRecord>
                                             implements IAssetOperationRecordService {

    @Resource
    private AssetOperationRecordDao              assetOperationRecordDao;
    @Resource
    private AssetOperationRecordMapperToResponse responseConverter;

    @Override
    public List<AssetOperationRecordResponse> findAssetOperationRecordByAssetId(AssetOperationRecordQuery assetOperationRecordQuery) {
        List<AssetOperationRecordMapper> assetOperationRecordList = assetOperationRecordDao
            .findAssetOperationRecordByAssetId(assetOperationRecordQuery);
        if (assetOperationRecordList == null || assetOperationRecordList.isEmpty()) {
            return Lists.newArrayList();
        }
        return responseConverter.convert(assetOperationRecordList, AssetOperationRecordResponse.class);
    }
}

@Component
class AssetOperationRecordMapperToResponse extends
                                           BaseConverter<AssetOperationRecordMapper, AssetOperationRecordResponse> {
    @Override
    protected void convert(AssetOperationRecordMapper assetOperationRecordMapper,
                           AssetOperationRecordResponse assetOperationRecordResponse) {
        super.convert(assetOperationRecordMapper, assetOperationRecordResponse);
        // TODO 需要加上用户信息
    }
}
