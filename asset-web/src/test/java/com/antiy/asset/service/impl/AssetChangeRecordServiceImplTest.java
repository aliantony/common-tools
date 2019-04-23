package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetChangeRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class, ComputerEquipmentFieldCompareImpl.class, AssetChangeRecordFactory.class, AssetChangeRecordServiceImpl.class})
@SpringBootTest
public class AssetChangeRecordServiceImplTest {

    @Mock
    private BaseConverter<AssetChangeRecordRequest, AssetChangeRecord> requestConverter;
    @Mock
    private AssetChangeRecordDao assetChangeRecordDao;
    @Mock
    private ActivityClient activityClient;
    @Mock
    private BaseConverter<AssetChangeRecord, AssetChangeRecordResponse> responseConverter;
    @Mock
    private AssetCategoryModelDao categoryModelDao;
    @InjectMocks
    private AssetChangeRecordServiceImpl assetChangeRecordService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        assetChangeRecordService = PowerMockito.spy(assetChangeRecordService);
        PowerMockito.mockStatic(AssetChangeRecordFactory.class);
    }

    @Test
    public void saveAssetChangeRecordTest() throws Exception {
        AssetChangeRecordRequest request = getAssetChangeRecordRequest();
        AssetChangeRecord assetChangeRecord = getAssetChangeRecord();
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser user = new LoginUser();
        user.setId(1);
        ActionResponse actionResponse = new ActionResponse(RespBasicCode.SUCCESS);
        PowerMockito.when(LoginUserUtil.class, "getLoginUser").thenReturn(user);
        Mockito.when(requestConverter.convert(request, AssetChangeRecord.class)).thenReturn(assetChangeRecord);
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(actionResponse);
        Mockito.when(assetChangeRecordDao.insert(assetChangeRecord)).thenReturn(1);
        Assert.assertEquals(assetChangeRecord.getId(), assetChangeRecordService.saveAssetChangeRecord(request).getBody());
    }

    @Test
    public void updateAssetChangeRecordTest() throws Exception {
        AssetChangeRecordRequest request = getAssetChangeRecordRequest();
        AssetChangeRecord assetChangeRecord = getAssetChangeRecord();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetChangeRecord.class)).thenReturn(assetChangeRecord);
        Mockito.when(assetChangeRecordDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetChangeRecordService.updateAssetChangeRecord(request));
    }

    @Test
    public void findPageAssetChangeRecordTest() throws Exception {
        AssetChangeRecordQuery query = new AssetChangeRecordQuery();
        List<AssetChangeRecord> assetChangeRecordList = new ArrayList<>();
        List<AssetChangeRecordResponse> expect = new ArrayList<>();
        PowerMockito.doReturn(1).when(assetChangeRecordService, "findCount", Mockito.any());
        Mockito.when(assetChangeRecordDao.findQuery(Mockito.any())).thenReturn(assetChangeRecordList);
        Mockito.when(responseConverter.convert(assetChangeRecordList, AssetChangeRecordResponse.class)).thenReturn(expect);
        Assert.assertEquals(1, assetChangeRecordService.findPageAssetChangeRecord(query).getTotalRecords());
        Assert.assertEquals(expect, assetChangeRecordService.findPageAssetChangeRecord(query).getItems());

    }

    @Test
    public void queryNetworkEquipmentByIdTest() throws Exception {
        List<Map<String, Object>> expect = new ArrayList<>();
        NetworkEquipmentFieldCompareImpl networkEquipmentFieldCompare = Mockito.mock(NetworkEquipmentFieldCompareImpl.class);
        PowerMockito.when(AssetChangeRecordFactory.getAssetChangeRecordProcess(Mockito.any())).thenReturn(networkEquipmentFieldCompare);
        Mockito.when(networkEquipmentFieldCompare.compareCommonBusinessInfo(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetChangeRecordService.queryNetworkEquipmentById(1));
    }

    @Test
    public void queryStorageEquipmentByIdTest() throws Exception {
        List<Map<String, Object>> expect = new ArrayList<>();
        StorageMediumFieldCompareImpl storageMediumFieldCompare = Mockito.mock(StorageMediumFieldCompareImpl.class);
        PowerMockito.when(AssetChangeRecordFactory.getAssetChangeRecordProcess(Mockito.any())).thenReturn(storageMediumFieldCompare);
        Mockito.when(storageMediumFieldCompare.compareCommonBusinessInfo(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetChangeRecordService.queryStorageEquipmentById(1));
    }

    @Test
    public void querySafetyEquipmentByIdTest() throws Exception {
        List<Map<String, Object>> expect = new ArrayList<>();
        SafetyEquipmentFieldCompareImpl safetyEquipmentFieldCompare = Mockito.mock(SafetyEquipmentFieldCompareImpl.class);
        PowerMockito.when(AssetChangeRecordFactory.getAssetChangeRecordProcess(Mockito.any())).thenReturn(safetyEquipmentFieldCompare);
        Mockito.when(safetyEquipmentFieldCompare.compareCommonBusinessInfo(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetChangeRecordService.querySafetyEquipmentById(1));
    }

    @Test
    public void queryOtherEquipmentByIdvTest() throws Exception {
        List<Map<String, Object>> expect = new ArrayList<>();
        OtherEquipmentFieldCompareImpl otherEquipmentFieldCompare = Mockito.mock(OtherEquipmentFieldCompareImpl.class);
        PowerMockito.when(AssetChangeRecordFactory.getAssetChangeRecordProcess(Mockito.any())).thenReturn(otherEquipmentFieldCompare);
        Mockito.when(otherEquipmentFieldCompare.compareCommonBusinessInfo(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetChangeRecordService.queryOtherEquipmentById(1));
    }

    @Test
    public void queryUniformChangeInfoTest1() throws Exception {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(4);
        assetCategoryModel.setParentId("2");
        List<Map<String, Object>> expect = new ArrayList<>();
        Map m = new HashMap();
        m.put("2", assetCategoryModel);
        expect.add(m);
        Mockito.when(categoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        PowerMockito.doReturn(assetCategoryModel).when(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        PowerMockito.doReturn(expect).when(assetChangeRecordService).queryComputerEquipmentById(Mockito.anyInt());
        Assert.assertEquals(expect, assetChangeRecordService.queryUniformChangeInfo(1, 1));
    }

    @Test
    public void queryUniformChangeInfoTest2() throws Exception {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(5);
        assetCategoryModel.setParentId("2");
        List<Map<String, Object>> expect = new ArrayList<>();
        Map m = new HashMap();
        m.put("2", assetCategoryModel);
        expect.add(m);
        Mockito.when(categoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        PowerMockito.doReturn(assetCategoryModel).when(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        PowerMockito.doReturn(expect).when(assetChangeRecordService).queryNetworkEquipmentById(Mockito.anyInt());
        Assert.assertEquals(expect, assetChangeRecordService.queryUniformChangeInfo(1, 1));
    }

    @Test
    public void queryUniformChangeInfoTest3() throws Exception {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(6);
        assetCategoryModel.setParentId("2");
        List<Map<String, Object>> expect = new ArrayList<>();
        Map m = new HashMap();
        m.put("2", assetCategoryModel);
        expect.add(m);
        Mockito.when(categoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        PowerMockito.doReturn(assetCategoryModel).when(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        PowerMockito.doReturn(expect).when(assetChangeRecordService).queryStorageEquipmentById(Mockito.anyInt());
        Assert.assertEquals(expect, assetChangeRecordService.queryUniformChangeInfo(1, 1));
    }

    @Test
    public void queryUniformChangeInfoTest4() throws Exception {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(7);
        assetCategoryModel.setParentId("2");
        List<Map<String, Object>> expect = new ArrayList<>();
        Map m = new HashMap();
        m.put("2", assetCategoryModel);
        expect.add(m);
        Mockito.when(categoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        PowerMockito.doReturn(assetCategoryModel).when(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        PowerMockito.doReturn(expect).when(assetChangeRecordService).querySafetyEquipmentById(Mockito.anyInt());
        Assert.assertEquals(expect, assetChangeRecordService.queryUniformChangeInfo(1, 1));
    }

    @Test
    public void queryUniformChangeInfoTest5() throws Exception {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(8);
        assetCategoryModel.setParentId("2");
        List<Map<String, Object>> expect = new ArrayList<>();
        Map m = new HashMap();
        m.put("2", assetCategoryModel);
        expect.add(m);
        Mockito.when(categoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        PowerMockito.doReturn(assetCategoryModel).when(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        PowerMockito.doReturn(expect).when(assetChangeRecordService).queryOtherEquipmentById(Mockito.anyInt());
        Assert.assertEquals(expect, assetChangeRecordService.queryUniformChangeInfo(1, 1));
    }

    @Test
    public void queryUniformChangeInfoTest6() throws Exception {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(9);
        assetCategoryModel.setParentId("2");
        List<Map<String, Object>> expect = new ArrayList<>();
        Map m = new HashMap();
        m.put("2", assetCategoryModel);
        expect.add(m);
        Mockito.when(categoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        PowerMockito.doReturn(assetCategoryModel).when(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        PowerMockito.doReturn(expect).when(assetChangeRecordService).queryOtherEquipmentById(Mockito.anyInt());
        Assert.assertNull(assetChangeRecordService.queryUniformChangeInfo(1, 1));
    }

    @Test
    public void queryComputerEquipmentByIdTest() throws Exception {
        List<Map<String, Object>> expect = new ArrayList<>();
        ComputerEquipmentFieldCompareImpl computerEquipmentFieldCompare = Mockito.mock(ComputerEquipmentFieldCompareImpl.class);
        PowerMockito.when(AssetChangeRecordFactory.getAssetChangeRecordProcess(Mockito.any())).thenReturn(computerEquipmentFieldCompare);
        Mockito.when(computerEquipmentFieldCompare.compareCommonBusinessInfo(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetChangeRecordService.queryComputerEquipmentById(1));
    }

    @Test
    public void getParentCategoryTest() throws Exception {
        List<AssetCategoryModel> allCategory = new AssetCategoryModelServiceImplTest().getAssetCategoryModelList();
        allCategory.get(0).setParentId("2");
        AssetCategoryModel assetCategoryModel = new AssetCategoryModelServiceImplTest().getAssetCategoryModel();
        assetCategoryModel.setParentId("1");
        Mockito.when(categoryModelDao.findAllCategory()).thenReturn(allCategory);
        AssetCategoryModel result = Whitebox.invokeMethod(assetChangeRecordService, "getParentCategory", assetCategoryModel);
        AssetCategoryModel expect = allCategory.get(0);
        Assert.assertEquals(expect, result);
    }

    private AssetChangeRecordRequest getAssetChangeRecordRequest() {
        AssetChangeRecordRequest request = new AssetChangeRecordRequest();
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        String assetId = "38";
        assetRequest.setId(assetId);
        assetOuterRequest.setAsset(assetRequest);
        request.setAssetOuterRequest(assetOuterRequest);
        return request;
    }

    private AssetChangeRecord getAssetChangeRecord() {
        AssetChangeRecord assetChangeRecord = new AssetChangeRecord();
        Integer expect = 101;
        assetChangeRecord.setId(expect);
        return assetChangeRecord;
    }

}
