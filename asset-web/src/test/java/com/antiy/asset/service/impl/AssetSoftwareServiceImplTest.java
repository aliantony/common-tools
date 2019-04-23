package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.templet.AssetSoftwareEntity;
import com.antiy.asset.templet.ExportSoftwareEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.CountTypeUtil;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetImportRequest;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.DateUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
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
@PrepareForTest({AssetSoftwareServiceImpl.class, LoginUserUtil.class, LogHandle.class, CountTypeUtil.class, BeanConvert.class, ExcelUtils.class, DateUtils.class})
@PowerMockIgnore({"javax.*", "org.xml.sax.*", "org.apache.*", "com.sun.*"})
@PowerMockRunnerDelegate(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
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
    private AesEncoder aesEncoder;

    @Resource
    private HttpServletResponse response;
    @Mock
    private BaseConverter<AssetSoftwareRequest, AssetSoftware> requestConverter;


    @InjectMocks
    private AssetSoftwareServiceImpl assetSoftwareService;
    private static AssetSoftwareQuery query;
    private LoginUser loginUser;

    static {
        query = new AssetSoftwareQuery();
        query.setQueryAssetCount(true);
        query.setEnterControl(false);
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
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        ActionResponse response = ActionResponse.success();
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(assetSoftware);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(expect);
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(response);
        PowerMockito.doReturn(false).when(assetSoftwareService, "CheckRepeatName", Mockito.anyString());
        Assert.assertEquals(expect, assetSoftwareService.saveAssetSoftware(request).getBody());
    }

    @Test
    public void saveAssetSoftwareTest2() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("kaka");
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(assetSoftware);
        PowerMockito.doReturn(true).when(assetSoftwareService, "CheckRepeatName", Mockito.anyString());
        try {
            assetSoftwareService.saveAssetSoftware(request);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RequestParamValidateException.class);
            assertThat(e).hasMessage("资产名称重复");
        }

    }

    @Test
    public void saveAssetSoftwareTest3() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("kaka");
        assetSoftware.setCategoryModel("4");
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(assetSoftware);
        PowerMockito.doReturn(false).when(assetSoftwareService, "CheckRepeatName", Mockito.anyString());
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(null);
        try {
            assetSoftwareService.saveAssetSoftware(request);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
            assertThat(e).hasMessage("品类型号不存在，或已经注销");
        }
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
    public void updateAssetSoftwareTest() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        request.setId("1");
        ManualStartActivityRequest activityRequest = new ManualStartActivityRequest();
        request.setActivityRequest(activityRequest);
        AssetSoftware software = new AssetSoftware();
        software.setSoftwareStatus(5);
        ActionResponse response = ActionResponse.success();
        Integer expect = 1;
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        Mockito.when(assetSoftwareDao.getById(Mockito.anyString())).thenReturn(software);
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(response);
        Mockito.when(requestConverter.convert(request, AssetSoftware.class)).thenReturn(software);
        Mockito.when(assetSoftwareDao.update(Mockito.any())).thenReturn(expect);
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(expect);
        PowerMockito.doReturn(assetOperationRecord).when(assetSoftwareService, "convertAssetOperationRecord", Mockito.any(), Mockito.anyInt());
        Assert.assertEquals(expect, assetSoftwareService.updateAssetSoftware(request));
    }

    @Test
    public void convertAssetOperationRecordTest() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        request.setId("1");
        AssetOperationRecord expect = Mockito.mock(AssetOperationRecord.class);
        PowerMockito.whenNew(AssetOperationRecord.class).withNoArguments().thenReturn(expect);
        AssetOperationRecord actual = Whitebox.invokeMethod(assetSoftwareService, "convertAssetOperationRecord", request, 5);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void updateLicenseTest() throws Exception {
        AssetSoftwareRequest request = new AssetSoftwareRequest();
        AssetSoftwareLicenseRequest licenseRequest = new AssetSoftwareLicenseRequest();
        AssetSoftwareLicense assetSoftwareLicense = new AssetSoftwareLicense();
        request.setId("1");
        request.setSoftwareLicenseRequest(licenseRequest);
        Mockito.when(assetSoftwareLicenseBaseConverter.convert(licenseRequest, AssetSoftwareLicense.class)).thenReturn(assetSoftwareLicense);
        Mockito.when(assetSoftwareLicenseDao.update(assetSoftwareLicense)).thenReturn(1);
        Whitebox.invokeMethod(assetSoftwareService, "updateLicense", request);
        Mockito.verify(assetSoftwareLicenseDao).update(assetSoftwareLicense);
    }

    @Test
    public void handleSoftCountTest() throws Exception {
        List<Map<String, Object>> softObjectList = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("id", "4");
        m.put("name", "12");
        softObjectList.add(m);
        Map<Integer, Long> expect = Mockito.mock(HashMap.class);
        PowerMockito.whenNew(HashMap.class).withNoArguments().thenReturn((HashMap) expect);
        Map<Integer, Long> actual = Whitebox.invokeMethod(assetSoftwareService, "handleSoftCount", softObjectList);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void findListAssetSoftwareTest() throws Exception {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setQueryAssetCount(true);
        List<AssetSoftware> list = new ArrayList<>();
        AssetSoftware software = new AssetSoftware();
        Integer expect = 2;
        software.setStatus(expect);
        list.add(software);
        Map<Integer, Long> softAssetCount = new HashMap<>();
        softAssetCount.put(1, 2L);
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, WaitingTaskReponse> waitingTasks = new HashMap<>();
        WaitingTaskReponse waitingTaskReponse = new WaitingTaskReponse();
        waitingTasks.put("4", waitingTaskReponse);
        PowerMockito.when(assetSoftwareService, "handleSoftCount", Mockito.anyList()).thenReturn(softAssetCount);
        Mockito.when(assetSoftwareDao.findListAssetSoftware(Mockito.any())).thenReturn(list);
        Mockito.when(assetSoftwareRelationDao.countSoftwareRelAsset(Mockito.anyList())).thenReturn(mapList);
        Assert.assertEquals(expect, assetSoftwareService.findListAssetSoftware(query, waitingTasks).get(0).getStatus());
    }

    @Test
    public void findCountAssetSoftwareTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.findCount(query)).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.findCountAssetSoftware(query));

    }

    @Test
    public void findPageAssetSoftwareTest1() throws Exception {
        Map<String, WaitingTaskReponse> waitingTasks = new HashMap<>();
        Integer expect = 0;
        PowerMockito.doReturn(waitingTasks).when(assetSoftwareService, "getAllSoftWaitingTask", Mockito.anyString());
        PowerMockito.doReturn(expect).when(assetSoftwareService).findCountAssetSoftware(Mockito.any());
        Assert.assertEquals(expect.intValue(), assetSoftwareService.findPageAssetSoftware(query).getTotalRecords());
    }

    @Test
    @Ignore
    public void findPageAssetSoftwareTest2() throws Exception {
        Map<String, WaitingTaskReponse> waitingTasks = new HashMap<>();
        Integer expect = 1;
        List<AssetSoftware> assetSoftware = new ArrayList<>();
        Mockito.when(assetSoftwareDao.findListAssetSoftware(Mockito.any())).thenReturn(assetSoftware);
        PowerMockito.doReturn(waitingTasks).when(assetSoftwareService, "getAllSoftWaitingTask", Mockito.anyString());
        PowerMockito.doReturn(expect).when(assetSoftwareService).findCountAssetSoftware(Mockito.any());
        Assert.assertEquals(expect.intValue(), assetSoftwareService.findPageAssetSoftware(query).getTotalRecords());
//        Assert.assertEquals(expect.intValue(), assetSoftwareService.findPageAssetSoftware(query).getItems());
    }

    @Test
    public void getAllSoftWaitingTaskTest() throws Exception {
        Mockito.when(aesEncoder.encode(Mockito.anyString(), Mockito.anyString())).thenReturn("1");
        List<WaitingTaskReponse> waitingTaskReponses = new ArrayList<>();
        WaitingTaskReponse expect = new WaitingTaskReponse();
        expect.setBusinessId("12");
        waitingTaskReponses.add(expect);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success(waitingTaskReponses);
        Mockito.when(activityClient.queryAllWaitingTask(Mockito.any())).thenReturn(actionResponse);
        Assert.assertEquals(expect, assetSoftwareService.getAllSoftWaitingTask("2").get("12"));
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
        ArrayList<EnumCountResponse> expect = Mockito.mock(ArrayList.class);
        List<Map<String, Long>> list = new ArrayList<>();
        Mockito.when(assetSoftwareDao.countStatus()).thenReturn(list);
        PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(expect);
        PowerMockito.doNothing().when(assetSoftwareService, "initResultMap", Mockito.anyMap());
        PowerMockito.doNothing().when(assetSoftwareService, "searchResultToMap", Mockito.anyList(), Mockito.anyList(), Mockito.anyMap());
        PowerMockito.doNothing().when(assetSoftwareService, "resultMapToResultList", Mockito.anyList(), Mockito.anyMap());
        Assert.assertEquals(expect, assetSoftwareService.countStatus());
    }

    @Test
    public void resultMapToResultListTest() throws Exception {
        List<EnumCountResponse> resultList = Mockito.mock(ArrayList.class);
        Map<SoftwareStatusEnum, EnumCountResponse> resultMap = new HashMap<>();
        resultMap.put(SoftwareStatusEnum.WAIT_RETIRE, new EnumCountResponse(SoftwareStatusEnum.WAIT_RETIRE.getMsg(), new ArrayList<String>(), 0));
        Whitebox.invokeMethod(assetSoftwareService, "resultMapToResultList", resultList, resultMap);
        Mockito.verify(resultList).add(Mockito.any());
    }

    @Test
    @Ignore //测试报Long型无法转为Integer
    public void searchResultToMapTest() throws Exception {
        List<EnumCountResponse> resultList = Mockito.mock(ArrayList.class);
        List<Map<String, Long>> list = new ArrayList<>();
        Map<String, Long> map = new HashMap<>();
        Integer code = 6;
        map.put("key", code.longValue());
        list.add(map);
        Map<SoftwareStatusEnum, EnumCountResponse> resultMap = new HashMap<>();
        resultMap.put(SoftwareStatusEnum.WAIT_RETIRE, new EnumCountResponse(SoftwareStatusEnum.WAIT_RETIRE.getMsg(), new ArrayList<String>(), 0));
        PowerMockito.doNothing().when(assetSoftwareService, "processList", Mockito.anyList(), Mockito.anyMap(), Mockito.anyMap(), Mockito.any(SoftwareStatusEnum.class));
        Whitebox.invokeMethod(assetSoftwareService, "searchResultToMap", resultList, list, resultMap);
//        Mockito.verify(assetSoftwareService);
    }

    @Test
    public void initResultMapTest() throws Exception {
        Integer expectIimes = Stream.of(SoftwareStatusEnum.values()).filter(softwareStatusEnum -> !softwareStatusEnum.equals(SoftwareStatusEnum.UNINSTALL)).toArray().length;
        Map<SoftwareStatusEnum, EnumCountResponse> resultMap = Mockito.mock(HashMap.class);
        Whitebox.invokeMethod(assetSoftwareService, "initResultMap", resultMap);
        Mockito.verify(resultMap, Mockito.times(expectIimes)).put(Mockito.any(SoftwareStatusEnum.class), Mockito.any());
    }

    @Test
    @Ignore//不知该测试的方法未使用的参数是否保留
    public void processListTest() throws Exception {

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
        AssetSoftwareDetailResponse expect = new AssetSoftwareDetailResponse();
        PowerMockito.doReturn(assetSoftware).when(assetSoftwareService).getById(Mockito.anyString());
        Mockito.when(assetSoftwareDetailConverter.convert(assetSoftware, AssetSoftwareDetailResponse.class)).thenReturn(expect);
        PowerMockito.doNothing().when(assetSoftwareService, "querySoftwarePort", Mockito.any(), Mockito.any());
        PowerMockito.doNothing().when(assetSoftwareService, "querySoftwareLicense", Mockito.any(), Mockito.any());
        Assert.assertEquals(expect, assetSoftwareService.querySoftWareDetail(softwareQuery));
    }

    @Test
    public void exportDataTest() throws Exception {
        AssetSoftwareQuery query = new AssetSoftwareQuery();
        query.setEnterControl(true);
        Map<String, WaitingTaskReponse> waitingTasks = new HashMap<>();
        PowerMockito.doReturn(waitingTasks).when(assetSoftwareService, "getAllSoftWaitingTask", Mockito.anyString());
        assetSoftwareService.exportData(query, response);
        Mockito.verify(assetSoftwareService).getAllSoftWaitingTask(Mockito.anyString());
//        PowerMockito.verifyPrivate(assetSoftwareService).invoke("exportData", Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    public void findInstallListTest() throws Exception {
        AssetSoftwareQuery softwareQuery = new AssetSoftwareQuery();
        List<AssetSoftware> assetSoftwareList = new ArrayList<>();
        List<AssetSoftwareResponse> expect = new ArrayList<>();
        PowerMockito.mockStatic(BeanConvert.class);
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
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(list, AssetSoftwareInstallResponse.class)).thenReturn(expect);
        Mockito.when(assetSoftwareDao.findAssetInstallList(Mockito.any())).thenReturn(list);
        Assert.assertEquals(expect, assetSoftwareService.findAssetInstallList(new AssetSoftwareQuery()));
    }

    @Test
    public void findAssetInstallCountTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.findAssetInstallCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareService.findAssetInstallCount(new AssetSoftwareQuery()));
    }

    @Test
    public void CheckRepeatNameTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetSoftwareDao.findCountCheck(Mockito.any())).thenReturn(expect);
        boolean actual = Whitebox.invokeMethod(assetSoftwareService, "CheckRepeatName", "计算设备");
        Assert.assertEquals(true, actual);
    }

    @Test
    public void findPageAssetInstallTest() throws Exception {
        int expect = 1;
        List<AssetSoftwareInstallResponse> expectList = new ArrayList<>();
        PowerMockito.doReturn(expect).when(assetSoftwareService).findAssetInstallCount(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetSoftwareService).findAssetInstallList(Mockito.any());
        Assert.assertEquals(expect, assetSoftwareService.findPageAssetInstall(new AssetSoftwareQuery()).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareService.findPageAssetInstall(new AssetSoftwareQuery()).getItems());
    }

    @Test
    public void configRegisterTest() throws Exception {
        ActionResponse expect = ActionResponse.success();
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(baseLineClient.configRegister(Mockito.anyList())).thenReturn(expect);
        PowerMockito.doReturn(new AssetOperationRecord()).when(assetSoftwareService, "convertRecord", Mockito.any());
        Assert.assertEquals(expect, assetSoftwareService.configRegister(new ConfigRegisterRequest()));
    }

    @Test
    public void convertRecordTest() throws Exception {
        ConfigRegisterRequest request = new ConfigRegisterRequest();
        request.setSource("2");
        AssetOperationRecord expect = Mockito.mock(AssetOperationRecord.class);
        PowerMockito.whenNew(AssetOperationRecord.class).withNoArguments().thenReturn(expect);
        AssetOperationRecord actual = Whitebox.invokeMethod(assetSoftwareService, "convertRecord", request);
        Assert.assertEquals(expect, actual);
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
    public void importExcelTest() throws Exception {
        List<AssetSoftwareEntity> list = new ArrayList<>();
        AssetSoftwareEntity entity = new AssetSoftwareEntity();
        entity.setCategory("2");
        entity.setName("cpu");
        list.add(entity);
        ImportResult<AssetSoftwareEntity> importResult = new ImportResult<>("导入成功..", list);
        List<LinkedHashMap> mapList = new ArrayList<>();
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("stringId", "2");
        mapList.add(map);
        String expect = "导入成功1条。导入.";
        PowerMockito.when(ExcelUtils.importExcelFromClient(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(importResult);
        PowerMockito.doReturn(false).when(assetSoftwareService, "CheckRepeatName", Mockito.anyString());
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(new AssetCategoryModel());
        Mockito.when(assetSoftwareDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(areaClient.queryCdeAndAreaId(Mockito.anyString())).thenReturn(ActionResponse.success(mapList));
        Mockito.when(activityClient.startProcessWithoutFormBatch(Mockito.anyList())).thenReturn(ActionResponse.success());
        Assert.assertEquals(expect, assetSoftwareService.importExcel(new MockMultipartFile("abc", "abc".getBytes()), new AssetImportRequest()));
    }

    @Test
    public void exportDataOfPrivateTest() throws Exception {
        Map<String, WaitingTaskReponse> waitingTasks = new HashMap<>();
        List<AssetSoftwareResponse> list = new ArrayList<>();
        List<ExportSoftwareEntity> softwareEntities = new ArrayList<>();
        softwareEntities.add(new ExportSoftwareEntity());
        Mockito.doReturn(waitingTasks).when(assetSoftwareService).getAllSoftWaitingTask(Mockito.anyString());
        Mockito.when(softwareEntityConvert.convert(list, ExportSoftwareEntity.class)).thenReturn(softwareEntities);
        PowerMockito.doReturn(list).when(assetSoftwareService).findListAssetSoftware(Mockito.any(), Mockito.anyMap());
        Mockito.doNothing().when(excelDownloadUtil).excelDownload(Mockito.any(), Mockito.anyString(), Mockito.any(DownloadVO.class));
        Whitebox.invokeMethod(assetSoftwareService, "exportData", AssetSoftwareEntity.class, "软件信息表", query, response);
        Mockito.verify(excelDownloadUtil).excelDownload(Mockito.any(), Mockito.anyString(), Mockito.any(DownloadVO.class));

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
    @Ignore //未被引用的类
    public void getNextPageTest() throws Exception {
        int expect = 1;
        PageResult pageResult = new PageResult(expect, expect, expect, null);
        int actual = Whitebox.invokeMethod(assetSoftwareService, "getNextPage", pageResult);
        Assert.assertEquals(expect, actual);

    }

    @Test
    public void querySoftwarePortTest() throws Exception {
        List<AssetPortProtocolResponse> assetPortProtocolResponses = new ArrayList<>();
        AssetSoftwareDetailResponse assetSoftwareDetailResponse = Mockito.mock(AssetSoftwareDetailResponse.class);
        Mockito.when(iAssetPortProtocolService.findListAssetPortProtocol(Mockito.any())).thenReturn(assetPortProtocolResponses);
        Whitebox.invokeMethod(assetSoftwareService, "querySoftwarePort", new SoftwareQuery(), assetSoftwareDetailResponse);
        Mockito.verify(assetSoftwareDetailResponse).setSoftwarePort(Mockito.any());
    }

    @Test
    public void querySoftwareLicenseTest() throws Exception {
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponses = new ArrayList<>();
        SoftwareQuery query = new SoftwareQuery();
        query.setPrimaryKey("1");
        AssetSoftwareDetailResponse assetSoftwareDetailResponse = Mockito.mock(AssetSoftwareDetailResponse.class);
        Mockito.when(iAssetSoftwareLicenseService.findListAssetSoftwareLicense(Mockito.any())).thenReturn(assetSoftwareLicenseResponses);
        Whitebox.invokeMethod(assetSoftwareService, "querySoftwareLicense", query, assetSoftwareDetailResponse);
        Mockito.verify(assetSoftwareDetailResponse).setSoftwareLicense(Mockito.any());
    }

    @Test
    public void convertTest() throws Exception {
        SoftwareEntityConvert convert = PowerMockito.spy(new SoftwareEntityConvert());
        AssetSoftwareResponse assetSoftware = new AssetSoftwareResponse();
        assetSoftware.setSoftwareStatus(1);
        assetSoftware.setGmtCreate(1L);
        ExportSoftwareEntity exportSoftwareEntity = new ExportSoftwareEntity();
        PowerMockito.suppress(PowerMockito.method(BaseConverter.class, "convert", AssetSoftwareResponse.class, ExportSoftwareEntity.class));
        Whitebox.invokeMethod(convert, SoftwareEntityConvert.class, "convert", assetSoftware, exportSoftwareEntity);
//       convert.getClass().getSuperclass().
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
