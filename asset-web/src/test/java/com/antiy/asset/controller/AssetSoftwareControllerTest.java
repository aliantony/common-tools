package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.manage.controller.SoftwareControllerManager;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.support.MockContext;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ObjectQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetSoftwareControllerTest extends MockContext {
    private final static String       URL_PREFIX = "/api/v1/asset/software";
    private final static String       TIP        = "软件信息controller单元测试失败";
    private MockMvc                   mockMvc;
    @InjectMocks
    private AssetSoftwareController   controller;
    @MockBean
    private IAssetSoftwareService     softwareService;
    @Autowired
    private SoftwareControllerManager controllerManager;
    @Autowired
    private CommonManager             commonManager;
    @Autowired
    private AssertManager             assertManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void saveSingle() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/save/single",
            controllerManager.initSoftwareRequest(" 软件1"));
        when(softwareService.saveAssetSoftware(any())).thenReturn(ActionResponse.success());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }

    @Test
    public void updateSingle() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/update/single",
            controllerManager.initUpdateSoftwareRequest("1", " 软件1"));
        when(softwareService.saveAssetSoftware(any())).thenReturn(ActionResponse.success(null));
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }

    @Test
    public void queryList() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/list",
            new ObjectQuery());
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).findPageAssetSoftware(any());
    }

    @Test
    public void queryById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query",
            commonManager.initQueryCondition("1"));
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).getById(any());
    }

    @Test
    public void deleteById() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/delete", baseRequest);
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).deleteById(1);
    }

    @Test
    public void export() throws Exception {
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.get(URL_PREFIX + "/export/template").param("assetName", "test"))
            .andReturn();
        Mockito.verify(softwareService).exportTemplate();
    }

    @Test
    public void pulldownManufacturer() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager
            .postAction(URL_PREFIX + "/query/pulldown/manufacturer", "");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void exportFile() throws Exception {
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.get(URL_PREFIX + "/export/file").param("assetName", "test")).andReturn();
        Mockito.verify(softwareService).exportData(Mockito.any(), Mockito.any(), Mockito.any());

    }

    @Test
    public void importExcel() {
        commonManager.postAction(URL_PREFIX + "/import/file",
            controllerManager.initImportRequest("1", new String[] { "3" }));

    }

    @Test
    public void countAssetByCategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/count/category", "");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void countAssetByStatus() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/count/status", "");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void countAssetByManufacturer() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/count/manufacturer", "");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void queryAssetByManufacturer() throws Exception {
        Map map = new HashMap();
        map.put("manufacturerName", "123");
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/manufacturer",
            JSON.toJSON(map));
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void querySoftwareDetail() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        query.setPrimaryKey("1");
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/detail",
            JSON.toJSON(query));
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).querySoftWareDetail(any());
    }

    @Test
    public void queryInstallList() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/install",
            JSON.toJSON(query));
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).findPageInstall(any());
    }

    @Test
    public void queryAssetInstallList() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/assetinstall",
            JSON.toJSON(query));
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).findPageAssetInstall(any());
    }

    @Test
    public void queryInstallSchedule() throws Exception {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setId("1");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL_PREFIX + "/query/installSchedule")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(query))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void assetSetting() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/asset/setting",
            controllerManager.initRegisterRequest());
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).softwareInstallConfig(any());
    }
}