package com.antony.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
@Data
public class SmsProperties {

    private SmsMessage aliyun = new SmsMessage();

    private SmsMessage tencent = new SmsMessage();


}