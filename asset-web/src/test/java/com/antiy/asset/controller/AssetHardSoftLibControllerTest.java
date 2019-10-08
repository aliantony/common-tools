package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetHardSoftLibControllerTest {

    private static final String PREFIX_URL = "/api/v1/asset/assethardsoftlib";

    @InjectMocks
    private AssetHardSoftLibController assetHardSoftLibController;

    @Mock
    public IAssetHardSoftLibService iAssetHardSoftLibService;

    private MockMvc mockMvc;

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
}