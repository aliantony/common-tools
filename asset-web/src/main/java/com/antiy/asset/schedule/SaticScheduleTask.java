package com.antiy.asset.schedule;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.service.impl.AssetStatusJumpServiceImpl;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetCorrectRequest;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/*@Configuration
@EnableScheduling*/
public class SaticScheduleTask {
    @Resource
    public IAssetService iAssetService;
    private static final Logger logger = LogUtils.get(AssetStatusJumpServiceImpl.class);
    @Resource
    IAssetStatusJumpService iAssetStatusJumpService;

    //@Scheduled(cron="0/5 * * * * ?")
    public void assetContinueNet(){
        try {
            List<Asset> assetList = iAssetService.getAll();
            List<String> assetIds = assetList.stream().filter(v -> AssetStatusEnum.CORRECTING.getCode().equals(v.getAssetStatus())).map(t -> t.getStringId()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(assetIds)){
                for(String  assetId :assetIds){
                    AssetCorrectRequest assetCorrectRequest=new AssetCorrectRequest();
                    ActivityHandleRequest activityHandleRequest=new ActivityHandleRequest();
                    activityHandleRequest.setStringId(assetId);
                    iAssetStatusJumpService.assetCorrectingInfo(assetCorrectRequest);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
