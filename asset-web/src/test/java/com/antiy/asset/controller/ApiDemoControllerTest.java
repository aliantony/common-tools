package com.antiy.asset.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.antiy.asset.controller.swagger.ApiDemo;

@RunWith(PowerMockRunner.class)
public class ApiDemoControllerTest {

    @InjectMocks
    private ApiDemo apiDemo;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(apiDemo).build();
    }

    @Test
    public void apiTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/api")).andReturn();
        Assert.assertEquals(302, mvcResult.getResponse().getStatus());
    }

}
