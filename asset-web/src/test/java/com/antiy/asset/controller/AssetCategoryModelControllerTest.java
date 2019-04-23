package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
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

public class AssetCategoryModelControllerTest {

    private MockMvc mockMvc;

    @Mock
    public IAssetCategoryModelService iAssetCategoryModelService;

    @InjectMocks
    private AssetCategoryModelController assetCategoryModelController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetCategoryModelController).build();
    }

    @Test
    public void saveSingle() throws Exception {
        AssetCategoryModelRequest assetCategoryModel = new AssetCategoryModelRequest();
        when(iAssetCategoryModelService.saveAssetCategoryModel(any())).thenReturn(ActionResponse.success());
        this.mockMvc
            .perform(
                post("/api/v1/asset/categorymodel/save/single")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(assetCategoryModel)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));

    }

    @Test
    public void updateSingle() throws Exception {
        AssetCategoryModelRequest assetCategoryModel = new AssetCategoryModelRequest();
        when(iAssetCategoryModelService.updateAssetCategoryModel(any())).thenReturn(ActionResponse.success());
        this.mockMvc
            .perform(
                post("/api/v1/asset/categorymodel/update/single")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(assetCategoryModel)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryList() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryById() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/id")
                .param("primaryKey", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void deleteById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        when(iAssetCategoryModelService.delete(any())).thenReturn(ActionResponse.success());
        this.mockMvc
            .perform(post("/api/v1/asset/categorymodel/delete/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(queryCondition)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void getById() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/get/id")
                .param("primaryKey", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }

    @Test
    public void getAssetCategoryByName() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/second"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }

    @Test
    public void getSoftwareCategoryByName() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/software"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }

    @Test
    public void queryCategoryNode() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/node"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryCategoryNodeByType() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/typeNode")
                .param("type", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void querySecondCategoryNode() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/categorymodel/query/typeNode")
                .param("type", "4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }
}