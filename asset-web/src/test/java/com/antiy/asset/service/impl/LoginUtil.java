package com.antiy.asset.service.impl;

import com.antiy.common.base.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoginUtil {

    /**
     * 提供的当前用户信息mock
     *
     * @param loginUser 假用户信息
     */
    public static void mockLoginUser(LoginUser loginUser) {
        Map<String, String> map = new HashMap<>();
        map.put("username", loginUser.getUsername());
        map.put("id", String.valueOf(loginUser.getId()));
        map.put("name", loginUser.getName());
        map.put("password", loginUser.getPassword());
        Set<String> set = new HashSet<>(1);
        set.add("none");
        OAuth2Request oAuth2Request = new OAuth2Request(map, "1", null, true, set, null, "", null, null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(map, "2333");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("principal", map);
        authentication.setDetails(map1);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);

    }
    public static  void generateDefaultLoginUser(){
        LoginUser loginUser=new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("chen");
        loginUser.setPassword("1234");


        loginUser.setName("wangzai");

        mockLoginUser(loginUser);
    }
}
