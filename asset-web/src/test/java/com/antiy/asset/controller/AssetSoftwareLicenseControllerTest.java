package com.antiy.asset.controller;


import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetSoftwareLicenseControllerTest {

    @MockBean
    private IAssetSoftwareLicenseService iAssetSoftwareLicenseService;


    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void saveSingle() throws Exception {
        AssetSoftwareLicenseRequest assetSoftwareLicenseRequest=new AssetSoftwareLicenseRequest();
        assetSoftwareLicenseRequest.setId("1");
        when(iAssetSoftwareLicenseService.saveAssetSoftwareLicense(any())).thenReturn(1);
        MvcResult mvcResult = mvc.perform(post("/api/v1/asset/softwarelicense/save/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetSoftwareLicenseRequest))).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map map = JsonUtil.json2Object(contentAsString, Map.class);
        Assert.assertEquals(1,map.get("body"));

    }

    @Test
    public void update() throws Exception {
        AssetSoftwareLicenseRequest assetSoftwareLicenseRequest=new AssetSoftwareLicenseRequest();
        MvcResult mvcResult = mvc.perform(post("/api/v1/asset/softwarelicense/update/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetSoftwareLicenseRequest))).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map map = JsonUtil.json2Object(contentAsString, Map.class);
        Assert.assertEquals(null,map.get("body"));

    }
    @Test
    public void queryById() throws Exception {

        mvc.perform(get("/api/v1/asset/softwarelicense/query")
                .param("primaryKey","1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void queryList() throws Exception {
        AssetSoftwareLicenseRequest assetSoftwareLicenseRequest=new AssetSoftwareLicenseRequest();
        assetSoftwareLicenseRequest.setId("1");
        PageResult<AssetSoftwareLicenseResponse> page=new PageResult<AssetSoftwareLicenseResponse>(10,100,1,null);
        when(iAssetSoftwareLicenseService.findPageAssetSoftwareLicense(any())).thenReturn(page);
        MvcResult mvcResult = mvc.perform(get("/api/v1/asset/softwarelicense/query/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetSoftwareLicenseRequest))).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
       Assert.assertTrue(contentAsString.contains("200"));
    }
    @Test
    public void deleteById() throws Exception {
        BaseRequest baseRequest=new BaseRequest ();

        baseRequest.setStringId("12");
        when(iAssetSoftwareLicenseService.deleteById(any())).thenReturn(1);

        MvcResult mvcResult = mvc.perform(post("/api/v1/asset/softwarelicense/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(baseRequest))).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map map = JsonUtil.json2Object(contentAsString, Map.class);
        Assert.assertEquals(1,map.get("body"));


    }

}
