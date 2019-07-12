package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.request.CategoryNodeRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    private MockMvc                      mockMvc;

    @Mock
    public IAssetCategoryModelService    iAssetCategoryModelService;

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
            .perform(post("/api/v1/asset/categorymodel/save/single").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(assetCategoryModel)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body", is(equalTo(null))));

    }

    @Test
    public void updateSingle() throws Exception {
        AssetCategoryModelRequest assetCategoryModel = new AssetCategoryModelRequest();
        when(iAssetCategoryModelService.updateAssetCategoryModel(any())).thenReturn(ActionResponse.success());
        this.mockMvc
            .perform(post("/api/v1/asset/categorymodel/update/single").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(assetCategoryModel)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryList() throws Exception {
        this.mockMvc
            .perform(
                post("/api/v1/asset/categorymodel/query/list").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        this.mockMvc
            .perform(post("/api/v1/asset/categorymodel/query/id").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(queryCondition)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void deleteById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        when(iAssetCategoryModelService.delete(any())).thenReturn(ActionResponse.success());
        this.mockMvc
            .perform(post("/api/v1/asset/categorymodel/delete/id").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(queryCondition)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void getById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        this.mockMvc
            .perform(post("/api/v1/asset/categorymodel/get/id").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(queryCondition)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }

    @Test
    public void getAssetCategoryByName() throws Exception {
        this.mockMvc.perform(post("/api/v1/asset/categorymodel/query/second")).andExpect(status().isOk())
            .andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }

    @Test
    public void getSoftwareCategoryByName() throws Exception {
        this.mockMvc.perform(post("/api/v1/asset/categorymodel/query/software")).andExpect(status().isOk())
            .andExpect(jsonPath("$.body.size()", is(equalTo(0))));
    }

    @Test
    public void queryCategoryNode() throws Exception {
        this.mockMvc.perform(post("/api/v1/asset/categorymodel/query/node")).andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryCategoryNodeWhihoutNode() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/asset/categorymodel/query/withoutnode"))
            .andExpect(status().isOk()).andReturn();
        Assert.assertEquals(mvcResult.getResponse().getContentAsString(),
            "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":[]}");
    }

    @Test
    public void queryComputeAndNetCategoryNode() throws Exception {
        this.mockMvc.perform(post("/api/v1/asset/categorymodel/query/computeNetNode")
            .contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void queryCategoryNodeByType() throws Exception {
        CategoryNodeRequest categoryNodeRequest = new CategoryNodeRequest();
        categoryNodeRequest.setType(1);
        this.mockMvc
            .perform(post("/api/v1/asset/categorymodel/query/typeNode").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(categoryNodeRequest)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.body", is(equalTo(null))));
    }

}