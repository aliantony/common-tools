package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.IBaseDao;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.SysArea;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ RedisKeyUtil.class, LoginUserUtil.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetTopologyServiceImplTest {
    @Mock
    private AssetLinkRelationDao                assetLinkRelationDao;
    @Mock
    private AssetDao                            assetDao;
    @Mock
    private AssetTopologyDao                    assetTopologyDao;
    @Spy
    private BaseConverter<Asset, AssetResponse> converter  = new BaseConverter<>();
    @Mock
    private RedisUtil                           redisUtil;
    @Spy
    private AesEncoder                          aesEncoder = new AesEncoder();
    @Mock
    private OperatingSystemClient               operatingSystemClient;
    @Mock
    private IAssetService                       iAssetService;
    @Mock
    private IBaseDao                            baseDao;
    @InjectMocks
    private AssetTopologyServiceImpl            assetTopologyService;
    private List<Double>                        middlePoint;
    private List<Double>                        cameraPos;
    private List<Double>                        targetPos;
    private Double                              firstLevelSpacing;
    private Double                              firstLevelHeight;
    private Double                              secondLevelSpacing;
    private Double                              secondLevelHeight;
    private Double                              thirdLevelSpacing;
    private Double                              thirdLevelHeight;

    @Before
    public void setUp() {
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
        PowerMockito.mockStatic(LoginUserUtil.class);
        loginUser =new LoginUser();
        loginUser.setUsername("a");
        loginUser.setAreas(Arrays.asList(new SysArea()));
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        PowerMockito.mockStatic(RedisKeyUtil.class);
        List<Double> list = Arrays.asList(1d, 2d, 3d);
        ReflectionTestUtils.setField(assetTopologyService, "middlePoint", list);
        ReflectionTestUtils.setField(assetTopologyService, "cameraPos", list);
        ReflectionTestUtils.setField(assetTopologyService, "targetPos", list);
        ReflectionTestUtils.setField(assetTopologyService, "firstLevelSpacing", 1d);
        ReflectionTestUtils.setField(assetTopologyService, "firstLevelHeight", 1d);
        ReflectionTestUtils.setField(assetTopologyService, "secondLevelSpacing", 1d);
        ReflectionTestUtils.setField(assetTopologyService, "secondLevelHeight", 1d);
        ReflectionTestUtils.setField(assetTopologyService, "thirdLevelSpacing", 1d);
        ReflectionTestUtils.setField(assetTopologyService, "thirdLevelHeight", 1d);

    }


    @Test
    public void queryAssetNodeInfoTest() throws Exception {
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setName("abc");
        List<AssetIpRelationResponse> list = new ArrayList<>();
        AssetIpRelationResponse ipRelationResponse = new AssetIpRelationResponse();
        ipRelationResponse.setIp("1");
        list.add(ipRelationResponse);
        assetResponse.setIp(list);
        assetOuterResponse.setAsset(assetResponse);
        when(iAssetService.getByAssetId(any())).thenReturn(assetOuterResponse);
        Assert.assertEquals("abc", assetTopologyService.queryAssetNodeInfo("1").getData().get(0).getAsset_name());
    }

    public AssetLink generateAssetLink(String parentId, String assetId) {
        AssetLink assetLink = new AssetLink();
        assetLink.setParentAssetId(parentId);
        assetLink.setAssetId(assetId);
        assetLink.setParentCategoryModal(2);
        assetLink.setCategoryModal(1);
        return assetLink;
    }

    public AssetLink generateAssetLink2(String parentId, String assetId) {
        AssetLink assetLink = new AssetLink();
        assetLink.setParentAssetId(parentId);
        assetLink.setAssetId(assetId);
        assetLink.setCategoryModal(2);
        assetLink.setParentCategoryModal(1);
        return assetLink;
    }

    public AssetLink generateAssetLink3(String parentId, String assetId) {
        AssetLink assetLink = new AssetLink();
        assetLink.setParentAssetId(parentId);
        assetLink.setAssetId(assetId);
        assetLink.setCategoryModal(2);
        assetLink.setParentCategoryModal(2);
        return assetLink;
    }

    @Test
    public void countAssetTopology() throws Exception {
        when(assetDao.findCountByCategoryModel(any())).thenReturn(100);
        List<AssetLink> assetLinks = new ArrayList<>();
        assetLinks.add(generateAssetLink("1", "2"));
        assetLinks.add(generateAssetLink("2", "3"));
        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(assetLinks);
        List<IdCount> idCounts = new ArrayList<>();
        idCounts.add(new IdCount("1", "2"));
        idCounts.add(new IdCount("2", "2"));
        idCounts.add(new IdCount("3", "2"));
        when(assetDao.queryAlarmCountByAssetIds(any())).thenReturn(idCounts);
        Assert.assertEquals("3", assetTopologyService.countAssetTopology().getList().get(0).get("warning"));
        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(new ArrayList<>());
        Assert.assertEquals("success", assetTopologyService.countAssetTopology().getStatus());
    }

    @Test
    public void queryGroupListTest() throws Exception {
        when(assetLinkRelationDao.queryGroupList(any())).thenReturn(Arrays.asList(generateGroup(), generateGroup()));
        Assert.assertEquals(2, assetTopologyService.queryGroupList().size());

    }

    AssetGroup generateGroup() {
        AssetGroup assetGroup = new AssetGroup();
        return assetGroup;
    }

    Asset generateAsset() {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAlarmCount("10");
        asset.setAreaId("1");
        return asset;
    }

    @Test
    public void getTopologyListTest() throws Exception {
        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), generateAssetLink("2", "3")));
        when(assetTopologyDao.findTopologyListAssetCount(any())).thenReturn(100);
        when(assetTopologyDao.findTopologyListAsset(any())).thenReturn(Arrays.asList(generateAsset(), generateAsset()));
        when(RedisKeyUtil.getKeyWhenGetObject(any(), any(), anyString())).thenReturn("区域");
        AssetQuery query = new AssetQuery();
        query.setAreaIds(new String[] { "1", "2" });
        Assert.assertEquals(2, assetTopologyService.getTopologyList(query).getData().size());
    }

    @Test
    public void getTopologyListTest1() throws Exception {
        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(new ArrayList<>());
        Assert.assertEquals("success", assetTopologyService.getTopologyList(new AssetQuery()).getStatus());

    }

    @Test
    public void getTopologyListTest2() throws Exception {
        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), generateAssetLink("2", "3")));
        when(assetTopologyDao.findTopologyListAssetCount(any())).thenReturn(0);
        Assert.assertEquals("success", assetTopologyService.getTopologyList(new AssetQuery()).getStatus());

    }

    @Test
    public void countTopologyCategoryTest() throws Exception {
        List<EnumCountResponse> enumCountResponses = new ArrayList<>();
        EnumCountResponse enumCountResponse = new EnumCountResponse();
        enumCountResponse.setStatus(new ArrayList<>());
        enumCountResponse.setMsg("a");
        enumCountResponse.setNumber(1);
        enumCountResponse.setCode(new ArrayList<>());
        enumCountResponses.add(enumCountResponse);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("key", "1");
        map.put("value", "2");
        list.add(map);
        Mockito.when(assetDao.countCategoryModel(Mockito.anyList(), Mockito.anyList())).thenReturn(list);
        Assert.assertEquals("success", assetTopologyService.countTopologyCategory().getStatus());
    }


    public BaselineCategoryModelNodeResponse generateBaselineCategoryModalNode(String name, String id,
                                                                               String parentId) {
        BaselineCategoryModelNodeResponse baselineCategoryModelNodeResponse = new BaselineCategoryModelNodeResponse();
        baselineCategoryModelNodeResponse.setName(name);
        baselineCategoryModelNodeResponse.setStringId(id);
        baselineCategoryModelNodeResponse.setParentId(parentId);
        return baselineCategoryModelNodeResponse;
    }

    @Test
    public void getAlarmTopologyTest() throws Exception {
        when(RedisKeyUtil.getKeyWhenGetObject(any(), any(), anyString())).thenReturn("区域");
        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), generateAssetLink("2", "3")));
        when(assetTopologyDao.findTopologyListAssetCount(any())).thenReturn(100);
        Asset asset1 = generateAsset();
        asset1.setId(1);
        asset1.setAreaId("1");
        Asset asset2 = generateAsset();
        asset2.setId(2);
        asset2.setAreaId("1");
        asset1.setAreaId("1");

        // 情况1
        when(assetTopologyDao.findTopologyListAsset(any())).thenReturn(Arrays.asList(asset1, asset2));
        when(assetDao.queryAlarmCountByAssetIds(any()))
            .thenReturn(Arrays.asList(new IdCount("1", "1"), new IdCount("2", "2")));
        Assert.assertEquals("success", assetTopologyService.getAlarmTopology().getStatus());

        // 情况2
        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(new ArrayList<>());
        Assert.assertEquals("success", assetTopologyService.getAlarmTopology().getStatus());

        // 情况3
        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), generateAssetLink("2", "3")));
        when(assetTopologyDao.findTopologyListAssetCount(any())).thenReturn(0);
        Assert.assertEquals("success", assetTopologyService.getAlarmTopology().getStatus());

    }

    @Test
    public void queryListByIpTest() throws Exception {
        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), generateAssetLink("2", "3")));
        when(assetTopologyDao.findTopologyListAssetCount(any())).thenReturn(100);
        Asset asset1 = generateAsset();
        asset1.setId(1);
        Asset asset2 = generateAsset();
        asset2.setId(2);
        when(assetTopologyDao.findTopologyListAsset(any())).thenReturn(Arrays.asList(asset1, asset2));
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAreaIds(new String[] { "1", "2" });
        Assert.assertEquals("success", assetTopologyService.queryListByIp(assetQuery).getStatus());

        when(assetLinkRelationDao.findLinkRelation(Mockito.any())).thenReturn(new ArrayList<>());
        Assert.assertEquals("success", assetTopologyService.queryListByIp(assetQuery).getStatus());
    }

    @Test
    public void getTopologyGraph() throws Exception {
        AssetLink assetLink = generateAssetLink("2", "4");
        AssetLink assetLink1 = generateAssetLink2("8", "4");
        AssetLink assetLink2 = generateAssetLink("8", "9");
        AssetLink assetLink3 = generateAssetLink2("10", "2");
        AssetLink assetLink4 = generateAssetLink3("12", "10");
        AssetLink assetLink5 = generateAssetLink3("10", "111");
        AssetLink assetLink6 = generateAssetLink3("10", "112");
        AssetLink assetLink7 = generateAssetLink3("10", "113");
        AssetLink assetLink8 = generateAssetLink2("50", "111");
        AssetLink assetLink9 = generateAssetLink2("50", "112");

        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), assetLink, assetLink1, assetLink2, assetLink3,
                assetLink4, assetLink5, assetLink6, assetLink7, assetLink8, assetLink9));
        Assert.assertEquals("success", assetTopologyService.getTopologyGraph().getStatus());

    }
    // @Test
    // public void getTopologyGraphTest() throws Exception {
    // when(baseDao.getAll()).thenReturn(getAssetCategoryModelList());
    // when(assetCategoryModelDao.getNextLevelCategoryByName(any())).thenReturn(getSecondAssetCategoryModelList());
    //
    // }

}
