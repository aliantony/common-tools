package com.antiy.asset.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

import com.antiy.biz.entity.SysMessageRequest;

/**
 * 描述: 配置消费者相关参数
 *
 * @author wangqian
 * @create 2019-01-04 13:38
 */
@Configuration
@EnableKafka
public class MessageKafkaConfig {
  /** kafka服务器地址 */
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  /** 等待响应的超时时长,如果超出阈值,则会导致请求被重试,取决"retries"参数. 此参数值必须大于sessionTimeoutMS 范围[0,500000] */
  private int requestTimeoutMS = 300000;

  /** //单次最多允许poll的消息条数. //此值不建议过大,应该考虑你的业务处理效率. */
  private int maxPollRecords = 1000;

  /**
   * 两次poll之间的时间隔间最大值,如果超过此值将会被认为此consumer失效,触发consumer重新平衡. 此值必须大于,一个batch所有消息处理时间总和. 最大于500000 单位
   * 秒
   */
  private int maxPollIntervalMS = 120000;

  /**
   * 会话过期时长,consumer通过ConsumerCoordinator间歇性发送心跳 超期后,会被认为consumer失效,服务迁移到其他consumer节点.(group)
   * 需要注意,Coordinator与kafkaConsumer共享底层通道,也是基于poll获取协调事件,但是会在单独的线程中
   */
  private int sessionTimeoutMS = 60000;

  /** //我们建议同一个project中,使用同一个broker集群的消费者,使用相同的groupId */
  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, SysMessageRequest>
      sampleListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, SysMessageRequest> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(sampleConsumerFactory());
    factory.setConcurrency(5);
    // 开启批量消费
    // factory.setBatchListener(true);
    factory.getContainerProperties().setPollTimeout(3000);
    // 开启手动ACK.
    factory
        .getContainerProperties()
        .setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
    return factory;
  }

  public ConsumerFactory<String, SysMessageRequest> sampleConsumerFactory() {
    return new DefaultKafkaConsumerFactory(
        sampleConsumerConfiguration(), new StringDeserializer(), new StringDeserializer());
  }

  public Map<String, Object> sampleConsumerConfiguration() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
    // 强烈建议关闭自动确认,我们使用手动ACK模式,Spring Kafka基于JMS语义为我们设计好了兼容实现.
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, this.maxPollIntervalMS);
    // 单次poll允许获取的最多条数.
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, this.maxPollRecords);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, this.sessionTimeoutMS);
    props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, this.requestTimeoutMS);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
    return props;
  }
}
