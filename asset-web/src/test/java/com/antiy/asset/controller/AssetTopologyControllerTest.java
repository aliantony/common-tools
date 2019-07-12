package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetTopologyControllerTest {
    private MockMvc                 mockMvc;
    private final static String     URL_PREFIX = "/api/v1/asset/assetTopology";
    private final static String     TIP        = "资产拓扑controller单元测试失败";

    @Mock
    private IAssetTopologyService   iAssetTopologyService;
    @InjectMocks
    private AssetTopologyController assetTopologyController;
    @Autowired
    private CommonManager           commonManager;
    @Autowired
    private AssertManager           assertManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetTopologyController).build();
    }

    @Test
    public void queryCategoryModels() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/categoryModels",
            "{}");
        when(iAssetTopologyService.queryCategoryModels()).thenReturn(new ArrayList<>());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_2, result);
    }

    @Test
    public void queryAssetNodeInfo() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/assetNodeInfo",
            JSON.toJSONString(baseRequest));
        when(iAssetTopologyService.queryAssetNodeInfo(Mockito.anyString())).thenReturn(new TopologyAssetResponse());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP,"{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"status\":null,\"version\":null,\"data\":null}}", result);
    }

    @Test
    public void countAssetTopology() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/countAsset", "{}");
        when(iAssetTopologyService.countAssetTopology()).thenReturn(new CountTopologyResponse());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"status\":null,\"list\":null}}", result);
    }

    @Test
    public void queryGroupList() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/queryGroupList", "{}");
        when(iAssetTopologyService.queryGroupList()).thenReturn(new ArrayList<>());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_2, result);
    }

    @Test
    public void getTopologyList() throws Exception {
        AssetQuery query = new AssetQuery();
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/get/topologyList",
            JSON.toJSONString(query));
        when(iAssetTopologyService.queryGroupList()).thenReturn(new ArrayList<>());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);
    }

    @Test
    public void countTopologyCategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/count/category", "{}");
        when(iAssetTopologyService.countTopologyCategory()).thenReturn(new TopologyCategoryCountResponse());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"status\":null,\"data\":null}}", result);
    }

    @Test
    public void countTopologyOs() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/count/os", "{}");
        when(iAssetTopologyService.countTopologyOs()).thenReturn(new TopologyOsCountResponse());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP,"{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"status\":null,\"data\":null}}", result);
    }

    @Test
    @Ignore
    public void alarmTopology() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/alarm", "{}");
        when(iAssetTopologyService.getAlarmTopology()).thenReturn(new AssetTopologyAlarmResponse());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP,"{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"data\":null,\"status\":null,\"version\":null}}", result);
    }

    @Test
    @Ignore
    public void getTopologyGraph() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/graph", "{}");
        when(iAssetTopologyService.getTopologyGraph()).thenReturn(new AssetTopologyNodeResponse());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"status\":null,\"data\":null}}", result);
    }

    @Test
    public void queryListByIp() throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/list/ip",
            JSON.toJSONString(assetQuery));
        when(iAssetTopologyService.queryListByIp(Mockito.any())).thenReturn(new AssetTopologyIpSearchResposne());
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":{\"status\":null,\"data\":null}}", result);
    }
}
