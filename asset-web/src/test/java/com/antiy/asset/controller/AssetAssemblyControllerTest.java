package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import com.google.common.collect.Lists;
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
public class AssetAssemblyControllerTest {

    private static final String PREFIX_URL = "/api/v1/asset/assetassembly";

    @InjectMocks
    private AssetAssemblyController assetAssemblyController;

    @Mock
    public IAssetAssemblyService iAssetAssemblyService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetAssemblyController).build();
    }

    @Test
    public void queryList() throws Exception {
        AssetAssemblyQuery query = new AssetAssemblyQuery();
        query.setExcludeAssemblyIds(Lists.newArrayList("1","2","3"));
        List<AssetAssemblyResponse> resultList = new ArrayList();
        AssetAssemblyResponse response = new AssetAssemblyResponse();
        response.setAssetId("1");
        resultList.add(response);
        PageResult<AssetAssemblyResponse> pageResult = new PageResult<>(10, 1, 1, resultList);
        Mockito.when(iAssetAssemblyService.queryPageAssetAssembly(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/query/enableList")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }
}