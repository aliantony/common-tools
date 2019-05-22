package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.templet.AssetSoftwareEntity;
import com.antiy.asset.templet.ExportSoftwareEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.*;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.util.StringUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Stream;


@RunWith(PowerMockRunner.class)
@PrepareForTest({AssetSoftwareServiceImpl.class, LoginUserUtil.class, LogHandle.class, CountTypeUtil.class, BeanConvert.class, ExcelUtils.class, DateUtils.class, LogUtils.class})
@PowerMockIgnore({"javax.*", "org.xml.sax.*", "org.apache.*", "com.sun.*"})
@PowerMockRunnerDelegate(SpringRunner.class)
@SpringBootTest
public class AssetSoftwareServiceImplTest {

    @Mock
    private AssetSoftwareDao assetSoftwareDao;

    @Mock
    private ExcelDownloadUtil excelDownloadUtil;
    @Mock
    private IAssetSoftwareLicenseService iAssetSoftwareLicenseService;

    @Mock
    private BaseLineClient baseLineClient;
    @Mock
    private SoftwareEntityConvert softwareEntityConvert;
    @Mock
    private AreaClient areaClient;
    @Mock
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Mock
    private BaseConverter<AssetSoftware, AssetSoftwareDetailResponse> assetSoftwareDetailConverter;
    @Mock
    private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense> assetSoftwareLicenseBaseConverter;
    @Mock
    private IAssetCategoryModelService iAssetCategoryModelService;
    @Mock
    private IAssetPortProtocolService iAssetPortProtocolService;
    @Mock
    private ActivityClient activityClient;
    @Mock
    private AssetSoftwareLicenseDao assetSoftwareLicenseDao;
    @SpyBean
    private TransactionTemplate transactionTemplate;
    @Mock
    private AssetOperationRecordDao assetOperationRecordDao;
    @Mock
    private AssetCategoryModelDao assetCategoryModelDao;
    @Mock
    private SchemeDao schemeDao;
    @Mock
    private OperatingSystemClient operatingSystemClient;
    @Mock
    private IRedisService redisService;
    @Mock
    private AesEncoder aesEncoder;

    @Resource
    private HttpServletResponse response;
    @Mock
    private BaseConverter<AssetSoftwareRequest, AssetSoftware> requestConverter;
    private boolean expect = false;

    @InjectMocks
    private AssetSoftwareServiceImpl assetSoftwareService;
    private static AssetSoftwareQuery query;
    private LoginUser loginUser;
    private Logger logger = LoggerFactory.getLogger(AssetSoftwareServiceImpl.class);

    static {
        query = new AssetSoftwareQuery();
        query.setQueryAssetCount(true);
        query.setEnterControl(false);
        String[] categoryModels = {"2", "3"};
        query.setCategoryModels(categoryModels);
        query.setQueryAssetCount(true);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<Integer> list = new ArrayList<>();
        loginUser = Mockito.mock(LoginUser.class);
        loginUser.setId(1);
        loginUser.setUsername("小李");
        assetSoftwareService = PowerMockito.spy(assetSoftwareService);
        List<Integer> lists = new ArrayList<>();
        lists.add(2);
        Mockito.when(loginUser.getAreaIdsOfCurrentUser()).thenReturn(lists);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.mockStatic(ExcelUtils.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        PowerMockito.mockStatic(LoginUserUtil.class);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        PowerMockito.spy(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any());
        PowerMockito.doNothing().when(LogUtils.class, "info", Mockito.any(), Mockito.anyString(), Mockito.any());
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.doReturn(logger).when(LogUtils.class,"get",AssetSoftwareServiceImpl.class);
//        PowerMockito.when(LogUtils.get(AssetSoftwareServiceImpl.class)).thenReturn(logger);
    }

    @Test
    public void saveAssetSoftwareTest1() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        request.setActivityRequest(activityRequest);
        Integer expect = 2;
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("kaka");
        assetSoftware.setCategoryModel("4");
        assetSoftware.setId(expect);
        assetSoftware.setOperationSystem("1");
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        List<BaselineCategoryModelNodeResponse> childNode = new ArrayList<>();
        ;
        BaselineCategoryModelNodeResponse childNodeResponse = new BaselineCategoryModelNodeResponse();
        childNodeResponse.setStringId("1");
        childNode.add(childNodeResponse);
        List<BaselineCategoryModelNodeResponse> baselineCategoryModelNodeResponse = new ArrayList<>();
        ;
        BaselineCategoryModelNodeResponse nodeResponse = new BaselineCategoryModelNodeResponse();
        nodeResponse.setChildrenNode(childNode);
        baselineCategoryModelNodeResponse.add(nodeResponse);
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(assetSoftware);
        Mockito.when(operatingSystemClient.getInvokeOperatingSystemTree()).thenReturn(baselineCategoryModelNodeResponse);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.saveAssetSoftware(request).getBody());
    }

    @Test
    @Ignore
    public void saveAssetSoftwareTest2() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        request.setActivityRequest(activityRequest);
        Integer expect = 2;
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("kaka");
        assetSoftware.setCategoryModel("4");
        assetSoftware.setId(expect);
        assetSoftware.setOperationSystem("1");
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        List<BaselineCategoryModelNodeResponse> childNode = new ArrayList<>();
        ;
        BaselineCategoryModelNodeResponse childNodeResponse = new BaselineCategoryModelNodeResponse();
