package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AlarmAssetRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlarmAssetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAssetService assetService;

    @InjectMocks
    private AlarmAssetController alarmAssetController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(alarmAssetController).build();
    }

    @Test
    public void queryListTest() throws Exception{
        AlarmAssetRequest alarmAssetRequest = new AlarmAssetRequest();
        this.mockMvc
                .perform(post("/api/v1/asset/alarm/query/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(alarmAssetRequest)))
                .andExpect(status().isOk());
    }
}
