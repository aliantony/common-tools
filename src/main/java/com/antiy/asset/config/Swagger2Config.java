package com.antiy.asset.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Timestamp;
import java.util.Date;


/**
 * Swagger2Config class
 *
 * @author liuyu
 * @date 2018/11/17
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String BASE_PACKAGE = "com.antiy.asset" ;

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .directModelSubstitute(Timestamp.class, Long.class)
                .directModelSubstitute(Date.class, Long.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger RESTful APIs")
                .description("Swagger API 服务")
                .termsOfServiceUrl("http://swagger.io/")
                .contact(new Contact("Swagger" , "127.0.0.1" , "liuyu@antiy.com"))
                .version("1.0.0")
                .build();
    }

}
