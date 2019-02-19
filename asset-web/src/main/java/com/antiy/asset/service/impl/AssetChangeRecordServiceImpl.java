package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.entity.AssetChangeRecord;
import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 变更记录表
 服务实现类
 * </p>
 *
 * @author why
 * @since 2019-02-19
 */
@Service
public class AssetChangeRecordServiceImpl extends BaseServiceImpl<AssetChangeRecord> implements IAssetChangeRecordService{

        private static final Logger logger = LogUtils.get();

        @Resource
        private AssetChangeRecordDao assetChangeRecordDao;
        @Resource
        private BaseConverter<AssetChangeRecordRequest, AssetChangeRecord>  requestConverter;
        @Resource
        private BaseConverter<AssetChangeRecord, AssetChangeRecordResponse> responseConverter;

        @Override
        public Integer saveAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
            AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
            assetChangeRecordDao.insert(assetChangeRecord);
            return assetChangeRecord.getId();
        }

        @Override
        public Integer updateAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
            AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
            return assetChangeRecordDao.update(assetChangeRecord);
        }

        @Override
        public List<AssetChangeRecordResponse> findListAssetChangeRecord(AssetChangeRecordQuery query) throws Exception {
            List<AssetChangeRecord> assetChangeRecordList = assetChangeRecordDao.findQuery(query);
            //TODO
            List<AssetChangeRecordResponse> assetChangeRecordResponse = responseConverter.convert(assetChangeRecordList,AssetChangeRecordResponse.class );
            return assetChangeRecordResponse;
        }

        @Override
        public PageResult<AssetChangeRecordResponse> findPageAssetChangeRecord(AssetChangeRecordQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.findListAssetChangeRecord(query));
        }
}
