package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.query.AssetIDAndSchemeTypeQuery;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.PageResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//@SpringBootTest
@RunWith(SpringRunner.class)
public class SchemeControllerTest {
    @Mock
    private ISchemeService   iSchemeService;
    @InjectMocks
    private SchemeController schemeController;
    private MockMvc          mockMvc;
    /**
     * 两个及以上test需要的公共对象
     */
    private static String    baseUrl = "/api/v1/asset/scheme";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schemeController).build();
    }

    @Test
    public void testQueryList() throws Exception {
        String url = "/query/list";
        PageResult<SchemeResponse> pageResult = new PageResult<>();
        pageResult.setPageSize(10);
        pageResult.setCurrentPage(1);
        pageResult.setTotalRecords(100);
        when(iSchemeService.findPageScheme(any())).thenReturn(pageResult);
        SchemeQuery schemeQuery = new SchemeQuery();
        String requestJson = JSONObject.toJSONString(schemeQuery);
        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(baseUrl + url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andReturn();
        Assert.assertNotNull(mvcResult);

    }

    @Test
    public void testQueryByAssetIdAndType() throws Exception {
        String url = "/query/AssetIdAndType";
        AssetIDAndSchemeTypeQuery assetIDAndSchemeTypeQuery = new AssetIDAndSchemeTypeQuery();
        assetIDAndSchemeTypeQuery.setAssetId("1");
        when(iSchemeService.findSchemeByAssetIdAndType(any())).thenReturn(new SchemeResponse());
        String requestJson = JSONObject.toJSONString(assetIDAndSchemeTypeQuery);
        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(baseUrl + url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andReturn();
        Assert.assertNotNull(mvcResult);
    }

    @Test
    public void testQueryMemoByAssetId() throws Exception {
        String url = "/query/memoById";
        when(iSchemeService.queryMemoById(any())).thenReturn(new SchemeResponse());
        SchemeQuery schemeQuery = new SchemeQuery();
        String requestJson = JSONObject.toJSONString(schemeQuery);
        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(baseUrl + url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andReturn();
        Assert.assertNotNull(mvcResult);
    }
}
