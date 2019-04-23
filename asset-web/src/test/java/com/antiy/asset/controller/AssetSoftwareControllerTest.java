package com.antiy.asset.controller;

import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.manage.controller.SoftwareControllerManager;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.support.MockContext;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.common.base.ActionResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AssetSoftwareControllerTest extends MockContext {
    private final static String URL_PREFIX = "/api/v1/asset/software";
    private final static String TIP = "软件信息controller单元测试失败";
    private MockMvc mockMvc;
    @Autowired
    private AssetSoftwareController controller;
    @MockBean
    private IAssetSoftwareService softwareService;
    @Autowired
    private SoftwareControllerManager controllerManager;
    @Autowired
    private CommonManager commonManager;
    @Autowired
    private AssertManager assertManager;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    //todo
    @Test
    @Ignore
    public void saveSingle() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/save/single", controllerManager.initSoftwareRequest(" 软件1"));
        when(softwareService.saveAssetSoftware(any())).thenReturn(ActionResponse.success(null));
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }

    //todo
    @Test
    @Ignore
    public void updateSingle() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/update/single", controllerManager.initUpdateSoftwareRequest("1", " 软件1"));
        when(softwareService.saveAssetSoftware(any())).thenReturn(ActionResponse.success(null));
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }

    @Test
    public void queryList() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/list", controllerManager.initSoftwareQuery());
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).findPageAssetSoftware(any());
    }

    @Test
    public void queryById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query", commonManager.initQueryCondition("1"));
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).getById(any());
    }

    //todo
    @Test
    @Ignore
    public void deleteById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.delAction(URL_PREFIX + "/delete", commonManager.initQueryCondition("1"));
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).deleteById(1);
    }

    //todo
    @Test
    public void export() {
    }

    @Test
    public void pulldownManufacturer() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/pulldown/manufacturer");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    //todo
    @Test
    public void exportFile() {

    }

    //todo
    @Test
    public void importExcel() {
        commonManager.getAction(URL_PREFIX + "/import/file", controllerManager.initImportRequest("1", new String[]{"3"}));

    }

    @Test
    public void countAssetByCategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/count/category");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void countAssetByStatus() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/count/status");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void countAssetByManufacturer() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/count/manufacturer");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void queryAssetByManufacturer() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/manufacturer");
        requestBuilder.param("manufacturerName", "helloWorld");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        assertManager.assertSuccResponse(TIP, result);
    }

    @Test
    public void querySoftwareDetail() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        query.setPrimaryKey("1");
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/detail", query);
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).querySoftWareDetail(any());
    }

    @Test
    public void queryInstallList() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/install", query);
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).findPageInstall(any());
    }

    @Test
    public void queryAssetInstallList() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/assetinstall", query);
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).findPageAssetInstall(any());
    }

    @Test
    public void queryInstallSchedule() throws Exception {
        AssetSoftwareQuery query = controllerManager.initSoftwareQuery();
        query.setId("1");
        MockHttpServletRequestBuilder requestBuilder = commonManager.getAction(URL_PREFIX + "/query/installSchedule", query);
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"assetSoftware\":null,\"assetSoftwareInstallResponseList\":[]}}", result);
    }

    @Test
    public void assetSetting() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/deal/asset/setting", controllerManager.initRegisterRequest());
        commonManager.getResult(mockMvc, requestBuilder);
        verify(softwareService).configRegister(any(), any());
    }
}