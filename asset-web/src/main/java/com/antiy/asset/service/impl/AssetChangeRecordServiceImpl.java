package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.entity.AssetChangeRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
        private ActivityClient  activityClient;
        @Resource
        private AssetChangeRecordDao assetChangeRecordDao;
        @Resource
        private BaseConverter<AssetChangeRecordRequest, AssetChangeRecord>  requestConverter;
        @Resource
        private BaseConverter<AssetChangeRecord, AssetChangeRecordResponse> responseConverter;

        @Override
        public Integer saveAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
            AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
            AssetOuterRequest assetOuterRequest = request.getAssetOuterRequest ();
            assetChangeRecord.setChangeVal (JsonUtil.object2Json (assetOuterRequest));
            assetChangeRecordDao.insert(assetChangeRecord);
            ManualStartActivityRequest manualStartActivityRequest = request.getActivityRequest ();
            if (Objects.isNull(manualStartActivityRequest)) {
                manualStartActivityRequest = new ManualStartActivityRequest();
            }
            //其他设备
            if (assetOuterRequest.getAssetOthersRequest ()!=null){

                manualStartActivityRequest.setBusinessId(assetOuterRequest.getAssetOthersRequest ().getId ());
            }else {
                manualStartActivityRequest.setBusinessId(assetOuterRequest.getAsset ().getId ());
            }
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_CHANGE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);
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
