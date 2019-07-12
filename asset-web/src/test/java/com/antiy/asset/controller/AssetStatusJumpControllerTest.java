package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.impl.AssetStatusChangeFlowProcessImpl;
import com.antiy.asset.service.impl.SoftWareStatusChangeProcessImpl;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.request.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.LoginUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetStatusJumpControllerTest {
    private MockMvc                       mockMvc;

    @Mock
    private IAssetService                 assetService;
    @Mock
    private AssetDao                      assetDao;
    @Mock
    private AssetSoftwareDao              softwareDao;
    @Mock
    private AssetOperationRecordDao       operationRecordDao;
    @Mock
    private SchemeDao                     schemeDao;
    @Mock
    private ActivityClient                activityClient;

    @Mock
    private IAssetSoftwareRelationService softwareRelationService;

    @InjectMocks
    private AssetStatusJumpController     assetStatusJumpController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetStatusJumpController).build();

        // 模拟用户登录
        LoginUser loginUser = JSONObject.parseObject(
            "{ \"id\":8, \"username\":\"zhangbing\", \"password\":\"$2a$10$hokzLPdz15q9XFuNB8HA0ObV9j301oxkFBlsJUCe/8iWBvql5gBdO\", \"name\":\"张冰\", \"duty\":\"部门经历\", \"department\":\"A是不\", \"phone\":\"123\", \"email\":\"string123@email\", \"status\":1, \"errorCount\":4, \"lastLoginTime\":1553737022175, \"lastModifiedPassword\":1550657104216, \"sysRoles\":[ { \"id\":9, \"code\":\"config_admin\", \"name\":\"配置管理员\", \"description\":\"\" } ], \"areas\":[ { \"id\":10, \"parentId\":2, \"levelType\":2, \"fullName\":\"金牛区\", \"shortName\":\"1\", \"fullSpell\":\"1\", \"shortSpell\":\"1\", \"status\":1, \"demo\":\"\" }, { \"id\":112, \"parentId\":0, \"levelType\":1, \"fullName\":\"四川省成都市\", \"status\":1, \"demo\":\"\" } ], \"enabled\":true, \"accountNonExpired\":true, \"accountNonLocked\":true, \"credentialsNonExpired\":true } ",
            LoginUser.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, "123");
        Map<String, Object> map = new HashMap<>();
        map.put("principal", loginUser);
        token.setDetails(map);

        OAuth2Authentication authentication = Mockito.mock(OAuth2Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @Ignore
    public void testStatusJump() throws Exception {
        AssetStatusReqeust assetStatusReqeust = new AssetStatusReqeust();
        assetStatusReqeust.setSoftware(true);
        assetStatusReqeust.setAgree(true);
        assetStatusReqeust.setAssetId("1");
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.SOFTWARE_REGISTER);
        assetStatusReqeust.setSoftwareStatusEnum(SoftwareStatusEnum.WATI_REGSIST);
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setSoftwareStatus(SoftwareStatusEnum.NOT_REGSIST.getCode());
        Mockito.when(softwareDao.getById(Mockito.anyString())).thenReturn(assetSoftware);
        mockMvc
            .perform(post("/api/v1/asset/statusjump").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(assetStatusReqeust)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        assetStatusReqeust.setSoftware(false);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_REGISTER);
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WATI_REGSIST);
        mockMvc
            .perform(post("/api/v1/asset/statusjump").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(assetStatusReqeust)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void statusJumpWithAsset() throws Exception {
        AssetStatusJumpRequst assetStatusJumpRequst = new AssetStatusJumpRequst();
        assetStatusJumpRequst.setAssetStatusEnum(AssetStatusEnum.NET_IN);
        assetStatusJumpRequst.setAgree(true);
        assetStatusJumpRequst.setAssetId("1");
        mockMvc
            .perform(post("/api/v1/asset/statusjump/baseline").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(assetStatusJumpRequst)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void configurateSoftware() throws Exception {
        AssetRelationSoftRequest assetRelationSoftRequest = new AssetRelationSoftRequest();
        assetRelationSoftRequest.setAssetId("1");
        mockMvc
            .perform(post("/api/v1/asset/statusjump/baseline/configurateSoftware")
                .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(assetRelationSoftRequest)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

    }

    @Test
    public void assetNoRegister() throws Exception {
        AssetStatusChangeRequest assetStatusChangeRequest = new AssetStatusChangeRequest();
        assetStatusChangeRequest.setSoftware(true);
        assetStatusChangeRequest.setAssetId("1");
        assetStatusChangeRequest.setStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        assetStatusChangeRequest.setActivityHandleRequest(new ActivityHandleRequest());
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setSoftwareStatus(1);
        Mockito.when(softwareDao.getById(Mockito.any())).thenReturn(assetSoftware);
        Asset asset = new Asset();
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        Mockito.when(assetDao.getById(Mockito.any())).thenReturn(asset);
        Mockito.when(activityClient.completeTask(Mockito.any())).thenReturn(ActionResponse.success());
        Mockito.when(assetDao.getNumberById(Mockito.anyString())).thenReturn("1");
        mockMvc
            .perform(post("/api/v1/asset/statusjump/noRegister").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(assetStatusChangeRequest)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        assetStatusChangeRequest.setSoftware(false);
        asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        mockMvc
            .perform(post("/api/v1/asset/statusjump/noRegister").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(assetStatusChangeRequest)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void updateAssetStatus() throws Exception {
        AssetConfigValidateRefuseReqeust assetConfigValidateRefuseReqeust = new AssetConfigValidateRefuseReqeust();
        mockMvc
            .perform(post("/api/v1/asset/statusjump/updateAssetStatus").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(assetConfigValidateRefuseReqeust)))
            .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

    }

}
