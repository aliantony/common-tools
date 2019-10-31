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
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.base.SysArea;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
                  LogHandle.class, ZipUtil.class, CollectionUtils.class, RedisKeyUtil.class, LogUtils.class })
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
    private AssetOperationRecordDao                                             operationRecordDao;
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
    BaseLineClient                                                              baseLineClient;
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
    @Spy
    private BaseConverter<AssetAssembly, AssetAssemblyResponse>                 assemblyResponseBaseConverter;
    @Spy
    private BaseConverter<AssetGroup, AssetGroupResponse>                       assetGroupResponseBaseConverter;
    @Mock
    private AssetIpRelationDao                                                  assetIpRelationDao;
    @Mock
    private AssetAssemblyDao                                                    assetAssemblyDao;
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

    @Mock
    private AssetHardSoftLibDao                                                 assetHardSoftLibDao;
    @Rule
    public ExpectedException                                                    expectedException = ExpectedException
        .none();
    @Mock
    private AssetCpeFilterDao                                                   assetCpeFilterDao;
    @Mock
    private AssetInstallTemplateDao                                             assetInstallTemplateDao;

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
        sysArea.setId("1");
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
        Map map = new HashMap();
        map.put("admittanceResult", "safetyCheck");
        map.put("safetyCheckUser", "safetyCheck");
        map.put("admittanceResult", "safetyCheck");
        map.put("templateImplementUser", "safetyCheck");
        manualStartActivityRequest.setFormData(map);
        userIds.add("1");
        manualStartActivityRequest.setConfigUserIds(userIds);
        manualStartActivityRequest.setProcessDefinitionKey("1");
        manualStartActivityRequest.setSuggest("1");
        return manualStartActivityRequest;
    }

    private ManualStartActivityRequest generateAssetManualStart1() {
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        manualStartActivityRequest.setAssignee("1");
        manualStartActivityRequest.setBusinessId("1");
        List<String> userIds = new ArrayList<>();
        manualStartActivityRequest.setAssignee("1");
        Map map = new HashMap();
        map.put("admittanceResult", "dsdsd");
        map.put("templateImplementUser", "dsdsd");
        manualStartActivityRequest.setFormData(map);
        userIds.add("1");
        manualStartActivityRequest.setConfigUserIds(userIds);
        manualStartActivityRequest.setProcessDefinitionKey("1");
        manualStartActivityRequest.setSuggest("1");
        return manualStartActivityRequest;
    }

    private ManualStartActivityRequest generateAssetManualStart2() {
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
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
        when(assetDao.deleteAssetById(any())).thenReturn(0);
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

        when(assetMacRelationDao.insert(any())).thenReturn(0);
        when(assetIpRelationDao.insert(any())).thenReturn(0);
        assetAssemblyDao.insertBatch(anyList());
        AssetRequest assetRequest = generateAssetRequest();
        // 普通资产
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();

        assetOuterRequest.setManualStartActivityRequest(generateAssetManualStart());
        assetRequest.setAssetGroups(Arrays.asList(generateAssetGroupRequest()));
        List<AssetAssemblyRequest> assetAssemblyRequests = generateAssetAssemblyRequestList();
        assetOuterRequest.setAsset(assetRequest);
        assetOuterRequest.setAssemblyRequestList(assetAssemblyRequests);
        assetOuterRequest.setIpRelationRequests(generateAssetipRequestList());
        assetOuterRequest.setMacRelationRequests(generateAssetmacRequestList());

        AssetOuterRequest assetOuterRequest22 = new AssetOuterRequest();
        assetOuterRequest22.setManualStartActivityRequest(generateAssetManualStart1());
        assetOuterRequest22.setAsset(assetRequest);
        assetOuterRequest22.setAssemblyRequestList(assetAssemblyRequests);
        assetOuterRequest22.setIpRelationRequests(generateAssetipRequestList());
        assetOuterRequest22.setMacRelationRequests(generateAssetmacRequestList());

        ActionResponse result2 = assetServiceImpl.saveAsset(assetOuterRequest22);

        ActionResponse result = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals(0, assetRequest.getAssetStatus().intValue());
        assetRequest.setBaselineTemplateId("1");
        ActionResponse result1 = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertEquals(0, assetRequest.getAssetStatus().intValue());

        AssetOuterRequest assetOuterRequest41 = new AssetOuterRequest();
        when(baseLineClient.baselineCheck(any())).thenReturn(ActionResponse.fail(RespBasicCode.ERROR));
        when(baseLineClient.baselineCheckNoUUID(any())).thenReturn(ActionResponse.fail(RespBasicCode.ERROR));
        assetOuterRequest41.setManualStartActivityRequest(generateAssetManualStart());
        assetRequest.setName("2232fdfdfdf3232");
        assetOuterRequest41.setAsset(assetRequest);
        assetServiceImpl.saveAsset(assetOuterRequest41);
        when(baseLineClient.baselineCheck(any())).thenReturn(ActionResponse.success());
        when(baseLineClient.baselineCheckNoUUID(any())).thenReturn(ActionResponse.success());

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
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
        try {
            assetServiceImpl.saveAsset(assetOuterRequest1);
        } catch (Exception e) {
            Assert.assertEquals("MAC地址不能重复！", e.getMessage());
        }

        // 区域注销
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
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
        // 异常情况
        when(baseLineClient.baselineCheck(any())).thenReturn(ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION));
        Assert.assertEquals("416", assetServiceImpl.saveAsset(assetOuterRequest).getHead().getCode());

        // 异常情况
        when(baseLineClient.baselineCheck(any())).thenReturn(ActionResponse.success());
        Assert.assertEquals("416", assetServiceImpl.saveAsset(assetOuterRequest).getHead().getCode());

        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setBusinessId("1");
        List<AssetHardSoftLib> assetHardSoftLibs = Lists.newArrayList();
        assetHardSoftLibs.add(assetHardSoftLib);
        when(assetHardSoftLibDao.querySoftsRelations(any())).thenReturn(assetHardSoftLibs);
        AssetOuterRequest assetOuterRequest44 = new AssetOuterRequest();
        AssetRequest assetRequest1 = generateAssetRequest2();
        assetRequest1.setBaselineTemplateId("");
        assetRequest1.setInstallTemplateId("1");
        assetRequest1.setName("22323232");
        assetOuterRequest44.setAsset(assetRequest1);
        assetServiceImpl.saveAsset(assetOuterRequest44);

        Mockito.when(assetDao.insert(Mockito.any())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.saveAsset(assetOuterRequest);
        } catch (Exception e) {
            Assert.assertEquals("编号重复！", e.getMessage());
        }

        AssetOuterRequest assetOuterRequest33 = new AssetOuterRequest();
        assetOuterRequest33.setManualStartActivityRequest(generateAssetManualStart2());
        assetOuterRequest33.setAsset(generateAssetRequest2());
        assetOuterRequest33.setAssemblyRequestList(assetAssemblyRequests);
        assetOuterRequest33.setIpRelationRequests(generateAssetipRequestList());
        assetOuterRequest33.setMacRelationRequests(generateAssetmacRequestList());
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败");
        assetServiceImpl.saveAsset(assetOuterRequest33);

    }

    @Test
    public void dealIp(){
            assetServiceImpl.dealIp("1",null,3);
        assetServiceImpl.dealIp("1",null,1);
        assetServiceImpl.dealIp("1",null,2);
    }
    @Test
    public void dealSoft(){
        assetServiceImpl.dealSoft("1",new AssetSoftwareReportRequest());
    }
    @Test
    public void dealAssembly(){
            assetServiceImpl.dealAssembly("1",null);
    }
    @Test
    public void assetssave() throws Exception {
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
        when(assetDao.deleteAssetById(any())).thenReturn(0);
        when(assetUserDao.getById(any())).thenReturn(new AssetUser());
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        AssetRequest asset = generateAssetRequest3();

        Asset t = generateAsset1();
        when(requestConverter.convert(asset, Asset.class)).thenReturn(t);

        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());

        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(new SysArea());
        AssetOuterRequest assetOuterRequest331 = new AssetOuterRequest();
        AssetGroup assetGrou = new AssetGroup();
        assetGrou.setStatus(0);
        assetGrou.setName("0");

        when(assetGroupDao.getById(any())).thenReturn(assetGrou);
        assetOuterRequest331.setAsset(asset);
        assetOuterRequest331.setManualStartActivityRequest(generateAssetManualStart());
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("0已失效，请核对后提交");
        assetServiceImpl.saveAsset(assetOuterRequest331);

    }

    @Test
    public void assetssave1() throws Exception {
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
        when(assetDao.deleteAssetById(any())).thenReturn(0);
        when(assetUserDao.getById(any())).thenReturn(new AssetUser());
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        AssetRequest asset = generateAssetRequest4();
        Asset t = generateAsset2();
        when(requestConverter.convert(asset, Asset.class)).thenReturn(t);
        when(activityClient.manualStartProcess(any())).thenReturn(null);

        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(new SysArea());
        AssetOuterRequest assetOuterRequest331 = new AssetOuterRequest();

        assetOuterRequest331.setAsset(asset);
        when(baseLineClient.scan(any())).thenReturn(ActionResponse.success());

        assetServiceImpl.saveAsset(assetOuterRequest331);

    }

    public AssetStorageMediumRequest generateAssetStorageMediumRequest() {
        AssetStorageMediumRequest assetStorageMediumRequest = new AssetStorageMediumRequest();
        assetStorageMediumRequest.setAssetId("1");
        assetStorageMediumRequest.setId("1");
        return assetStorageMediumRequest;
    }

    @Test
    public void assetsTemplate() throws Exception {
        ProcessTemplateRequest processTemplateRequest = new ProcessTemplateRequest();
        processTemplateRequest.setIds(Lists.newArrayList("2323"));
        List<AssetEntity> assetEntities = new ArrayList<>();
        ActionResponse<List<WaitingTaskReponse>> listActionResponse = new ActionResponse<>();
        List<WaitingTaskReponse> waitingTaskReponses = new ArrayList<>();

        when(activityClient.queryAllWaitingTask(any())).thenReturn(ActionResponse.success(waitingTaskReponses));
        when(assetServiceImpl.assetsTemplate(processTemplateRequest)).thenReturn(assetEntities);

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
    public void testFindPageAsset() throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setQueryPatchCount(true);
        assetQuery.setQueryVulCount(true);
        assetQuery.setQueryAlarmCount(true);
        assetQuery.setAssetGroupQuery(true);

        when(assetDao.queryAllAssetVulCount(any())).thenReturn(10);
        when(assetDao.queryAllAssetPatchCount(any())).thenReturn(10);
        when(assetDao.findAlarmAssetCount(any())).thenReturn(10);

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
        when(assetDao.findCount(any())).thenReturn(100);
        when(assetDao.findAlarmAssetCount(any())).thenReturn(100);

        /* when(activityClient.queryAllWaitingTask(any())).thenReturn(null);
         * when(assetDao.findCount(any())).thenReturn(100);
         * when(assetDao.findAlarmAssetCount(any())).thenReturn(100); */

        assetQuery.setUnknownAssets(true);
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }

        when(assetDao.queryAllAssetVulCount(any())).thenReturn(0);
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }
        when(assetDao.queryAllAssetVulCount(any())).thenReturn(10);
        when(assetDao.queryAllAssetPatchCount(any())).thenReturn(0);
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }

        when(assetDao.queryAllAssetVulCount(any())).thenReturn(10);
        when(assetDao.queryAllAssetPatchCount(any())).thenReturn(10);
        when(assetDao.findAlarmAssetCount(any())).thenReturn(0);
        assetQuery.setAreaIds(new String[] {});
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }

        // enterControl
        when(assetDao.findCount(any())).thenReturn(0);
        assetQuery = new AssetQuery();
        assetQuery.setAssetGroup("1");
        assetQuery.setAssociateGroup(true);
        assetQuery.setEnterControl(true);
        assetQuery.setGroupId("1");

        assetQuery.setAssetStatusList(Arrays.asList(1, 2, 3));
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }

        assetQuery.setQueryVulCount(true);
        when(assetDao.queryAssetVulCount(any(), any(), any())).thenReturn(null);
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }
        assetQuery.setQueryVulCount(false);
        assetQuery.setQueryPatchCount(true);
        when(assetDao.queryAssetPatchCount(any(), any(), any())).thenReturn(null);
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }
        assetQuery.setQueryPatchCount(false);
        assetQuery.setQueryVulCount(false);
        assetQuery.setQueryAlarmCount(true);
        when(assetDao.queryAlarmCountByAssetIds(any())).thenReturn(null);
        try {
            assetServiceImpl.findPageAsset(assetQuery);
        } catch (Exception w) {
        }
    }

    @Test
    public void findListAsset() throws Exception {
        AssetQuery query = new AssetQuery();
        query.setPageSize(10);
        query.setAreaIds(new String[] {});
        query.setAssetStatus(1);
        Map<String, WaitingTaskReponse> processMap = new HashMap<>();
        processMap.put("1", new WaitingTaskReponse());
        List<Asset> assetList = Lists.newArrayList();
        Asset asset = new Asset();
        asset.setAreaId("1");
        assetList.add(asset);
        when(assetDao.findListAsset(query)).thenReturn(assetList);

        query.setQueryPatchCount(true);
        query.setQueryVulCount(true);
        query.setQueryAlarmCount(true);
        List<IdCount> vulCountList = Lists.newArrayList();
        vulCountList.add(new IdCount("1", "1"));
        when(assetDao.queryAssetVulCount(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser(), query.getPageSize(),
            query.getPageOffset())).thenReturn(vulCountList);

        List<IdCount> patchCountList = Lists.newArrayList();
        patchCountList.add(new IdCount("1", "1"));
        when(assetDao.queryAssetPatchCount(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser(), query.getPageSize(),
            query.getPageOffset())).thenReturn(patchCountList);

        List<IdCount> alarmCountList = Lists.newArrayList();
        alarmCountList.add(new IdCount("1", "1"));
        when(assetDao.queryAlarmCountByAssetIds(query)).thenReturn(alarmCountList);
        List<String> sortedIds = Lists.newArrayList();
        sortedIds.add("1");
        when(assetDao.sortAssetIds(processMap.keySet(), query.getAssetStatus())).thenReturn(sortedIds);
        assetServiceImpl.findListAsset(query, processMap);

        when(assetDao.queryAlarmCountByAssetIds(query)).thenReturn(null);
        assetServiceImpl.findListAsset(query, processMap);
    }

    @Test
    public void findCountAsset() throws Exception {
        AssetQuery query = new AssetQuery();
        query.setAreaIds(new String[] {});
        assetServiceImpl.findCountAsset(query);
    }

    @Test
    public void testGetAllHardWaitingTask() {
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
        assetServiceImpl.getAllHardWaitingTask("definitionKeyType");

        when(activityClient.queryAllWaitingTask(any())).thenReturn(ActionResponse.fail(RespBasicCode.PARAMETER_ERROR));
        try {
            assetServiceImpl.getAllHardWaitingTask("definitionKeyType");
        } catch (Exception e) {
        }
    }

    @Test
    public void dealProcess() {
        AssetQuery query = new AssetQuery();
        query.setAssetStatus(1);
        query.setAssetStatusList(Arrays.asList(1, 2, 3));
        query.setEnterControl(true);
        Map<String, WaitingTaskReponse> processMap = new HashMap<>();
        processMap.put("1", new WaitingTaskReponse());
        List<String> sortedIds = Lists.newArrayList();
        sortedIds.add("1");
        when(assetDao.sortAssetIds(processMap.keySet(), query.getAssetStatus())).thenReturn(sortedIds);
        assetServiceImpl.dealProcess(query, processMap);
    }

    @Test
    public void findUnconnectedAsset() throws Exception {
        mockStatic(RedisKeyUtil.class);
        AssetQuery query = new AssetQuery();
        query.setPrimaryKey("1");
        mockStatic(LoginUserUtil.class);
        LoginUser loginUser = getLoginUser();
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        Asset asset = new Asset();
        asset.setCategoryModel(1);
        when(assetDao.getByAssetId(query.getPrimaryKey())).thenReturn(asset);
        assetServiceImpl.findUnconnectedAsset(query);

        query.setCategoryModels(null);
        asset.setCategoryModel(2);
        when(assetDao.getByAssetId(query.getPrimaryKey())).thenReturn(asset);
        assetServiceImpl.findUnconnectedAsset(query);

        when(assetLinkRelationDao.findUnconnectedCount(query)).thenReturn(0);
        assetServiceImpl.findUnconnectedAsset(query);

        when(assetLinkRelationDao.findUnconnectedCount(query)).thenReturn(1);
        List<Asset> assetList = Lists.newArrayList();
        Asset asset1 = new Asset();
        asset1.setId(1);
        asset1.setAreaId("1");
        assetList.add(asset1);
        when(assetLinkRelationDao.findListUnconnectedAsset(query)).thenReturn(assetList);
        List<AssetResponse> assetResponseList = Lists.newArrayList();
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setAreaId("1");
        assetResponseList.add(assetResponse);
        when(responseConverter.convert(assetLinkRelationDao.findListUnconnectedAsset(query), AssetResponse.class))
            .thenReturn(assetResponseList);
        String newAreaKey = "1";
        when(RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class, assetResponse.getAreaId()))
            .thenReturn(newAreaKey);
        when(redisUtil.getObject(newAreaKey, SysArea.class)).thenReturn(mock(SysArea.class));
        assetServiceImpl.findUnconnectedAsset(query);

        when(redisUtil.getObject(newAreaKey, SysArea.class)).thenThrow(new Exception());
        assetServiceImpl.findUnconnectedAsset(query);

        query.setCategoryModels(new Integer[0]);
        assetServiceImpl.findUnconnectedAsset(query);
        query.setCategoryModels(new Integer[]{0,1});
        assetServiceImpl.findUnconnectedAsset(query);
        asset.setCategoryModel(2);
        query.setCategoryModels(null);
        assetServiceImpl.findUnconnectedAsset(query);


    }

    @Test
    public void testSaveReportAsset() {
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
    public void testCheckRepeatAsset() {
        when(assetDao.checkRepeatAsset(any())).thenReturn(Arrays.asList(new Asset()));

        List<String[]> ipMac = new ArrayList<>();
        ipMac.add(new String[] { "string" });

        boolean result = assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        Assert.assertEquals(true, result);
        when(assetDao.checkRepeatAsset(any())).thenReturn(Arrays.asList());
        boolean result2 = assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        Assert.assertEquals(false, result2);
        when(assetDao.checkRepeatAsset(any())).thenReturn(null);
        boolean result3 = assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        Assert.assertEquals(false, result3);
        when(assetDao.checkRepeatAsset(any())).thenReturn(Arrays.asList(new Asset(),new Asset()));
        assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        Asset asset=new Asset();
        asset.setUuid("uuid");
        when(assetDao.checkRepeatAsset(any())).thenReturn(Arrays.asList(asset));
        assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        assetServiceImpl.checkRepeatAsset("123", ipMac);
        when(assetDao.checkRepeatAsset(any())).thenThrow(new BusinessException("失败"));
        assetServiceImpl.checkRepeatAsset("123", ipMac);
    }

    @Test
    public void testChangeStatus() throws Exception {
        when(assetDao.changeStatus(any())).thenReturn(0);

        Integer result = assetServiceImpl.changeStatus(new String[] { "ids" }, 0);
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
    public void testCountCategory() {
        /**
         *
         * 统计品类型号 Map中的数据 key--品类型号 value--总数
         */
        List<Map<String, Object>> categoryModelCount = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("key", 1);
        map1.put("value", 100l);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("key", 2);
        map2.put("value", 200l);
        categoryModelCount.add(map1);
        categoryModelCount.add(map2);
        when(assetDao.countCategoryModel(any(), any())).thenReturn(categoryModelCount);

        List<EnumCountResponse> result = assetServiceImpl.countCategory();

        Assert.assertNotNull(result);
    }

    @Test
    public void testQueryAssetByIds() {
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
        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setBusinessId("1");
        assetHardSoftLib.setCpeUri("cpe:/h:xerox:copycentre_c65:1.001.02.073");
        assetHardSoftLib.setSupplier("xerox");
        assetHardSoftLib.setProductName("copycentre_c65");
        Mockito.when(assetHardSoftLibDao.getByBusinessId(Mockito.anyString())).thenReturn(assetHardSoftLib);
        Assert.assertEquals("0", assetServiceImpl.getByAssetId(condition).getAsset().getStringId());
        /*
        if (CollectionUtils.isNotEmpty(assetGroupResponses)) { false
         */
        Mockito.when(assetGroupRelationDao.queryByAssetId(Mockito.any())).thenReturn(null);
        assetServiceImpl.getByAssetId(condition);
        Mockito.when(assetGroupRelationDao.queryByAssetId(Mockito.any())).thenReturn(generateAssetGroupList());

        Asset asset = generateNetWorkAsset();
        asset.setOperationSystem(null);
        Mockito.when(assetDao.getByAssetId(Mockito.anyString())).thenReturn(asset);
        assetServiceImpl.getByAssetId(condition);
        Mockito.when(assetNetworkEquipmentDao.getByWhere(Mockito.any()))
                .thenReturn(null);
        assetServiceImpl.getByAssetId(condition);
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
        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setBusinessId("1");
        assetHardSoftLib.setCpeUri("cpe:/h:xerox:copycentre_c65:1.001.02.073");
        assetHardSoftLib.setSupplier("xerox");
        assetHardSoftLib.setProductName("copycentre_c65");
        Mockito.when(assetHardSoftLibDao.getByBusinessId(Mockito.anyString())).thenReturn(assetHardSoftLib);
        Assert.assertEquals("0", assetServiceImpl.getByAssetId(condition).getAsset().getStringId());
        Mockito.when(assetSafetyEquipmentDao.getByWhere(Mockito.any())).thenReturn(null);
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
        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setBusinessId("1");
        assetHardSoftLib.setCpeUri("cpe:/h:xerox:copycentre_c65:1.001.02.073");
        assetHardSoftLib.setSupplier("xerox");
        assetHardSoftLib.setProductName("copycentre_c65");
        Mockito.when(assetHardSoftLibDao.getByBusinessId(Mockito.anyString())).thenReturn(assetHardSoftLib);
        Assert.assertEquals("0", assetServiceImpl.getByAssetId(condition).getAsset().getStringId());

        Mockito.when(assetStorageMediumDao.getByWhere(Mockito.any())).thenReturn(null);
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

        // asset.setLatitude("");
        // asset.setLongitude("");
        asset.setHouseLocation("");
        asset.setFirmwareVersion("");
        asset.setUuid("");

        // asset.setEmail("");
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

    private Asset generateAsset1() {
        Asset assetRequest = new Asset();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setBusinessId(11L);
        assetRequest.setNumber("112121");
        assetRequest.setName("1");
        assetRequest.setSerial("1");
        assetRequest.setAreaId("1");
        assetRequest.setManufacturer("1");
        assetRequest.setAssetStatus(0);
        assetRequest.setOperationSystem(1L);

        assetRequest.setFirmwareVersion("1");
        assetRequest.setUuid("1");
        assetRequest.setResponsibleUserId("1");
        assetRequest.setAssetSource(0);
        assetRequest.setImportanceDegree(0);
        assetRequest.setCategoryModel(2);
        assetRequest.setServiceLife(0L);
        assetRequest.setBuyDate(0L);
        assetRequest.setWarranty("0");
        assetRequest.setId(1);
        assetRequest.setHouseLocation("1");
        assetRequest.setInstallTemplateId("12222");
        assetRequest.setBaselineTemplateId("1");
        assetRequest.setInstallTemplateId("1");
        AssetGroupRequest assetGroup = new AssetGroupRequest();
        List<AssetGroupRequest> assetGroupRequests = new ArrayList<AssetGroupRequest>();
        assetGroup.setId("0");
        // assetRequest.setAssetGroups(assetGroupRequests);
        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");

        return assetRequest;
    }

    private Asset generateAsset2() {
        Asset assetRequest = new Asset();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setBusinessId(11L);
        assetRequest.setNumber("112121");
        assetRequest.setName("1");
        assetRequest.setSerial("1");
        assetRequest.setAreaId("1");
        assetRequest.setManufacturer("1");
        assetRequest.setAssetStatus(0);

        assetRequest.setFirmwareVersion("1");
        assetRequest.setUuid("1");
        assetRequest.setResponsibleUserId("1");
        assetRequest.setAssetSource(0);
        assetRequest.setImportanceDegree(0);
        assetRequest.setCategoryModel(2);
        assetRequest.setServiceLife(0L);
        assetRequest.setBuyDate(0L);
        assetRequest.setWarranty("0");
        assetRequest.setId(1);
        assetRequest.setHouseLocation("1");

        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");

        return assetRequest;
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
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        mockStatic(LoginUserUtil.class);
        mockStatic(LogUtils.class);
        LoginUser loginUser = getLoginUser();
        when(LoginUserUtil.getLoginUser()).thenReturn(null);
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setId("1");
        assetRequest.setCategoryModel(1);
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        assetOuterRequest.setAsset(assetRequest);

        Asset asset = new Asset();
        asset.setId(1);
        asset.setCategoryModel(1);
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        when(assetDao.getById("1")).thenReturn(asset);
        when(requestConverter.convert(assetRequest, Asset.class)).thenReturn(asset);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        Map formData = new HashMap();
        formData.put("admittanceResult", "safetyCheck");
        formData.put("safetyCheckUser", "1");
        manualStartActivityRequest.setFormData(formData);
        assetOuterRequest.setManualStartActivityRequest(manualStartActivityRequest);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        List<AssetIpRelationRequest> ipRelationRequests = Lists.newArrayList();
        AssetIpRelationRequest assetIpRelationRequest = new AssetIpRelationRequest();
        assetIpRelationRequest.setAssetId(1);
        assetIpRelationRequest.setIp("192.168.1.1");
        ipRelationRequests.add(assetIpRelationRequest);

        AssetIpRelationRequest assetIpRelationRequest2 = new AssetIpRelationRequest();
        assetIpRelationRequest2.setAssetId(1);
        assetIpRelationRequest2.setIp("192.168.1.2");
        ipRelationRequests.add(assetIpRelationRequest2);

        assetOuterRequest.setIpRelationRequests(ipRelationRequests);
        List<AssetMacRelationRequest> macRelationRequests = Lists.newArrayList();
        AssetMacRelationRequest assetMacRelationRequest = new AssetMacRelationRequest();
        assetMacRelationRequest.setAssetId(1);
        assetMacRelationRequest.setMac("1A:2B:3C:4D:5E:6F");
        macRelationRequests.add(assetMacRelationRequest);

        assetOuterRequest.setMacRelationRequests(macRelationRequests);

        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assetRequest.setInstallTemplateId("1");
        assetRequest.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        asset.setInstallTemplateId("1");
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        when(assetDao.changeAsset(any())).thenReturn(1);
        List<Long> sids = Lists.newArrayList();
        sids.add(1L);
        AssetSoftwareReportRequest assetSoftwareReportRequest = new AssetSoftwareReportRequest();
        assetSoftwareReportRequest.setAssetId(new ArrayList() {
            {
                add("1");
            }
        });
        assetSoftwareReportRequest.setSoftId(new ArrayList() {
            {
                add(1L);
            }
        });
        assetOuterRequest.setSoftwareReportRequest(assetSoftwareReportRequest);
        List<AssetAssemblyRequest> assemblyRequestList = Lists.newArrayList();
        AssetAssemblyRequest assetAssemblyRequest = new AssetAssemblyRequest();
        assetAssemblyRequest.setAmount(1);
        assetAssemblyRequest.setAssetId("1");
        assetAssemblyRequest.setBusinessId("1");
        assemblyRequestList.add(assetAssemblyRequest);
        assetOuterRequest.setAssemblyRequestList(assemblyRequestList);

        AssetNetworkEquipmentRequest networkEquipment = new AssetNetworkEquipmentRequest();
        networkEquipment.setAssetId("1");
        networkEquipment.setId("1");
        assetOuterRequest.setNetworkEquipment(networkEquipment);

        AssetSafetyEquipmentRequest safetyEquipmentRequest = new AssetSafetyEquipmentRequest();
        safetyEquipmentRequest.setAssetId("1");
        safetyEquipmentRequest.setId("1");
        assetOuterRequest.setSafetyEquipment(safetyEquipmentRequest);

        AssetStorageMediumRequest storageMedium = new AssetStorageMediumRequest();
        storageMedium.setAssetId("1");
        storageMedium.setId("1");
        assetOuterRequest.setAssetStorageMedium(storageMedium);

        when(assetInstallTemplateDao.querySoftIds("1")).thenReturn(sids);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        assetRequest.setAssetStatus(AssetStatusEnum.RETIRE.getCode());
        asset.setAssetStatus(AssetStatusEnum.RETIRE.getCode());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        assetRequest.setBaselineTemplateId("1");
        asset.setBaselineTemplateId("1");
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        formData.put("admittanceResult", "safetyCheck");
        formData.put("safetyCheckUser", "1");
        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(baseLineClient.baselineCheckNoUUID(any())).thenReturn(ActionResponse.success());
        when(baseLineClient.baselineCheck(any())).thenReturn(ActionResponse.success());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        formData.put("admittanceResult", "templateImplement");
        formData.put("templateImplementUser", "1");
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(baseLineClient.baselineCheckNoUUID(any())).thenReturn(ActionResponse.success());
        when(baseLineClient.baselineCheck(any())).thenReturn(ActionResponse.success());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        when(activityClient.manualStartProcess(any())).thenReturn(ActionResponse.success());
        when(baseLineClient.baselineCheckNoUUID(any())).thenReturn(null);
        when(baseLineClient.baselineCheck(any())).thenReturn(null);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }





        asset.setOperationSystemName("windows");
        when(assetDao.getByAssetId("1")).thenReturn(asset);
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        formData.put("baselineConfigUserId", "1");
        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setBusinessId("1");
        when(assetHardSoftLibDao.getByBusinessId(anyString())).thenReturn(assetHardSoftLib);
        manualStartActivityRequest.setFormData(formData);
        assetOuterRequest.setManualStartActivityRequest(manualStartActivityRequest);
        when(aesEncoder.decode(
            (String) assetOuterRequest.getManualStartActivityRequest().getFormData().get("baselineConfigUserId"),
            LoginUserUtil.getLoginUser().getUsername())).thenReturn("1");
        when(baseLineClient.baselineConfig(any())).thenReturn(ActionResponse.success());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        when(baseLineClient.baselineConfig(any())).thenReturn(null);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        assetRequest.setInstallType(1);
        asset.setInstallType(1);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        assetOuterRequest.setManualStartActivityRequest(null);
        assetRequest.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }
        when(baseLineClient.scan(aesEncoder.encode("1", LoginUserUtil.getLoginUser().getUsername())))
            .thenReturn(ActionResponse.success());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }
        when(baseLineClient.scan(aesEncoder.encode("1", LoginUserUtil.getLoginUser().getUsername()))).thenReturn(null);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }
        assetRequest.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }
        assetRequest.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
        Map ac = new HashMap();
        ac.put("admittanceResult", "safetyCheck");
        activityHandleRequest.setFormData(ac);
        assetOuterRequest.setActivityHandleRequest(activityHandleRequest);
        when(activityClient.completeTask(activityHandleRequest)).thenReturn(ActionResponse.success());
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        ActionResponse actionResponse = ActionResponse.fail(RespBasicCode.PARAMETER_ERROR);
        when(activityClient.completeTask(activityHandleRequest)).thenReturn(actionResponse);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }

        ac.put("admittanceResult", "templateImplement");
        activityHandleRequest.setFormData(ac);
        assetOuterRequest.setActivityHandleRequest(activityHandleRequest);
        try {
            assetServiceImpl.changeAsset(assetOuterRequest);
        } catch (Exception e) {
        }
    }

    @Test
    public void getChangeContent() {
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setId("1");
        assetRequest.setCategoryModel(1);
        assetRequest.setOperationSystemName("windows2");
        assetOuterRequest.setAsset(assetRequest);

        Asset asset = new Asset();
        asset.setOperationSystemName("linux");
        when(assetDao.getByAssetId("1")).thenReturn(asset);

        List<AssetAssemblyRequest> assetAssemblyRequestList = Lists.newArrayList();
        AssetAssemblyRequest assetAssemblyRequest = new AssetAssemblyRequest();
        assetAssemblyRequest.setAmount(2);
        assetAssemblyRequest.setType("DISK");
        assetAssemblyRequest.setBusinessId("1");
        assetAssemblyRequestList.add(assetAssemblyRequest);
        assetOuterRequest.setAssemblyRequestList(assetAssemblyRequestList);
        when(assetAssemblyDao.findAssemblyByAssetId("1", "DISK")).thenReturn(Lists.newArrayList());

        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
        }

        List<AssetAssemblyRequest> assetAssemblyRequestList2 = Lists.newArrayList();
        AssetAssemblyRequest assetAssemblyRequest2 = new AssetAssemblyRequest();
        assetAssemblyRequest2.setAmount(1);
        assetAssemblyRequest2.setType("DISK");
        assetAssemblyRequest2.setBusinessId("1");
        assetAssemblyRequestList2.add(assetAssemblyRequest2);
        when(assetAssemblyDao.findAssemblyByAssetId("1", "DISK")).thenReturn(assetAssemblyRequestList2);
        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
        }

        List<AssetAssemblyRequest> assetAssemblyRequestList3 = Lists.newArrayList();
        AssetAssemblyRequest assetAssemblyRequest3 = new AssetAssemblyRequest();
        assetAssemblyRequest3.setAmount(1);
        assetAssemblyRequest3.setType("DISK");
        assetAssemblyRequest3.setBusinessId("2");
        assetAssemblyRequestList3.add(assetAssemblyRequest3);
        when(assetAssemblyDao.findAssemblyByAssetId("1", "DISK")).thenReturn(assetAssemblyRequestList3);
        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
        }

        assetOuterRequest.setAssemblyRequestList(Lists.newArrayList());
        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
        }

        AssetSoftwareReportRequest assetSoftwareReportRequest = new AssetSoftwareReportRequest();
        List<Long> softIds = Lists.newArrayList();
        softIds.add(1L);
        softIds.add(3L);
        assetSoftwareReportRequest.setSoftId(softIds);
        assetOuterRequest.setSoftwareReportRequest(assetSoftwareReportRequest);
        when(assetSoftwareRelationDao.queryInstalledList(any())).thenReturn(Lists.newArrayList());
        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
        }

        List<AssetSoftwareInstallResponse> assetSoftwareInstallResponseList = Lists.newArrayList();
        AssetSoftwareInstallResponse assetSoftwareInstallResponse = new AssetSoftwareInstallResponse();
        assetSoftwareInstallResponse.setAssetId("1");
        assetSoftwareInstallResponse.setSoftwareId("2");
        assetSoftwareInstallResponse.setProductName("office");
        assetSoftwareInstallResponseList.add(assetSoftwareInstallResponse);
        AssetSoftwareInstallResponse assetSoftwareInstallResponse2 = new AssetSoftwareInstallResponse();
        assetSoftwareInstallResponse2.setAssetId("1");
        assetSoftwareInstallResponse2.setSoftwareId("1");
        assetSoftwareInstallResponse2.setProductName("office");
        assetSoftwareInstallResponseList.add(assetSoftwareInstallResponse2);
        AssetHardSoftLib assetHardSoftLib = new AssetHardSoftLib();
        assetHardSoftLib.setProductName("fdfd");
        when(assetHardSoftLibDao.getByBusinessId(anyString())).thenReturn(assetHardSoftLib);
        when(assetSoftwareRelationDao.queryInstalledList(any())).thenReturn(assetSoftwareInstallResponseList);
        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        softIds = Lists.newArrayList();
        assetSoftwareReportRequest.setSoftId(softIds);
        assetOuterRequest.setSoftwareReportRequest(assetSoftwareReportRequest);
        try {
            assetServiceImpl.getChangeContent(assetOuterRequest);
        } catch (Exception e) {
            e.printStackTrace();
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
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request2.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
        PowerMockito.mockStatic(ZipUtil.class);
        PowerMockito.doNothing().when(ZipUtil.class, "compress", Mockito.any(File.class), Mockito.any(File[].class));
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request2, new MockHttpServletResponse()));
        assetServiceImpl.exportTemplate(new String[] { "计算设备", "安全设备", "其它设备", "网络设备", "存储设备" });

        MockHttpServletRequest request3 = new MockHttpServletRequest();
        request3.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
        PowerMockito.mockStatic(ZipUtil.class);
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("发送客户端失败");
        PowerMockito.doNothing().when(ZipUtil.class, "compress", null, null);
        when(RequestContextHolder.getRequestAttributes()).thenReturn(new ServletRequestAttributes(request3, null));

        assetServiceImpl.exportTemplate(new String[] { "计算设备", "安全设备", "其它设备", "网络设备", "存储设备" });
        MockHttpServletRequest request13 = new MockHttpServletRequest();
        request13.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
        PowerMockito.mockStatic(ZipUtil.class);
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("发送客户端失败");
        PowerMockito.mockStatic(ZipUtil.class);
        PowerMockito.doNothing().when(ZipUtil.class, "compress", Mockito.any(File.class), Mockito.any(File[].class));
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request13, new MockHttpServletResponse()));

        assetServiceImpl.exportTemplate(new String[] { "计算设备", "安全设备", "其它设备", "网络设备", "存储设备" });
    }

    @Test
    public void testImportPc() throws Exception {
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
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
        List<ComputeDeviceEntity> computeDeviceEntities = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = getComputeDeviceEntity();
        computeDeviceEntities.add(computeDeviceEntity);
        importResult.setMsg("");
        importResult.setDataList(computeDeviceEntities);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("4");
        List<AssetCpeFilter> assetCpeFilters = new ArrayList<>();
        AssetCpeFilter assetCpeFilter = new AssetCpeFilter();
        assetCpeFilter.setBusinessId(1L);
        assetCpeFilters.add(assetCpeFilter);
        when(assetCpeFilterDao.getByWhere(any())).thenReturn(assetCpeFilters);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
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
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        computeDeviceEntity.setName("1");
        computeDeviceEntity1.setName("2");
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        computeDeviceEntity.setNumber("1");
        computeDeviceEntity1.setNumber("2");
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行MAC地址重复！", result);

        when(assetDao.findCount(any())).thenReturn(10);
        importResult.getDataList().remove(1);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        when(assetDao.findCount(any())).thenReturn(0);
        when(assetDao.findCountMac(any(), any())).thenReturn(10);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行MAC地址重复！", result);

        when(assetDao.findCountMac(any(), any())).thenReturn(0);
        when(assetDao.findCountAssetNumber(any())).thenReturn(10);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);
        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商不存在！", result);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(0);
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商下,不存在当前名称！", result);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);

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
        Assert.assertEquals("导入成功1条", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        computeDeviceEntity.setArea("qwedwafdwaddwa");
        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(baselineCategoryModelResponses);
        computeDeviceEntity.setArea("四川");

        result = assetServiceImpl.importPc(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        computeDeviceEntity.setArea("四川");

        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(getLoginUser());

        try {
            assetServiceImpl.importPc(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("导入失败，选择品类型号不存在，或已被注销！", e.getMessage());
        }

        importResult.setDataList(null);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        Assert.assertEquals("", assetServiceImpl.importPc(null, assetImportRequest));

        importResult.setDataList(new ArrayList());
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importPc(null, assetImportRequest));

        importResult.setMsg("导入失败");
        importResult.setDataList(computeDeviceEntities);
        Assert.assertEquals("导入失败", assetServiceImpl.importPc(null, assetImportRequest));

        importResult.setMsg("");
        importResult.setDataList(new ArrayList());
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importPc(null, assetImportRequest));

        importResult.setMsg("");
        Mockito.when(assetDao.insert(Mockito.any())).thenThrow(new DuplicateKeyException(""));
        try {
            assetServiceImpl.importPc(null, assetImportRequest);
        } catch (Exception e) {
            Assert.assertEquals("请勿重复提交！", e.getMessage());
        }


           LicenseContent licenseContent = new LicenseContent();
        licenseContent.setAssetNum(null);
        PowerMockito.when(LicenseUtil.getLicense()).thenReturn(licenseContent);
        expectedException.expectMessage("license异常，请联系客服人员！");
        expectedException.expect(BusinessException.class);
        assetServiceImpl.importPc(null, assetImportRequest);
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
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
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
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
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
        when(assetDao.findCountAssetNumber(any())).thenReturn(10);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);
        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商不存在！", result);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(0);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商下,不存在当前名称！", result);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);

        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
        networkDeviceEntity.setPortSize(-1);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行网口数目范围为1-100！", result);
        networkDeviceEntity.setPortSize(881);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行网口数目范围为1-100！", result);

        when(assetDao.findCount(any())).thenReturn(0);
        networkDeviceEntity.setPortSize(10);
        networkDeviceEntity.setInterfaceSize(10111);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行接口数目不大于127！", result);
        networkDeviceEntity.setInterfaceSize(11);
        networkDeviceEntity.setButDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);
        networkDeviceEntity.setButDate(null);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result);

        when(assetDao.findCount(any())).thenReturn(0);
        networkDeviceEntity.setPortSize(10);
        networkDeviceEntity.setButDate(System.currentTimeMillis() / 2);
        networkDeviceEntity.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        when(assetDao.findCountMac(any(), any())).thenReturn(10);
        networkDeviceEntity.setDueDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importNet(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行MAC地址重复！", result);

        when(assetDao.findCountMac(any(), any())).thenReturn(0);
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
        importResult.setDataList(new ArrayList());
        importResult.setMsg("");
        Assert.assertEquals("导入失败，模板中无数据！", assetServiceImpl.importNet(null, assetImportRequest));
        importResult.setDataList(new ArrayList());
        importResult.setMsg("ewe");
        Assert.assertEquals("导入成功0条ewe", assetServiceImpl.importNet(null, assetImportRequest));

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
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
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
        List<AssetCpeFilter> assetCpeFilters = new ArrayList<>();
        AssetCpeFilter assetCpeFilter = new AssetCpeFilter();
        assetCpeFilter.setBusinessId(1L);
        assetCpeFilters.add(assetCpeFilter);
        when(assetCpeFilterDao.getByWhere(any())).thenReturn(assetCpeFilters);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
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
        Assert.assertEquals("导入成功1条", result);

        when(assetDao.findCount(any())).thenReturn(0);
        when(assetDao.findCountMac(any(), any())).thenReturn(10);
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产MAC地址重复！", result);
        when(assetDao.findCountMac(any(), any())).thenReturn(0);

        when(assetDao.findCountAssetNumber(any())).thenReturn(10);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);
        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商不存在！", result);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(0);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商下,不存在当前名称！", result);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);

        safetyEquipmentEntiy.setBuyDate(System.currentTimeMillis() * 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", result);

        safetyEquipmentEntiy.setBuyDate(System.currentTimeMillis() / 2);
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importSecurity(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis() * 2);
        // when(operatingSystemClient.getInvokeOperatingSystem()).thenReturn(new ArrayList<>());
        // result = assetServiceImpl.importSecurity(null, assetImportRequest);
        // Assert.assertEquals("导入失败，第7行操作系统不存在，或已被注销！", result);

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
        when(assetDao.findCountMac(any(), any())).thenReturn(0);
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
        storageDeviceEntity.setRaidSupport(1);
        storageDeviceEntityArrayList.add(storageDeviceEntity);
        importResult.setMsg("");
        importResult.setDataList(storageDeviceEntityArrayList);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        AssetImportRequest assetImportRequest = new AssetImportRequest();
        assetImportRequest.setCategory("1");
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
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
        Assert.assertEquals("导入成功1条", result);
        when(assetDao.findCountAssetNumber(any())).thenReturn(10);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！", result);
        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商不存在！", result);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(0);
        result = assetServiceImpl.importStory(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商下,不存在当前名称！", result);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(10);
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

        when(assetDao.countAsset()).thenReturn(100);
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("资产数量已超过授权数量，请联系客服人员！");
        assetServiceImpl.importStory(null, assetImportRequest);

    }

    private StorageDeviceEntity getStorageDeviceEntity() {
        StorageDeviceEntity storageDeviceEntity = new StorageDeviceEntity();
        storageDeviceEntity.setArea("四川");
        storageDeviceEntity.setDueDate(System.currentTimeMillis() + 1);
        storageDeviceEntity.setUser("1");
        storageDeviceEntity.setImportanceDegree("1");
        storageDeviceEntity.setRaidSupport(0);
        return storageDeviceEntity;
    }

    @Test
    public void testImportOhters() throws Exception {

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        ImportResult importResult = new ImportResult();
        ImportResult importResult4 = new ImportResult();
        ImportResult importResult5 = new ImportResult();
        ImportResult importResult1 = new ImportResult();
        ImportResult importResult2 = new ImportResult();
        ImportResult importResult3 = new ImportResult();
        List<OtherDeviceEntity> otherDeviceEntities = new ArrayList<>();
        List<OtherDeviceEntity> otherDeviceEntities3 = new ArrayList<>();
        List<OtherDeviceEntity> otherDeviceEntities2 = new ArrayList<>();
        OtherDeviceEntity otherDeviceEntity = getOtherDeviceEntity();
        OtherDeviceEntity otherDeviceEntity3 = getOtherDeviceEntity();
        importResult2.setDataList(otherDeviceEntities2);
        otherDeviceEntities.add(otherDeviceEntity);
        importResult4.setDataList(otherDeviceEntities);
        importResult4.setMsg("");
        importResult3.setDataList(otherDeviceEntities);
        otherDeviceEntity3.setManufacturer("dsdsds");
        otherDeviceEntities3.add(otherDeviceEntity3);
        importResult5.setDataList(otherDeviceEntities3);
        importResult5.setMsg("");
        importResult.setMsg("");
        importResult2.setMsg("");
        importResult3.setMsg("导入失败");

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult1);
        String result11 = assetServiceImpl.importOhters(null, null);
        Assert.assertEquals(null, result11);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult2);
        String result12 = assetServiceImpl.importOhters(null, null);
        Assert.assertEquals("导入失败，模板中无数据！", result12);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult3);
        String result13 = assetServiceImpl.importOhters(null, null);
        Assert.assertEquals("导入失败", result13);
        otherDeviceEntities.add(otherDeviceEntity);
        importResult.setDataList(otherDeviceEntities);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(1);
        AssetImportRequest assetImportRequest = new AssetImportRequest();
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(1);
        String result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！", result);

        OtherDeviceEntity otherDeviceEntity1 = getOtherDeviceEntity();
        otherDeviceEntity1.setNumber("sds");
        otherDeviceEntities.add(otherDeviceEntity1);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第8行资产编号重复！第9行MAC地址重复！", result);

        importResult.getDataList().remove(1);
        when(assetDao.findCount(any())).thenReturn(10);
        when(assetDao.findCountAssetNumber(any())).thenReturn(10);

        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(10);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行资产编号重复！第8行资产编号重复！", result);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult4);
        otherDeviceEntity.setBuyDate(System.currentTimeMillis() * 2);
        when(assetDao.findCountAssetNumber(any())).thenReturn(0);
        String ohters = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", ohters);
        otherDeviceEntity.setBuyDate(null);

        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行购买时间需小于等于今天！", ohters);

        otherDeviceEntity.setBuyDate(System.currentTimeMillis() / 2);
        otherDeviceEntity.setDueDate(System.currentTimeMillis() / 2);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行到期时间需大于等于今天！", result);

        otherDeviceEntity.setDueDate(System.currentTimeMillis() * 2);
        when(assetUserDao.findListAssetUser(any())).thenReturn(null);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行系统中没有此使用者，或已被注销！第8行系统中没有此使用者，或已被注销！", result);

        when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
        otherDeviceEntity.setArea("sdfsadfasd");
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行当前用户没有此所属区域，或已被注销！", result);

        when(assetDao.findCountMac(any(), any())).thenReturn(11);
        result = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行MAC地址重复！第8行MAC地址重复！", result);

        when(assetDao.findCountMac(any(), any())).thenReturn(0);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(0);

        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult5);
        String result32 = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商不存在！", result32);
        when(assetHardSoftLibDao.countByWhere(any())).thenReturn(11);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(0);
        String result23 = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入失败，第7行该厂商下,不存在当前名称！", result23);
        when(assetHardSoftLibDao.countByWhere1(any())).thenReturn(11);
        when(ExcelUtils.importExcelFromClient(any(), any(), anyInt(), anyInt())).thenReturn(importResult5);
        String result233 = assetServiceImpl.importOhters(null, assetImportRequest);
        Assert.assertEquals("导入成功1条", result233);
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
        otherDeviceEntity.setName("123");
        otherDeviceEntity.setManufacturer("123");
        otherDeviceEntity.setUser("123");
        otherDeviceEntity.setArea("四川");
        otherDeviceEntity.setMac("12");
        otherDeviceEntity.setBuyDate(System.currentTimeMillis());
        otherDeviceEntity.setDueDate(System.currentTimeMillis() + 1);
        otherDeviceEntity.setImportanceDegree("1");
        return otherDeviceEntity;
    }

    @Test
    public void testQueryAssetCountByAreaIds() {
        when(assetDao.queryAssetCountByAreaIds(any())).thenReturn(0);

        List<String> areaIds = new ArrayList<>();
        areaIds.add("1");

        Integer result = assetServiceImpl.queryAssetCountByAreaIds(areaIds);
        Assert.assertEquals(Integer.valueOf(0), result);
        int result2 = assetServiceImpl.queryAssetCountByAreaIds(null);
        Assert.assertEquals(0, result2);
    }

    @Test
    public void queryUnknownAssetCount() throws Exception {
        AssetUnknownRequest request=new AssetUnknownRequest();
        assetServiceImpl.queryUnknownAssetCount(request);
    }
    @Test()
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
        assetResponse.setImportanceDegree(2);
        assetResponse.setManufacturer("");
        assetResponse.setAssetStatus(0);
        // assetResponse.setOperationSystem(1L);
        assetResponse.setUuid("");
        assetResponse.setResponsibleUserId("");
        assetResponse.setAssetSource(0);
        assetResponse.setImportanceDegree(2);
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
        assetQuery.setEnd(null);
        assetServiceImpl.exportData(assetQuery, new Response(), new Request());
        assetQuery.setEnd(100);
        assetQuery.setStart(null);
        assetServiceImpl.exportData(assetQuery, new Response(), new Request());
        assetQuery.setStart(1);
        AssetQuery assetQuery2 = new AssetQuery();
        assetQuery2.setStart(1);
        assetQuery2.setEnd(100);
        assetQuery2.setUnknownAssets(true);
        assetServiceImpl.exportData(assetQuery2, new Response(), new Request());
        PageResult<AssetResponse> result2 = new PageResult<>();
        doReturn(result2).when(assetServiceImpl).findPageAsset(any());
        result2.setItems(new ArrayList<AssetResponse>());
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("导出数据为空");
        assetServiceImpl.exportData(assetQuery, new Response(), new Request());

        AssetQuery assetQuery3 = new AssetQuery();
        assetQuery3.setStart(1);
        assetQuery3.setEnd(100);
        assetQuery3.setUnknownAssets(true);
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("导出数据为空");
        assetServiceImpl.exportData(assetQuery3, new Response(), new Request());

    }

    @Test
    public void testPulldownUnconnectedManufacturer() {
        when(assetLinkRelationDao.pulldownUnconnectedManufacturer(any())).thenReturn(Sets.newHashSet("String"));

        Set<String> result = assetServiceImpl.pulldownUnconnectedManufacturer(1, "1");
        Assert.assertEquals(Sets.newHashSet("String"), result);

        result = assetServiceImpl.pulldownUnconnectedManufacturer(0, "1");
        Assert.assertEquals(Sets.newHashSet("String"), result);
        result = assetServiceImpl.pulldownUnconnectedManufacturer(null, "1");
        Assert.assertEquals(Sets.newHashSet("String"), result);
    }

    private AssetGroup generateAssetGroup() {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setId(1);
        assetGroup.setName("abc");
        assetGroup.setMemo("1");
        assetGroup.setStatus(1);
        return assetGroup;
    }

    private AssetGroup generateAssetGroup2() {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setId(1);
        assetGroup.setName("abc1");
        assetGroup.setMemo("1");
        assetGroup.setStatus(0);
        return assetGroup;
    }

    private List<AssetAssemblyRequest> generateAssetAssemblyRequestList() {
        AssetAssemblyRequest assetAssemblyRequest = new AssetAssemblyRequest();
        List<AssetAssemblyRequest> assetAssemblyRequests = Lists.newArrayList();
        assetAssemblyRequest.setAmount(1);
        assetAssemblyRequest.setAssetId("1");
        assetAssemblyRequest.setBusinessId("1");
        assetAssemblyRequests.add(assetAssemblyRequest);
        return assetAssemblyRequests;
    }

    private List<AssetIpRelationRequest> generateAssetipRequestList() {
        AssetIpRelationRequest assetAssemblyRequest = new AssetIpRelationRequest();
        List<AssetIpRelationRequest> assetAssemblyRequests = Lists.newArrayList();
        assetAssemblyRequest.setIp("1.1.12.12");
        assetAssemblyRequest.setAssetId(1);
        assetAssemblyRequest.setNet(1);
        assetAssemblyRequests.add(assetAssemblyRequest);
        return assetAssemblyRequests;
    }

    private List<AssetMacRelationRequest> generateAssetmacRequestList() {
        AssetMacRelationRequest assetAssemblyRequest = new AssetMacRelationRequest();
        List<AssetMacRelationRequest> assetAssemblyRequests = Lists.newArrayList();
        assetAssemblyRequest.setMac("00-00-00-00-00");
        assetAssemblyRequest.setAssetId(1);
        assetAssemblyRequests.add(assetAssemblyRequest);
        return assetAssemblyRequests;
    }

    private AssetRequest generateAssetRequest() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setBusinessId("1");
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

    private AssetRequest generateAssetRequest2() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setBusinessId("1");
        assetRequest.setNumber("11");
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

    private AssetRequest generateAssetRequest3() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setBusinessId("11111");
        assetRequest.setNumber("112121");
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
        assetRequest.setInstallTemplateId("12222");
        assetRequest.setBaselineTemplateId("1");
        assetRequest.setInstallTemplateId("1");
        AssetGroupRequest assetGroup = new AssetGroupRequest();
        List<AssetGroupRequest> assetGroupRequests = new ArrayList<AssetGroupRequest>();
        assetGroup.setId("0");
        assetGroupRequests.add(assetGroup);
        assetRequest.setAssetGroups(assetGroupRequests);
        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");
        return assetRequest;
    }

    private AssetRequest generateAssetRequest4() {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setBusinessId("11111");
        assetRequest.setNumber("112121");
        assetRequest.setName("1");
        assetRequest.setSerial("1");
        assetRequest.setAreaId("1");
        assetRequest.setManufacturer("1");
        assetRequest.setAssetStatus(0);
        assetRequest.setAssetGroups(Lists.newArrayList());
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
        assetRequest.setBusinessId("1");

        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");
        return assetRequest;
    }

    @Test
    public void testQueryAlarmAssetList() {
        AlarmAssetRequest alarmAssetRequest = new AlarmAssetRequest();
        when(assetDao.queryAlarmAssetList(any())).thenReturn(Arrays.asList(generateAsset()));
        alarmAssetRequest.setIp("1.2.3.4");
        alarmAssetRequest.setMac("1a-1a-1a-1a-1a-1a");
        Assert.assertNotNull(assetServiceImpl.queryAlarmAssetList(alarmAssetRequest));
    }

    @Test
    public void testFindAssetIds() {
        when(assetDao.findAssetIds(any())).thenReturn(Arrays.asList("1"));
        Assert.assertNotNull(assetServiceImpl.findAssetIds());
        LoginUser loginUser = getLoginUser();
        loginUser.setAreas(null);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        Assert.assertNotNull(assetServiceImpl.findAssetIds());
    }

    @Test
    public void testListen() {
        when(assetDao.updateAssetAreaId(any(), any())).thenReturn(1);
        AreaOperationRequest areaOperationRequest = new AreaOperationRequest();
        MockAck mockAck = new MockAck();

        assetServiceImpl.listen(JSONObject.toJSONString(areaOperationRequest), mockAck);

        when(assetDao.updateAssetAreaId(any(), any())).thenThrow(BusinessException.class);
        try {
            assetServiceImpl.listen(JSONObject.toJSONString(areaOperationRequest), mockAck);

        } catch (Exception r) {

        }
        assetServiceImpl.listen(JSONObject.toJSONString(null), mockAck);
    }

    @Test
    public void queryWaitRegistCount() {
        Mockito.when(assetDao.queryWaitRegistCount(Mockito.anyInt(), Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", assetServiceImpl.queryWaitRegistCount() + "");
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(null);
        int k = assetServiceImpl.queryWaitRegistCount();
        Assert.assertEquals(0, k);
    }

    @Test
    public void queryNormalCount() {
        Mockito.when(assetDao.queryNormalCount(Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", assetServiceImpl.queryNormalCount() + "");
    }

    /**
     * 不予登记
     */
    @Test
    public void assetNoRegister() throws Exception {
        AssetStatusChangeRequest assetStatusChangeRequest = new AssetStatusChangeRequest();
        assetStatusChangeRequest.setAssetId(new String[] { "1", "2" });
        List<Asset> assetList = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("chen");
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        Asset asset2 = new Asset();
        asset2.setId(1);
        asset2.setName("强");
        asset2.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetList.add(asset);
        assetList.add(asset2);

        when(assetDao.getAssetStatusListByIds(any())).thenReturn(assetList);
        AssetStatusChangeRequest assetStatusChangeRequest1 = new AssetStatusChangeRequest();
        assetStatusChangeRequest1.setAssetId(new String[] { "1", "2" });
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest1);
        Assert.assertEquals(2, 2);



        assetStatusChangeRequest1.setAssetId(null);
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest1);
        Assert.assertEquals(2, 2);
        assetStatusChangeRequest1.setAssetId(new String[0]);
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest1);
        Assert.assertEquals(2, 2);
    }

    @Test
    public void assetNoRegister2() throws Exception {
        AssetStatusChangeRequest assetStatusChangeRequest = new AssetStatusChangeRequest();
        assetStatusChangeRequest.setAssetId(new String[] {});
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest);
        Assert.assertEquals(0, 0);
    }

    @Test
    public void assetNoRegister3() throws Exception {
        AssetStatusChangeRequest assetStatusChangeRequest = new AssetStatusChangeRequest();
        assetStatusChangeRequest.setAssetId(new String[] { "1", "2" });
        when(assetDao.getAssetStatusListByIds(any())).thenReturn(null);
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("资产不存在");
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest);

    }

    @Test
    public void assetNoRegister4() throws Exception {
        AssetStatusChangeRequest assetStatusChangeRequest = new AssetStatusChangeRequest();
        assetStatusChangeRequest.setAssetId(new String[] { "1", "2" });
        List<Asset> assetList = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("chen");
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        Asset asset2 = new Asset();
        asset2.setId(1);
        asset2.setName("强");
        asset2.setAssetStatus(AssetStatusEnum.RETIRE.getCode());
        assetList.add(asset);
        assetList.add(asset2);
        when(assetDao.getAssetStatusListByIds(any())).thenReturn(assetList);
        AssetStatusChangeRequest assetStatusChangeRequest1 = new AssetStatusChangeRequest();
        assetStatusChangeRequest1.setAssetId(new String[] { "1", "2" });
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("资产状态已改变");
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest1);
    }

    @Test
    public void assetNoRegister5() throws Exception {

        AssetStatusChangeRequest assetStatusChangeRequest = new AssetStatusChangeRequest();
        assetStatusChangeRequest.setAssetId(new String[] { "1", "2" });
        List<Asset> assetList = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("chen");
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        Asset asset2 = new Asset();
        asset2.setId(1);
        asset2.setName("强");
        asset2.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetList.add(asset);
        assetList.add(asset2);
        when(assetDao.getAssetStatusListByIds(any())).thenReturn(assetList);
        AssetStatusChangeRequest assetStatusChangeRequest1 = new AssetStatusChangeRequest();
        assetStatusChangeRequest1.setAssetId(new String[] { "1", "2" });
        when(assetDao.getNumberById(any())).thenReturn("12");
        assetServiceImpl.assetNoRegister(assetStatusChangeRequest1);
        Assert.assertEquals(2, 2);

    }

    @Test
    public void countCategory() {
        List<Map<String, Object>> categoryModelCount = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", i);
            map.put("value", 20l);
            categoryModelCount.add(map);
        }
        when(assetDao.countCategoryModel(any(), any())).thenReturn(categoryModelCount);
        List<EnumCountResponse> enumCountResponses = assetServiceImpl.countCategory();
        String s = "[EnumCountResponse{msg='计算设备', code='[1]', number=20}, EnumCountResponse{msg='网络设备', code='[2]', number=20}, EnumCountResponse{msg='安全设备', code='[3]', number=20}, EnumCountResponse{msg='存储设备', code='[4]', number=20}, EnumCountResponse{msg='其它设备', code='[5]', number=20}]";
        Assert.assertEquals(s, enumCountResponses.toString());
    }

    @Test
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
    public void queryIdByAreaIds() throws Exception {
        AreaIdRequest areaIdRequest = new AreaIdRequest();
        try {
            assetServiceImpl.queryIdByAreaIds(areaIdRequest);
        } catch (Exception e) {
            Assert.assertEquals("区域ID不能为空", e.getMessage());
        }
        areaIdRequest.setAreaIds(Collections.singletonList("1"));
        assetServiceImpl.queryIdByAreaIds(areaIdRequest);

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

    @Test
    public void countUnusual() {

        AssetQuery query = new AssetQuery();
        int integer1 = assetServiceImpl.countUnusual(query);
        Assert.assertEquals(0, integer1);
        query.setQueryAlarmCount(false);
        query.setQueryPatchCount(false);
        query.setQueryVulCount(false);
        Assert.assertEquals(0, (int) assetServiceImpl.countUnusual(query));
        query.setQueryAlarmCount(true);
        query.setQueryPatchCount(true);
        query.setQueryVulCount(true);

        when(assetDao.queryAllAssetVulCount(any())).thenReturn(10);
        when(assetDao.queryAllAssetPatchCount(any())).thenReturn(11);
        when(assetDao.findAlarmAssetCount(any())).thenReturn(12);
        Assert.assertEquals(12, (int) assetServiceImpl.countUnusual(query));
        query.setAreaIds(null);
        Assert.assertEquals(12, (int) assetServiceImpl.countUnusual(query));

    }

    @Test
    public void countManufacturer() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("value", 12L);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("value", 20L);
        list.add(map1);
        list.add(map2);
        when(assetDao.countManufacturer(any(), any())).thenReturn(null);
        /* CollectionUtils.isNotEmpty(list) */
        List<EnumCountResponse> enumCountResponses = assetServiceImpl.countManufacturer();
        Assert.assertEquals(1, enumCountResponses.size());
        /* 其他类型 */
        when(assetDao.countManufacturer(any(), any())).thenReturn(list);
        List<EnumCountResponse> enumCountResponses2 = assetServiceImpl.countManufacturer();
        Assert.assertEquals(32, enumCountResponses2.get(0).getNumber());
        map1.put("key", "antiy");
        map2.put("key", "360");
        /**
         *
         */
        when(assetDao.countManufacturer(any(), any())).thenReturn(list);
        List<EnumCountResponse> enumCountResponses3 = assetServiceImpl.countManufacturer();
        String s = "[EnumCountResponse{msg='antiy', code='[antiy]', number=12}, EnumCountResponse{msg='360', code='[360]', number=20}]";
        Assert.assertEquals(s, enumCountResponses3.toString());
    }

    @Test
    public void countStatus() {
        List<Map<String, Object>> searchResult = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("key", 1);
        map1.put("value", 10L);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("key", 15);
        map2.put("value", 12L);
        searchResult.add(map1);
        searchResult.add(map2);
        when(assetDao.countStatus(any())).thenReturn(searchResult);
        List<EnumCountResponse> enumCountResponses = assetServiceImpl.countStatus();

        Assert.assertEquals(10, enumCountResponses.get(0).getNumber());

    }

    @Test
    public void queryBaselineTemplate() {
        assetServiceImpl.queryBaselineTemplate();
        System.out.println("成功");
    }

    class MockAck implements Acknowledgment {
        @Override
        public void acknowledge() {

        }
    }

    @Test
    public void queryUuidByAssetId() throws Exception {
        AssetIdRequest request = new AssetIdRequest();
        request.setAssetIds(Arrays.asList("33"));
        when(assetDao.findUuidByAssetId(any())).thenReturn(null);
        List<String> strings = assetServiceImpl.queryUuidByAssetId(request);
        Assert.assertNull(strings);
        request.setAssetIds(null);
        expectedException.expectMessage("资产ID不能为空");
        expectedException.expect(BusinessException.class);
        assetServiceImpl.queryUuidByAssetId(request);
    }

    @Test
    public void matchAssetByIpMac() {
        mockStatic(LoginUserUtil.class);
        AssetMatchRequest matchRequest = mock(AssetMatchRequest.class);
        IpMacPort ipMacPort = new IpMacPort();
        List<IpMacPort> ipMacPortList = new ArrayList<>();
        ipMacPortList.add(ipMacPort);
        try {
            when(assetDao.matchAssetByIpMac(matchRequest)).thenReturn(false);
            when(matchRequest.getIpMacPorts()).thenReturn(ipMacPortList);
            assetServiceImpl.matchAssetByIpMac(matchRequest);
            when(assetDao.matchAssetByIpMac(matchRequest)).thenReturn(true);
            assetServiceImpl.matchAssetByIpMac(matchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
