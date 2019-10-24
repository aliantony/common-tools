package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetTypeEnum;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;

import static com.antiy.common.utils.LoginUserUtil.getLoginUser;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LogUtils.class, LogHandle.class})
// @SpringBootTest
@PowerMockIgnore({"javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*"})

public class AssetHardSoftLibServiceImplTest {
    @Mock
    private AssetHardSoftLibDao assetHardSoftLibDao;
    @Spy
    private BaseConverter<AssetHardSoftLibRequest, AssetHardSoftLib> requestConverter;
    @Mock
    private BaseConverter<AssetHardSoftLib, AssetHardSoftLibResponse> responseConverter;
    @Mock
    private IAssetInstallTemplateService iAssetInstallTemplateService;
    @Mock
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @InjectMocks
    private AssetHardSoftLibServiceImpl assetHardSoftLibService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // 模拟用户登录
        LoginUser loginUser = JSONObject.parseObject(
                "{ \"id\":8, \"username\":\"zhangbing\", \"password\":\"$2a$10$hokzLPdz15q9XFuNB8HA0ObV9j301oxkFBlsJUCe/8iWBvql5gBdO\", \"name\":\"张冰\", \"duty\":\"部门经历\", \"department\":\"A是不\", \"phone\":\"123\", \"email\":\"string123@email\", \"status\":1, \"errorCount\":4, \"lastLoginTime\":1553737022175, \"lastModifiedPassword\":1550657104216, \"sysRoles\":[ { \"id\":9, \"code\":\"config_admin\", \"name\":\"配置管理员\", \"description\":\"\" } ], \"areas\":[ { \"id\":10, \"parentId\":2, \"levelType\":2, \"fullName\":\"金牛区\", \"shortName\":\"1\", \"fullSpell\":\"1\", \"shortSpell\":\"1\", \"status\":1, \"demo\":\"\" }, { \"id\":112, \"parentId\":0, \"levelType\":1, \"fullName\":\"四川省成都市\", \"status\":1, \"demo\":\"\" } ], \"enabled\":true, \"accountNonExpired\":true, \"accountNonLocked\":true, \"credentialsNonExpired\":true } ",
                LoginUser.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, "123");
        Map<String, Object> map = new HashMap<>();
        map.put("principal", loginUser);
        token.setDetails(map);

