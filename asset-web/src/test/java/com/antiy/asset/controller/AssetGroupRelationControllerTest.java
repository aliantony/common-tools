package com.antiy.asset.controller;

import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupRelationResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetGroupRelationControllerTest {
    @MockBean
    private IAssetGroupRelationService iAssetGroupRelationService;


    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void saveSingle() throws Exception {
        AssetGroupRelationRequest assetGroup=new AssetGroupRelationRequest ();

        mvc.perform(post("/api/v1/asset/grouprelation/save/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetGroup)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateSingle() throws Exception {
        AssetGroupRelationRequest assetGroup=new AssetGroupRelationRequest ();
        mvc.perform(put("/api/v1/asset/grouprelation/update/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetGroup)))
                .andExpect(status().isOk());
    }
    @Test
    public void queryList() throws Exception {
        AssetGroupRequest assetGroup=new AssetGroupRequest ();

        PageResult<AssetGroupRelationResponse> page=new PageResult<AssetGroupRelationResponse>(10,100,1,null);
        when( iAssetGroupRelationService.findPageAssetGroupRelation(any())).thenReturn(page);
        MvcResult mvcResult = mvc.perform(get("/api/v1/asset/grouprelation/query/list")
                .param("assetGroupId","1") )
                .andExpect(status().isOk()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(contentAsString.contains("\"totalPages\":10"));

    }

    @Test
    public void queryById() throws Exception {
        QueryCondition query=new QueryCondition();
        query.setPrimaryKey("1");
        AssetGroupRelation assetGroupRelation=new AssetGroupRelation();
        assetGroupRelation.setId(1);
        when( iAssetGroupRelationService.getById(any())).thenReturn(assetGroupRelation);
        MvcResult mvcResult = mvc.perform(get("/api/v1/asset/grouprelation/query/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.object2Json(query)))
                .andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

       Assert.assertTrue(contentAsString.contains("\"id\":1"));


    }
    @Test
    public void deleteById() throws Exception {

        QueryCondition query=new QueryCondition ();
       query.setPrimaryKey("1");
        when(iAssetGroupRelationService.deleteById(any())).thenReturn(1);
        MvcResult mvcResult = mvc.perform(delete("/api/v1/asset/grouprelation/delete/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(query)))
                .andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        Assert.assertTrue(contentAsString.contains("\"body\":1"));
    }

}
