package com.antiy.asset;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Demo class
 *
 * @author liuyu
 * @date 2018/11/01
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.antiy.asset.dao")
public class AssetApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }
}
