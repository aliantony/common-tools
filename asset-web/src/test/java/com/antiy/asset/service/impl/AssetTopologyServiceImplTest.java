package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.LoginUtil;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.IBaseDao;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
        when(RedisKeyUtil.getKeyWhenGetObject(any(), any(), any())).thenReturn("区域");
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

    @Test
    public void countTopologyOs() throws Exception {
        List<AssetCountResult> assetCountResults = new ArrayList<>();
        AssetCountResult assetCountResult = new AssetCountResult();
        assetCountResult.setCode("a");
        assetCountResult.setNum(1);
        assetCountResults.add(assetCountResult);
        Mockito.when(assetTopologyDao.countAssetByOs(Mockito.any())).thenReturn(assetCountResults);
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

    // @Test
    // public void getTopologyGraphTest() throws Exception {
    // AssetLink assetLink = generateAssetLink("2", "4");
    // assetLink.setCategoryModal(17);
    // assetLink.setParentCategoryModal(6);
    // AssetLink assetLink1 = generateAssetLink("4", "8");
    // assetLink1.setCategoryModal(16);
    // assetLink1.setParentCategoryModal(17);
    // AssetLink assetLink2 = generateAssetLink("8", "9");
    // assetLink2.setCategoryModal(4);
    // assetLink2.setParentCategoryModal(16);
    // AssetLink assetLink3 = generateAssetLink("2", "10");
    // assetLink3.setCategoryModal(6);
    // assetLink3.setParentCategoryModal(6);
    //
    // when(assetLinkRelationDao.findLinkRelation(any()))
    // .thenReturn(Arrays.asList(generateAssetLink("1", "2"), assetLink, assetLink1, assetLink2));
    // when(baseDao.getAll()).thenReturn(getAssetCategoryModelList());
    // when(assetCategoryModelDao.getNextLevelCategoryByName(any())).thenReturn(getSecondAssetCategoryModelList());
    // Assert.assertEquals("success", assetTopologyService.getTopologyGraph().getStatus());
    //
    // }
    //

}
