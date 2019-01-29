package com.antiy.asset.util;

import javax.annotation.Resource;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/17 17:54
 * @description: 远程调用的client
 */
@Component
public class BaseClient<T> {

    @Resource
    RestTemplate                restTemplate;
    private static final String TOKEN_KEY = "Authorization";

    /**
     * get 请求
     * @param params
     * @param parameterizedTypeReference
     * @param url
     * @return
     */
    public T get(Object params, ParameterizedTypeReference<T> parameterizedTypeReference, String url) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        headers.set(TOKEN_KEY, LoginUserUtil.getCommonInfo().getToken());
        HttpEntity<String> entity = new HttpEntity(JSONObject.toJSONString(params), headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
            parameterizedTypeReference);
        /* ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
         * parameterizedTypeReference, params); */
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
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        headers.set(TOKEN_KEY, LoginUserUtil.getCommonInfo().getToken());
        HttpEntity<String> entity = new HttpEntity(JSONObject.toJSONString(params), headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
            parameterizedTypeReference);
        /* ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers),
         * parameterizedTypeReference, JSONObject.toJSONString(params)); */
        return responseEntity.getBody();
    }
}
