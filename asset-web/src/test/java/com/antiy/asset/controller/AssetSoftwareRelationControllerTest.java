package com.antiy.asset.controller;

import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.manage.controller.SoftwareRelationControllerManager;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.support.MockContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    @MockBean
    private IAssetSoftwareRelationService iAssetSoftwareRelationService;
    @Autowired
    private AssetSoftwareRelationController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void saveSingle() throws Exception {
        MockHttpServletRequestBuilder builder = commonManager.postAction(URL_PREFIX + "/save/single", relationContManager.initRelationRequest("1", "1", 2));
        String actualResult = commonManager.getResult(mockMvc, builder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_3, actualResult);
    }


    @Test
    public void countAssetBySoftId() throws Exception {
        MockHttpServletRequestBuilder builder = commonManager.getAction(URL_PREFIX + "/countAssetBySoftId", commonManager.initQueryCondition("1"));
        String actualResult = commonManager.getResult(mockMvc, builder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_3, actualResult);
    }

    @Test
    public void getSoftwareByAssetId() throws Exception {
        MockHttpServletRequestBuilder builder = commonManager.getAction(URL_PREFIX + "/countAssetBySoftId", commonManager.initQueryCondition("1"));
        String result = commonManager.getResult(mockMvc, builder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_3, result);
    }

    @Test
    public void queryOS() throws Exception {
        MockHttpServletRequestBuilder builder = commonManager.getAction(URL_PREFIX + "/query/os");
        String result = commonManager.getResult(mockMvc, builder);
        assertManager.assertSuccResponse(TIP, result);

    }


    @Test
    public void installSoftware() throws Exception {
        MockHttpServletRequestBuilder builder = commonManager.postAction(URL_PREFIX + "/software/install", relationContManager.initRelationList("1", 1));
        String result = commonManager.getResult(mockMvc, builder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }

    @Test
    public void getSimpleSoftwarePageByAssetId() throws Exception {
        MockHttpServletRequestBuilder builder = commonManager.getAction(URL_PREFIX + "/query/simpleSoftwareList", relationContManager.initSoftwareRelationQuery("1", "1", 1));
        String result = commonManager.getResult(mockMvc, builder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }
}