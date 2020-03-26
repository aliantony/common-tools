package com.antiy.asset.schedule;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.service.impl.AssetStatusJumpServiceImpl;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class SaticScheduleTask {
    @Resource
    public IAssetService iAssetService;

    @Resource
    AssetOperationRecordDao assetOperationRecordDao;
    private static final Logger logger = LogUtils.get(AssetStatusJumpServiceImpl.class);
    @Resource
    IAssetStatusJumpService iAssetStatusJumpService;
    //@Scheduled(cron="0/5 * * * * ?")
    public void assetContinueNet(){
        try {
            List<Asset> assetList = iAssetService.getAll();
            List<Integer> assetIdsOfCorrecting = assetList.stream().filter(v -> AssetStatusEnum.CORRECTING.getCode().equals(v.getAssetStatus())).map(t -> t.getId()).collect(Collectors.toList());
            List<AssetOperationRecord> assetOperationRecords = assetOperationRecordDao.listByAssetIds(assetIdsOfCorrecting);
            if(CollectionUtils.isNotEmpty(assetOperationRecords)){
                for(AssetOperationRecord assetOperationRecord :assetOperationRecords){
                    ActivityHandleRequest activityHandleRequest=new ActivityHandleRequest();
                    activityHandleRequest.setProcInstId(assetOperationRecord.getTaskId().toString());
                    activityHandleRequest.setStringId(assetOperationRecord.getTargetObjectId());
                  //  iAssetStatusJumpService.assetCorrectingInfo(activityHandleRequest);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
