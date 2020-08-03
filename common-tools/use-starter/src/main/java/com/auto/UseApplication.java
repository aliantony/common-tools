package com.auto;

import com.antony.config.AliyunSmsSenderImpl;
import com.antony.config.TencentSmsSenderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-08-03
 * @version  1.0.0
 */
@SpringBootApplication
public class UseApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UseApplication.class, args);
        AliyunSmsSenderImpl aliyunSmsSender = applicationContext.getBean(AliyunSmsSenderImpl.class);
        aliyunSmsSender.send("用阿里云发送短信");
        TencentSmsSenderImpl tencentSmsSender = applicationContext.getBean(TencentSmsSenderImpl.class);
        tencentSmsSender.send("用腾讯云发送短信");
    }
}
