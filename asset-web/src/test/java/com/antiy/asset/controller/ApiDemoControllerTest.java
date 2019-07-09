package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.controller.swagger.ApiDemo;
import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.service.IAssetService;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.RespBasicCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDemoControllerTest {
    private MockMvc             mockMvc;
    @Autowired
    private CommonManager       commonManager;
    @InjectMocks
    private ApiDemo             apiDemo;
    private final static String URL_PREFIX = "/api/v1/asset/api";
    private final static String TIP        = "api demo单元测试失败";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiDemo).build();

    }

    @Test
    public void api() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX);
        String result = commonManager.getResult(mockMvc, requestBuilder);
    }
}
