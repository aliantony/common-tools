package com.antiy.asset;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Demo class
 *
 * @author liuyu
 * @date 2018/11/01
 */

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({ "com.antiy.asset.dao" })
@ComponentScan(basePackages = { "com.antiy.biz.util","com.antiy.common.base","com.antiy.common.config", "com.antiy.asset", "com.antiy.common.exception",
                                "com.antiy.common.download", "com.antiy.common.utils" })// TODO 测试
public class AssetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }
}
