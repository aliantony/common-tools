package com.antiy.asset.schedule;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
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
import com.antiy.asset.dao.AssetMonitorRuleDao;
import com.antiy.asset.dao.AssetStatusMonitorDao;
import com.antiy.asset.dto.AssetMonitorDTO;
import com.antiy.asset.dto.AssetRunExpMonitorDTO;
import com.antiy.asset.entity.AssetArea;
import com.antiy.asset.entity.AssetMonitor;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.vo.enums.TimeEnum;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.biz.entity.SysMessageRequest;
import com.antiy.biz.message.SysMessageSender;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;

/**
 * @Author: lvliang
 * @Date: 2020/5/19 14:33
 */
@Component
@EnableScheduling
public class ScanAssetExpiration {
    private Logger                log   = LogUtils.get(ScanAssetExpiration.class);
    @Resource
    private AssetDao              assetDao;

    @Resource
    private AssetStatusMonitorDao assetStatusMonitorDao;
    @Resource
    private KafkaTemplate         kafkaTemplate;

    @Resource
    private AssetMonitorRuleDao   assetMonitorRuleDao;
    private String                topic = "queue.csos.alarm.asset.monitor";
    @Resource
    private SysMessageSender      sysMessageSender;

    @Resource
    private BaseLineClient        baseLineClient;

    /**
     * 每天0点扫描到期提醒、过期的资产
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    // @Scheduled(cron = "*/5 * * * * ?")
    public void scan() {
        Long currentTime = System.currentTimeMillis();
        // 到期提醒资产发消息
        List<AssetArea> expirationAsset = assetDao.getExpirationAsset(currentTime);
        // 到期资产发告警
        List<AssetMonitor> serviceLifeAsset = assetDao.getServiceLifeAsset(currentTime);
        if (CollectionUtils.isNotEmpty(expirationAsset)) {
            List<SysMessageRequest> msgs = Lists.newArrayList();
            expirationAsset.stream().forEach(asset -> {
                Map map = new HashMap();
                map.put("areaIds", dealArea(asset.getArea()));
                // 获取区域下的用户id
                ActionResponse users = baseLineClient.getUserIdsByAreaIds(map);
                List<String> userIds = (List<String>) ((LinkedHashMap) users.getBody()).get("userIds");
                if (CollectionUtils.isNotEmpty(userIds)) {
                    userIds.stream().forEach(userId -> {
                        SysMessageRequest sysMessageRequest = new SysMessageRequest();
                        sysMessageRequest.setContent("您有一条由系统提交的[资产到期提醒]任务,请尽快处理");
                        sysMessageRequest.setTopic("待办任务");
                        sysMessageRequest.setReceiveUserId(DataTypeUtils.stringToInteger(userId));
                        sysMessageRequest.setOther(JSONObject.toJSONString(new HashMap() {
                            {
                                put("id", asset.getId());
                            }
                        }));
                        sysMessageRequest.setOrigin(1);
                        msgs.add(sysMessageRequest);
                    });
                }
            });
            // 发送消息
            sysMessageSender.batchSendMessage(msgs);
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
     * 获取指定区域的所有上级区域
     * @param childNode
     * @return
     */
    private List<String> dealArea(String childNode) {
        List<String> areas = Lists.newArrayList();
        do {
            areas.add(childNode);
            childNode = childNode.substring(0, childNode.length() - 3);
        } while (childNode.length() >= 3);
        return areas;
    }

    /**
     * 每天0点扫描资产离线 ，并异常告警
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void scanAsset() {
        List<AssetRunExpMonitorDTO> assetRunExpMonitorDTOs = assetMonitorRuleDao.getRunExpMonitorInfo();
        for (AssetRunExpMonitorDTO assetRunExpMonitorDTO : assetRunExpMonitorDTOs) {
            String runtimeExceptionThreshold = assetRunExpMonitorDTO.getRuntimeExceptionThreshold();
            AssetRunExpMonitorDTO.AssetRunExpTime assetRunExpTime = JSONObject.parseObject(runtimeExceptionThreshold,
                AssetRunExpMonitorDTO.AssetRunExpTime.class);
            if (assetRunExpTime.getRuntimeExceptionThreshold() == null || assetRunExpTime.getUnit() == null) {
                continue;
            }
            for (AssetMonitorDTO assetMonitorDTO : assetRunExpMonitorDTO.getAssetDTOs()) {
                AssetStatusMonitorResponse assetStatusMonitorResponse = assetStatusMonitorDao
                    .queryBasePerformance(assetMonitorDTO.getAssetId());
                if (assetStatusMonitorResponse == null) {
                    // 报异常
                    assetMonitorDTO.setTime(System.currentTimeMillis());
                    assetMonitorDTO.setCurrent(assetRunExpTime.getRuntimeExceptionThreshold() + 5 + "");
                    assetMonitorDTO.setThreshole(assetRunExpTime.getRuntimeExceptionThreshold().toString());
                    assetMonitorDTO.setAlarmCode("1050004");
                    sendMsg(topic, JSONObject.toJSONString(assetMonitorDTO));
                }

                Long reportTime = assetStatusMonitorResponse.getGmtModified();
                Long currentTime = System.currentTimeMillis();
                Long intervalTime = currentTime - reportTime;
                boolean alarm = false;
                if (TimeEnum.HOUR.getName().equals(assetRunExpTime.getUnit())) {

                    assetMonitorDTO.setCurrent(String.format("%.1f", intervalTime / (1000 * 36000 * 24.0)));
                    assetMonitorDTO
                        .setThreshole(String.format("%.1f", assetRunExpTime.getRuntimeExceptionThreshold() / 24.0));
                    intervalTime /= (1000 * 3600);
                    if (intervalTime > assetRunExpTime.getRuntimeExceptionThreshold()) {
                        alarm = true;
                    }
                }
                if (TimeEnum.DAY.getName().equals(assetRunExpTime.getUnit())) {
                    assetMonitorDTO.setCurrent(String.format("%.1f", intervalTime / (1000 * 36000 * 24.0)));
                    assetMonitorDTO.setThreshole(assetRunExpTime.getRuntimeExceptionThreshold().toString());
                    intervalTime /= (1000 * 3600 * 24);
                    if (intervalTime > assetRunExpTime.getRuntimeExceptionThreshold()) {
                        alarm = true;
                    }
                }
                if (alarm) {
                    // 产生告警
                    assetMonitorDTO.setTime(System.currentTimeMillis());
                    assetMonitorDTO.setAlarmCode("1050004");
                    sendMsg(topic, JSONObject.toJSONString(assetMonitorDTO));
                    LogUtils.info(log, "产生告警{} ", assetMonitorDTO.getNumber());
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
