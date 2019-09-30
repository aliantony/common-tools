package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.*;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.enums.AssetCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.base.BaseResponse;
import com.antiy.common.base.SysArea;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.google.common.collect.Lists;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
                  LogHandle.class, ZipUtil.class })
// @SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })

public class AssetServiceImplTest {
    @Mock
    AssetDao                                                                    assetDao;

    @Mock
    AssetNetworkEquipmentDao                                                    assetNetworkEquipmentDao;
    @Mock
    AssetSafetyEquipmentDao                                                     assetSafetyEquipmentDao;
    @Mock
    AssetSoftwareDao                                                            assetSoftwareDao;
    @Mock
    AssetSoftwareLicenseDao                                                     assetSoftwareLicenseDao;

    @Mock
    TransactionTemplate                                                         transactionTemplate;
    @Mock
    AssetSoftwareRelationDao                                                    assetSoftwareRelationDao;
    @Mock
    AssetStorageMediumDao                                                       assetStorageMediumDao;
    @Mock
    AssetOperationRecordDao                                                     assetOperationRecordDao;
    @Mock
    BaseConverter<AssetRequest, Asset>                                          requestConverter;
    @Spy
    BaseConverter<Asset, AssetRequest>                                          assetToRequestConverter;
    @Spy
    BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationRequest>          softRelationToRequestConverter;

    @Spy
    BaseConverter<Asset, AssetResponse>                                         responseConverter;
    @Spy
    BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentRequest>            safetyEquipmentToRequestConverter;
    @Spy
    BaseConverter<AssetStorageMedium, AssetStorageMediumRequest>                storageMediumToRequestConverter;
    @Spy
    BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentRequest>          networkEquipmentToRequestConverter;
    @Mock
    AssetUserDao                                                                assetUserDao;
    @Mock
    AssetGroupRelationDao                                                       assetGroupRelationDao;

    @Mock
    ExcelDownloadUtil                                                           excelDownloadUtil;
    @Spy
    AssetEntityConvert                                                          assetEntityConvert;

    @Mock
    AssetGroupDao                                                               assetGroupDao;

    @Mock
    ActivityClient                                                              activityClient;

