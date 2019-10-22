package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.IAssetAssemblyService;
import com.antiy.asset.service.IAssetSysServiceLibService;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.query.AssetSysServiceLibQuery;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class AssetSysServiceLibControllerTest {
    private static final String PREFIX_URL = "/api/v1/asset/assetsysservicelib";

    @InjectMocks
    private AssetSysServiceLibController assetSysServiceLibController;

    @Mock
    public IAssetSysServiceLibService assetSysServiceLibService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetSysServiceLibController).build();
    }

    @Test
    public void queryList() throws Exception {
        AssetSysServiceLibQuery query=new AssetSysServiceLibQuery();
        query.setService("1");
        List<AssetSysServiceLibResponse> resultList = new ArrayList();
        AssetSysServiceLibResponse response = new AssetSysServiceLibResponse();
        response.setBusinessId("1");
        resultList.add(response);
        PageResult<AssetSysServiceLibResponse> pageResult = new PageResult<>(10, 1, 1, resultList);
        Mockito.when(assetSysServiceLibService.queryPageAssetSysServiceLib(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX_URL + "/query/list")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }
}
