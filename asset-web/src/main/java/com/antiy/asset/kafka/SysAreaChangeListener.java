package com.antiy.asset.kafka;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.common.utils.LogUtils;

/**
 * @Author: lvliang
 * @Date: 2020/5/6 16:18
 */
@Component
public class SysAreaChangeListener {
    @Resource
    private AssetDao assetDao;

    private Logger   log = LogUtils.get(SysAreaChangeListener.class);

    //@KafkaListener(topics = "${kafka.sysarea.change.topic}")
    public void sysAreaChange(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              Consumer consumer, Acknowledgment ack) {
        Map<String, Integer> area = JSONObject.parseObject((String) record.value(), Map.class);
        if (!Objects.isNull(area.get("childId")) && !Objects.isNull(area.get("parentId"))) {
            log.info("更新资产区域");
            assetDao.updateAreaIdOfAsset(area);
        }
        ack.acknowledge();
    }
}
