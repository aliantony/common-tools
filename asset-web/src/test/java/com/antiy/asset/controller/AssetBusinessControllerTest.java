package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AreaIdRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetBusinessControllerTest {
    @Mock
    public IAssetService           iAssetService;
    private MockMvc                mockMvc;
    @InjectMocks
    private AssetBusinessController assetBusinessController;
    @Autowired
    private CommonManager          commonManager;
    private final static String    URL_PREFIX = "/api/v1/asset";
    private final static String    TIP        = "资产License controller单元测试失败";
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetBusinessController).build();
    }

    @Test
    public void queryUuidByAreaId() throws Exception {
        AreaIdRequest areaIdRequest = new AreaIdRequest();
        areaIdRequest.setAreaIds(Arrays.asList("1", "2"));
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query/uuidByAreaId",
            JSON.toJSONString(areaIdRequest));
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_2, result);

    }
}