    @Mock
    AreaClient                                                                  areaClient;
    @Mock
    Logger                                                                      logger;
    @Mock
    AesEncoder                                                                  aesEncoder;
    @Mock
    RedisUtil                                                                   redisUtil;
    @Mock
    AssetLinkRelationDao                                                        assetLinkRelationDao;
    @Mock
    OperatingSystemClient                                                       operatingSystemClient;
    @Mock
    EmergencyClient                                                             emergencyClient;
    @Mock
    IBaseDao                                                                    baseDao;
    @Spy
    @InjectMocks
    AssetServiceImpl                                                            assetServiceImpl;
    @Mock
    IRedisService                                                               redisService;
    @Mock
    AssetClient                                                                 assetClient;
    @Spy
    private BaseConverter<AssetAssembly, AssetAssemblyResponse>                 assemblyResponseBaseConverter;
    @Spy
    private BaseConverter<AssetGroup, AssetGroupResponse>                       assetGroupResponseBaseConverter;
    @Mock
    private AssetIpRelationDao                                                  assetIpRelationDao;
    @Mock
    private AssetMacRelationDao                                                 assetMacRelationDao;
    @Spy
    private BaseConverter<AssetIpRelation, AssetIpRelationResponse>             ipResponseConverter;
    @Spy
    private BaseConverter<AssetMacRelation, AssetMacRelationResponse>           macResponseConverter;
    @Spy
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> networkResponseConverter;
    @Spy
    private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentResponse>   safetyResponseConverter;
    @Spy
    private BaseConverter<AssetStorageMedium, AssetStorageMediumResponse>       storageResponseConverter;

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
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);

        SecurityContextHolder.setContext(securityContext);
        PowerMockito.mockStatic(ExcelUtils.class);

        PowerMockito.mockStatic(RequestContextHolder.class);

        PowerMockito.mockStatic(LoginUserUtil.class);
        loginUser = getLoginUser();
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        PowerMockito.mockStatic(LicenseUtil.class);

        // PowerMockito.when(LicenseUtil.getLicense().getAssetNum()).thenReturn(100);
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setAssetNum(100);
        PowerMockito.when(LicenseUtil.getLicense()).thenReturn(licenseContent);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any());

        when(transactionTemplate.execute(Mockito.<TransactionCallback> any())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TransactionCallback arg = (TransactionCallback) args[0];
                return arg.doInTransaction(new SimpleTransactionStatus());
            }
        });

    }

    private LoginUser getLoginUser() {
        LoginUser loginUser;
        loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("小李");
        List<SysArea> areaList = new ArrayList<>();
        SysArea sysArea = new SysArea();
        sysArea.setFullName("四川");
        sysArea.setId(1);
        areaList.add(sysArea);
        loginUser.setAreas(areaList);
        return loginUser;
    }

    private AssetNetworkCardRequest generateAssetNetworkCardRequest() {
        AssetNetworkCardRequest assetNetworkCardRequest = new AssetNetworkCardRequest();
        assetNetworkCardRequest.setAssetId("1");
        assetNetworkCardRequest.setBrand("1");
        assetNetworkCardRequest.setIpAddress("1");
        assetNetworkCardRequest.setModel("1");
        assetNetworkCardRequest.setId("1");
        return assetNetworkCardRequest;
    }

    private AssetNetworkEquipment generateAssetNetworkEquipment() {
        AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
        assetNetworkEquipment.setAssetId("1");
        assetNetworkEquipment.setId(1);
        assetNetworkEquipment.setPortSize(1);

        return assetNetworkEquipment;
    }

    private AssetSafetyEquipmentRequest generateAssetSafetyEquipmentRequest() {
        AssetSafetyEquipmentRequest assetSafetyEquipmentRequest = new AssetSafetyEquipmentRequest();
        assetSafetyEquipmentRequest.setId("1");
        assetSafetyEquipmentRequest.setAssetId("1");
        assetSafetyEquipmentRequest.setFeatureLibrary("1");
        assetSafetyEquipmentRequest.setNewVersion("1");
        assetSafetyEquipmentRequest.setStrategy("1");
        return assetSafetyEquipmentRequest;
    }

    private AssetSafetyEquipment generateAssetSafetyEquipment() {
        AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
        assetSafetyEquipment.setId(1);
        assetSafetyEquipment.setAssetId("1");
        assetSafetyEquipment.setFeatureLibrary("1");
        assetSafetyEquipment.setNewVersion("1");
        assetSafetyEquipment.setStrategy("1");
        return assetSafetyEquipment;
    }

    private List<AssetSafetyEquipment> generateAssetSafetyEquipmentList() {
        List<AssetSafetyEquipment> result = new ArrayList<>();
        result.add(generateAssetSafetyEquipment());
        return result;
    }

    private AssetNetworkEquipmentRequest genrateAssetNetworkEquipmentRequest() {
        AssetNetworkEquipmentRequest assetNetworkEquipment = new AssetNetworkEquipmentRequest();
        assetNetworkEquipment.setAssetId("1");
        assetNetworkEquipment.setId("1");
        assetNetworkEquipment.setPortSize(1);
        return assetNetworkEquipment;
    }

    private ManualStartActivityRequest generateAssetManualStart() {
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        manualStartActivityRequest.setAssignee("1");
        manualStartActivityRequest.setBusinessId("1");
        List<String> userIds = new ArrayList<>();
        manualStartActivityRequest.setAssignee("1");
        userIds.add("1");
        manualStartActivityRequest.setConfigUserIds(userIds);
        manualStartActivityRequest.setProcessDefinitionKey("1");
        manualStartActivityRequest.setSuggest("1");
        return manualStartActivityRequest;
    }

    @Test
    public void testSaveAsset() throws Exception {
        when(assetDao.findCountMac(any())).thenReturn(0);
        when(assetUserDao.getById(any())).thenReturn(new AssetUser());
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        when(assetGroupDao.getById(any())).thenReturn(generateAssetGroup());

        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());

        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(new SysArea());

        when(aesEncoder.decode(any(), any())).thenReturn("1");
        Asset asset = new Asset();
        asset.setId(1);
        when(requestConverter.convert(any(AssetRequest.class), any())).thenReturn(asset);
        List<BaselineCategoryModelNodeResponse> baselineCategoryModelNodeResponseArrayList = new ArrayList<>();
        BaselineCategoryModelNodeResponse baselineCategoryModelNodeResponse = new BaselineCategoryModelNodeResponse();
        baselineCategoryModelNodeResponse.setStringId("1");
        baselineCategoryModelNodeResponseArrayList.add(baselineCategoryModelNodeResponse);
        when(operatingSystemClient.getInvokeOperatingSystemTree())
            .thenReturn(baselineCategoryModelNodeResponseArrayList);

        AssetRequest assetRequest = generateAssetRequest();
        // 普通资产
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());
        assetRequest.setAssetGroups(Arrays.asList(generateAssetGroupRequest()));
        assetOuterRequest.setAsset(assetRequest);
        String baselineTemplateId = assetRequest.getBaselineTemplateId();
        String installTemplateId = assetRequest.getInstallTemplateId();

        Assert.assertTrue(installTemplateId.equals("1"));
        if (baselineTemplateId.isEmpty()) {
            System.currentTimeMillis();
        }
        ActionResponse result = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals(0, assetRequest.getAssetStatus().intValue());
        Assert.assertEquals("416", result.getHead().getCode());

        // 安全设备
        AssetOuterRequest assetOuterRequest1 = new AssetOuterRequest();
        assetOuterRequest1.setAsset(assetRequest);
        assetOuterRequest1.setSafetyEquipment(generateAssetSafetyEquipmentRequest());
        assetOuterRequest1.setManualStartActivityRequest(generateAssetManualStart());
        assetOuterRequest.setAsset(assetRequest);

        ActionResponse result6 = assetServiceImpl.saveAsset(assetOuterRequest1);
        Assert.assertEquals("416", result6.getHead().getCode());

        // 网络设备
        AssetOuterRequest assetOuterRequest2 = new AssetOuterRequest();
        assetOuterRequest2.setAsset(assetRequest);
        assetOuterRequest2.setNetworkEquipment(genrateAssetNetworkEquipmentRequest());
        assetOuterRequest2.setManualStartActivityRequest(generateAssetManualStart());
        assetOuterRequest.setAsset(assetRequest);

        ActionResponse result7 = assetServiceImpl.saveAsset(assetOuterRequest2);
        Assert.assertEquals("416", result7.getHead().getCode());

        // 存储设备
        AssetOuterRequest assetOuterRequest3 = new AssetOuterRequest();
        assetOuterRequest3.setAsset(assetRequest);
        assetOuterRequest3.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest3.setManualStartActivityRequest(generateAssetManualStart());
        ActionResponse result8 = assetServiceImpl.saveAsset(assetOuterRequest3);
        Assert.assertEquals("416", result8.getHead().getCode());

        // ip重复
        when(assetDao.findCountMac(any())).thenReturn(10);
        try {
            assetServiceImpl.saveAsset(assetOuterRequest1);
        } catch (Exception e) {
            Assert.assertEquals("MAC地址不能重复！", e.getMessage());
        }

        // 区域注销
        when(assetDao.findCountMac(any())).thenReturn(0);
        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(null);
        try {
            assetServiceImpl.saveAsset(assetOuterRequest1);
        } catch (Exception e) {
            Assert.assertEquals("当前区域不存在，或已经注销", e.getMessage());
        }

        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(new SysArea());
        // 异常情况
        when(activityClient.manualStartProcess(any())).thenReturn(null);
        Assert.assertEquals("416", assetServiceImpl.saveAsset(assetOuterRequest).getHead().getCode());

        // 异常情况
        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        Assert.assertEquals("416", assetServiceImpl.saveAsset(assetOuterRequest).getHead().getCode());


    }

    @Test
    public void findAlarmAssetCountTest() {
        when(assetDao.findAlarmAssetCount(any())).thenReturn(100);
        Assert.assertEquals(100, assetServiceImpl.findAlarmAssetCount().get("currentAlarmAssetIdNum"));
    }

    public AssetStorageMediumRequest generateAssetStorageMediumRequest() {
        AssetStorageMediumRequest assetStorageMediumRequest = new AssetStorageMediumRequest();
        assetStorageMediumRequest.setAssetId("1");
        assetStorageMediumRequest.setId("1");
        return assetStorageMediumRequest;
    }

    public AssetStorageMedium generateAssetStorageMedium() {
        AssetStorageMedium assetStorageMedium = new AssetStorageMedium();
        assetStorageMedium.setAssetId("1");
        assetStorageMedium.setId(1);
        return assetStorageMedium;
    }

    public List<AssetStorageMedium> generateAssetStorageMediumList() {
        List<AssetStorageMedium> result = new ArrayList<>();
        result.add(generateAssetStorageMedium());
        return result;
    }

    public AssetOthersRequest generateAssetOtherRequest() {
        AssetOthersRequest assetOthersRequest = new AssetOthersRequest();
        assetOthersRequest.setAreaId("1");
        assetOthersRequest.setId("1");
        assetOthersRequest.setName("1");
        assetOthersRequest.setNumber("1");
        assetOthersRequest.setResponsibleUserId("1");
        assetOthersRequest.setCategoryModel("1");
        return assetOthersRequest;
    }

    @Test
    public void testUpdateAsset() throws Exception {
        Integer result = assetServiceImpl.updateAsset(new AssetRequest());
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    private WaitingTaskReponse generateWaitingTask() {
        WaitingTaskReponse waitingTaskReponse = new WaitingTaskReponse();
        waitingTaskReponse.setAssignee("");
        waitingTaskReponse.setName("");
        waitingTaskReponse.setPriority(0);
        waitingTaskReponse.setCreateTime(new Date());
        waitingTaskReponse.setExecutionId("1");
        waitingTaskReponse.setProcessInstanceId("1");
        waitingTaskReponse.setProcessDefinitionId("1");
        waitingTaskReponse.setTaskDefinitionKey("1");
        waitingTaskReponse.setFormKey("1");
        waitingTaskReponse.setTaskId("1");
        waitingTaskReponse.setBusinessId("1");
        return waitingTaskReponse;
    }

    @Test
    public void testFindListAsset() throws Exception {

        when(assetDao.findListAsset(any())).thenReturn(Arrays.asList(generateAsset()));
        when(assetDao.findCount(any())).thenReturn(10);
        when(assetDao.queryAllAssetVulCount(any())).thenReturn(10);
        when(assetDao.queryAllAssetPatchCount(any())).thenReturn(10);
        AlarmAssetIdResponse alarmAssetIdResponse = new AlarmAssetIdResponse();
        List<BaseResponse> baseResponses = new ArrayList<>();
        baseResponses.add(new BaseResponse());
        alarmAssetIdResponse.setAssetIdList(baseResponses);
        ActionResponse alarmAssetIdResponseActionResponse = ActionResponse.success(alarmAssetIdResponse);
        when(emergencyClient.queryEmergecyAllCount()).thenReturn(alarmAssetIdResponseActionResponse);

        List<IdCount> idCounts = new ArrayList<>();
        IdCount idCount = new IdCount();
        idCount.setId("1");
        idCount.setCount("1");
        idCounts.add(idCount);
        when(assetDao.queryAssetVulCount(any(), any(), any())).thenReturn(idCounts);
        when(assetDao.queryAssetPatchCount(any(), any(), any())).thenReturn(idCounts);
        when(assetDao.queryAlarmCountByAssetIds(any()))
            .thenReturn(Arrays.asList(new IdCount("1", "1"), new IdCount("2", "1")));
        List<BaselineCategoryModelResponse> categoryModelResponseList = new ArrayList<>();
        BaselineCategoryModelResponse categoryModelResponse = new BaselineCategoryModelResponse();
        categoryModelResponse.setStringId("1");
        categoryModelResponse.setName("windows");
        categoryModelResponseList.add(categoryModelResponse);
        when(redisService.getAllSystemOs()).thenReturn(categoryModelResponseList);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success();

        PageResult pageResult = new PageResult();
        pageResult.setItems(idCounts);
        actionResponse.setBody(Arrays.asList(generateWaitingTask()));
        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);
        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(assetGroupRelationDao.findAssetIdByAssetGroupId(any())).thenReturn(Arrays.asList("1"));
        when(emergencyClient.queryEmergencyCount(any())).thenReturn(ActionResponse.success(pageResult));
        when(assetDao.findCount(any())).thenReturn(100);
        when(assetDao.findAlarmAssetCount(any())).thenReturn(100);

        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setQueryPatchCount(true);
        assetQuery.setQueryVulCount(true);
        assetQuery.setQueryAlarmCount(true);
        assetQuery.setAssetGroupQuery(true);

        PageResult<AssetResponse> result = assetServiceImpl.findPageAsset(assetQuery);
        Assert.assertNotNull(result);

        // enterControl
        when(assetDao.findCount(any())).thenReturn(0);
        assetQuery = new AssetQuery();
        assetQuery.setAssetGroup("1");
        assetQuery.setAssociateGroup(true);
        assetQuery.setEnterControl(true);
        assetQuery.setGroupId("1");
        assetQuery.setAssetStatusList(Arrays.asList(1, 2, 3));
        result = assetServiceImpl.findPageAsset(assetQuery);
        Assert.assertNotNull(result);

        assetQuery = new AssetQuery();
        List list = assetServiceImpl.findListAsset(assetQuery, null);
        Assert.assertNotNull(list);

    }

    @Test
    public void testFindCountAsset() throws Exception {
        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success();

        actionResponse.setBody(Arrays.asList(generateWaitingTask()));

        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);

        Integer result = assetServiceImpl.findCountAsset(new AssetQuery());
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testGetAllHardWaitingTask() throws Exception {
        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success();
        WaitingTaskReponse waitingTaskReponse = new WaitingTaskReponse();
        waitingTaskReponse.setAssignee("");
        waitingTaskReponse.setName("");
        waitingTaskReponse.setPriority(0);
        waitingTaskReponse.setCreateTime(new Date());
        waitingTaskReponse.setExecutionId("");
        waitingTaskReponse.setProcessInstanceId("");
        waitingTaskReponse.setProcessDefinitionId("");
        waitingTaskReponse.setTaskDefinitionKey("");
        waitingTaskReponse.setFormKey("");
        waitingTaskReponse.setTaskId("");
        waitingTaskReponse.setBusinessId("1");

        actionResponse.setBody(Arrays.asList(waitingTaskReponse));

        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);

        Map<String, WaitingTaskReponse> result = assetServiceImpl.getAllHardWaitingTask("definitionKeyType");
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void testFindCountAssetNumber() throws Exception {
        Integer result = assetServiceImpl.findCountAssetNumber("");
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testFindPageAsset() throws Exception {
        when(assetDao.findListAsset(any())).thenReturn(Arrays.asList(new Asset()));

        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success(new ArrayList<>());
        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);

        PageResult<AssetResponse> result = assetServiceImpl.findPageAsset(new AssetQuery());
        Assert.assertNotNull(result);
    }

    @Test
    public void testSaveReportAsset() throws Exception {
        // assetServiceImpl.saveReportAsset(Arrays.asList(new AssetOuterRequest()));
    }

    @Test
    public void testBatchSave() throws Exception {
        Integer result = assetServiceImpl.batchSave(Arrays.asList(new Asset()));
        Assert.assertNotNull(result);
    }

    @Test
    public void testPulldownManufacturer() throws Exception {
        when(assetDao.pulldownManufacturer()).thenReturn(Arrays.asList("String"));

        // List<String> result = assetServiceImpl.pulldownManufacturer();
        // Assert.assertEquals(Arrays.asList("String"), result);
    }

    @Test
    public void testCheckRepeatAsset() throws Exception {
        when(assetDao.checkRepeatAsset(any())).thenReturn(Arrays.asList(new Asset()));

        List<String[]> ipMac = new ArrayList<>();
        ipMac.add(new String[] { "string" });

        boolean result = assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testChangeStatus() throws Exception {
        when(assetDao.changeStatus(any())).thenReturn(0);

        Integer result = assetServiceImpl.changeStatus(new String[] { "ids" }, 0);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testChangeStatusById() throws Exception {
        when(assetDao.changeStatus(any())).thenReturn(0);

        Integer result = assetServiceImpl.changeStatusById("id", 0);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testFindListAssetByCategoryModel() throws Exception {
        when(assetDao.findListAssetByCategoryModel(any())).thenReturn(Arrays.asList(new Asset()));

        List<AssetResponse> result = assetServiceImpl.findListAssetByCategoryModel(new AssetQuery());
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindCountByCategoryModel() throws Exception {
        when(assetDao.findCountByCategoryModel(any())).thenReturn(0);

        Integer result = assetServiceImpl.findCountByCategoryModel(new AssetQuery());
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testFindPageAssetByCategoryModel() throws Exception {
        when(assetDao.findCountByCategoryModel(any())).thenReturn(0);
        when(assetDao.findListAssetByCategoryModel(any())).thenReturn(Arrays.asList(new Asset()));

        PageResult<AssetResponse> result = assetServiceImpl.findPageAssetByCategoryModel(new AssetQuery());
        Assert.assertNotNull(result);
    }

    @Test
    public void testCountManufacturer() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("value", 1L);

        when(assetDao.countManufacturer(any(), any())).thenReturn(Arrays.asList(map, map));

        List<EnumCountResponse> result = assetServiceImpl.countManufacturer();
        Assert.assertNotNull(result);
    }

    @Test
    public void testCountStatus() throws Exception {
        when(assetDao.countStatus(any())).thenReturn(Arrays.asList(new HashMap<String, Object>() {
            {
                put("key", 1);
                put("value", 1L);
            }
        }));

        List<EnumCountResponse> result = assetServiceImpl.countStatus();
        Assert.assertNotNull(result);
    }

    @Test
    public void testCountCategory() throws Exception {
        when(assetDao.findCountByCategoryModel(any())).thenReturn(0);

        List<EnumCountResponse> result = assetServiceImpl.countCategory();
        Assert.assertNotNull(result);
    }

    @Test
    public void testQueryAssetByIds() throws Exception {
        when(assetDao.queryAssetByIds(any())).thenReturn(Arrays.asList(new Asset()));

        List<AssetResponse> result = assetServiceImpl.queryAssetByIds(new Integer[] { 0 });
        Assert.assertNotNull(result);
    }

    /**
     * 查询网络设备
     * @throws Exception
     */
    @Test
    public void testGetByAssetId1() throws Exception {
        Mockito.when(assetDao.getByAssetId(Mockito.anyString())).thenReturn(generateNetWorkAsset());
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        mockRedisUtil();
        Mockito.when(assetGroupRelationDao.queryByAssetId(Mockito.any())).thenReturn(generateAssetGroupList());
        Mockito.when(assetNetworkEquipmentDao.getByWhere(Mockito.any()))
            .thenReturn(generateAssetNetworkEquipmentList());
        Assert.assertEquals("0", assetServiceImpl.getByAssetId(condition).getAsset().getStringId());
    }

    /**
     * 查询安全设备
     * @throws Exception
     */
    @Test
    public void testGetByAssetId2() throws Exception {
        Mockito.when(assetDao.getByAssetId(Mockito.anyString())).thenReturn(generateSafetyAsset());
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        mockRedisUtil();
        Mockito.when(assetGroupRelationDao.queryByAssetId(Mockito.any())).thenReturn(generateAssetGroupList());
        Mockito.when(assetSafetyEquipmentDao.getByWhere(Mockito.any())).thenReturn(generateAssetSafetyEquipmentList());
        Assert.assertEquals("0", assetServiceImpl.getByAssetId(condition).getAsset().getStringId());
    }

    /**
     * 查询存储设备
     * @throws Exception
     */
    @Test
    public void testGetByAssetId3() throws Exception {
        Mockito.when(assetDao.getByAssetId(Mockito.anyString())).thenReturn(generateStorageAsset());
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        mockRedisUtil();
        Mockito.when(assetGroupRelationDao.queryByAssetId(Mockito.any())).thenReturn(generateAssetGroupList());
        Mockito.when(assetStorageMediumDao.getByWhere(Mockito.any())).thenReturn(generateAssetStorageMediumList());
        Assert.assertEquals("0", assetServiceImpl.getByAssetId(condition).getAsset().getStringId());
    }

    private List<AssetNetworkEquipment> generateAssetNetworkEquipmentList() {
        List<AssetNetworkEquipment> result = new ArrayList<>();
        result.add(generateAssetNetworkEquipment());
        return result;
    }

    private void mockRedisUtil() throws Exception {
        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        Mockito.when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(sysArea);
    }

    private Asset generateNetWorkAsset() {
        Asset asset = generateAsset();
        asset.setCategoryModel(AssetCategoryEnum.NETWORK.getCode());
        return asset;
    }

    private Asset generateStorageAsset() {
        Asset asset = generateAsset();
        asset.setCategoryModel(AssetCategoryEnum.STORAGE.getCode());
        return asset;
    }

    private Asset generateSafetyAsset() {
        Asset asset = generateAsset();
        asset.setCategoryModel(AssetCategoryEnum.SAFETY.getCode());
        return asset;
    }

    private List<AssetGroup> generateAssetGroupList() {
        List<AssetGroup> result = new ArrayList<>();
        result.add(generateAssetGroup());
        AssetGroup assetGroup = generateAssetGroup();
        assetGroup.setName("1");
        result.add(generateAssetGroup());
        return result;
    }

    private Asset generateAsset() {
        Asset asset = new Asset();
        asset.setOperationSystemName("");
        asset.setAreaName("");
        asset.setResponsibleUserName("");
        asset.setAssetGroup("");
        asset.setNumber("");
        asset.setName("");
        asset.setInstallType(0);
        asset.setSerial("");
        asset.setAreaId("1");
        asset.setCategoryModel(5);
        asset.setManufacturer("");
        asset.setAssetStatus(8);
        asset.setAdmittanceStatus(0);
        asset.setOperationSystem(1L);

//        asset.setLatitude("");
//        asset.setLongitude("");
        asset.setHouseLocation("");
        asset.setFirmwareVersion("");
        asset.setUuid("");

//        asset.setEmail("");
        asset.setAssetSource(0);
        asset.setImportanceDegree(0);
        asset.setDescrible("");

        asset.setFirstEnterNett(0L);
        asset.setServiceLife(0L);
        asset.setBuyDate(0L);
        asset.setWarranty("");
        asset.setGmtCreate(0L);
        asset.setGmtModified(0L);
        asset.setMemo("");
        asset.setCreateUser(0);
        asset.setModifyUser(0);
        asset.setStatus(0);
        asset.setResponsibleUserId("");
        asset.setSoftwareVersion("");
        asset.setImportanceDegreeName("");
        asset.setInstallTypeName("");
        asset.setId(0);
        return asset;
    }

    private AssetCpuRequest generateAssetCpuRequest() {
        AssetCpuRequest assetCpuRequest = new AssetCpuRequest();
        assetCpuRequest.setId("1");
        assetCpuRequest.setSerial("1");
        assetCpuRequest.setBrand("1");
        assetCpuRequest.setModel("1");
        assetCpuRequest.setMainFrequency(0.0F);
        assetCpuRequest.setThreadSize(0);
        assetCpuRequest.setCoreSize(0);
        assetCpuRequest.setAssetId("1");
        return assetCpuRequest;
    }

    private AssetMainboradRequest generateAssetMainboradRequest() {
        AssetMainboradRequest assetMainboradRequest = new AssetMainboradRequest();
        assetMainboradRequest.setAssetId("1");
        assetMainboradRequest.setId("1");
        assetMainboradRequest.setBrand("");
        assetMainboradRequest.setModel("");
        assetMainboradRequest.setSerial("");
        assetMainboradRequest.setBiosVersion("");
        assetMainboradRequest.setBiosDate(0L);
        assetMainboradRequest.setMemo("");
        return assetMainboradRequest;
    }

    private AssetGroupRequest generateAssetGroupRequest() {
        AssetGroupRequest assetGroupRequest = new AssetGroupRequest();
        assetGroupRequest.setId("1");
        assetGroupRequest.setAssetIds(new String[] { "1", "2", "3" });
        assetGroupRequest.setMemo("a");
        assetGroupRequest.setName("a");
        return assetGroupRequest;
    }

    @Test
    public void testChangeAsset() throws Exception {
        when(assetDao.findCountMac(any())).thenReturn(0);

        when(assetNetworkEquipmentDao.getById(any())).thenReturn(generateAssetNetworkEquipment());
        when(assetSoftwareRelationDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetGroupRelationDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(activityClient.completeTask(any())).thenReturn(ActionResponse.success());
        when(assetGroupDao.getById(any())).thenReturn(generateAssetGroup());
        when(assetLinkRelationDao.deleteRelationByAssetId(any())).thenReturn(0);
        when(assetDao.changeAsset(any())).thenReturn(10);
        List<AssetGroupRequest> assetGroupRequests = new ArrayList<>();
        assetGroupRequests.add(generateAssetGroupRequest());
        Asset asset = generateAsset();
        asset.setAssetStatus(AssetStatusEnum.RETIRE.getCode());
        when(assetDao.getById(any())).thenReturn(asset);
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = generateAssetRequest();
        assetRequest.setAssetGroups(assetGroupRequests);
        assetOuterRequest.setAsset(assetRequest);

        int result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        AssetCpuRequest assetCpu = generateAssetCpuRequest();
        assetCpu.setId(null);

        AssetMainboradRequest assetMainboradRequest = generateAssetMainboradRequest();
        assetMainboradRequest.setId(null);

        AssetHardDiskRequest assetHardDiskRequest = generateHardDiskRequest();
        assetHardDiskRequest.setId(null);

        AssetNetworkCardRequest assetNetworkCard = generateAssetNetworkCardRequest();
        assetNetworkCard.setId(null);

        AssetMemoryRequest assetMemoryRequest = generateAssetMemoryRequest();
        assetMemoryRequest.setId(null);

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        // 安全设备变更
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setSafetyEquipment(generateAssetSafetyEquipmentRequest());

        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        // 网络设备变更
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setNetworkEquipment(genrateAssetNetworkEquipmentRequest());

        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        // 存储设备变更
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());

        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        when(assetDao.getById(any())).thenReturn(asset);
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());

        assetOuterRequest.setManualStartActivityRequest(null);
        assetServiceImpl.changeAsset(assetOuterRequest);

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.NOT_REGISTER.getCode());
        assetRequest.setAssetStatus(AssetStatusEnum.NOT_REGISTER.getCode());

        when(assetDao.getById(any())).thenReturn(asset);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());

        assetOuterRequest.setManualStartActivityRequest(null);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("配置信息不能为空", e.getMessage());
        }

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetRequest.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());

        when(assetDao.getById(any())).thenReturn(asset);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());

        assetOuterRequest.setManualStartActivityRequest(null);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("配置信息不能为空", e.getMessage());
        }

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        when(assetDao.getById(any())).thenReturn(asset);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());

        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交，当前资产状态是：已入网", e.getMessage());

        }

        when(activityClient.manualStartProcess(any())).thenReturn(null);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("流程服务异常", e.getMessage());
        }

        when(activityClient.manualStartProcess(any()))
            .thenReturn(ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, "123"));
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("123", e.getMessage());
        }

        OAuth2Authentication authentication = Mockito.mock(OAuth2Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        LoginUser loginUser = JSONObject.parseObject(
            "{ \"id\":8, \"username\":\"zhangbing\", \"password\":\"$2a$10$hokzLPdz15q9XFuNB8HA0ObV9j301oxkFBlsJUCe/8iWBvql5gBdO\", \"name\":\"张冰\", \"duty\":\"部门经历\", \"department\":\"A是不\", \"phone\":\"123\", \"email\":\"string123@email\", \"status\":1, \"errorCount\":4, \"lastLoginTime\":1553737022175, \"lastModifiedPassword\":1550657104216, \"sysRoles\":[ { \"id\":9, \"code\":\"config_admin\", \"name\":\"配置管理员\", \"description\":\"\" } ], \"areas\":[ { \"id\":10, \"parentId\":2, \"levelType\":2, \"fullName\":\"金牛区\", \"shortName\":\"1\", \"fullSpell\":\"1\", \"shortSpell\":\"1\", \"status\":1, \"demo\":\"\" }, { \"id\":112, \"parentId\":0, \"levelType\":1, \"fullName\":\"四川省成都市\", \"status\":1, \"demo\":\"\" } ], \"enabled\":true, \"accountNonExpired\":true, \"accountNonLocked\":true, \"credentialsNonExpired\":true } ",
            LoginUser.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, "123");
        Map<String, Object> map = new HashMap<>();
        map.put("principal", loginUser);
        token.setDetails(map);

        authentication = Mockito.mock(OAuth2Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);

        SecurityContextHolder.setContext(securityContext);

        // 存储设备变更

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());

        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("资产变更失败", e.getMessage());
        }

        try {
            PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(null);
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("获取用户失败", e.getMessage());
        }
    }

    private AssetHardDiskRequest generateHardDiskRequest() {
        AssetHardDiskRequest hardDiskRequest = new AssetHardDiskRequest();
        hardDiskRequest.setMemo("");
        hardDiskRequest.setAssetId("1");
        hardDiskRequest.setBrand("");
        hardDiskRequest.setModel("");
        hardDiskRequest.setSerial("");
        hardDiskRequest.setInterfaceType(0);
        hardDiskRequest.setCapacity(0);
        hardDiskRequest.setDiskType(0);
        hardDiskRequest.setBuyDate(0L);
        hardDiskRequest.setUseTimes(0);
        hardDiskRequest.setCumulativeHour(0);
        hardDiskRequest.setId("1");
        return hardDiskRequest;
    }

    private AssetMemoryRequest generateAssetMemoryRequest() {
        AssetMemoryRequest memoryRequest = new AssetMemoryRequest();
        memoryRequest.setId("1");
        memoryRequest.setAssetId("1");
        memoryRequest.setCapacity(0);
        memoryRequest.setFrequency(0.0D);
        memoryRequest.setSlotType(0);
        memoryRequest.setHeatsink(1);
        memoryRequest.setStitch(0);
        memoryRequest.setBuyDate(0L);
        memoryRequest.setWarrantyDate(0L);
        memoryRequest.setTelephone("");
        memoryRequest.setMemo("");
        memoryRequest.setBrand("");
        memoryRequest.setTransferType(0);
        memoryRequest.setSerial("");
        return memoryRequest;
    }

    @Test
    public void testExportTemplate() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        PowerMockito.mockStatic(ZipUtil.class);
        PowerMockito.doNothing().when(ZipUtil.class, "compress", Mockito.any(File.class), Mockito.any(File[].class));
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));
        assetServiceImpl.exportTemplate(new String[] { "4", "5", "6", "7", "8" });
    }

    @Test
    public void testImportPc() throws Exception {
        when(assetDao.findCountMac(any())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        List<ComputeDeviceEntity> computeDeviceEntities = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = getComputeDeviceEntity();
        computeDeviceEntities.add(computeDeviceEntity);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(null);


        Mockito.when(assetDao.insertBatch(Mockito.anyList())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.importPc(null, null);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交！", e.getMessage());
        }
    }

    private ComputeDeviceEntity getComputeDeviceEntity() {
        ComputeDeviceEntity computeDeviceEntity = new ComputeDeviceEntity();
        computeDeviceEntity.setOperationSystem("1");
        computeDeviceEntity.setArea("四川");
        computeDeviceEntity.setDueTime(System.currentTimeMillis() + 1);
        computeDeviceEntity.setUser("1");
        computeDeviceEntity.setImportanceDegree("1");

        return computeDeviceEntity;
    }

    @Test
    public void testImportNet() throws Exception {
        when(assetDao.findCountMac(any())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        List<BaselineCategoryModelResponse> baselineCategoryModelResponses = new ArrayList<>();
        BaselineCategoryModelResponse baselineCategoryModelResponse = new BaselineCategoryModelResponse();
        baselineCategoryModelResponse.setStringId("1");
        baselineCategoryModelResponse.setName("1");
        baselineCategoryModelResponses.add(baselineCategoryModelResponse);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        ImportResult importResult = new ImportResult();
        List<NetworkDeviceEntity> networkDeviceEntities = new ArrayList<>();
        NetworkDeviceEntity networkDeviceEntity = getNetworkDeviceEntity();
        networkDeviceEntities.add(networkDeviceEntity);
        importResult.setMsg("");
        importResult.setDataList(networkDeviceEntities);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("4");
        String result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        NetworkDeviceEntity networkDeviceEntity1 = getNetworkDeviceEntity();
        networkDeviceEntities.add(networkDeviceEntity1);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        networkDeviceEntity.setName("1");
        networkDeviceEntity1.setName("2");
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        networkDeviceEntity.setNumber("1");
        networkDeviceEntity1.setNumber("2");
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产MAC地址重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        networkDeviceEntity.setPortSize(-1);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行网口数目范围为1-99！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        networkDeviceEntity.setPortSize(10);
        networkDeviceEntity.setButDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        networkDeviceEntity.setPortSize(10);
        networkDeviceEntity.setButDate(System.currentTimeMillis() / 2);
        networkDeviceEntity.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        when(assetDao.findCountMac(any())).thenReturn(10);
        networkDeviceEntity.setDueDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产MAC地址重复！", result);

        when(assetDao.findCountMac(any())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统没有此使用者，或已被注销！", result);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        networkDeviceEntity.setArea("sdfsadfasd");
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

        networkDeviceEntity.setArea("四川");
        try {
            assetServiceImpl.importNet(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("导入失败，选择品类型号不存在，或已被注销！", e.getMessage());
        }

        importResult.setDataList(null);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        Assert.assertEquals("", assetServiceImpl.importNet(null, assetImportRequest));

        importResult.setDataList(new ArrayList());
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importNet(null, assetImportRequest));

        importResult.setMsg("导入失败");
        importResult.setDataList(networkDeviceEntities);
        Assert.assertEquals("导入失败", assetServiceImpl.importNet(null, assetImportRequest));

        importResult.setMsg("");
        Mockito.when(assetDao.insertBatch(Mockito.anyList())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.importNet(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交！", e.getMessage());
        }

    }

    private NetworkDeviceEntity getNetworkDeviceEntity() {
        NetworkDeviceEntity networkDeviceEntity = new NetworkDeviceEntity();
        networkDeviceEntity.setArea("四川");
        networkDeviceEntity.setDueDate(System.currentTimeMillis() + 1);
        networkDeviceEntity.setUser("1");
        networkDeviceEntity.setButDate(System.currentTimeMillis() / 2);
        networkDeviceEntity.setPortSize(1);
        networkDeviceEntity.setMac("mac");
        networkDeviceEntity.setImportanceDegree("1");
        return networkDeviceEntity;
    }

    @Test
    public void testImportSecurity() throws Exception {
        when(assetDao.findCountMac(any())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        List<BaselineCategoryModelResponse> baselineCategoryModelResponses = new ArrayList<>();
        BaselineCategoryModelResponse baselineCategoryModelResponse = new BaselineCategoryModelResponse();
        baselineCategoryModelResponse.setStringId("1");
        baselineCategoryModelResponse.setName("1");
        baselineCategoryModelResponses.add(baselineCategoryModelResponse);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        ImportResult importResult = new ImportResult();
        List<SafetyEquipmentEntiy> safetyEquipmentEntiys = new ArrayList<>();
        SafetyEquipmentEntiy safetyEquipmentEntiy = getSafetyEquipmentEntiy();
        safetyEquipmentEntiys.add(safetyEquipmentEntiy);
        importResult.setMsg("");
        importResult.setDataList(safetyEquipmentEntiys);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("1");
        String result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        SafetyEquipmentEntiy safetyEquipmentEntiy1 = getSafetyEquipmentEntiy();
        safetyEquipmentEntiys.add(safetyEquipmentEntiy1);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        safetyEquipmentEntiy.setName("1");
        safetyEquipmentEntiy1.setName("2");
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        safetyEquipmentEntiy.setNumber("1");
        safetyEquipmentEntiy1.setNumber("2");
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产MAC地址重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        when(assetDao.findCountMac(any())).thenReturn(10);
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产MAC地址重复！", result);

        when(assetDao.findCountMac(any())).thenReturn(0);
        safetyEquipmentEntiy.setBuyDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

        safetyEquipmentEntiy.setBuyDate(System.currentTimeMillis() / 2);
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() * 2);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(new ArrayList<>());
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行操作系统不存在，或已被注销！", result);

        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统中没有此使用者，或已被注销！", result);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        safetyEquipmentEntiy.setArea("sdfsadfasd");
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域！", result);

        safetyEquipmentEntiy.setArea("四川");
        try {
            assetServiceImpl.importSecurity(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("导入失败，选择品类型号不存在，或已被注销！", e.getMessage());
        }

        importResult.setDataList(null);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        Assert.assertEquals("", assetServiceImpl.importSecurity(null, assetImportRequest));

        importResult.setDataList(new ArrayList());
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importSecurity(null, assetImportRequest));

        importResult.setMsg("导入失败");
        importResult.setDataList(safetyEquipmentEntiys);
        Assert.assertEquals("导入失败", assetServiceImpl.importSecurity(null, assetImportRequest));

        importResult.setMsg("");
        Mockito.when(assetDao.insertBatch(Mockito.anyList())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.importSecurity(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交！", e.getMessage());
        }
    }

    private SafetyEquipmentEntiy getSafetyEquipmentEntiy() {
        SafetyEquipmentEntiy safetyEquipmentEntiy = new SafetyEquipmentEntiy();
        safetyEquipmentEntiy.setArea("四川");
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() + 1);
        safetyEquipmentEntiy.setUser("1");
        safetyEquipmentEntiy.setOperationSystem("1");
        safetyEquipmentEntiy.setImportanceDegree("1");
        return safetyEquipmentEntiy;
    }

    @Test
    public void testImportStory() throws Exception {
        when(assetDao.findCountMac(any())).thenReturn(0);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        List<BaselineCategoryModelResponse> baselineCategoryModelResponses = new ArrayList<>();
        BaselineCategoryModelResponse baselineCategoryModelResponse = new BaselineCategoryModelResponse();
        baselineCategoryModelResponse.setStringId("1");
        baselineCategoryModelResponse.setName("1");
        baselineCategoryModelResponses.add(baselineCategoryModelResponse);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        ImportResult importResult = new ImportResult();
        List<StorageDeviceEntity> storageDeviceEntityArrayList = new ArrayList<>();
        StorageDeviceEntity storageDeviceEntity = getStorageDeviceEntity();
        storageDeviceEntityArrayList.add(storageDeviceEntity);
        importResult.setMsg("");
        importResult.setDataList(storageDeviceEntityArrayList);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("1");
        String result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        StorageDeviceEntity storageDeviceEntity1 = getStorageDeviceEntity();
        storageDeviceEntityArrayList.add(storageDeviceEntity1);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        storageDeviceEntity.setName("1");
        storageDeviceEntity1.setName("2");
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        storageDeviceEntity.setBuyDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        storageDeviceEntity.setRaidSupport(1);
        result = assetServiceImpl.importStory(null, assetImportRequest);

        storageDeviceEntity.setBuyDate(System.currentTimeMillis() / 2);
        storageDeviceEntity.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        storageDeviceEntity.setDueDate(System.currentTimeMillis() * 2);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统中没有此使用者，或已被注销！", result);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        storageDeviceEntity.setArea("sdfsadfasd");
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

        storageDeviceEntity.setArea("四川");
        try {
            assetServiceImpl.importStory(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("导入失败，选择品类型号不存在，或已被注销！", e.getMessage());
        }

        importResult.setDataList(null);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        Assert.assertEquals("", assetServiceImpl.importStory(null, assetImportRequest));

        importResult.setDataList(new ArrayList());
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importStory(null, assetImportRequest));

        importResult.setMsg("导入失败");
        importResult.setDataList(storageDeviceEntityArrayList);
        Assert.assertEquals("导入失败", assetServiceImpl.importStory(null, assetImportRequest));

        importResult.setMsg("");
        Mockito.when(assetDao.insertBatch(Mockito.anyList())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.importStory(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交！", e.getMessage());
        }
    }

    private StorageDeviceEntity getStorageDeviceEntity() {
        StorageDeviceEntity storageDeviceEntity = new StorageDeviceEntity();
        storageDeviceEntity.setArea("四川");
        storageDeviceEntity.setDueDate(System.currentTimeMillis() + 1);
        storageDeviceEntity.setUser("1");
        storageDeviceEntity.setImportanceDegree("1");
        return storageDeviceEntity;
    }

    @Test
    public void testImportOhters() throws Exception {

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        ImportResult importResult = new ImportResult();
        List<OtherDeviceEntity> otherDeviceEntities = new ArrayList<>();
        OtherDeviceEntity otherDeviceEntity = getOtherDeviceEntity();
        otherDeviceEntities.add(otherDeviceEntity);
        importResult.setDataList(otherDeviceEntities);
        importResult.setMsg("");
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("1");
        String result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        OtherDeviceEntity otherDeviceEntity1 = getOtherDeviceEntity();
        otherDeviceEntities.add(otherDeviceEntity1);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        otherDeviceEntity.setName("1");
        otherDeviceEntity1.setName("2");
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        otherDeviceEntity.setBuyDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

        otherDeviceEntity.setBuyDate(System.currentTimeMillis() / 2);
        otherDeviceEntity.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        otherDeviceEntity.setDueDate(System.currentTimeMillis() * 2);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统中没有此使用者，或已被注销！", result);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        otherDeviceEntity.setArea("sdfsadfasd");
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

        otherDeviceEntity.setArea("四川");
        try {
            assetServiceImpl.importOhters(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("导入失败，选择品类型号不存在，或已被注销！", e.getMessage());
        }

        importResult.setDataList(null);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        Assert.assertEquals("", assetServiceImpl.importOhters(null, assetImportRequest));

        importResult.setDataList(new ArrayList());
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importOhters(null, assetImportRequest));

        importResult.setMsg("导入失败");
        importResult.setDataList(otherDeviceEntities);
        Assert.assertEquals("导入失败", assetServiceImpl.importOhters(null, assetImportRequest));

        importResult.setMsg("");
        Mockito.when(assetDao.insertBatch(Mockito.anyList())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.importOhters(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交！", e.getMessage());
        }
    }

    private OtherDeviceEntity getOtherDeviceEntity() {
        OtherDeviceEntity otherDeviceEntity = new OtherDeviceEntity();
        otherDeviceEntity.setNumber("123");
        otherDeviceEntity.setUser("123");
        otherDeviceEntity.setArea("四川");
        otherDeviceEntity.setBuyDate(System.currentTimeMillis());
        otherDeviceEntity.setDueDate(System.currentTimeMillis() + 1);
        otherDeviceEntity.setImportanceDegree("1");
        return otherDeviceEntity;
    }

    @Test
    public void testQueryAssetCountByAreaIds() throws Exception {
        when(assetDao.queryAssetCountByAreaIds(any())).thenReturn(0);

        List<Integer> areaIds = new ArrayList<>();
        areaIds.add(1);

        Integer result = assetServiceImpl.queryAssetCountByAreaIds(areaIds);
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testExportData() throws Exception {
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setAreaName("");
        assetResponse.setInstallType(0);
        assetResponse.setWaitingTaskReponse(new WaitingTaskReponse());
        assetResponse.setDescrible("");
        assetResponse.setAreaId("");
        assetResponse.setResponsibleUserName("");
        assetResponse.setAdmittanceStatus(0);
        assetResponse.setCategoryModelName("");
        assetResponse.setAssetGroup("");

        assetResponse.setNumber("");
        assetResponse.setName("");
        assetResponse.setSerial("");
        assetResponse.setCategoryModel(1);
        assetResponse.setManufacturer("");
        assetResponse.setAssetStatus(0);
//        assetResponse.setOperationSystem(1L);

        assetResponse.setUuid("");
        assetResponse.setResponsibleUserId("");

        assetResponse.setAssetSource(0);
        assetResponse.setImportanceDegree(0);

        assetResponse.setServiceLife(0L);
        assetResponse.setBuyDate(0L);
        assetResponse.setWarranty("0");
        assetResponse.setAssetGroups(Lists.newArrayList());
        assetResponse.setGmtCreate(0L);
        assetResponse.setFirstEnterNett(0L);

        assetResponse.setHouseLocation("");
        assetResponse.setStringId("");

        PageResult<AssetResponse> result = new PageResult<>();
        result.setItems(Arrays.asList(assetResponse));

        doReturn(result).when(assetServiceImpl).findPageAsset(any());
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setStart(1);
        assetQuery.setEnd(100);
        assetServiceImpl.exportData(assetQuery, new Response(), new Request());
    }

    @Test
    public void testPulldownUnconnectedManufacturer() throws Exception {
        when(assetLinkRelationDao.pulldownUnconnectedManufacturer(any())).thenReturn(Arrays.asList("String"));

        List<String> result = assetServiceImpl.pulldownUnconnectedManufacturer(1, "1");
        Assert.assertEquals(Arrays.asList("String"), result);

        result = assetServiceImpl.pulldownUnconnectedManufacturer(1, "1");
        Assert.assertEquals(Arrays.asList("String"), result);

    }

    private AssetGroup generateAssetGroup() {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setId(1);
        assetGroup.setName("abc");
        assetGroup.setMemo("1");
        assetGroup.setStatus(1);
        return assetGroup;
    }

    private AssetRequest generateAssetRequest() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setNumber("1");
        assetRequest.setName("1");
        assetRequest.setSerial("1");
        assetRequest.setAreaId("1");
        assetRequest.setManufacturer("1");
        assetRequest.setAssetStatus(0);
        assetRequest.setOperationSystem(1L);
        assetRequest.setSystemBit(0);
        assetRequest.setFirmwareVersion("1");
        assetRequest.setUuid("1");
        assetRequest.setResponsibleUserId("1");
        assetRequest.setAssetSource(0);
        assetRequest.setImportanceDegree(0);
        assetRequest.setCategoryModel(2);
        assetRequest.setServiceLife(0L);
        assetRequest.setBuyDate(0L);
        assetRequest.setWarranty("0");
        assetRequest.setId("1");
        assetRequest.setHouseLocation("1");
        assetRequest.setInstallTemplateId("1");
        assetRequest.setBaselineTemplateId("1");
        assetRequest.setInstallTemplateId("1");
        assetRequest.setAssetGroups(Lists.newArrayList());
        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");
        return assetRequest;
    }

    @Test
    public void testQueryAlarmAssetList() throws Exception {
        AlarmAssetRequest alarmAssetRequest = new AlarmAssetRequest();
        when(assetDao.queryAlarmAssetList(any())).thenReturn(Arrays.asList(generateAsset()));
        alarmAssetRequest.setIp("1.2.3.4");
        alarmAssetRequest.setMac("1a-1a-1a-1a-1a-1a");
        Assert.assertNotNull(assetServiceImpl.queryAlarmAssetList(alarmAssetRequest));
    }

    @Test
    public void testFindAssetIds() throws Exception {
        when(assetDao.findAssetIds(any())).thenReturn(Arrays.asList("1"));
        Assert.assertNotNull(assetServiceImpl.findAssetIds());
    }

    @Test
    public void testListen() {
        when(assetDao.updateAssetAreaId(any(), any())).thenReturn(1);
        AreaOperationRequest areaOperationRequest = new AreaOperationRequest();
        MockAck mockAck = new MockAck();
        assetServiceImpl.listen(JSONObject.toJSONString(areaOperationRequest), mockAck);
    }

    @Test
    public void queryWaitRegistCount() {
        Mockito.when(assetDao.queryWaitRegistCount(Mockito.anyInt(), Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", assetServiceImpl.queryWaitRegistCount() + "");
    }

    @Test
    public void queryNormalCount() {
        Mockito.when(assetDao.queryNormalCount(Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", assetServiceImpl.queryNormalCount() + "");
    }

    public void queryUuidByAreaIds() throws Exception {
        AreaIdRequest areaIdRequest = new AreaIdRequest();
        try {
            assetServiceImpl.queryUuidByAreaIds(areaIdRequest);
        } catch (Exception e) {
            Assert.assertEquals("区域ID不能为空", e.getMessage());
        }
        areaIdRequest.setAreaIds(Arrays.asList("1"));
        Mockito.when(assetDao.findUuidByAreaIds(Mockito.any())).thenReturn(Arrays.asList("1", "2"));
        Assert.assertEquals(2, assetServiceImpl.queryUuidByAreaIds(areaIdRequest).size());

    }

    @Test
    public void getAssemblyInfo() {
        Mockito.when(assetDao.getAssemblyInfoById(Mockito.any())).thenReturn(generateAssemblyList());
        Assert.assertEquals(1, assetServiceImpl.getAssemblyInfo(new QueryCondition()).size());
    }

    private List<AssetAssembly> generateAssemblyList() {
        List<AssetAssembly> result = new ArrayList<>();
        result.add(generateAssembly());
        return result;
    }

    private AssetAssembly generateAssembly() {
        AssetAssembly assetAssembly = new AssetAssembly();
        assetAssembly.setAmount(1);
        assetAssembly.setAssetId("1");
        assetAssembly.setBusinessId("1");
        assetAssembly.setProductName("1");
        return assetAssembly;
    }

    class MockAck implements Acknowledgment {
        @Override
        public void acknowledge() {

        }
    }

}
