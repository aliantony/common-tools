package com.antiy.asset.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyajun
 * @create 2020-03-12 13:07
 **/
@Configuration
public class BaseBeanConfig {

    /**
     * @Description 防止centos下，tmp文件夹长时间未操作被自动清理
     * @Date 14:21 2020/2/25
     * @param
     * @return javax.servlet.MultipartConfigElement
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp/";
        File file = new File(location);
        if (!file.exists()) {
            file.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
