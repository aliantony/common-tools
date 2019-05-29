package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.dao.AssetCpuDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.dao.AssetHardDiskDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetMainboradDao;
import com.antiy.asset.dao.AssetMemoryDao;
import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.dao.AssetStorageMediumDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.entity.AssetSafetyEquipment;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.entity.AssetStorageMedium;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.asset.vo.request.AssetHardDiskRequest;
import com.antiy.asset.vo.request.AssetMainboradRequest;
import com.antiy.asset.vo.request.AssetMemoryRequest;
import com.antiy.asset.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.EnumCountResponse;
import com.antiy.asset.vo.response.WaitingTaskReponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.google.common.collect.Lists;
import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetServiceImplTest {
    @Mock
    AssetDao assetDao;
    @Mock
    AssetMainboradDao assetMainboradDao;
    @Mock
    AssetMemoryDao assetMemoryDao;
    @Mock
    AssetHardDiskDao assetHardDiskDao;
    @Mock
    AssetCpuDao assetCpuDao;
    @Mock
    AssetNetworkCardDao assetNetworkCardDao;
    @Mock
    AssetNetworkEquipmentDao assetNetworkEquipmentDao;
    @Mock
    AssetSafetyEquipmentDao assetSafetyEquipmentDao;
    @Mock
    AssetSoftwareDao assetSoftwareDao;
    @Mock
    AssetSoftwareLicenseDao assetSoftwareLicenseDao;
    @Mock
    AssetCategoryModelDao assetCategoryModelDao;
    @SpyBean
    TransactionTemplate transactionTemplate;
    @Mock
    AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Mock
    AssetStorageMediumDao assetStorageMediumDao;
    @Mock
    AssetOperationRecordDao assetOperationRecordDao;
    @Spy
    BaseConverter<AssetRequest, Asset> requestConverter;
    @Spy
    BaseConverter<Asset, AssetRequest> assetToRequestConverter;
    @Spy
    BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationRequest> softRelationToRequestConverter;
    @Spy
    BaseConverter<AssetMainborad, AssetMainboradRequest> mainboradToRequestConverter;
    @Spy
    BaseConverter<AssetCpu, AssetCpuRequest> cpuToRequestConverter;
    @Spy
    BaseConverter<AssetNetworkCard, AssetNetworkCardRequest> networkCardToRequestConverter;
    @Spy
    BaseConverter<AssetHardDisk, AssetHardDiskRequest> hardDiskToRequestConverter;
    @Spy
    BaseConverter<AssetMemory, AssetMemoryRequest> memoryToRequestConverter;
    @Spy
    BaseConverter<Asset, AssetResponse> responseConverter;
    @Spy
    BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentRequest> safetyEquipmentToRequestConverter;
    @Spy
    BaseConverter<AssetStorageMedium, AssetStorageMediumRequest> storageMediumToRequestConverter;
    @Spy
    BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentRequest> networkEquipmentToRequestConverter;
    @Mock
    AssetUserDao assetUserDao;
    @Mock
    AssetGroupRelationDao assetGroupRelationDao;
    @Mock
    AssetChangeRecordDao assetChangeRecordDao;
    @Mock
    ExcelDownloadUtil excelDownloadUtil;
    @Spy
    AssetEntityConvert assetEntityConvert;
    @Mock
    IAssetCategoryModelService iAssetCategoryModelService;
    @Mock
    AssetGroupDao assetGroupDao;
    @Mock
    IAssetCategoryModelService assetCategoryModelService;
    @Mock
    ActivityClient activityClient;
    @Mock
    AreaClient areaClient;
    @Mock
    Logger logger;
    @Mock
    AesEncoder aesEncoder;
    @Mock
    RedisUtil redisUtil;
    @Mock
    AssetLinkRelationDao assetLinkRelationDao;
    @Mock
    OperatingSystemClient operatingSystemClient;
    @Mock
    IBaseDao baseDao;
    @Spy
    @InjectMocks
    AssetServiceImpl assetServiceImpl;

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
    public void testSaveAsset() throws Exception {
        when(assetDao.findCountIp(any())).thenReturn(0);
        when(assetUserDao.getById(any())).thenReturn(new AssetUser());
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        when(activityClient.manualStartProcess(any())).thenReturn(null);
        when(assetCategoryModelDao.getById(any())).thenReturn(new AssetCategoryModel());
        when(redisUtil.getObject(any(), any(Class.class))).thenReturn(new SysArea());

        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setNumber("");
        assetRequest.setName("");
        assetRequest.setSerial("");
        assetRequest.setAreaId("1");
        assetRequest.setManufacturer("");
        assetRequest.setAssetStatus(0);
        assetRequest.setOperationSystem("");
        assetRequest.setSystemBit(0);
        assetRequest.setLocation("");
        assetRequest.setFirmwareVersion("");
        assetRequest.setUuid("");
        assetRequest.setResponsibleUserId("1");
        assetRequest.setAssetSource(0);
        assetRequest.setImportanceDegree(0);
        assetRequest.setCategoryModel("1");
        assetRequest.setParentId("1");
        assetRequest.setTags("");
        assetRequest.setServiceLife(0L);
        assetRequest.setBuyDate(0L);
        assetRequest.setWarranty("0");
        assetRequest.setId("1");
        assetRequest.setEmail("");
        assetRequest.setContactTel("1");
        assetRequest.setHouseLocation("1");
        assetRequest.setAssetGroups(Lists.newArrayList());
        assetRequest.setInstallType(0);
        assetRequest.setDescrible("1");
        assetRequest.setSoftwareVersion("1");
        assetOuterRequest.setAsset(assetRequest);

        ActionResponse result = assetServiceImpl.saveAsset(assetOuterRequest);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateAsset() throws Exception {
        Integer result = assetServiceImpl.updateAsset(new AssetRequest());
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testFindListAsset() throws Exception {
        Asset asset = new Asset();
        asset.setAreaName("");
        asset.setResponsibleUserName("");
        asset.setCategoryModelName("");
        asset.setHardDisk("");
        asset.setMemory("");
        asset.setCpu("");
        asset.setNetworkCard("");
        asset.setParentId("");
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
        asset.setAssetStatus(0);
        asset.setAdmittanceStatus(0);
        asset.setOperationSystem("");
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
        asset.setWarranty("0");
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

        when(assetDao.findListAsset(any())).thenReturn(Arrays.asList(asset));

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
        waitingTaskReponse.setBusinessId("");

        actionResponse.setBody(Arrays.asList(waitingTaskReponse));

        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);

        List<AssetResponse> result = assetServiceImpl.findListAsset(new AssetQuery(), null);
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindCountAsset() throws Exception {
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
        waitingTaskReponse.setBusinessId("");

        actionResponse.setBody(Arrays.asList(waitingTaskReponse));

        when(activityClient.queryAllWaitingTask(any())).thenReturn(actionResponse);

        Integer result = assetServiceImpl.findCountAsset(new AssetQuery());
        Assert.assertEquals(Integer.valueOf(0), result);
    }

    @Test
    public void testGetAllHardWaitingTask() throws Exception {
        ActionResponse<List<WaitingTaskReponse>> actionResponse =
                ActionResponse.success();
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
        when(assetDao.findUnconnectedCount(any())).thenReturn(0);
        when(assetDao.findListUnconnectedAsset(any())).thenReturn(Arrays.asList(new Asset()));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt())).thenReturn(Arrays.asList(0));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(iAssetCategoryModelService.recursionSearchParentCategory(anyString(), any(), any())).thenReturn("recursionSearchParentCategoryResponse");
        when(iAssetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {{
            put("String", "String");
        }});
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.recursionSearchParentCategory(anyString(), any(), any())).thenReturn("recursionSearchParentCategoryResponse");
        when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {{
            put("String", "String");
        }});

        PageResult<AssetResponse> result = assetServiceImpl.findUnconnectedAsset(new AssetQuery());
        Assert.assertNotNull(result);
    }

    @Test
    public void testSaveReportAsset() throws Exception {
//        assetServiceImpl.saveReportAsset(Arrays.asList(new AssetOuterRequest()));
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
        ipMac.add(new String[]{"string"});

        boolean result = assetServiceImpl.checkRepeatAsset("uuid", ipMac);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testChangeStatus() throws Exception {
        when(assetDao.changeStatus(any())).thenReturn(0);

        Integer result = assetServiceImpl.changeStatus(new String[]{"ids"}, 0);
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
        when(assetDao.countStatus(any())).thenReturn(Arrays.asList(new HashMap<String, Long>() {{
            put("String", 1L);
        }}));

        List<EnumCountResponse> result = assetServiceImpl.countStatus();
        Assert.assertNotNull(result);
    }

    @Test
    public void testCountCategory() throws Exception {
        when(assetDao.findCountByCategoryModel(any())).thenReturn(0);
        when(assetCategoryModelDao.getNextLevelCategoryByName(anyString())).thenReturn(Arrays.asList(new AssetCategoryModel()));
        when(iAssetCategoryModelService.recursionSearch(any(), anyInt())).thenReturn(Arrays.asList(new AssetCategoryModel()));
        when(assetCategoryModelService.recursionSearch(any(), anyInt())).thenReturn(Arrays.asList(new AssetCategoryModel()));

        List<EnumCountResponse> result = assetServiceImpl.countCategory();
        Assert.assertNotNull(result);
    }

    @Test
    public void testQueryAssetByIds() throws Exception {
        when(assetDao.queryAssetByIds(any())).thenReturn(Arrays.asList(new Asset()));

        List<AssetResponse> result = assetServiceImpl.queryAssetByIds(new Integer[]{0});
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetByAssetId() throws Exception {
        when(assetDao.findListAsset(any())).thenReturn(Arrays.asList(new Asset()));
        when(assetSoftwareRelationDao.getSoftByAssetId(anyInt())).thenReturn(Arrays.asList(new AssetSoftware()));
        when(assetSoftwareRelationDao.getReleationByAssetId(anyInt())).thenReturn(Arrays.asList(new AssetSoftwareRelation()));
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

    @Test
    public void testChangeAsset() throws Exception {
        when(assetDao.findCountIp(any())).thenReturn(0);
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

        when(assetNetworkCardDao.getById(any())).thenReturn(assetNetworkCard);
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
        when(assetSoftwareRelationDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetGroupRelationDao.deleteByAssetId(anyInt())).thenReturn(0);
        when(assetGroupRelationDao.insertBatch(any())).thenReturn(0);
        when(activityClient.manualStartProcess(any())).thenReturn(null);
        when(activityClient.completeTask(any())).thenReturn(null);
        when(assetLinkRelationDao.deleteRelationByAssetId(any())).thenReturn(0);

        Asset asset = new Asset();
        asset.setOperationSystemName("");
        asset.setAreaName("");
        asset.setResponsibleUserName("");
        asset.setCategoryModelName("");
        asset.setHardDisk("");
        asset.setMemory("");
        asset.setCpu("");
        asset.setNetworkCard("");
        asset.setParentId("");
        asset.setIp("");
        asset.setMac("");
        asset.setAssetGroup("");
        asset.setNumber("");
        asset.setName("");
        asset.setEthernetPort(0);
        asset.setSerialPort(0);
        asset.setInstallType(0);
        asset.setSerial("");
        asset.setAreaId("");
        asset.setCategoryModel("");
        asset.setManufacturer("");
        asset.setAssetStatus(8);
        asset.setAdmittanceStatus(0);
        asset.setOperationSystem("");
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

        when(assetDao.getById(any())).thenReturn(asset);

        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        assetOuterRequest.setAsset(assetRequest);
        assetRequest.setFirstEnterNett(0L);
        assetRequest.setAdmittanceStatus(0);
        assetRequest.setNumber("");
        assetRequest.setName("");
        assetRequest.setSerial("");
        assetRequest.setAreaId("");
        assetRequest.setManufacturer("");
        assetRequest.setAssetStatus(0);
        assetRequest.setOperationSystem("");
        assetRequest.setSystemBit(0);
        assetRequest.setLocation("");
        assetRequest.setFirmwareVersion("");
        assetRequest.setUuid("");
        assetRequest.setResponsibleUserId("");
        assetRequest.setAssetSource(0);
        assetRequest.setImportanceDegree(0);
        assetRequest.setCategoryModel("");
        assetRequest.setParentId("");
        assetRequest.setTags("");
        assetRequest.setServiceLife(0L);
        assetRequest.setBuyDate(0L);
        assetRequest.setWarranty("0");
        assetRequest.setId("");
        assetRequest.setEmail("");
        assetRequest.setContactTel("");
        assetRequest.setHouseLocation("");
        assetRequest.setAssetGroups(Lists.newArrayList());
        assetRequest.setInstallType(0);
        assetRequest.setDescrible("");
        assetRequest.setSoftwareVersion("");

        // cpu
        AssetCpuRequest assetCpuRequest = new AssetCpuRequest();
        assetCpuRequest.setId("");
        assetCpuRequest.setSerial("");
        assetCpuRequest.setBrand("");
        assetCpuRequest.setModel("");
        assetCpuRequest.setMainFrequency(0.0F);
        assetCpuRequest.setThreadSize(0);
        assetCpuRequest.setCoreSize(0);
        assetCpuRequest.setAssetId("");
        assetOuterRequest.setCpu(Arrays.asList(assetCpuRequest));

        // 主板信息
        AssetMainboradRequest assetMainboradRequest = new AssetMainboradRequest();
        assetMainboradRequest.setAssetId("1");
        assetMainboradRequest.setId("");
        assetMainboradRequest.setBrand("");
        assetMainboradRequest.setModel("");
        assetMainboradRequest.setSerial("");
        assetMainboradRequest.setBiosVersion("");
        assetMainboradRequest.setBiosDate(0L);
        assetMainboradRequest.setMemo("");
        List<AssetMainboradRequest> assetMainboradRequestList = Arrays.asList(assetMainboradRequest);
        assetOuterRequest.setMainboard(assetMainboradRequestList);

        // 内存信息
        AssetMemoryRequest memoryRequest = new AssetMemoryRequest();
        memoryRequest.setId("1");
        memoryRequest.setAssetId("");
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
        List<AssetMemoryRequest> assetMemoryRequestList = Arrays.asList(memoryRequest);
        assetOuterRequest.setMemory(assetMemoryRequestList);

        // 硬盘信息
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
        List<AssetHardDiskRequest> assetHardDiskRequestList = Arrays.asList(hardDiskRequest);
        assetOuterRequest.setHardDisk(assetHardDiskRequestList);

        Integer result = assetServiceImpl.changeAsset(assetOuterRequest);
        Assert.assertEquals(Integer.valueOf(0), result);
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
        assetCategoryModel.setId(1);

        when(assetCategoryModelDao.getNextLevelCategoryByName(anyString()))
                .thenReturn(Arrays.asList(assetCategoryModel));

        // assetServiceImpl.exportTemplate(new Integer[]{1});
    }

    // @Test
    // public void testImportPc() throws Exception {
    //     when(assetDao.findCountIp(any())).thenReturn(0);
    //     when(assetMainboradDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
    //     when(assetMemoryDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
    //     when(assetHardDiskDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
    //     when(assetCpuDao.insertBatchWithId(any(), anyInt())).thenReturn(0);
    //     when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
    //     when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
    //     when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
    //
    //     String result = assetServiceImpl.importPc(null, new AssetImportRequest());
    //     Assert.assertEquals("replaceMeWithExpectedResult", result);
    // }
    //
    // @Test
    // public void testImportNet() throws Exception {
    //     when(assetDao.findCountIp(any())).thenReturn(0);
    //     when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
    //     when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
    //     when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
    //
    //     MultipartFile multipartFile = new MockMultipartFile("123", new byte[]{});
    //     String result = assetServiceImpl.importNet(multipartFile, new AssetImportRequest());
    //     Assert.assertEquals("replaceMeWithExpectedResult", result);
    // }
    //
    // @Test
    // public void testImportSecurity() throws Exception {
    //     when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
    //     when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
    //     when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
    //
    //     String result = assetServiceImpl.importSecurity(null, new AssetImportRequest());
    //     Assert.assertEquals("replaceMeWithExpectedResult", result);
    // }
    //
    // @Test
    // public void testImportStory() throws Exception {
    //     when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
    //     when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
    //     when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
    //
    //     String result = assetServiceImpl.importStory(null, new AssetImportRequest());
    //     Assert.assertEquals("replaceMeWithExpectedResult", result);
    // }
    //
    // @Test
    // public void testImportOhters() throws Exception {
    //     when(assetUserDao.findListAssetUser(any())).thenReturn(Arrays.asList(new AssetUser()));
    //     when(activityClient.startProcessWithoutFormBatch(any())).thenReturn(null);
    //     when(areaClient.queryCdeAndAreaId(anyString())).thenReturn(null);
    //
    //     String result = assetServiceImpl.importOhters(null, new AssetImportRequest());
    //     Assert.assertEquals("replaceMeWithExpectedResult", result);
    // }

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
        assetResponse.setParentId(0+"");
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

//        assetServiceImpl.exportData(new AssetQuery(), new Response());
    }

    @Test
    public void testPulldownUnconnectedManufacturer() throws Exception {
        when(assetDao.pulldownUnconnectedManufacturer(any())).thenReturn(Arrays.asList("String"));
        when(iAssetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(iAssetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {{
            put("String", "String");
        }});
        when(assetCategoryModelService.findAssetCategoryModelIdsById(anyInt(), any())).thenReturn(Arrays.asList(0));
        when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(new HashMap<String, String>() {{
            put("String", "String");
        }});

//        List<String> result = assetServiceImpl.pulldownUnconnectedManufacturer(1);
//        Assert.assertEquals(Arrays.asList("String"), result);
    }
}
