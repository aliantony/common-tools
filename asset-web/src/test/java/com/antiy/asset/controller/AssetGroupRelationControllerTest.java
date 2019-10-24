package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.JsonUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import util.ControllerUtil;

import static org.mockito.Mockito.when;

/**
 * @author zhangxin
 * @date 2019/10/22
 */

@RunWith(PowerMockRunner.class)
public class AssetGroupRelationControllerTest {

    @InjectMocks
    private AssetGroupRelationController groupRelationController;

    @Mock
    private IAssetGroupRelationService iAssetGroupRelationService;

    private MockMvc mockMvc;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupRelationController).build();
    }


    @After
    public void tearDown(){
        groupRelationController = null;
        iAssetGroupRelationService = null;
        mockMvc = null;
    }
    @Test
    public void hasRealtionAsset() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("123");

        when(iAssetGroupRelationService.hasRealtionAsset(Mockito.anyString())).thenReturn(1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/grouprelation/hasRealtionAsset")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(queryCondition))).andReturn();
        Assert.assertEquals(true, ControllerUtil.getResponse(mvcResult).getBody());
    }
}