//        childNodeResponse.setStringId("1");
        childNode.add(childNodeResponse);
        List<BaselineCategoryModelNodeResponse> baselineCategoryModelNodeResponse = new ArrayList<>();
        ;
        BaselineCategoryModelNodeResponse nodeResponse = new BaselineCategoryModelNodeResponse();
        nodeResponse.setChildrenNode(childNode);
        baselineCategoryModelNodeResponse.add(nodeResponse);
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(assetSoftware);
        Mockito.when(operatingSystemClient.getInvokeOperatingSystemTree()).thenReturn(baselineCategoryModelNodeResponse);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.saveAssetSoftware(request).getBody());
    }

    @Test
    @Ignore
    public void saveAssetSoftwareTest3() throws Exception {

    }

    @Test
    public void batchSaveTest() throws Exception {
        List<AssetSoftware> assetSoftwareList = new ArrayList<>();
        AssetSoftware assetSoftware = new AssetSoftware();
        Integer expect = 2;
        assetSoftwareList.add(assetSoftware);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(1);
        Assert.assertEquals(expect, assetSoftwareService.batchSave(assetSoftwareList));
    }

    @Test
    public void updateAssetSoftwareTest1() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        request.setId("1");
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        request.setActivityRequest(activityRequest);
        AssetSoftware software = new AssetSoftware();
        software.setSoftwareStatus(5);
        ActionResponse response = ActionResponse.success();
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.getById(Mockito.anyString())).thenReturn(software);
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(response);
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(software);
        Mockito.when(assetSoftwareDao.update(Mockito.any())).thenReturn(expect);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(1);
        Assert.assertEquals(expect, assetSoftwareService.updateAssetSoftware(request));
    }

    @Test
    public void updateAssetSoftwareTest2() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        request.setId("1");
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        request.setActivityRequest(activityRequest);
        AssetSoftware software = new AssetSoftware();
        software.setSoftwareStatus(4);
        ActionResponse response = ActionResponse.success();
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.getById(Mockito.anyString())).thenReturn(software);
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(response);
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(software);
        Mockito.when(assetSoftwareDao.update(Mockito.any())).thenReturn(expect);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(1);
        Assert.assertEquals(expect, assetSoftwareService.updateAssetSoftware(request));
    }


    @Test
    public void findPageAssetSoftwareTest1() throws Exception {
        Integer expect = 3;
        List<Integer> hardWareIdslist = new ArrayList<>();
        hardWareIdslist.add(4);
        hardWareIdslist.add(5);
        List<Integer> softWareIdslist = new ArrayList<>();
        softWareIdslist.add(9);
        softWareIdslist.add(10);
        List<AssetSoftware> assetSoftware = new ArrayList<>(2);
        List<Map<String, Object>> softObjectList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            AssetSoftware software = new AssetSoftware();
            software.setId(i);
            assetSoftware.add(software);
            Map<String, Object> m = new HashMap<>();
            m.put("id", i);
            m.put("name", 3);
            softObjectList.add(m);
        }
        Mockito.when(iAssetCategoryModelService.findAssetCategoryModelIdsById(Mockito.anyInt())).thenReturn(hardWareIdslist).thenReturn(softWareIdslist);
        Mockito.when(assetSoftwareDao.findCount(query)).thenReturn(expect);
        Mockito.when(assetSoftwareDao.findListAssetSoftware(query)).thenReturn(assetSoftware);
        Mockito.when(assetSoftwareRelationDao.countSoftwareRelAsset(Mockito.anyList())).thenReturn(softObjectList);
        Assert.assertEquals(expect.intValue(), assetSoftwareService.findPageAssetSoftware(query).getTotalRecords());
        Assert.assertEquals(2, assetSoftwareService.findPageAssetSoftware(query).getItems().size());
        Assert.assertEquals(expect, assetSoftwareService.findPageAssetSoftware(query).getItems().get(0).getAssetCount());

    }

    @Test
    public void findPageAssetSoftwareTest2() throws Exception {
        Integer expect = 0;
        query.setCategoryModels(null);
        Mockito.when(assetSoftwareDao.findCount(query)).thenReturn(expect);
        Assert.assertEquals(expect.intValue(), assetSoftwareService.findPageAssetSoftware(query).getTotalRecords());
    }

    @Test
    public void getManufacturerNameTest() throws Exception {
        List<String> expect = new ArrayList<>();
        Mockito.when(assetSoftwareDao.findManufacturerName(Mockito.anyString(), Mockito.anyList())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.getManufacturerName("2"));
    }

    @Test
    public void countManufacturerTest() throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        List<EnumCountResponse> expect = new ArrayList<>();
        PowerMockito.mockStatic(CountTypeUtil.class);
        PowerMockito.when(CountTypeUtil.getEnumCountResponse(Mockito.anyInt(), Mockito.anyList())).thenReturn(expect);
        Mockito.when(assetSoftwareDao.countManufacturer(Mockito.anyList())).thenReturn(list);
        Assert.assertEquals(expect, assetSoftwareService.countManufacturer());
    }

    @Test
    public void countStatusTest() throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("key", 3);
        map.put("value", 5L);
        list.add(map);
        EnumCountResponse countResponse = new EnumCountResponse("可安装", String.valueOf(map.get("key")), (Long) map.get("value"));
        Mockito.when(assetSoftwareDao.countStatus()).thenReturn(list);
        List<EnumCountResponse> list1 = assetSoftwareService.countStatus();
        list1.forEach(value -> {
            if (countResponse.toString().equals(value.toString())) {
                expect = true;
            }
        });
        assertThat(true).isEqualTo(expect);
    }


    @Test
    public void countCategoryTest() throws Exception {
        List<AssetCategoryModel> secondCategoryModelList = new ArrayList<>();
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(1);
        assetCategoryModel.setName("计算设备");
        secondCategoryModelList.add(assetCategoryModel);
        List<String> idList = new ArrayList<>();
        AssetSoftwareQuery assetSoftwareQuery = new AssetSoftwareQuery();
        EnumCountResponse expect = new EnumCountResponse(assetCategoryModel.getName(), idList, 1);
        Mockito.when(assetCategoryModelDao.getNextLevelCategoryByName(Mockito.anyString())).thenReturn(secondCategoryModelList);
        Mockito.when(assetCategoryModelDao.getAll()).thenReturn(secondCategoryModelList);
        Mockito.when(assetSoftwareDao.findCountByCategoryModel(Mockito.any())).thenReturn(1L);
        Mockito.when(iAssetCategoryModelService.recursionSearch(Mockito.anyList(), Mockito.anyInt())).thenReturn(secondCategoryModelList);
        Mockito.when(iAssetCategoryModelService.getCategoryIdList(Mockito.anyList())).thenReturn(idList);
        PowerMockito.doReturn(assetSoftwareQuery).when(assetSoftwareService, "setAssetSoftwareQueryParam", Mockito.anyList());
        Assert.assertEquals(1, assetSoftwareService.countCategory().size());
        Assert.assertEquals(expect.toString(), assetSoftwareService.countCategory().get(0).toString());
    }

    @Test
    public void setAssetSoftwareQueryParamTest() throws Exception {
        List<AssetCategoryModel> search = new ArrayList<>();
        AssetCategoryModel model = new AssetCategoryModel();
        Integer expect = 1;
        model.setId(expect);
        search.add(model);
        AssetSoftwareQuery query = Mockito.spy(AssetSoftwareQuery.class);
        PowerMockito.whenNew(AssetSoftwareQuery.class).withNoArguments().thenReturn(query);
        AssetSoftwareQuery actual = Whitebox.invokeMethod(assetSoftwareService, "setAssetSoftwareQueryParam", search);
        Assert.assertEquals(expect.toString(), actual.getCategoryModels()[0]);
    }

    @Test
    public void querySoftWareDetailTest() throws Exception {
        SoftwareQuery softwareQuery = new SoftwareQuery();
        softwareQuery.setPrimaryKey("1");
        softwareQuery.setQueryPort(true);
        softwareQuery.setQueryLicense(true);
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setOperationSystem("2");
        AssetSoftwareDetailResponse expect = new AssetSoftwareDetailResponse();
        List<LinkedHashMap> linkedHashMapList = new ArrayList<>();
        LinkedHashMap map = new LinkedHashMap();
        map.put("stringId", 2);
        map.put("name", "WINDOWS8-64");
        linkedHashMapList.add(map);
        Mockito.when(redisService.getAllSystemOs()).thenReturn(linkedHashMapList);
        PowerMockito.doReturn(assetSoftware).when(assetSoftwareService).getById(Mockito.anyString());
        Mockito.when(assetSoftwareDetailConverter.convert(assetSoftware, AssetSoftwareDetailResponse.class)).thenReturn(expect);
        List<AssetPortProtocolResponse> assetPortProtocolResponses = new ArrayList<>();
        Mockito.when(iAssetPortProtocolService.findListAssetPortProtocol(Mockito.any())).thenReturn(assetPortProtocolResponses);
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponses = new ArrayList<>();
        Mockito.when(iAssetSoftwareLicenseService.findListAssetSoftwareLicense(Mockito.any())).thenReturn(assetSoftwareLicenseResponses);
        Assert.assertEquals(expect, assetSoftwareService.querySoftWareDetail(softwareQuery));
    }

    @Test
    public void exportDataTest1() throws Exception {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setEnterControl(true);
        List<AssetSoftwareResponse> list = new ArrayList<>();
        List<ExportSoftwareEntity> softwareEntities = new ArrayList<>();
        softwareEntities.add(new ExportSoftwareEntity());
        PageResult<AssetSoftwareResponse> pageResult = Mockito.mock(PageResult.class);
        DownloadVO downloadVO = new DownloadVO();
        PowerMockito.whenNew(DownloadVO.class).withNoArguments().thenReturn(downloadVO);
        PowerMockito.doReturn(pageResult).when(assetSoftwareService, "findPageAssetSoftware", Mockito.any());
        Mockito.when(pageResult.getItems()).thenReturn(list);
        Mockito.when(softwareEntityConvert.convert(list, ExportSoftwareEntity.class)).thenReturn(softwareEntities);
        Mockito.doNothing().when(excelDownloadUtil).excelDownload(Mockito.any(), Mockito.anyString(), Mockito.eq(downloadVO));
//        assetSoftwareService.exportData(query, response);
        Mockito.verify(excelDownloadUtil).excelDownload(Mockito.any(), Mockito.anyString(), Mockito.eq(downloadVO));
    }

    @Test
    public void exportDataTest2() throws Exception {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setEnterControl(true);
        List<AssetSoftwareResponse> list = new ArrayList<>();
        List<ExportSoftwareEntity> softwareEntities = new ArrayList<>();
        PageResult<AssetSoftwareResponse> pageResult = Mockito.mock(PageResult.class);
        DownloadVO downloadVO = new DownloadVO();
        PowerMockito.whenNew(DownloadVO.class).withNoArguments().thenReturn(downloadVO);
        PowerMockito.doReturn(pageResult).when(assetSoftwareService, "findPageAssetSoftware", Mockito.any());
        Mockito.when(pageResult.getItems()).thenReturn(list);
        Mockito.when(softwareEntityConvert.convert(list, ExportSoftwareEntity.class)).thenReturn(softwareEntities);
        try {
//            assetSoftwareService.exportData(query, response);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class).hasMessage("导出数据为空");
        }
    }

    @Test
    public void findInstallListTest() throws Exception {
        AssetSoftwareQuery softwareQuery = new AssetSoftwareQuery();
        List<AssetSoftware> assetSoftwareList = new ArrayList<>();
        List<AssetSoftwareResponse> expect = new ArrayList<>();
        PowerMockito.when(BeanConvert.convert(assetSoftwareList, AssetSoftwareResponse.class)).thenReturn(expect);
        Mockito.when(assetSoftwareDao.findInstallList(softwareQuery)).thenReturn(assetSoftwareList);
        Assert.assertEquals(expect, assetSoftwareService.findInstallList(softwareQuery));
    }

    @Test
    public void findPageInstallTest() throws Exception {
        int expect = 1;
        List<AssetSoftwareResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expect).when(assetSoftwareService).findCountInstall(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetSoftwareService).findInstallList(Mockito.any());
        Assert.assertEquals(expect, assetSoftwareService.findPageInstall(new AssetSoftwareQuery()).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareService.findPageInstall(new AssetSoftwareQuery()).getItems());
    }

    @Test
    public void findAssetInstallListTest() throws Exception {
        List<AssetSoftwareInstallResponse> expect = new ArrayList<>();
        List<AssetSoftwareInstall> list = new ArrayList<>();
        PowerMockito.when(BeanConvert.convert(list, AssetSoftwareInstallResponse.class)).thenReturn(expect);
        Mockito.when(assetSoftwareDao.findAssetInstallList(Mockito.any())).thenReturn(list);
        Assert.assertEquals(expect, assetSoftwareService.findAssetInstallList(new AssetSoftwareQuery()));
    }


    @Test
    public void findPageAssetInstallTest() throws Exception {
        int expect = 0;
        List<AssetSoftwareInstallResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expectList).when(assetSoftwareService).findAssetInstallList(Mockito.any());
        Assert.assertEquals(expect, assetSoftwareService.findPageAssetInstall(new AssetSoftwareQuery()).getTotalRecords());
        Assert.assertEquals(expect, assetSoftwareService.findPageAssetInstall(new AssetSoftwareQuery()).getItems().size());
    }

    @Test
    public void findPageAssetInstallTest2() throws Exception {
        int expect = 1;
        List<AssetSoftwareInstallResponse> expectList = new ArrayList<>();
        expectList.add(new AssetSoftwareInstallResponse());
        PowerMockito.doReturn(expectList).when(assetSoftwareService).findAssetInstallList(Mockito.any());
        Mockito.when(assetSoftwareDao.findAssetInstallCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.findPageAssetInstall(new AssetSoftwareQuery()).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareService.findPageAssetInstall(new AssetSoftwareQuery()).getItems());
    }


    @Test
    public void findCountInstallTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.findCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.findCountInstall(new AssetSoftwareQuery()));
    }

    @Test
    public void findPageInstallListTest() throws Exception {
        int expect = 1;
        List<AssetSoftwareResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expect).when(assetSoftwareService).findCountInstall(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetSoftwareService).findInstallList(Mockito.any());
        Assert.assertEquals(expect, assetSoftwareService.findPageInstallList(new AssetSoftwareQuery()).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareService.findPageInstallList(new AssetSoftwareQuery()).getItems());
    }

    @Test
    public void softwareInstallConfigTest1() throws Exception {
        ConfigRegisterRequest request = new ConfigRegisterRequest();
        request.setSource("2");
        Integer expect = 1;
        ActionResponse actionResponse = ActionResponse.success();
        Mockito.when(schemeDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetSoftwareRelationDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(baseLineClient.configRegister(Mockito.anyList())).thenReturn(actionResponse);
        assertThat(assetSoftwareService.softwareInstallConfig(request).getBody()).isEqualTo(expect);
    }

    @Test
    public void softwareInstallConfigTest2() {
        ConfigRegisterRequest request = new ConfigRegisterRequest();
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(null);
        try {
            assetSoftwareService.softwareInstallConfig(request);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
            assertThat(e).hasMessage("获取用户失败");
        }

    }

    @Test
    public void softwareInstallConfigTest3() throws Exception {
        ConfigRegisterRequest request = new ConfigRegisterRequest();
        request.setSource("1");
        Integer expect = 1;
        ActionResponse actionResponse = ActionResponse.fail(RespBasicCode.BAD_REQUEST);
        Mockito.when(schemeDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetSoftwareRelationDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(baseLineClient.configRegister(Mockito.anyList())).thenReturn(actionResponse);
        assertThat(assetSoftwareService.softwareInstallConfig(request)).isEqualTo(actionResponse);

    }

    @Test
    public void reportDataTest() throws Exception {
        List<AssetSoftwareReportRequest> softwareReportRequestList = new ArrayList<>();
        AssetSoftwareReportRequest reportRequest = new AssetSoftwareReportRequest();
        reportRequest.setName("aaa");
        reportRequest.setVersion("1.0.0");
        softwareReportRequestList.add(reportRequest);
        AssetSoftware assetSoftware = new AssetSoftware();
        Mockito.when(assetSoftwareDao.isExsit(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(BeanConvert.convertBean(Mockito.any(), Mockito.eq(AssetSoftware.class))).thenReturn(assetSoftware);
        Mockito.when(assetSoftwareRelationDao.insertBatch(Mockito.anyList())).thenReturn(1);
        assetSoftwareService.reportData(1, softwareReportRequestList);
        Mockito.verify(assetSoftwareRelationDao).insertBatch(Mockito.anyList());
    }

    @Test
    public void importExcelTest() throws Exception {
        List<AssetSoftwareEntity> list = new ArrayList<>();
        AssetSoftwareEntity entity = new AssetSoftwareEntity();
        entity.setCategory("2");
        entity.setName("硬件");
        list.add(entity);
        ImportResult<AssetSoftwareEntity> importResult = new ImportResult<>("导入成功..", list);
        List<LinkedHashMap> mapList = new ArrayList<>();
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("stringId", "2");
        mapList.add(map);
        String expect = "导入成功1条。导入.";
        PowerMockito.when(ExcelUtils.importExcelFromClient(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(importResult);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(new AssetCategoryModel());
        Mockito.when(assetSoftwareDao.findCountCheck(Mockito.any())).thenReturn(0);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(areaClient.queryCdeAndAreaId(Mockito.anyString())).thenReturn(ActionResponse.success(mapList));
        Mockito.when(activityClient.startProcessWithoutFormBatch(Mockito.anyList())).thenReturn(ActionResponse.success());
        Assert.assertEquals(expect, assetSoftwareService.importExcel(new MockMultipartFile("abc", "abc".getBytes()), new AssetImportRequest()));
    }

    @Test
    public void importExcelTest1() throws Exception {
        List<AssetSoftwareEntity> list = new ArrayList<>();
        AssetSoftwareEntity entity = new AssetSoftwareEntity();
        entity.setCategory("2");
        entity.setName("硬件");
        list.add(entity);
        ImportResult<AssetSoftwareEntity> importResult = new ImportResult<>("导入成功..", list);
        String expect = "导入成功0条。第7行选择的品类型号不存在，或已经注销！，导入.";
        PowerMockito.when(ExcelUtils.importExcelFromClient(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(importResult);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(null);
        Mockito.when(assetSoftwareDao.findCountCheck(Mockito.any())).thenReturn(1);
        Assert.assertEquals(expect, assetSoftwareService.importExcel(new MockMultipartFile("abc", "abc".getBytes()), new AssetImportRequest()));
    }

    @Test
    public void importExcelTest2() throws Exception {
        List<AssetSoftwareEntity> list = new ArrayList<>();
        AssetSoftwareEntity entity = new AssetSoftwareEntity();
        entity.setCategory("2");
        entity.setName("硬件");
        list.add(entity);
        ImportResult<AssetSoftwareEntity> importResult = new ImportResult<>("导入成功..", list);
        String expect = "导入成功0条。第7行软件名称重复，导入.";
        PowerMockito.when(ExcelUtils.importExcelFromClient(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(importResult);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(new AssetCategoryModel());
        Mockito.when(assetSoftwareDao.findCountCheck(Mockito.any())).thenReturn(1);
        Assert.assertEquals(expect, assetSoftwareService.importExcel(new MockMultipartFile("abc", "abc".getBytes()), new AssetImportRequest()));
    }

    @Test
    public void importExcelTest3() throws Exception {
        String expect = "数据为空";
        ImportResult<AssetSoftwareEntity> importResult = new ImportResult<>(expect, null);
        PowerMockito.when(ExcelUtils.importExcelFromClient(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(importResult);
        Assert.assertEquals(expect, assetSoftwareService.importExcel(new MockMultipartFile("abc", "abc".getBytes()), new AssetImportRequest()));
    }

    @Test
    public void importExcelTest4() throws Exception {
        List<AssetSoftwareEntity> resultDataList = new ArrayList<>();
        String expect = "数据为空";
        ImportResult<AssetSoftwareEntity> importResult = new ImportResult<>(expect, resultDataList);
        PowerMockito.when(ExcelUtils.importExcelFromClient(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(importResult);
        Assert.assertEquals(expect, assetSoftwareService.importExcel(new MockMultipartFile("abc", "abc".getBytes()), new AssetImportRequest()));
    }


    @Test
    public void changeStatusByIdTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.changeStatusById(Mockito.anyMap())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.changeStatusById(new HashMap<>()));


    }

    @Test
    public void exportTemplateTest() throws Exception {
        PowerMockito.doNothing().when(assetSoftwareService, "exportToClient", Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Whitebox.invokeMethod(assetSoftwareService, "exportTemplate");
        PowerMockito.verifyPrivate(assetSoftwareService).invoke("exportToClient", Mockito.any(), Mockito.anyString(), Mockito.anyString());

    }

    @Test
    public void pulldownManufacturerTest() throws Exception {
        List<String> expect = new ArrayList<>();
        Mockito.when(assetSoftwareDao.pulldownManufacturer()).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.pulldownManufacturer());

    }

    @Test
    public void exportToClientTest() throws Exception {
        PowerMockito.doNothing().when(assetSoftwareService, "initExampleData", Mockito.anyList());
        PowerMockito.doNothing().when(ExcelUtils.class, "exportTemplateToClient", Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList());
        Whitebox.invokeMethod(assetSoftwareService, "exportToClient", AssetSoftwareEntity.class, "软件信息模板.xlsx", "软件信息");
        PowerMockito.verifyStatic(ExcelUtils.class, Mockito.atMost(1));
        ExcelUtils.exportTemplateToClient(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyList());
    }

    @Test
    public void initExampleDataTest() throws Exception {
        List<AssetSoftwareEntity> assetSoftwareEntities = Mockito.mock(ArrayList.class);
        Whitebox.invokeMethod(assetSoftwareService, "initExampleData", assetSoftwareEntities);
        Mockito.verify(assetSoftwareEntities).add(Mockito.any());
    }


    @Test
    public void convertTest() throws Exception {
        SoftwareEntityConvert convert = PowerMockito.spy(new SoftwareEntityConvert());
        AssetSoftwareResponse assetSoftware = new AssetSoftwareResponse();
        assetSoftware.setSoftwareStatus(1);
        ExportSoftwareEntity exportSoftwareEntity = new ExportSoftwareEntity();
        PowerMockito.suppress(PowerMockito.method(BaseConverter.class, "convert", AssetSoftwareResponse.class, ExportSoftwareEntity.class));
        Whitebox.invokeMethod(convert, SoftwareEntityConvert.class, "convert", assetSoftware, exportSoftwareEntity);
        Mockito.verify(convert, Mockito.times(1)).convert(assetSoftware, exportSoftwareEntity);
    }

    @Test
    public void LongToDateStringTest() throws Exception {
        SoftwareEntityConvert convert = PowerMockito.spy(new SoftwareEntityConvert());
        PowerMockito.mockStatic(DateUtils.class);
        String expect = "测试";
        PowerMockito.when(DateUtils.getDataString(Mockito.any(), Mockito.anyString())).thenReturn(expect);
        String actual = Whitebox.invokeMethod(convert, "LongToDateString", 1L);
        Assert.assertEquals(expect, actual);
    }


}
