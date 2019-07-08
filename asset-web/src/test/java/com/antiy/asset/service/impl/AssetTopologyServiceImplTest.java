package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.IBaseDao;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ RedisKeyUtil.class })
@SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetTopologyServiceImplTest {
    @Mock
    private AssetLinkRelationDao                assetLinkRelationDao;
    @Mock
    private AssetDao                            assetDao;
    @SpyBean
    private IAssetCategoryModelService          iAssetCategoryModelService;
    @Mock
    private AssetTopologyDao                    assetTopologyDao;
    @Mock
    private EmergencyClient                     emergencyClient;
    @Spy
    private BaseConverter<Asset, AssetResponse> converter  = new BaseConverter<>();
    @Mock
    private RedisUtil                           redisUtil;
    @Mock
    private AssetCategoryModelDao               assetCategoryModelDao;
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
        LoginUtil.generateDefaultLoginUser();
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
    public void queryCategoryModelsTest() {
        when(assetLinkRelationDao.queryCategoryModes()).thenReturn(Arrays.asList("1", "2"));
        Assert.assertEquals(2, assetTopologyService.queryCategoryModels().size());

    }

    @Test
    public void queryAssetNodeInfoTest() throws Exception {
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setName("abc");
        assetOuterResponse.setAsset(assetResponse);
        when(iAssetService.getByAssetId(any())).thenReturn(assetOuterResponse);
        Assert.assertEquals("abc", assetTopologyService.queryAssetNodeInfo("1").getData().get(0).getAsset_name());
    }

    public AssetLink generateAssetLink(String parentId, String assetId) {
        AssetLink assetLink = new AssetLink();
        assetLink.setParentAssetId(parentId);
        assetLink.setAssetId(assetId);
        assetLink.setCategoryModal(4);
        assetLink.setParentCategoryModal(5);
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
        asset.setAreaId("1");
        return asset;
    }

    @Test
    public void getTopologyListTest() throws Exception {
        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), generateAssetLink("2", "3")));
        when(assetTopologyDao.findTopologyListAssetCount(any())).thenReturn(100);
        when(assetTopologyDao.findTopologyListAsset(any())).thenReturn(Arrays.asList(generateAsset(), generateAsset()));
        when(RedisKeyUtil.getKeyWhenGetObject(any(), any(), any())).thenReturn("区域");
        AssetQuery query = new AssetQuery();
        query.setAreaIds(new String[] { "1", "2" });
        Assert.assertEquals(2, assetTopologyService.getTopologyList(query).getData().size());

        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(new ArrayList<>());
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
        when(iAssetService.countCategoryByStatus(any())).thenReturn(enumCountResponses);
        Assert.assertEquals("success", assetTopologyService.countTopologyCategory().getStatus());
    }

    @Test
    public void countTopologyOs() throws Exception {
        BaselineCategoryModelNodeResponse windows = generateBaselineCategoryModalNode("Windows", "1", "0");
        BaselineCategoryModelNodeResponse linux = generateBaselineCategoryModalNode("Linux", "2", "0");
        BaselineCategoryModelNodeResponse other = generateBaselineCategoryModalNode("other", "3", "0");
        BaselineCategoryModelNodeResponse windows1 = generateBaselineCategoryModalNode("Windows1", "4", "1");
        BaselineCategoryModelNodeResponse windows2 = generateBaselineCategoryModalNode("Windows2", "5", "1");
        BaselineCategoryModelNodeResponse windows3 = generateBaselineCategoryModalNode("Windows3", "6", "1");
        windows.setChildrenNode(Arrays.asList(windows1, windows2, windows3));
        when(operatingSystemClient.getInvokeOperatingSystemTree()).thenReturn(Arrays.asList(windows, linux, other));
        when(assetDao.findCountByCategoryModel(any())).thenReturn(100);
        when(assetTopologyDao.findOtherTopologyCountByCategory(any())).thenReturn(100);
        Assert.assertEquals("success", assetTopologyService.countTopologyOs().getStatus());

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
        when(RedisKeyUtil.getKeyWhenGetObject(any(), any(), any())).thenReturn("区域");
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

        when(assetTopologyDao.findTopologyListAsset(any())).thenReturn(Arrays.asList(asset1, asset2));
        when(assetDao.queryAlarmCountByAssetIds(any()))
            .thenReturn(Arrays.asList(new IdCount("1", "1"), new IdCount("2", "2")));
        Assert.assertEquals("success", assetTopologyService.getAlarmTopology().getStatus());

        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(new ArrayList<>());

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
    public void getTopologyGraphTest() throws Exception {
        AssetLink assetLink = generateAssetLink("2", "4");
        assetLink.setCategoryModal(17);
        assetLink.setParentCategoryModal(6);
        AssetLink assetLink1 = generateAssetLink("4", "8");
        assetLink1.setCategoryModal(16);
        assetLink1.setParentCategoryModal(17);
        AssetLink assetLink2 = generateAssetLink("8", "9");
        assetLink2.setCategoryModal(4);
        assetLink2.setParentCategoryModal(16);
        AssetLink assetLink3 = generateAssetLink("2", "10");
        assetLink3.setCategoryModal(6);
        assetLink3.setParentCategoryModal(6);

        when(assetLinkRelationDao.findLinkRelation(any()))
            .thenReturn(Arrays.asList(generateAssetLink("1", "2"), assetLink, assetLink1, assetLink2));
        when(baseDao.getAll()).thenReturn(getAssetCategoryModelList());
        when(assetCategoryModelDao.getNextLevelCategoryByName(any())).thenReturn(getSecondAssetCategoryModelList());
        Assert.assertEquals("success", assetTopologyService.getTopologyGraph().getStatus());

    }

    protected List<AssetCategoryModel> getSecondAssetCategoryModelList() {
        List<AssetCategoryModel> list = new ArrayList<>();

        AssetCategoryModel model3 = new AssetCategoryModel();
        model3.setName("计算设备");
        model3.setParentId("2");
        model3.setAssetType(2);
        model3.setType(1);
        model3.setIsDefault(0);
        model3.setId(4);

        AssetCategoryModel model4 = new AssetCategoryModel();
        model4.setName("网络设备");
        model4.setParentId("2");
        model4.setAssetType(2);
        model4.setType(1);
        model4.setIsDefault(0);
        model4.setId(5);

        AssetCategoryModel model1 = new AssetCategoryModel();
        model1.setName("安全设备");
        model1.setParentId("2");
        model1.setAssetType(2);
        model1.setType(1);
        model1.setIsDefault(0);
        model1.setId(6);

        AssetCategoryModel model2 = new AssetCategoryModel();
        model2.setName("其它设备");
        model2.setParentId("2");
        model2.setAssetType(2);
        model2.setType(1);
        model2.setIsDefault(0);
        model2.setId(7);

        AssetCategoryModel model5 = new AssetCategoryModel();
        model5.setName("存储设备");
        model5.setParentId("2");
        model5.setAssetType(2);
        model5.setType(1);
        model5.setIsDefault(0);
        model5.setId(8);

        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.sort((o1, o2) -> o1.getId() - o2.getId());
        return list;
    }

    protected List<AssetCategoryModel> getAssetCategoryModelList() {
        List<AssetCategoryModel> list = new ArrayList<>();
        // list.add(getAssetCategoryModel());
        AssetCategoryModel model1 = new AssetCategoryModel();
        model1.setName("交换机");
        model1.setParentId("5");
        model1.setAssetType(2);
        model1.setIsDefault(0);
        model1.setType(1);
        model1.setId(16);
        AssetCategoryModel model2 = new AssetCategoryModel();
        model2.setName("路由器");
        model2.setParentId("5");
        model2.setAssetType(2);
        model2.setType(1);
        model2.setIsDefault(0);
        model2.setId(17);

        AssetCategoryModel model3 = new AssetCategoryModel();
        model3.setName("计算设备");
        model3.setParentId("2");
        model3.setAssetType(2);
        model3.setType(1);
        model3.setIsDefault(0);

        model3.setId(4);
        AssetCategoryModel model4 = new AssetCategoryModel();
        model4.setName("网络设备");
        model4.setParentId("2");
        model4.setAssetType(2);
        model4.setType(1);
        model4.setIsDefault(0);
        model4.setId(5);

        AssetCategoryModel model5 = new AssetCategoryModel();
        model5.setName("硬件");
        model5.setParentId("1");
        model5.setAssetType(2);
        model5.setType(1);
        model5.setId(2);
        model5.setIsDefault(0);

        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        // list.add(model6);
        list.sort((o1, o2) -> o1.getId() - o2.getId());
        return list;
    }

}
