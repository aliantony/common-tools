package com.antiy.asset.controller;

import com.antiy.asset.manage.AssertManager;
import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.service.IAssetLicenseService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetLicenseControllerTest {
    private MockMvc                mockMvc;
    @Mock
    private IAssetLicenseService   licenseService;
    @InjectMocks
    private AssetLicenseController assetLicenseController;
    @Autowired
    private CommonManager          commonManager;
    private final static String    URL_PREFIX = "/api/v1/asset/license";
    private final static String     TIP        = "资产License controller单元测试失败";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetLicenseController).build();
    }

    @Test
    public void validateAuthNum() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/validate/authNum",
            "{}");
        String result = commonManager.getResult(mockMvc, requestBuilder);
        Assert.assertEquals(TIP, AssertManager.EXPECTED_1, result);

    }
}
