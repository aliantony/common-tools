package com.antiy.asset.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetMonitor;
import com.antiy.common.utils.LogUtils;

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
    private KafkaTemplate kafkaTemplate;
    private String        topic = "queue.csos.alarm.asset.monitor";

    /**
     * 每天0点扫描到期提醒的资产
     */
    //@Scheduled(cron = "0 0 0 */1 * ?")
    @Scheduled(cron = "*/20 * * * * ?")
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
