package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.entity.AssetAssemblyLib;
import com.antiy.asset.service.IAssetAssemblyLibService;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.QueryCondition;
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
public class AssetAssemblyLibControllerTest {
    @Mock
    public IAssetAssemblyLibService iAssetAssemblyLibService;

    @InjectMocks
    private AssetAssemblyLibController assetAssemblyLibController;

    private MockMvc                 mockMvc;

    private final static String PREFIX_URL ="/api/v1/asset/assetassemblylib";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetAssemblyLibController).build();
    }

    @Test
    public void queryAssemblyByHardSoftId() throws Exception {
        List<AssetAssemblyResponse> assetAssemblyResponses = new ArrayList<>();
        AssetAssemblyResponse assetAssemblyResponse = new AssetAssemblyResponse();
        assetAssemblyResponse.setProductName("abc");
        assetAssemblyResponses.add(assetAssemblyResponse);
        QueryCondition condition=new QueryCondition();
        condition.setPrimaryKey("1");
        Mockito.when(iAssetAssemblyLibService.queryAssemblyByHardSoftId(Mockito.any()))
            .thenReturn(assetAssemblyResponses);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/query/assembly/id")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(condition))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }

}