        OAuth2Authentication authentication = Mockito.mock(OAuth2Authentication.class);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);

        SecurityContextHolder.setContext(securityContext);
        PowerMockito.mockStatic(ExcelUtils.class);

        PowerMockito.mockStatic(RequestContextHolder.class);

        PowerMockito.mockStatic(LoginUserUtil.class);
        loginUser = getLoginUser();
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any());
    }

    @Test
    public void pulldownSupplier() throws Exception {
        AssetPulldownQuery query = new AssetPulldownQuery();
        List<String> list = new ArrayList<>();
        list.add("antiy");
        Mockito.when(assetHardSoftLibDao.pulldownSupplier(Mockito.any())).thenReturn(list);
        Assert.assertEquals("antiy", assetHardSoftLibService.pulldownSupplier(query).get(0));
    }

    @Test
    public void queryHardSoftLibList() throws Exception {
        AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
        Mockito.when(assetHardSoftLibDao.queryHardSoftLibList(Mockito.any())).thenReturn(new ArrayList<>());
        Assert.assertEquals(JSONObject.toJSONString(new ArrayList<>()),
                JSONObject.toJSONString(assetHardSoftLibService.queryHardSoftLibList(query)));
    }

    @Test
    public void queryPageAssetHardSoftLib() throws Exception {
        AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
        query.setBusinessId("1");
        query.setSourceType("a");
        query.setAssetType(AssetTypeEnum.HARD);
        Mockito.when(assetHardSoftLibDao.queryHardSoftLibCount(Mockito.any())).thenReturn(0);
        Assert.assertEquals(
                JSONObject
                        .toJSONString(new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList())),
                JSONObject.toJSONString(assetHardSoftLibService.queryPageAssetHardSoftLib(query)));
    }

    @Test
    public void queryPageAssetHardSoftLib1() throws Exception {
        AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
        query.setBusinessId("1");
        query.setSourceType("a");
        query.setAssetType(AssetTypeEnum.HARD);

        Mockito.when(assetHardSoftLibDao.queryHardSoftLibCount(Mockito.any())).thenReturn(1);
        Assert.assertEquals("{\"currentPage\":1,\"items\":[],\"pageSize\":10,\"totalPages\":1,\"totalRecords\":1}",
                JSONObject.toJSONString(assetHardSoftLibService.queryPageAssetHardSoftLib(query)));
    }

    @Test
    public void queryPageAssetHardSoftLib2() throws Exception {
        AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
        query.setBusinessId("1");
        query.setSourceType("a");
        query.setAssetType(AssetTypeEnum.HARD);
        Mockito.when(assetHardSoftLibDao.queryHardSoftLibCount(Mockito.any())).thenReturn(11);
        Assert.assertEquals("{\"currentPage\":1,\"items\":[],\"pageSize\":10,\"totalPages\":2,\"totalRecords\":11}",
                JSONObject.toJSONString(assetHardSoftLibService.queryPageAssetHardSoftLib(query)));

    }

    @Test
    public void queryHardSoftLibCount() throws Exception {
        AssetHardSoftLibQuery query = new AssetHardSoftLibQuery();
        Mockito.when(assetHardSoftLibDao.queryAssetList(Mockito.any())).thenReturn(new ArrayList<>());
        Mockito.when(assetHardSoftLibDao.queryHardSoftLibCount(Mockito.any())).thenReturn(1);
        Assert.assertEquals(1 + "", assetHardSoftLibService.queryHardSoftLibCount(query) + "");
    }

    @Test
    public void pulldownName() throws Exception {
        AssetPulldownQuery query = new AssetPulldownQuery();
        List<String> list = new ArrayList<>();
        list.add("antiy");
        Mockito.when(assetHardSoftLibDao.pulldownName(Mockito.any())).thenReturn(list);
        Assert.assertEquals("antiy", assetHardSoftLibService.pulldownName(query).get(0));
    }

    @Test
    public void pulldownVersion() throws Exception {
        AssetPulldownQuery query = new AssetPulldownQuery();
        query.setName("n");
        query.setVersion("v");
        query.setSupplier("s");
        List<AssetHardSoftLib> assetHardSoftLibs = new ArrayList<>();
        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setBusinessId("1");
        assetHardSoftLib.setCpeUri("cpe:/h:xerox:copycentre_c65:1.001.02.073");
        assetHardSoftLib.setSupplier("xerox");
        assetHardSoftLib.setProductName("copycentre_c65");
        assetHardSoftLibs.add(assetHardSoftLib);
        Mockito.when(assetHardSoftLibDao.queryHardSoftLibByVersion(Mockito.any())).thenReturn(assetHardSoftLibs);
        Assert.assertEquals("1.001.02.073", assetHardSoftLibService.pulldownVersion(query).get(0).getValue());
    }

    @Test
    public void pulldownOs() {
        OsQuery query = new OsQuery();
        List<OsSelectResponse> osList = new ArrayList<>();
        Mockito.when(assetHardSoftLibDao.pullDownOs(query)).thenReturn(osList);
        assetHardSoftLibService.pullDownOs(query);
    }

    @Test
    public void getPageSoftWareList() {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setAssetId("1");
        query.setCurrentPage(1);
        query.setPageSize(10);
        Mockito.when(assetSoftwareRelationDao.countSoftwareByAssetId(Mockito.any())).thenReturn(0);
        assetHardSoftLibService.getPageSoftWareList(query);
        Mockito.when(assetSoftwareRelationDao.countSoftwareByAssetId(Mockito.any())).thenReturn(10);
        List<SoftwareResponse> result = new ArrayList<>();
        Mockito.when(assetSoftwareRelationDao.getSimpleSoftwareByAssetId(query)).thenReturn(result);
        assetHardSoftLibService.getPageSoftWareList(query);
    }

    @Test
    public void queryPageSoftTest() {
        AssetTemplateSoftwareRelationQuery query = new AssetTemplateSoftwareRelationQuery();
        query.setCurrentPage(2);
        query.setOperationSystem("hp-ux");
        Mockito.doAnswer(invocation -> {
            List<AssetInstallTemplateOsResponse> list = new ArrayList<>();
            AssetInstallTemplateOsResponse response = new AssetInstallTemplateOsResponse();
            response.setOsName("hp-ux");
            list.add(response);
            return list;
        }).when(iAssetInstallTemplateService).queryOs(Mockito.anyString());
        Mockito.doAnswer(invocation -> 0).when(assetHardSoftLibDao).queryCountSoftWares(Mockito.any());
        Assertions.assertThat(assetHardSoftLibService.queryPageSoft(query).getItems()).isEmpty();

        Mockito.doAnswer(invocation -> 1).when(assetHardSoftLibDao).queryCountSoftWares(Mockito.any());
        List expect = new ArrayList();
        Mockito.doAnswer(invocation -> expect).when(assetHardSoftLibDao).querySoftWares(Mockito.any());
        Mockito.doAnswer(invocation -> expect).when(responseConverter).convert(Mockito.anyList(), Mockito.any());
        Assertions.assertThat(assetHardSoftLibService.queryPageSoft(query).getItems()).isEmpty();

    }

    @Test
    public void querySoftsRelationsTest() {
        List list = new ArrayList();
        Mockito.doAnswer(invocation -> list).when(assetHardSoftLibDao).querySoftsRelations(Mockito.any());
        Assertions.assertThat(assetHardSoftLibService.querySoftsRelations("hp-ux")).isEmpty();

        AssetHardSoftLibResponse expect = new AssetHardSoftLibResponse();
        list.add(expect);
        Mockito.doAnswer(invocation -> list).when(responseConverter).convert(Mockito.anyList(), Mockito.any());
        Assertions.assertThat(assetHardSoftLibService.querySoftsRelations("hp-ux")).hasSize(1);
        Assertions.assertThat(assetHardSoftLibService.querySoftsRelations("hp-ux")).containsOnly(expect);
    }
}
