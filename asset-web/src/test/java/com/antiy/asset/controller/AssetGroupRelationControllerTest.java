package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
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
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
    @InjectMocks
    private AssetGroupRelationController assetGroupRelationController;
    private MockMvc mockMvc;
    /**
     * 两个及以上test需要的公共对象
     */
    private static String    baseUrl = "/api/v1/asset/grouprelation";
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetGroupRelationController).build();
    }

    @Test
    public void hasRealtionAssetTest() throws Exception {
        String url = "/hasRealtionAsset";
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        String requestJson = JSONObject.toJSONString(queryCondition);
        when(iAssetGroupRelationService.hasRealtionAsset(Mockito.anyString())).thenReturn(1);
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(baseUrl + url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andReturn();
        Assert.assertNotNull(mvcResult);
    }
}
