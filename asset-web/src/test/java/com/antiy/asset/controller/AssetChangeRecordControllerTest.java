package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.common.base.ActionResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssetChangeRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    public IAssetChangeRecordService iAssetChangeRecordService;

    @InjectMocks
    private AssetChangeRecordController assetChangeRecordController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetChangeRecordController).build();
    }

    @Test
    public void saveSingle() throws Exception {
        when(iAssetChangeRecordService.saveAssetChangeRecord(any())).thenReturn(ActionResponse.success());
        AssetChangeRecordRequest request = new AssetChangeRecordRequest();
        this.mockMvc
            .perform(post("/api/v1/asset/changerecord/save/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void updateSingle() throws Exception {
        AssetChangeRecordRequest request = new AssetChangeRecordRequest();
        this.mockMvc
            .perform(post("/api/v1/asset/changerecord/update/single"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryList() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/changerecord/query/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void deleteById() throws Exception {
        this.mockMvc
            .perform(post("/api/v1/asset/changerecord/delete/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(0))));
    }

    @Test
    public void queryUniformChangeInfo() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/changerecord/queryUniformChangeInfo")
                .param("categoryModelId", "1")
                .param("businessId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }
}