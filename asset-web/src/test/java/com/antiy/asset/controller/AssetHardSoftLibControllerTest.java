package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.BusinessSelectResponse;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetHardSoftLibControllerTest {

    private static final String        PREFIX_URL = "/api/v1/asset/assethardsoftlib";

    @InjectMocks
    private AssetHardSoftLibController assetHardSoftLibController;

    @Mock
    public IAssetHardSoftLibService    iAssetHardSoftLibService;

    private MockMvc                    mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetHardSoftLibController).build();
    }

    @Test
    public void getPageSoftwarePage() throws Exception {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setAssetId("1");
        List<SoftwareResponse> resultList = new ArrayList();
        SoftwareResponse response = new SoftwareResponse();
        response.setSoftwareId("1");
        resultList.add(response);
        PageResult<SoftwareResponse> pageResult = new PageResult<>(10, 1, 1, resultList);
        Mockito.when(iAssetHardSoftLibService.getPageSoftWareList(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/software/list")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
        String json = JsonUtil.object2Json(actionResponse.getBody());
        Assert.assertTrue(1 == JsonUtil.json2Object(json, PageResult.class).getItems().size());
    }

    @Test
    public void pullDownOs() throws Exception {
        OsQuery query = new OsQuery();
        List<OsSelectResponse> resultList = new ArrayList();
        OsSelectResponse response = new OsSelectResponse();
        response.setId("1");
        resultList.add(response);
        Mockito.when(iAssetHardSoftLibService.pullDownOs(Mockito.any())).thenReturn(resultList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/pullDown/os")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }

    @Test
    public void pulldownSupplier() throws Exception {
        AssetPulldownQuery query = new AssetPulldownQuery();
        List<String> resultList = new ArrayList();
        resultList.add("1");
        Mockito.when(iAssetHardSoftLibService.pulldownSupplier(Mockito.any())).thenReturn(resultList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/pullDown/supplier")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
        Assert.assertEquals(resultList, actionResponse.getBody());
    }

    @Test
    public void pulldownName() throws Exception {
        List<String> resultList = new ArrayList();
        resultList.add("1");
        AssetPulldownQuery query = new AssetPulldownQuery();
        query.setSupplier("1");
        Mockito.when(iAssetHardSoftLibService.pulldownName(Mockito.any())).thenReturn(resultList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/pullDown/name")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
        Assert.assertEquals(resultList, actionResponse.getBody());
    }

    @Test
    public void queryList() throws Exception {
        AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
        List<AssetHardSoftLibResponse> libResponses = new ArrayList<>();
        libResponses.add(new AssetHardSoftLibResponse());
        Mockito.when(iAssetHardSoftLibService.queryPageAssetHardSoftLib(Mockito.any()))
            .thenReturn(new PageResult<>(1, 1, 1, libResponses));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/query/list")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
        Assert.assertEquals("{\"pageSize\":1,\"totalRecords\":1,\"currentPage\":1,\"items\":[{}],\"totalPages\":1}",
            JSONObject.toJSONString(actionResponse.getBody()));
    }

    @Test
    public void pulldownVersion() throws Exception {
        List<BusinessSelectResponse> resultList = new ArrayList();
        BusinessSelectResponse businessSelectResponse = new BusinessSelectResponse();
        businessSelectResponse.setId("1");
        businessSelectResponse.setValue("cpe:/h:3com:3crwe747a:-");
        resultList.add(businessSelectResponse);
        AssetPulldownQuery query = new AssetPulldownQuery();
        query.setSupplier("1");
        query.setName("1");
        Mockito.when(iAssetHardSoftLibService.pulldownVersion(Mockito.any())).thenReturn(resultList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/pullDown/version")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
        Assert.assertEquals(JSONObject.toJSON(resultList), actionResponse.getBody());
    }

    @Test
    public void queryPageSoft() throws Exception {
        Mockito.when(iAssetHardSoftLibService.queryPageSoft(Mockito.any()))
            .thenReturn(new PageResult<>(1, 1, 1, Arrays.asList(new AssetHardSoftLibResponse())));
        AssetTemplateSoftwareRelationQuery query = new AssetTemplateSoftwareRelationQuery();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/query/softList")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
        Assert.assertEquals("{\"body\":{\"pageSize\":1,\"totalRecords\":1,\"currentPage\":1,\"items\":[{}],\"totalPages\":1},\"head\":{\"code\":\"200\",\"result\":\"成功\"}}", JSONObject.toJSONString(actionResponse));
    }

    @Test
    public void queryAllList() throws Exception {
        Mockito.when(iAssetHardSoftLibService.queryAssetList(Mockito.any())).thenReturn(ActionResponse.success());
        AssetHardSoftOperQuery query = new AssetHardSoftOperQuery();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/query/all")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }
}