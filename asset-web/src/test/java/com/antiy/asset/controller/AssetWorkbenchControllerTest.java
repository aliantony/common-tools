package com.antiy.asset.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AssetUnknownRequest;
import com.antiy.common.utils.LogUtils;

/**
 * @author zhangyajun
 * @create 2019-11-04 10:35
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LogUtils.class })
@PowerMockRunnerDelegate(SpringRunner.class)
public class AssetWorkbenchControllerTest {

    @InjectMocks
    private AssetWorkbenchController workbenchController;

    @Mock
    private IAssetService            assetService;

    private MockMvc                  mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(workbenchController).build();
    }

    @Test
    public void queryUnknownAssetCount() throws Exception {
        AssetUnknownRequest request = mock(AssetUnknownRequest.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/unknownAssetAmount")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(request))).andReturn();
        Mockito.verify(assetService).queryUnknownAssetCount(Mockito.any());
    }
}