package com.antiy.asset.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class SoftWareStatusChangeProcessImplTest {
    @Mock
    AssetSoftwareDao assetSoftwareDao;
    @Mock
    AssetOperationRecordDao assetOperationRecordDao;
    @Mock
    SchemeDao schemeDao;
    @Mock
    AssetDao assetDao;
    @Spy
    BaseConverter<SchemeRequest, Scheme> schemeRequestToSchemeConverter;
    @Mock
    ActivityClient activityClient;
    @Mock
    WorkOrderClient workOrderClient;
    @Mock
    AesEncoder aesEncoder;
    @InjectMocks
    SoftWareStatusChangeProcessImpl softWareStatusChangeProcessImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // 模拟用户登录
        LoginUser loginUser =
                JSONObject.parseObject(
                        "{ \"id\":8, \"username\":\"zhangbing\", \"password\":\"$2a$10$hokzLPdz15q9XFuNB8HA0ObV9j301oxkFBlsJUCe/8iWBvql5gBdO\", \"name\":\"张冰\", \"duty\":\"部门经历\", \"department\":\"A是不\", \"phone\":\"123\", \"email\":\"string123@email\", \"status\":1, \"errorCount\":4, \"lastLoginTime\":1553737022175, \"lastModifiedPassword\":1550657104216, \"sysRoles\":[ { \"id\":9, \"code\":\"config_admin\", \"name\":\"配置管理员\", \"description\":\"\" } ], \"areas\":[ { \"id\":10, \"parentId\":2, \"levelType\":2, \"fullName\":\"金牛区\", \"shortName\":\"1\", \"fullSpell\":\"1\", \"shortSpell\":\"1\", \"status\":1, \"demo\":\"\" }, { \"id\":112, \"parentId\":0, \"levelType\":1, \"fullName\":\"四川省成都市\", \"status\":1, \"demo\":\"\" } ], \"enabled\":true, \"accountNonExpired\":true, \"accountNonLocked\":true, \"credentialsNonExpired\":true } ",
                        LoginUser.class);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginUser, "123");
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
    public void testChangeStatus() throws Exception {
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setLicenseSecretKey("");
        assetSoftware.setAssetCount(0);
        assetSoftware.setBuyDate(0L);
        assetSoftware.setServiceLife(0L);
        assetSoftware.setSize(0);
        assetSoftware.setOperationSystem("");
        assetSoftware.setCategoryModel("");
        assetSoftware.setCategoryModelName("");
        assetSoftware.setName("");
        assetSoftware.setUploadSoftwareName("");
        assetSoftware.setPath("");
        assetSoftware.setVersion("");
        assetSoftware.setManufacturer("");
        assetSoftware.setDescription("");
        assetSoftware.setAssetGroup("");
        assetSoftware.setSoftwareLabel(0);
        assetSoftware.setSoftwareStatus(0);
        assetSoftware.setAuthorization(0);
        assetSoftware.setReportSource(0);
        assetSoftware.setProtocol("");
        assetSoftware.setLanguage("");
        assetSoftware.setReleaseTime(0L);
        assetSoftware.setPublisher("");
        assetSoftware.setGmtCreate(0L);
        assetSoftware.setMemo("");
        assetSoftware.setGmtModified(0L);
        assetSoftware.setCreateUser(0);
        assetSoftware.setModifyUser(0);
        assetSoftware.setStatus(0);
        assetSoftware.setSerial("");
        assetSoftware.setMd5Code("");
        assetSoftware.setPort("");
        assetSoftware.setId(0);

        when(assetSoftwareDao.getById(any())).thenReturn(assetSoftware);

        Asset asset = new Asset();
        asset.setAreaId("1");
        when(assetDao.getById(any())).thenReturn(asset);

        AssetStatusReqeust assetStatusReqeust = new AssetStatusReqeust();
        assetStatusReqeust.setManualStartActivityRequest(new ManualStartActivityRequest());
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.SOFTWARE_IMPL_RETIRE);
        assetStatusReqeust.setSoftwareStatusEnum(SoftwareStatusEnum.WAIT_ANALYZE_RETIRE);
        assetStatusReqeust.setAssetId("");
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WATI_REGSIST);
        assetStatusReqeust.setSoftware(false);
        assetStatusReqeust.setSchemeRequest(new SchemeRequest());
        assetStatusReqeust.setWorkOrderVO(null);
        assetStatusReqeust.setActivityHandleRequest(new ActivityHandleRequest());
        assetStatusReqeust.setAgree(false);

        ActionResponse result = softWareStatusChangeProcessImpl.changeStatus(assetStatusReqeust);
        Assert.assertNotNull(result);
    }
}
