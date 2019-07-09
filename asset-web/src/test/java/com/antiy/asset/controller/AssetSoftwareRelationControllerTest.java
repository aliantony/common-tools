package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.manage.controller.SoftwareRelationControllerManager;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.support.MockContext;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class AssetSoftwareRelationControllerTest extends MockContext {
    private final static String URL_PREFIX = "/api/v1/asset/softwarerelation";
    private final static String TIP = "资产软件关系信息";
    private MockMvc mockMvc;
    @Autowired
    private CommonManager commonManager;
    @Autowired
    private AssertManager assertManager;
    @Autowired
    private SoftwareRelationControllerManager relationContManager;
    @Mock
    private IAssetSoftwareRelationService iAssetSoftwareRelationService;
    @InjectMocks
    private AssetSoftwareRelationController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void countAssetBySoftIdTest() throws Exception {
        when(iAssetSoftwareRelationService.countAssetBySoftId(1)).thenReturn(1);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(URL_PREFIX + "/countAssetBySoftId")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(queryCondition))).andReturn();
        Assert.assertNotNull(mvcResult);
    }

    @Test
    public void getSoftwareByAssetIdTest() throws Exception {
        when(iAssetSoftwareRelationService.getSoftByAssetId(1)).thenReturn(new ArrayList<>());
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(URL_PREFIX + "/getSoftwareByAssetId")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(queryCondition))).andReturn();
        Assert.assertNotNull(mvcResult);
    }


    @Test
    public void queryOSTest() throws Exception {
        when(iAssetSoftwareRelationService.findOS()).thenReturn(new ArrayList<>());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(URL_PREFIX + "/query/os")).andReturn();
        Assert.assertNotNull(mvcResult);

    }

    
    @Test
    public void queryInstallListTest() throws Exception {
        InstallQuery query = new InstallQuery();
        when(iAssetSoftwareRelationService.queryInstallList(Mockito.any())).thenReturn(new PageResult<>());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL_PREFIX + "/query/installList")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        Assert.assertNotNull(mvcResult);
    }

    @Test
    public void installSoftwareTest() throws Exception {
        AssetSoftwareRelationList assetSoftwareRelationList = new AssetSoftwareRelationList();
        when(iAssetSoftwareRelationService.installSoftware(Mockito.any())).thenReturn(new ActionResponse());
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.post(URL_PREFIX + "/software/install")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetSoftwareRelationList)))
            .andReturn();
        Assert.assertNotNull(mvcResult);
    }

    @Test
    public void getSimpleSoftwarePageByAssetId() throws Exception {
        AssetSoftwareRelationQuery assetSoftwareRelationQuery = new AssetSoftwareRelationQuery();
        assetSoftwareRelationQuery.setAssetId("1");
        when(iAssetSoftwareRelationService.getSimpleSoftwarePageByAssetId(Mockito.any())).thenReturn(new PageResult<>());
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(URL_PREFIX + "/query/simpleSoftwareList")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetSoftwareRelationQuery)))
                .andReturn();
        Assert.assertNotNull(mvcResult);
    }
}