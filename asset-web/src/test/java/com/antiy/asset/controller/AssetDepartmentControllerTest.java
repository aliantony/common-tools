package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetCpuResponse;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.JsonUtil;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetDepartmentControllerTest {
    @InjectMocks
    private AssetDepartmentController assetDepartmentController;
    @Mock
    private IAssetDepartmentService iAssetDepartmentService;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(assetDepartmentController).build();
    }


    @Test
    public void saveSingle()throws Exception {
        AssetDepartmentRequest assetDepartment = new AssetDepartmentRequest();
        assetDepartment.setParentId("1");
        assetDepartment.setName("test");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/department/save/single")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetDepartment))).andReturn();
        Mockito.verify(iAssetDepartmentService).saveAssetDepartment(Mockito.any());
    }

    @Test
    public void updateSingle()throws Exception {
        AssetDepartmentRequest assetDepartment = new AssetDepartmentRequest();
        assetDepartment.setId("1");
        assetDepartment.setName("test");
        Mockito.when(iAssetDepartmentService.updateAssetDepartment(Mockito.any())).thenReturn(ActionResponse.success(1));
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/department/update/single")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetDepartment))).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":1}"));
    }

    @Test
    public void queryList()throws Exception {
        AssetDepartmentQuery assetDepartment = new AssetDepartmentQuery();
        assetDepartment.setName("test");
        List<AssetDepartmentResponse> assetDepartmentResponseList = new ArrayList<>();
        AssetDepartmentResponse assetDepartmentResponse = new AssetDepartmentResponse();
        assetDepartmentResponse.setName("test");
        assetDepartmentResponseList.add(assetDepartmentResponse);
        PageResult<AssetDepartmentResponse> pageResult = new PageResult<>(10,1,1,assetDepartmentResponseList);
        Mockito.when(iAssetDepartmentService.findPageAssetDepartment(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/department/query/list")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetDepartment))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String json = JsonUtil.object2Json(actionResponse.getBody());
        PageResult<AssetCpuResponse> actual = JsonUtil.json2Object(json,PageResult.class);
        Assert.assertTrue(pageResult.getItems().size() == actual.getItems().size());
    }

    @Test
    public void queryById()throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/department/query/id").param("primaryKey","1")).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    @Test
    public void deleteById()throws Exception {
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        Mockito.when(iAssetDepartmentService.deleteAllById(Mockito.any())).thenReturn(ActionResponse.success(1));
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/department/delete/id")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(condition))).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":1}"));
    }

    @Test
    public void getByID()throws Exception {
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        List<AssetDepartmentResponse> assetDepartmentResponseList = new ArrayList<>();
        AssetDepartmentResponse assetDepartmentResponse = new AssetDepartmentResponse();
        assetDepartmentResponse.setName("test");
        assetDepartmentResponseList.add(assetDepartmentResponse);
        Mockito.when(iAssetDepartmentService.findAssetDepartmentById(Mockito.any())).thenReturn(assetDepartmentResponseList);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/department/get/id")
                       .param("primaryKey","1")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),ActionResponse.class);
        List<AssetDepartmentResponse> actual = JsonUtil.json2Object(JsonUtil.object2Json(actionResponse.getBody()),ArrayList.class);
        Assert.assertEquals(actual.size(),assetDepartmentResponseList.size());
    }

    @Test
    public void queryDepartmentNode()throws Exception {
        AssetDepartmentNodeResponse assetDepartmentNodeResponse = new AssetDepartmentNodeResponse();
        assetDepartmentNodeResponse.setStringId("1");
        assetDepartmentNodeResponse.setParentId("1");
        Mockito.when(iAssetDepartmentService.findDepartmentNode()).thenReturn(assetDepartmentNodeResponse);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/department/query/node")
                        .param("primaryKey","1")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),ActionResponse.class);
        AssetDepartmentNodeResponse actual = JsonUtil.json2Object(JsonUtil.object2Json(actionResponse.getBody()),AssetDepartmentNodeResponse.class);
        Assert.assertEquals(assetDepartmentNodeResponse.getParentId(),actual.getParentId());
    }
}