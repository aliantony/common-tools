package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.*;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.StatusEnumUtil;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.base.BaseResponse;
import com.antiy.common.base.SysArea;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.google.common.collect.Lists;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.commons.collections.MapUtils;
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
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class })
@SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })

public class AssetServiceImplTest {
    @Mock
    AssetDao                                                           assetDao;
    @Mock
    AssetMainboradDao                                                  assetMainboradDao;
    @Mock
    AssetMemoryDao                                                     assetMemoryDao;
    @Mock
    AssetHardDiskDao                                                   assetHardDiskDao;
    @Mock
    AssetCpuDao                                                        assetCpuDao;
    @Mock
    AssetNetworkCardDao                                                assetNetworkCardDao;
    @Mock
    AssetNetworkEquipmentDao                                           assetNetworkEquipmentDao;
    @Mock
    AssetSafetyEquipmentDao                                            assetSafetyEquipmentDao;
    @Mock
    AssetSoftwareDao                                                   assetSoftwareDao;
    @Mock
    AssetSoftwareLicenseDao                                            assetSoftwareLicenseDao;
    @Mock
    AssetCategoryModelDao                                              assetCategoryModelDao;
    @SpyBean
    TransactionTemplate                                                transactionTemplate;
    @Mock
    AssetSoftwareRelationDao                                           assetSoftwareRelationDao;
    @Mock
    AssetStorageMediumDao                                              assetStorageMediumDao;
    @Mock
    AssetOperationRecordDao                                            assetOperationRecordDao;
    @Mock
    BaseConverter<AssetRequest, Asset>                                 requestConverter;
    @Spy
    BaseConverter<Asset, AssetRequest>                                 assetToRequestConverter;
    @Spy
    BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationRequest> softRelationToRequestConverter;
    @Spy
    BaseConverter<AssetMainborad, AssetMainboradRequest>               mainboradToRequestConverter;
    @Spy
    BaseConverter<AssetCpu, AssetCpuRequest>                           cpuToRequestConverter;
    @Spy
    BaseConverter<AssetNetworkCard, AssetNetworkCardRequest>           networkCardToRequestConverter;
    @Spy
    BaseConverter<AssetHardDisk, AssetHardDiskRequest>                 hardDiskToRequestConverter;
    @Spy
    BaseConverter<AssetMemory, AssetMemoryRequest>                     memoryToRequestConverter;
    @Spy
    BaseConverter<Asset, AssetResponse>                                responseConverter;
    @Spy
    BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentRequest>   safetyEquipmentToRequestConverter;
    @Spy
    BaseConverter<AssetStorageMedium, AssetStorageMediumRequest>       storageMediumToRequestConverter;
    @Spy
    BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentRequest> networkEquipmentToRequestConverter;
    @Mock
    AssetUserDao                                                       assetUserDao;
    @Mock
    AssetGroupRelationDao                                              assetGroupRelationDao;
    @Mock
    AssetChangeRecordDao                                               assetChangeRecordDao;
    @Mock
    ExcelDownloadUtil                                                  excelDownloadUtil;
    @Spy
    AssetEntityConvert                                                 assetEntityConvert;
    @Mock
    IAssetCategoryModelService                                         iAssetCategoryModelService;
    @Mock
    AssetGroupDao                                                      assetGroupDao;
    @Mock
    IAssetCategoryModelService                                         assetCategoryModelService;
    @Mock
    ActivityClient                                                     activityClient;
    @Mock
    IAssetSoftwareService                                              assetSoftwareService;
    @Mock
    AreaClient                                                         areaClient;
    @Mock
    Logger                                                             logger;
    @Mock
    AesEncoder                                                         aesEncoder;
    @Mock
    RedisUtil                                                          redisUtil;
    @Mock
    AssetLinkRelationDao                                               assetLinkRelationDao;
    @Mock
    OperatingSystemClient                                              operatingSystemClient;
    @Mock
    EmergencyClient                                                    emergencyClient;
    @Mock
    IBaseDao                                                           baseDao;
    @Spy
    @InjectMocks
    AssetServiceImpl                                                   assetServiceImpl;
    @Mock
    IRedisService                                                      redisService;
    @Mock
    AssetClient                                                        assetClient;
    @Mock
    SchemeDao                                                          schemeDao;

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
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);

        SecurityContextHolder.setContext(securityContext);
        PowerMockito.mockStatic(ExcelUtils.class);

        PowerMockito.mockStatic(RequestContextHolder.class);

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
        assetNetworkEquipment.setMacAddress("1A-1A-1A-1A-1A-1A");
        assetNetworkEquipment.setInnerIp("1.2.1.2");
        return assetNetworkEquipment;
    }

    private AssetSafetyEquipmentRequest generateAssetSafetyEquipmentRequest() {
        AssetSafetyEquipmentRequest assetSafetyEquipmentRequest = new AssetSafetyEquipmentRequest();
        assetSafetyEquipmentRequest.setId("1");
        assetSafetyEquipmentRequest.setAssetId("1");
        assetSafetyEquipmentRequest.setFeatureLibrary("1");
        assetSafetyEquipmentRequest.setIp("1");
        assetSafetyEquipmentRequest.setMac("1");
        assetSafetyEquipmentRequest.setMemo("1");
        assetSafetyEquipmentRequest.setNewVersion("1");
        assetSafetyEquipmentRequest.setStrategy("1");
        return assetSafetyEquipmentRequest;
    }

    private AssetNetworkEquipmentRequest genrateAssetNetworkEquipmentRequest() {
        AssetNetworkEquipmentRequest assetNetworkEquipment = new AssetNetworkEquipmentRequest();
        assetNetworkEquipment.setAssetId("1");
        assetNetworkEquipment.setId("1");
        assetNetworkEquipment.setPortSize(1);
        assetNetworkEquipment.setInnerIp("192.188.1.1");
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
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetUserDao.getById(any())).thenReturn(new AssetUser());
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        when(assetGroupDao.getById(any())).thenReturn(generateAssetGroup());

        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(assetCategoryModelDao.getById(any())).thenReturn(new AssetCategoryModel());
        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(new SysArea());
        when(assetSoftwareService.configRegister(any(), any())).thenReturn(ActionResponse.success());
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
        ActionResponse result = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals("200", result.getHead().getCode());

        // 带网卡的资产
        List<AssetNetworkCardRequest> networkCardRequests = new ArrayList<>();
        networkCardRequests.add(generateAssetNetworkCardRequest());
        assetOuterRequest.setNetworkCard(networkCardRequests);
        ActionResponse result1 = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals("200", result1.getHead().getCode());

        // 带主板的资产
        List<AssetMainboradRequest> mainboradRequests = new ArrayList<>();
        AssetMainboradRequest assetMainboradRequest = new AssetMainboradRequest();
        assetMainboradRequest.setAssetId("1");
        assetMainboradRequest.setBrand("1");
        mainboradRequests.add(assetMainboradRequest);
        assetOuterRequest.setMainboard(mainboradRequests);
        ActionResponse result2 = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals("200", result2.getHead().getCode());

        // 带内存的资产
        List<AssetMemoryRequest> memoryRequests = new ArrayList<>();
        AssetMemoryRequest assetMemoryRequest = new AssetMemoryRequest();
        assetMemoryRequest.setAssetId("1");
        assetMemoryRequest.setBrand("1");
        memoryRequests.add(assetMemoryRequest);
        assetOuterRequest.setMemory(memoryRequests);
        ActionResponse result3 = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals("200", result3.getHead().getCode());

        // 带cpu的资产
        List<AssetCpuRequest> cpuRequests = new ArrayList<>();
        AssetCpuRequest assetCpuRequest = new AssetCpuRequest();
        assetCpuRequest.setAssetId("1");
        assetCpuRequest.setBrand("1");
        cpuRequests.add(assetCpuRequest);
        assetOuterRequest.setCpu(cpuRequests);
        ActionResponse result4 = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals("200", result4.getHead().getCode());

        // 带硬盘的资产
        List<AssetHardDiskRequest> hardDisks = new ArrayList<>();
        AssetHardDiskRequest assetHardDiskRequest = new AssetHardDiskRequest();
        assetHardDiskRequest.setAssetId("1");
        assetHardDiskRequest.setBrand("1");
        hardDisks.add(assetHardDiskRequest);
        assetOuterRequest.setHardDisk(hardDisks);
        ActionResponse result5 = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals("200", result5.getHead().getCode());

        // 安全设备
        AssetOuterRequest assetOuterRequest1 = new AssetOuterRequest();
        assetOuterRequest1.setAsset(assetRequest);
        assetOuterRequest1.setSafetyEquipment(generateAssetSafetyEquipmentRequest());
        assetOuterRequest1.setManualStartActivityRequest(generateAssetManualStart());
        assetOuterRequest.setAsset(assetRequest);

        ActionResponse result6 = assetServiceImpl.saveAsset(assetOuterRequest1);
        Assert.assertEquals("200", result6.getHead().getCode());

        // 网络设备
        AssetOuterRequest assetOuterRequest2 = new AssetOuterRequest();
        assetOuterRequest2.setAsset(assetRequest);
        assetOuterRequest2.setNetworkEquipment(genrateAssetNetworkEquipmentRequest());
        assetOuterRequest2.setManualStartActivityRequest(generateAssetManualStart());
        assetOuterRequest.setAsset(assetRequest);

        ActionResponse result7 = assetServiceImpl.saveAsset(assetOuterRequest2);
        Assert.assertEquals("200", result7.getHead().getCode());

        // 存储设备
        AssetOuterRequest assetOuterRequest3 = new AssetOuterRequest();
        assetOuterRequest3.setAsset(assetRequest);
        assetOuterRequest3.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest3.setManualStartActivityRequest(generateAssetManualStart());
        ActionResponse result8 = assetServiceImpl.saveAsset(assetOuterRequest3);
        Assert.assertEquals("200", result8.getHead().getCode());

        // 其它设备
        AssetOuterRequest assetOuterRequest4 = new AssetOuterRequest();
        assetOuterRequest4.setAssetOthersRequest(generateAssetOtherRequest());
        assetOuterRequest4.setManualStartActivityRequest(generateAssetManualStart());
        ActionResponse result9 = assetServiceImpl.saveAsset(assetOuterRequest4);
        Assert.assertEquals("200", result9.getHead().getCode());

        // ip重复
        when(assetDao.findCountIp(any())).thenReturn(10);
        try {
            assetServiceImpl.saveAsset(assetOuterRequest1);
        } catch (Exception e) {
            Assert.assertEquals("IP不能重复！", e.getMessage());
        }

        // 区域注销
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(null);
        try {
            assetServiceImpl.saveAsset(assetOuterRequest1);
        } catch (Exception e) {
            Assert.assertEquals("当前区域不存在，或已经注销", e.getMessage());
        }

    }

    public AssetStorageMediumRequest generateAssetStorageMediumRequest() {
        AssetStorageMediumRequest assetStorageMediumRequest = new AssetStorageMediumRequest();
        assetStorageMediumRequest.setAssetId("1");
        assetStorageMediumRequest.setId("1");
        return assetStorageMediumRequest;
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
        List<LinkedHashMap> linkedHashMaps = new ArrayList<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("stringId", "1");
        linkedHashMap.put("name", "windows");
        linkedHashMaps.add(linkedHashMap);
        when(redisService.getAllSystemOs()).thenReturn(linkedHashMaps);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success();

        PageResult pageResult = new PageResult();
        pageResult.setItems(idCounts);
        actionResponse.setBody(Arrays.asList(generateWaitingTask()));
        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);
        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(assetGroupRelationDao.findAssetIdByAssetGroupId(any())).thenReturn(Arrays.asList("1"));
        when(emergencyClient.queryEmergencyCount(any())).thenReturn(ActionResponse.success(pageResult));
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setQueryPatchCount(true);
        assetQuery.setQueryVulCount(true);
        assetQuery.setQueryAlarmCount(true);
        assetQuery.setCategoryModels(new String[] { "1" });
        assetQuery.setAssetGroupQuery(true);

        PageResult<AssetResponse> result = assetServiceImpl.findPageAsset(assetQuery);
        Assert.assertNotNull(result);

        // enterControl
        assetQuery = new AssetQuery();
        assetQuery.setAssetGroup("1");
        assetQuery.setAssociateGroup(true);
        assetQuery.setEnterControl(true);
        assetQuery.setGroupId("1");
        result = assetServiceImpl.findPageAsset(assetQuery);
        Assert.assertNotNull(result);

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
        Integer result = assetServiceImpl.findCountAssetNumber(new AssetQuery());
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testFindPageAsset() throws Exception {
        when(assetDao.findListAsset(any())).thenReturn(Arrays.asList(new Asset()));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt())).thenReturn(Arrays.asList(0));

        ActionResponse<List<WaitingTaskReponse>> actionResponse = ActionResponse.success(new ArrayList<>());
        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);

        PageResult<AssetResponse> result = assetServiceImpl.findPageAsset(new AssetQuery());
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindUnconnectedAsset() throws Exception {
        when(assetLinkRelationDao.findUnconnectedCount(any())).thenReturn(0);
        when(assetLinkRelationDao.findListUnconnectedAsset(any())).thenReturn(Arrays.asList(new Asset()));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt())).thenReturn(Arrays.asList(0));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(iAssetCategoryModelService.recursionSearchParentCategory(anyString(), any(), any()))
            .thenReturn("recursionSearchParentCategoryResponse");
        when(iAssetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {
            {
                put("String", "String");
            }
        });
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.recursionSearchParentCategory(anyString(), any(), any()))
            .thenReturn("recursionSearchParentCategoryResponse");
        when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {
            {
                put("String", "String");
            }
        });

        PageResult<AssetResponse> result = assetServiceImpl.findUnconnectedAsset(new AssetQuery());
        Assert.assertNotNull(result);
        when(assetLinkRelationDao.findUnconnectedCount(any())).thenReturn(5);

        Asset asset1 = generateAsset();
        asset1.setCategoryModel("1");
        Asset asset2 = generateAsset();
        asset2.setCategoryModel("1");
        when(assetLinkRelationDao.findListUnconnectedAsset(any())).thenReturn(Arrays.asList(asset1, asset2));
        when(iAssetCategoryModelService.getAll()).thenReturn(Arrays.asList(generateCategoryModal()));
        Map map = new HashMap();
        map.put("1", "1");
        when(iAssetCategoryModelService.getSecondCategoryMap()).thenReturn(map);
        when(iAssetCategoryModelService.recursionSearchParentCategory(any(), any(), any())).thenReturn("1");
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setCategoryModels(new String[] { "1" });
        result = assetServiceImpl.findUnconnectedAsset(assetQuery);
        Assert.assertNotNull(result);

    }

    private AssetCategoryModel generateCategoryModal() {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setName("1");
        assetCategoryModel.setId(1);
        assetCategoryModel.setType(1);
        return assetCategoryModel;
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

        List<String> result = assetServiceImpl.pulldownManufacturer();
        Assert.assertEquals(Arrays.asList("String"), result);
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
        when(assetCategoryModelDao.getNextLevelCategoryByName(anyString()))
            .thenReturn(Arrays.asList(new AssetCategoryModel()));
        when(iAssetCategoryModelService.recursionSearch(any(), anyInt()))
            .thenReturn(Arrays.asList(new AssetCategoryModel()));
        when(assetCategoryModelService.recursionSearch(any(), anyInt()))
            .thenReturn(Arrays.asList(new AssetCategoryModel()));

        List<EnumCountResponse> result = assetServiceImpl.countCategory();
        Assert.assertNotNull(result);
    }

    @Test
    public void testQueryAssetByIds() throws Exception {
        when(assetDao.queryAssetByIds(any())).thenReturn(Arrays.asList(new Asset()));

        List<AssetResponse> result = assetServiceImpl.queryAssetByIds(new Integer[] { 0 });
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetByAssetId() throws Exception {
        when(assetDao.findListAsset(any())).thenReturn(Arrays.asList(generateAsset()));
        when(assetSoftwareRelationDao.getSoftByAssetId(anyInt())).thenReturn(Arrays.asList(new AssetSoftware()));
        when(assetSoftwareRelationDao.getReleationByAssetId(anyInt()))
            .thenReturn(Arrays.asList(new AssetSoftwareRelation()));
        when(assetGroupRelationDao.queryByAssetId(anyInt())).thenReturn(Arrays.asList(new AssetGroup()));

        AssetDetialCondition condition = new AssetDetialCondition();
        condition.setIsNeedCpu(false);
        condition.setIsNeedNetwork(false);
        condition.setIsNeedMemory(false);
        condition.setIsNeedMainboard(false);
        condition.setIsNeedHarddisk(false);
        condition.setIsNeedSoftware(false);
        condition.setPrimaryKey("1");

        AssetOuterResponse result = assetServiceImpl.getByAssetId(condition);
        Assert.assertNotNull(result);
    }

    private AssetNetworkCard generateAssetNetworkCard() {
        AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
        assetNetworkCard.setNetworkAddress("");
        assetNetworkCard.setAssetId("");
        assetNetworkCard.setBrand("");
        assetNetworkCard.setModel("");
        assetNetworkCard.setSerial("");
        assetNetworkCard.setIpAddress("");
        assetNetworkCard.setMacAddress("");
        assetNetworkCard.setDefaultGateway("");
        assetNetworkCard.setSubnetMask("");
        assetNetworkCard.setGmtCreate(0L);
        assetNetworkCard.setGmtModified(0L);
        assetNetworkCard.setMemo("");
        assetNetworkCard.setCreateUser(0);
        assetNetworkCard.setModifyUser(0);
        assetNetworkCard.setStatus(0);
        assetNetworkCard.setId(0);
        return assetNetworkCard;
    }

    private Asset generateAsset() {
        Asset asset = new Asset();
        asset.setOperationSystemName("");
        asset.setAreaName("");
        asset.setResponsibleUserName("");
        asset.setCategoryModelName("");
        asset.setHardDisk("1");
        asset.setMemory("1");
        asset.setCpu("1");
        asset.setNetworkCard("");
        asset.setParentId("1");
        asset.setIp("");
        asset.setMac("");
        asset.setAssetGroup("");
        asset.setNumber("");
        asset.setName("");
        asset.setEthernetPort(0);
        asset.setSerialPort(0);
        asset.setInstallType(0);
        asset.setSerial("");
        asset.setAreaId("1");
        asset.setCategoryModel("");
        asset.setManufacturer("");
        asset.setAssetStatus(8);
        asset.setAdmittanceStatus(0);
        asset.setOperationSystem("1");
        asset.setSystemBit(0);
        asset.setLocation("");
        asset.setLatitude("");
        asset.setLongitude("");
        asset.setHouseLocation("");
        asset.setFirmwareVersion("");
        asset.setUuid("");
        asset.setContactTel("");
        asset.setEmail("");
        asset.setAssetSource(0);
        asset.setImportanceDegree(0);
        asset.setDescrible("");
        asset.setTags("");
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
        when(assetDao.findCountIp(any())).thenReturn(0);

        when(assetNetworkCardDao.getById(any())).thenReturn(generateAssetNetworkCard());
        when(assetMainboradDao.updateBatch(any())).thenReturn(0);
        when(assetMainboradDao.insertBatch(any())).thenReturn(0);
        when(assetMainboradDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetMemoryDao.updateBatch(any())).thenReturn(0);
        when(assetMemoryDao.insertBatch(any())).thenReturn(0);
        when(assetMemoryDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetHardDiskDao.updateBatch(any())).thenReturn(0);
        when(assetHardDiskDao.insertBatch(any())).thenReturn(0);
        when(assetHardDiskDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetCpuDao.updateBatch(any())).thenReturn(0);
        when(assetCpuDao.insertBatch(any())).thenReturn(0);
        when(assetCpuDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetNetworkCardDao.updateBatch(any())).thenReturn(0);
        when(assetNetworkCardDao.insertBatch(any())).thenReturn(0);
        when(assetNetworkCardDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetNetworkEquipmentDao.getById(any())).thenReturn(generateAssetNetworkEquipment());
        when(assetSoftwareRelationDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetGroupRelationDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(activityClient.completeTask(any())).thenReturn(ActionResponse.success());
        when(assetGroupDao.getById(any())).thenReturn(generateAssetGroup());
        when(assetLinkRelationDao.deleteRelationByAssetId(any())).thenReturn(0);
        when(iAssetCategoryModelService.recursionSearchParentCategory(anyString(), any(), any())).thenReturn("1");
        when(assetCategoryModelDao.getByName(anyString())).thenReturn(new AssetCategoryModel());
        when(assetDao.changeAsset(any())).thenReturn(10);
        when(assetNetworkCardDao.findListAssetNetworkCard(any())).thenReturn(Arrays.asList(generateNetworkCart()));
        when(assetSoftwareService.configRegister(any(), any())).thenReturn(ActionResponse.success());
        List<AssetGroupRequest> assetGroupRequests = new ArrayList<>();
        assetGroupRequests.add(generateAssetGroupRequest());
        Asset asset = generateAsset();
        asset.setAssetStatus(AssetStatusEnum.RETIRE.getCode());
        when(assetDao.getById(any())).thenReturn(asset);
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = generateAssetRequest();
        assetRequest.setAssetGroups(assetGroupRequests);
        assetOuterRequest.setAsset(assetRequest);
        // cpu
        assetOuterRequest.setCpu(Arrays.asList(generateAssetCpuRequest()));

        // 主板信息
        List<AssetMainboradRequest> assetMainboradRequestList = Arrays.asList(generateAssetMainboradRequest());
        assetOuterRequest.setMainboard(assetMainboradRequestList);

        // 内存信息
        List<AssetMemoryRequest> assetMemoryRequestList = Arrays.asList(generateAssetMemoryRequest());
        assetOuterRequest.setMemory(assetMemoryRequestList);

        // 硬盘信息
        List<AssetHardDiskRequest> assetHardDiskRequestList = Arrays.asList(generateHardDiskRequest());
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);

        // 网卡信息
        List<AssetNetworkCardRequest> assetNetworkCardRequests = Arrays.asList(generateAssetNetworkCardRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        int result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        // 安全设备变更
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setSafetyEquipment(generateAssetSafetyEquipmentRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        // 网络设备变更
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setNetworkEquipment(genrateAssetNetworkEquipmentRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);

        // 存储设备变更
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(10, result);
        when(assetNetworkCardDao.findListAssetNetworkCard(any())).thenReturn(Arrays.asList(generateNetworkCart()));

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        when(assetDao.getById(any())).thenReturn(asset);
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(null);
        assetServiceImpl.changeAsset(assetOuterRequest);

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.NOT_REGSIST.getCode());
        assetRequest.setAssetStatus(AssetStatusEnum.NOT_REGSIST.getCode());

        when(assetDao.getById(any())).thenReturn(asset);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(null);
        assetServiceImpl.changeAsset(assetOuterRequest);

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        assetRequest.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());

        when(assetDao.getById(any())).thenReturn(asset);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(null);
        assetServiceImpl.changeAsset(assetOuterRequest);

        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        when(assetDao.getById(any())).thenReturn(asset);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());
        try{
            assetServiceImpl.changeAsset(assetOuterRequest);
        }catch (Exception e){
            Assert.assertEquals("请勿重复提交，当前资产状态是：已入网", e.getMessage());

        }


        // 存储设备变更
        when(assetNetworkCardDao.findListAssetNetworkCard(any())).thenThrow(new Exception("资产变更失败"));
        assetOuterRequest = new AssetOuterRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setAssetStorageMedium(generateAssetStorageMediumRequest());
        assetOuterRequest.setNetworkCard(assetNetworkCardRequests);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);
        assetOuterRequest.setMemory(assetMemoryRequestList);
        assetOuterRequest.setMainboard(assetMainboradRequestList);
        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());

        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("资产变更失败", e.getMessage());
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
        memoryRequest.setHeatsink(0);
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
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setName("计算设备");
        assetCategoryModel.setType(1);
        assetCategoryModel.setAssetType(0);
        assetCategoryModel.setDescription("");
        assetCategoryModel.setGmtCreate(0L);
        assetCategoryModel.setMemo("");
        assetCategoryModel.setCreateUser(0);
        assetCategoryModel.setModifyUser(0);
        assetCategoryModel.setStatus(0);
        assetCategoryModel.setIsDefault(0);
        assetCategoryModel.setParentId("");
        assetCategoryModel.setGmtModified(0L);
        assetCategoryModel.setId(4);

        AssetCategoryModel assetCategoryModel2 = new AssetCategoryModel();
        assetCategoryModel2.setName("网络设备");
        assetCategoryModel2.setType(1);
        assetCategoryModel2.setAssetType(0);
        assetCategoryModel2.setDescription("");
        assetCategoryModel2.setGmtCreate(0L);
        assetCategoryModel2.setMemo("");
        assetCategoryModel2.setCreateUser(0);
        assetCategoryModel2.setModifyUser(0);
        assetCategoryModel2.setStatus(0);
        assetCategoryModel2.setIsDefault(0);
        assetCategoryModel2.setParentId("");
        assetCategoryModel2.setGmtModified(0L);
        assetCategoryModel2.setId(5);

        AssetCategoryModel assetCategoryModel3 = new AssetCategoryModel();
        assetCategoryModel3.setName("存储设备");
        assetCategoryModel3.setType(1);
        assetCategoryModel3.setAssetType(0);
        assetCategoryModel3.setDescription("");
        assetCategoryModel3.setGmtCreate(0L);
        assetCategoryModel3.setMemo("");
        assetCategoryModel3.setCreateUser(0);
        assetCategoryModel3.setModifyUser(0);
        assetCategoryModel3.setStatus(0);
        assetCategoryModel3.setIsDefault(0);
        assetCategoryModel3.setParentId("");
        assetCategoryModel3.setGmtModified(0L);
        assetCategoryModel3.setId(6);

        AssetCategoryModel assetCategoryModel4 = new AssetCategoryModel();
        assetCategoryModel4.setName("其它设备");
        assetCategoryModel4.setType(1);
        assetCategoryModel4.setAssetType(0);
        assetCategoryModel4.setDescription("");
        assetCategoryModel4.setGmtCreate(0L);
        assetCategoryModel4.setMemo("");
        assetCategoryModel4.setCreateUser(0);
        assetCategoryModel4.setModifyUser(0);
        assetCategoryModel4.setStatus(0);
        assetCategoryModel4.setIsDefault(0);
        assetCategoryModel4.setParentId("");
        assetCategoryModel4.setGmtModified(0L);
        assetCategoryModel4.setId(7);

        AssetCategoryModel assetCategoryModel5 = new AssetCategoryModel();
        assetCategoryModel5.setName("安全设备");
        assetCategoryModel5.setType(1);
        assetCategoryModel5.setAssetType(0);
        assetCategoryModel5.setDescription("");
        assetCategoryModel5.setGmtCreate(0L);
        assetCategoryModel5.setMemo("");
        assetCategoryModel5.setCreateUser(0);
        assetCategoryModel5.setModifyUser(0);
        assetCategoryModel5.setStatus(0);
        assetCategoryModel5.setIsDefault(0);
        assetCategoryModel5.setParentId("");
        assetCategoryModel5.setGmtModified(0L);
        assetCategoryModel5.setId(8);

        when(assetCategoryModelDao.getNextLevelCategoryByName(anyString())).thenReturn(Arrays.asList(assetCategoryModel,
            assetCategoryModel2, assetCategoryModel3, assetCategoryModel4, assetCategoryModel5));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));
        assetServiceImpl.exportTemplate(new String[] { "4", "5", "6", "7", "8" });
    }

    @Test
    public void testImportPc() throws Exception {
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetMainboradDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetMemoryDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetHardDiskDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetCpuDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        when(assetCategoryModelDao.getById(anyInt())).thenReturn(new AssetCategoryModel());
        List<BaselineCategoryModelResponse> baselineCategoryModelResponses = new ArrayList<>();
        BaselineCategoryModelResponse baselineCategoryModelResponse = new BaselineCategoryModelResponse();
        baselineCategoryModelResponse.setStringId("1");
        baselineCategoryModelResponse.setName("1");
        baselineCategoryModelResponses.add(baselineCategoryModelResponse);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);

        ImportResult importResult = new ImportResult();
        List<ComputeDeviceEntity> computeDeviceEntities = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = getComputeDeviceEntity();
        computeDeviceEntities.add(computeDeviceEntity);
        importResult.setMsg("");
        importResult.setDataList(computeDeviceEntities);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("4");
        String result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        importResult = new ImportResult();
        computeDeviceEntities = new ArrayList<>();
        computeDeviceEntity = getComputeDeviceEntity();
        ComputeDeviceEntity computeDeviceEntity1 = getComputeDeviceEntity();
        computeDeviceEntities.add(computeDeviceEntity);
        computeDeviceEntities.add(computeDeviceEntity1);
        importResult.setDataList(computeDeviceEntities);
        importResult.setMsg("");

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产名称重复！", result);

        computeDeviceEntity.setName("1");
        computeDeviceEntity1.setName("2");
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        computeDeviceEntity.setNumber("1");
        computeDeviceEntity1.setNumber("2");
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产网卡IP地址重复！", result);

        when(assetDao.findCount(any())).thenReturn(10);
        importResult.getDataList().remove(1);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产名称重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        when(assetDao.findCountIp(any())).thenReturn(10);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产网卡IP地址重复！", result);

        when(assetDao.findCountIp(any())).thenReturn(0);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        computeDeviceEntity.setBuyDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        computeDeviceEntity.setBuyDate(System.currentTimeMillis() / 2);
        computeDeviceEntity.setDueTime(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        computeDeviceEntity.setDueTime(System.currentTimeMillis() * 2);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统中没有此使用者，或已被注销！", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        computeDeviceEntity.setDueTime(System.currentTimeMillis() * 2);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(new ArrayList<>());
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行操作系统不存在，或已被注销！", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        computeDeviceEntity.setArea("qwedwafdwaddwa");
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        computeDeviceEntity.setArea("四川");
        computeDeviceEntity.setHardDiskBuyDate(System.currentTimeMillis() * 2);
        computeDeviceEntity.setHardDisCapacityl(1);
        computeDeviceEntity.setHardDiskType(1);

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行硬盘购买时间需小于等于今天！", result);

    }

    private ComputeDeviceEntity getComputeDeviceEntity() {
        ComputeDeviceEntity computeDeviceEntity = new ComputeDeviceEntity();
        computeDeviceEntity.setOperationSystem("1");
        computeDeviceEntity.setArea("四川");
        computeDeviceEntity.setMemoryCapacity(1);
        computeDeviceEntity.setHardDisCapacityl(1);
        computeDeviceEntity.setHardDiskType(1);
        computeDeviceEntity.setMainboradBrand("1");
        computeDeviceEntity.setCpuMainFrequency(1f);
        computeDeviceEntity.setDueTime(System.currentTimeMillis() + 1);
        computeDeviceEntity.setUser("1");
        computeDeviceEntity.setImportanceDegree("1");
        computeDeviceEntity.setNetworkIpAddress("1.1.1.1");
        return computeDeviceEntity;
    }

    @Test
    public void testImportNet() throws Exception {
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetMainboradDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetMemoryDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetHardDiskDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetCpuDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        when(assetCategoryModelDao.getById(anyInt())).thenReturn(new AssetCategoryModel());
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
        Assert.assertEquals("导入失败，第8行资产名称重复！", result);

        networkDeviceEntity.setName("1");
        networkDeviceEntity1.setName("2");
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        networkDeviceEntity.setNumber("1");
        networkDeviceEntity1.setNumber("2");
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产内网IP地址重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产名称重复！", result);

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

        when(assetDao.findCountIp(any())).thenReturn(10);
        networkDeviceEntity.setDueDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产内网IP地址重复！", result);

        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统没有此使用者，或已被注销！", result);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        networkDeviceEntity.setArea("sdfsadfasd");
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

    }

    private NetworkDeviceEntity getNetworkDeviceEntity() {
        NetworkDeviceEntity networkDeviceEntity = new NetworkDeviceEntity();
        networkDeviceEntity.setArea("四川");
        networkDeviceEntity.setDueDate(System.currentTimeMillis() + 1);
        networkDeviceEntity.setUser("1");
        networkDeviceEntity.setButDate(System.currentTimeMillis() / 2);
        networkDeviceEntity.setPortSize(1);
        networkDeviceEntity.setImportanceDegree("1");
        return networkDeviceEntity;
    }

    @Test
    public void testImportSecurity() throws Exception {
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetMainboradDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetMemoryDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetHardDiskDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetCpuDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        when(assetCategoryModelDao.getById(anyInt())).thenReturn(new AssetCategoryModel());
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
        Assert.assertEquals("导入失败，第8行资产名称重复！", result);

        safetyEquipmentEntiy.setName("1");
        safetyEquipmentEntiy1.setName("2");
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        safetyEquipmentEntiy.setNumber("1");
        safetyEquipmentEntiy1.setNumber("2");
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产IP地址重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产名称重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        when(assetDao.findCountIp(any())).thenReturn(10);
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产IP地址重复！", result);

        when(assetDao.findCountIp(any())).thenReturn(0);
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
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetMainboradDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetMemoryDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetHardDiskDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetCpuDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
        when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
        when(assetCategoryModelDao.getById(anyInt())).thenReturn(new AssetCategoryModel());
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
        Assert.assertEquals("导入失败，第8行资产名称重复！", result);

        storageDeviceEntity.setName("1");
        storageDeviceEntity1.setName("2");
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产名称重复！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        storageDeviceEntity.setBuyDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

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
        when(assetCategoryModelDao.getById(anyInt())).thenReturn(new AssetCategoryModel());
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
        Assert.assertEquals("导入失败，第8行资产名称重复！", result);

        otherDeviceEntity.setName("1");
        otherDeviceEntity1.setName("2");
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产名称重复！", result);

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
        assetResponse.setIp("");
        assetResponse.setMac("");
        assetResponse.setNumber("");
        assetResponse.setName("");
        assetResponse.setSerial("");
        assetResponse.setCategoryModel("");
        assetResponse.setManufacturer("");
        assetResponse.setAssetStatus(0);
        assetResponse.setOperationSystem("");
        assetResponse.setSystemBit(0);
        assetResponse.setFirmwareVersion("");
        assetResponse.setUuid("");
        assetResponse.setResponsibleUserId("");
        assetResponse.setContactTel("");
        assetResponse.setEmail("");
        assetResponse.setAssetSource(0);
        assetResponse.setImportanceDegree(0);
        assetResponse.setParentId(0 + "");
        assetResponse.setTags("");
        assetResponse.setServiceLife(0L);
        assetResponse.setBuyDate(0L);
        assetResponse.setWarranty("0");
        assetResponse.setAssetGroups(Lists.newArrayList());
        assetResponse.setGmtCreate(0L);
        assetResponse.setFirstEnterNett(0L);
        assetResponse.setLocation("");
        assetResponse.setHouseLocation("");
        assetResponse.setStringId("");

        PageResult<AssetResponse> result = new PageResult<>();
        result.setItems(Arrays.asList(assetResponse));

        doReturn(result).when(assetServiceImpl).findPageAsset(any());

        assetServiceImpl.exportData(new AssetQuery(), new Response(), new Request());
    }

    @Test
    public void testPulldownUnconnectedManufacturer() throws Exception {
        when(assetLinkRelationDao.pulldownUnconnectedManufacturer(any())).thenReturn(Arrays.asList("String"));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(iAssetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {
            {
                put("String", "String");
            }
        });
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {
            {
                put("String", "String");
            }
        });

        List<String> result = assetServiceImpl.pulldownUnconnectedManufacturer(1, "1");
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
        assetRequest.setOperationSystem("1");
        assetRequest.setSystemBit(0);
        assetRequest.setLocation("四川省");
        assetRequest.setFirmwareVersion("1");
        assetRequest.setUuid("1");
        assetRequest.setResponsibleUserId("1");
        assetRequest.setAssetSource(0);
        assetRequest.setImportanceDegree(0);
        assetRequest.setCategoryModel("1");
        assetRequest.setParentId("1");
        assetRequest.setTags("1");
        assetRequest.setServiceLife(0L);
        assetRequest.setBuyDate(0L);
        assetRequest.setWarranty("0");
        assetRequest.setId("1");
        assetRequest.setEmail("709976999@qq.com");
        assetRequest.setContactTel("1");
        assetRequest.setHouseLocation("1");
        assetRequest.setAssetGroups(Lists.newArrayList());
        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");
        return assetRequest;
    }

    private AssetNetworkCard generateNetworkCart() {
        AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
        assetNetworkCard.setAssetId("1");
        assetNetworkCard.setId(1);
        assetNetworkCard.setBrand("1");
        assetNetworkCard.setIpAddress("12.12.12.12");
        assetNetworkCard.setMacAddress("1A-1A-1A-1A-1A-1A");
        return assetNetworkCard;
    }

    @Test
    public void testChangeToNextStatus() throws Exception {
        AssetStatusJumpRequst assetStatusJumpRequst = new AssetStatusJumpRequst();
        assetStatusJumpRequst.setAgree(true);
        assetStatusJumpRequst.setAssetId("1");
        assetStatusJumpRequst.setAssetStatusEnum(AssetStatusEnum.WAIT_SETTING);
        SchemeRequest schemeRequest = new SchemeRequest();
        assetStatusJumpRequst.setSchemeRequest(schemeRequest);
        Assert.assertEquals(RespBasicCode.SUCCESS, assetServiceImpl.changeToNextStatus(assetStatusJumpRequst));

        when(schemeDao.insert(any())).thenReturn(1);
        assetStatusJumpRequst.setAssetStatusEnum(AssetStatusEnum.WAIT_VALIDATE);

        Assert.assertEquals(RespBasicCode.SUCCESS, assetServiceImpl.changeToNextStatus(assetStatusJumpRequst));
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

    class MockAck implements Acknowledgment {
        @Override
        public void acknowledge() {

        }
    }

}
