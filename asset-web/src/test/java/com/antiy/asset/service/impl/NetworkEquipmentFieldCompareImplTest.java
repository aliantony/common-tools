package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.SysArea;
import com.antiy.common.base.SysUser;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetworkEquipmentFieldCompareImplTest {

    @MockBean
    private AssetChangeRecordDao assetChangeRecordDao;
    @MockBean
    private RedisUtil redisUtil;
    @SpyBean
    private NetworkEquipmentFieldCompareImpl networkEquipmentFieldCompare;

    @Test
    public void compareCommonBusinessInfo() throws Exception {
        List<String> changeValStrList=new ArrayList<>();
        when(assetChangeRecordDao.findChangeValByBusinessId(any())).thenReturn(changeValStrList);
        List<Map<String, Object>> mapList = networkEquipmentFieldCompare.compareCommonBusinessInfo(1);




        Assert.assertEquals(0,mapList.size());

        AssetOuterRequest oldAssetOuterRequest=new AssetOuterRequest ();
        AssetRequest oldRequest= new AssetRequest();
        oldRequest.setAreaId("1");
        oldRequest.setName("chen1");
        oldRequest.setManufacturer("zai1");
        oldRequest.setSerial("1");
        oldRequest.setContactTel("12345");
        oldRequest.setEmail("95151592@qq.com");
        oldRequest.setResponsibleUserId("11");
        oldAssetOuterRequest.setAsset(oldRequest);
        AssetNetworkEquipmentRequest oldNetworkEquipmentRequest=new AssetNetworkEquipmentRequest();
        oldNetworkEquipmentRequest.setAssetId("111");
        oldAssetOuterRequest.setAsset(oldRequest);
        oldAssetOuterRequest.setNetworkEquipment(oldNetworkEquipmentRequest);
        String old = JsonUtil.object2Json(oldAssetOuterRequest);

        AssetOuterRequest freshAssetOuterRequest=new AssetOuterRequest ();
        AssetRequest freshRequest= new AssetRequest();
        freshRequest.setAreaId("2");
        freshRequest.setName("chen2");
        freshRequest.setManufacturer("zai2");
        freshRequest.setSerial("2");
        freshRequest.setResponsibleUserId("22");
        AssetNetworkEquipmentRequest freshNetworkEquipmentRequest=new AssetNetworkEquipmentRequest();
        freshNetworkEquipmentRequest.setAssetId("22");
        freshNetworkEquipmentRequest.setPortSize(22);
        freshAssetOuterRequest.setAsset(freshRequest);
        freshAssetOuterRequest.setNetworkEquipment(freshNetworkEquipmentRequest);
        String fresh = JsonUtil.object2Json(freshAssetOuterRequest);

        changeValStrList.add(old);
        changeValStrList.add(fresh);


        when(assetChangeRecordDao.findChangeValByBusinessId(any())).thenReturn(changeValStrList);

        SysArea oldSysArea=new SysArea ();
        oldSysArea.setFullName("chenqiang");
        String oldAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(freshRequest.getAreaId()));
        when(redisUtil.getObject(oldAreaKey, SysArea.class)).thenReturn(oldSysArea);

        networkEquipmentFieldCompare.compareCommonBusinessInfo(1);
        System.out.println("成功");

        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(freshRequest.getResponsibleUserId()));
        SysUser sysUser=new  SysUser();
        sysUser.setName("chen");
        when(redisUtil.getObject(key, SysUser.class)).thenReturn(sysUser);
        networkEquipmentFieldCompare.compareCommonBusinessInfo(1);
        System.out.println("成功");
        changeValStrList.remove(0);
        networkEquipmentFieldCompare.compareCommonBusinessInfo(1);
        System.out.println("成功");
    }

    @Test
    public void compareComponentInfo() {

        networkEquipmentFieldCompare.compareComponentInfo();
        System.out.println("成功");
    }
}