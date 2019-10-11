package com.antiy.asset.controller;

import com.antiy.asset.dto.StatusJumpAssetInfo;
import com.antiy.asset.service.IAssetStatusJumpService;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.common.utils.JsonUtil;
import org.junit.After;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangxin
 * @date 2019/10/11
 */
@RunWith(PowerMockRunner.class)
// @PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)  //委派给SpringJUnit4ClassRunner
public class AssetStatusJumpControllerTest {
    @InjectMocks
    private AssetStatusJumpController jumpController;

    private MockMvc mockMvc;
    @Mock
    public IAssetStatusJumpService assetStatusChangeFlowProcessImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jumpController).build();
    }

    @After
    public void after() {
        mockMvc = null;
        assetStatusChangeFlowProcessImpl = null;
        jumpController = null;
    }

    @Test
    public void statusJumpTest() throws Exception {
        AssetStatusJumpRequest jumpRequest = new AssetStatusJumpRequest();
        jumpRequest.setAgree(Boolean.TRUE);
        List<StatusJumpAssetInfo> assetInfoList = new ArrayList<>();
        StatusJumpAssetInfo assetInfo = new StatusJumpAssetInfo();
        assetInfo.setAssetId("1");
        assetInfo.setTaskId("12");
        assetInfoList.add(assetInfo);
        jumpRequest.setFormData(new HashMap());
        jumpRequest.setAssetInfoList(assetInfoList);
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.TEMPLATE_IMPL);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/statusjump").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(jumpRequest))).andReturn();
        Mockito.verify(assetStatusChangeFlowProcessImpl).changeStatus(Mockito.any(AssetStatusJumpRequest.class));
    }
}
