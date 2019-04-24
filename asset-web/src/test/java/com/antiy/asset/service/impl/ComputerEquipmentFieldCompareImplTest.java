package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.request.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.SysUser;
import com.antiy.common.utils.JsonUtil;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RedisKeyUtil.class)
public class ComputerEquipmentFieldCompareImplTest {

    @InjectMocks
    private ComputerEquipmentFieldCompareImpl computerEquipmentFieldCompare;
    @Mock
    private AssetChangeRecordDao assetChangeRecordDao;
    @Mock
    private AssetSoftwareDao softwareDao;
    @Spy
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter = new BaseConverter<>();
    @Mock
    private RedisUtil redisUtil;

    @Before
    public void setUp()throws Exception{
        MockitoAnnotations.initMocks(this);
        SysArea newSysArea = new SysArea();
        newSysArea.setFullName("fullName");
        Mockito.when(redisUtil.getObject("area", SysArea.class)).thenReturn(newSysArea);
        PowerMockito.mockStatic(RedisKeyUtil.class);
        PowerMockito.when(RedisKeyUtil.getKeyWhenGetObject(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn("area");
    }
    // 情况一：oldAssetOuterRequest和newAssetOuterRequest都不为空
    @Test
    public void compareCommonBusinessInfoTest()throws Exception{
        List<Map<String, Object>> changeValList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("1", InfoLabelEnum.CPU.getMsg());
        changeValList.add(map);
        List<String> changeValStrList = new ArrayList<>();
        changeValStrList.add("{\"asset\": {\"id\": \"1\",\"assetGroups\": [{\"id\": \"1\"}],\"name\": \"test1\",\"manufacturer\": \"1\",\"areaId\": \"1\",\"responsibleUserId\": \"1\",\"importanceDegree\": 1,\"buyDate\": \"1554797330\",\"serviceLife\": \"1554797330000\",\"warranty\": \"1554797330000\",\"describle\": \"test\"},\"mainboard\": [{\"id\": \"1\",\"brand\":\"test\"}],\"memory\": [{\"id\": \"1\",\"brand\":\"test\"}],\"hardDisk\": [{\"id\": \"1\",\"brand\":\"test\"}],\"cpu\": [{\"id\": \"1\",\"brand\":\"test\"}],\"networkCard\": [{\"id\": \"1\",\"brand\":\"test\"}],\"networkEquipment\": {\"id\": \"1\"},\"safetyEquipment\": {\"id\": \"1\"},\"assetSoftwareRelationList\": [{\"id\": \"1\",\"memo\": \"test\"}]}");
        changeValStrList.add("{\"asset\": {\"id\": \"1\",\"assetGroups\": [{\"id\": \"1\"}],\"name\": \"test1\",\"manufacturer\": \"1\",\"areaId\": \"1\",\"responsibleUserId\": \"1\",\"importanceDegree\": 1,\"buyDate\": \"1554797330\",\"serviceLife\": \"1554797330000\",\"warranty\": \"1554797330000\",\"describle\": \"test\"},\"mainboard\": [{\"id\": \"1\"}],\"memory\": [{\"id\": \"1\"}],\"hardDisk\": [{\"id\": \"1\"}],\"cpu\": [{\"id\": \"1\"}],\"networkCard\": [{\"id\": \"1\"}],\"networkEquipment\": {\"id\": \"1\"},\"safetyEquipment\": {\"id\": \"1\"}}");
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("name");
        Mockito.when(softwareDao.getById(Mockito.any())).thenReturn(assetSoftware);
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(changeValStrList);
        List<Map<String, Object>> actual = computerEquipmentFieldCompare.compareCommonBusinessInfo(1);
        Assert.assertTrue(actual.size()>0);
    }
    // 情况二：oldAssetOuterRequest为空和newAssetOuterRequest不为空
    @Test
    public void compareCommonBusinessInfoTest2()throws Exception{
        List<Map<String, Object>> changeValList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("1", InfoLabelEnum.CPU.getMsg());
        changeValList.add(map);
        List<String> changeValStrList = new ArrayList<>();
        changeValStrList.add("{\"asset\": {\"id\": \"1\",\"assetGroups\": [{\"id\": \"1\"}],\"name\": \"test1\",\"manufacturer\": \"1\",\"areaId\": \"1\",\"responsibleUserId\": \"1\",\"importanceDegree\": 1,\"buyDate\": \"1554797330\",\"serviceLife\": \"1554797330000\",\"warranty\": \"1554797330000\",\"describle\": \"test\"},\"mainboard\": [{\"id\": \"1\",\"brand\":\"test\"}],\"memory\": [{\"id\": \"1\",\"brand\":\"test\"}],\"hardDisk\": [{\"id\": \"1\",\"brand\":\"test\"}],\"cpu\": [{\"id\": \"1\",\"brand\":\"test\"}],\"networkCard\": [{\"id\": \"1\",\"brand\":\"test\"}],\"networkEquipment\": {\"id\": \"1\"},\"safetyEquipment\": {\"id\": \"1\"},\"assetSoftwareRelationList\": [{\"id\": \"1\",\"memo\": \"test\"}]}");
        changeValStrList.add("{\"asset\": {\"id\": \"1\",\"assetGroups\": [{\"id\": \"1\"}],\"name\": \"test1\",\"manufacturer\": \"1\",\"areaId\": \"1\",\"responsibleUserId\": \"1\",\"importanceDegree\": 1,\"buyDate\": \"1554797330\",\"serviceLife\": \"1554797330000\",\"warranty\": \"1554797330000\",\"describle\": \"test\"}}");
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("name");
        Mockito.when(softwareDao.getById(Mockito.any())).thenReturn(assetSoftware);
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(changeValStrList);
        List<Map<String, Object>> actual2 = computerEquipmentFieldCompare.compareCommonBusinessInfo(1);
        Assert.assertTrue(actual2.size()>0);
    }
    // 情况三：oldAssetOuterRequest不为空和newAssetOuterRequest为空
    @Test
    public void compareCommonBusinessInfoTest3()throws Exception{
        List<Map<String, Object>> changeValList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("1", InfoLabelEnum.CPU.getMsg());
        changeValList.add(map);
        List<String> changeValStrList = new ArrayList<>();
        changeValStrList.add("{\"asset\": {\"id\": \"1\",\"assetGroups\": [{\"id\": \"1\"}],\"name\": \"test1\",\"manufacturer\": \"1\",\"areaId\": \"1\",\"responsibleUserId\": \"1\",\"importanceDegree\": 1,\"buyDate\": \"1554797330\",\"serviceLife\": \"1554797330000\",\"warranty\": \"1554797330000\",\"describle\": \"test\"}}");
        changeValStrList.add("{\"asset\": {\"id\": \"1\",\"assetGroups\": [{\"id\": \"1\"}],\"name\": \"test1\",\"manufacturer\": \"1\",\"areaId\": \"1\",\"responsibleUserId\": \"1\",\"importanceDegree\": 1,\"buyDate\": \"1554797330\",\"serviceLife\": \"1554797330000\",\"warranty\": \"1554797330000\",\"describle\": \"test\"},\"mainboard\": [{\"id\": \"1\",\"brand\":\"test\"}],\"memory\": [{\"id\": \"1\",\"brand\":\"test\"}],\"hardDisk\": [{\"id\": \"1\",\"brand\":\"test\"}],\"cpu\": [{\"id\": \"1\",\"brand\":\"test\"}],\"networkCard\": [{\"id\": \"1\",\"brand\":\"test\"}],\"networkEquipment\": {\"id\": \"1\"},\"safetyEquipment\": {\"id\": \"1\"},\"assetSoftwareRelationList\": [{\"id\": \"1\",\"memo\": \"test\"}]}");
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setName("name");
        Mockito.when(softwareDao.getById(Mockito.any())).thenReturn(assetSoftware);
        Mockito.when(assetChangeRecordDao.findChangeValByBusinessId(Mockito.any())).thenReturn(changeValStrList);
        List<Map<String, Object>> actual3 = computerEquipmentFieldCompare.compareCommonBusinessInfo(1);
        Assert.assertTrue(actual3.size()>0);
    }
}
