package com.antiy.asset.util;

import javax.annotation.Resource;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 17:54
 * @description: 远程调用的client
 */
@Component
public class BaseClient<T> {

    @Resource
    RestTemplate restTemplate;

    /**
     * get 请求
     * @param params
     * @param parameterizedTypeReference
     * @param url
     * @return
     */
    public T get(Object params, ParameterizedTypeReference<T> parameterizedTypeReference, String url) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, parameterizedTypeReference,
            params);
        return responseEntity.getBody();
    }

    /**
     * post 请求
     * @param params
     * @param parameterizedTypeReference
     * @param url
     * @return
     */
    public T post(Object params, ParameterizedTypeReference<T> parameterizedTypeReference, String url) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null), parameterizedTypeReference,
            params);
        return responseEntity.getBody();
    }
}
