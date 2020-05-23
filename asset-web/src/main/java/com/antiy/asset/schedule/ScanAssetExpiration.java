package com.antiy.asset.schedule;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetMonitorRuleDao;
import com.antiy.asset.dao.AssetStatusMonitorDao;
import com.antiy.asset.dto.AssetMonitorDTO;
import com.antiy.asset.dto.AssetRunExpMonitorDTO;
import com.antiy.asset.entity.AssetMonitor;
import com.antiy.asset.vo.enums.TimeEnum;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvliang
 * @Date: 2020/5/19 14:33
 */
@Component
@EnableScheduling
public class ScanAssetExpiration {
    private Logger        log   = LogUtils.get(ScanAssetExpiration.class);
    @Resource
    private AssetDao      assetDao;

    @Resource
    private AssetStatusMonitorDao assetStatusMonitorDao;
    @Resource
    private KafkaTemplate kafkaTemplate;

    @Resource
    private AssetMonitorRuleDao assetMonitorRuleDao;
    private String        topic = "queue.csos.alarm.asset.monitor";





    /**
     * 每天0点扫描到期提醒、过期的资产
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    //@Scheduled(cron = "*/20 * * * * ?")
    public void scan() {
        Long currentTime = System.currentTimeMillis();
        List<AssetMonitor> expirationAsset = assetDao.getExpirationAsset(currentTime);
        List<AssetMonitor> serviceLifeAsset = assetDao.getServiceLifeAsset(currentTime);

        if (CollectionUtils.isNotEmpty(expirationAsset)) {
            expirationAsset.stream().forEach(t -> {
                t.setCurrent(String.valueOf(currentTime));
                t.setTime(currentTime);
                t.setAlarmCode("1050005");
                sendMsg(topic, JSONObject.toJSONString(t));
            });
        }
        if (CollectionUtils.isNotEmpty(serviceLifeAsset)) {
            serviceLifeAsset.stream().forEach(t -> {
                t.setCurrent(String.valueOf(currentTime));
                t.setTime(currentTime);
                t.setAlarmCode("1050006");
                sendMsg(topic, JSONObject.toJSONString(t));
            });
        }
    }

    /**
     * 每天0点扫描资产离线 ，并异常告警
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void scanAsset(){
        List<AssetRunExpMonitorDTO>  assetRunExpMonitorDTOs=assetMonitorRuleDao.getRunExpMonitorInfo();
        for(AssetRunExpMonitorDTO assetRunExpMonitorDTO:assetRunExpMonitorDTOs){
            String runtimeExceptionThreshold = assetRunExpMonitorDTO.getRuntimeExceptionThreshold();
            AssetRunExpMonitorDTO.AssetRunExpTime assetRunExpTime = JSONObject.parseObject(runtimeExceptionThreshold, AssetRunExpMonitorDTO.AssetRunExpTime.class);
            if(assetRunExpTime.getRuntimeExceptionThreshold()==null||assetRunExpTime.getUnit()==null){
                continue;
            }
            for(AssetMonitorDTO assetMonitorDTO:assetRunExpMonitorDTO.getAssetDTOs()){
                AssetStatusMonitorResponse assetStatusMonitorResponse= assetStatusMonitorDao.queryBasePerformance(assetMonitorDTO.getAssetId());
                if(assetStatusMonitorResponse==null){
                   //报异常
                    assetMonitorDTO.setTime(System.currentTimeMillis());
                    assetMonitorDTO.setCurrent(assetRunExpTime.getRuntimeExceptionThreshold()+5+"");
                    assetMonitorDTO.setThreshole(assetRunExpTime.getRuntimeExceptionThreshold().toString());
                    assetMonitorDTO.setAlarmCode("1050004");
                    sendMsg(topic,JSONObject.toJSONString(assetMonitorDTO));
                }

                Long reportTime = assetStatusMonitorResponse.getGmtModified();
                Long currentTime=System.currentTimeMillis();
                Long intervalTime=currentTime-reportTime;
                boolean alarm=false;
                if(TimeEnum.HOUR.getName().equals(assetRunExpTime.getUnit())){
                    intervalTime/=(1000*3600);
                    if(intervalTime>assetRunExpTime.getRuntimeExceptionThreshold()){
                        alarm=true;
                    }
                }
                if(TimeEnum.DAY.getName().equals(assetRunExpTime.getUnit())){
                    intervalTime/=(1000*3600*24);
                    if(intervalTime>assetRunExpTime.getRuntimeExceptionThreshold()){
                        alarm=true;
                    }
                }
                if(alarm){
                    //产生告警
                    assetMonitorDTO.setTime(System.currentTimeMillis());
                    assetMonitorDTO.setCurrent(intervalTime.toString());
                    assetMonitorDTO.setThreshole(assetRunExpTime.getRuntimeExceptionThreshold().toString());
                    assetMonitorDTO.setAlarmCode("1050004");
                    sendMsg(topic,JSONObject.toJSONString(assetMonitorDTO));
                    LogUtils.info(log,"产生告警{} ",assetMonitorDTO.getNumber());
                }
            }

        }
    }
    private void sendMsg(String topic, String msg) {
        try {
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("send msg to kafka failed");
                    // KafkaOperationTask.localQueenAdd(message);
                }

                @Override
                public void onSuccess(SendResult<String, String> OperLogSendResult) {
                    log.info("send msg to kafka success");
                }
            });
        } catch (Exception e) {
            log.error("send msg to kafka failed");
        }
    }
}
