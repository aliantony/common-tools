package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.asset.vo.response.NameValueVo;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetOperationRecordServiceImplTest {

    /**
     * 模拟Service，会发生真实调用
     */
    @SpyBean
    private IAssetOperationRecordService iAssetOperationRecordService;

    /**
     * 模拟Dao，将其注入上下文
     */
    @MockBean
    private AssetOperationRecordDao assetOperationRecordDao;
    @MockBean
    private SchemeDao schemeDao;

    /**
     * 场景一：目标类型为硬件
     * @throws Exception
     */
    @Test
    public void queryStatusBar() throws Exception{
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.ASSET);
        assetOperationRecordQuery.setOriginStatus("ss");
        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setAssetId("1");
        scheme.setType(1);
        scheme.setResult(1);
        scheme.setPutintoUserId(1);
        scheme.setPutintoUser("ss");
        scheme.setPutintoTime(1551422114000L);
        scheme.setOrderLevel(1);
        scheme.setExpecteEndTime(1651422114000L);
        scheme.setExpecteStartTime(1551422114000L);
        List<Scheme> schemeList = new ArrayList<>();
        schemeList.add(scheme);
        assetOperationRecordBarPOList.add(assetOperationRecordBarPO);
        Mockito.when(assetOperationRecordDao.findAssetOperationRecordBarByAssetId(Mockito.any()))
                .thenReturn(assetOperationRecordBarPOList);
        Mockito.when(schemeDao.findSchemeByAssetIdAndStatus(Mockito.anyMap())).thenReturn(schemeList);
        List<NameValueVo> result = iAssetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景二：目标类型为软件
     *
     */
    @Test
    public void queryStatusBarWithSoftware() throws Exception{
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.SOFTWARE);
        assetOperationRecordQuery.setOriginStatus("ss");
        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = new ArrayList<>();
        Scheme scheme = new Scheme();
        scheme.setAssetId("1");
        scheme.setType(1);
        scheme.setResult(1);
        scheme.setPutintoUserId(1);
        scheme.setPutintoUser("ss");
        scheme.setPutintoTime(1551422114000L);
        scheme.setOrderLevel(1);
        scheme.setExpecteEndTime(1651422114000L);
        scheme.setExpecteStartTime(1551422114000L);
        List<Scheme> schemeList = new ArrayList<>();
        schemeList.add(scheme);
        assetOperationRecordBarPOList.add(assetOperationRecordBarPO);
        Mockito.when(assetOperationRecordDao.findAssetOperationRecordBarByAssetId(Mockito.any()))
                .thenReturn(assetOperationRecordBarPOList);
        Mockito.when(schemeDao.findSchemeByAssetIdAndStatus(Mockito.anyMap())).thenReturn(schemeList);
        List<NameValueVo> result = iAssetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    @Test
    public void queryStatusBarOrderByTime() throws Exception{
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.SOFTWARE);
        assetOperationRecordQuery.setOriginStatus("ss");
        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = new ArrayList<>();
        assetOperationRecordBarPOList.add(assetOperationRecordBarPO);
        Mockito.when(assetOperationRecordDao.findAssetOperationRecordBarByAssetId(Mockito.any()))
                .thenReturn(assetOperationRecordBarPOList);
        List<AssetOperationRecordBarResponse> result = iAssetOperationRecordService
                .queryStatusBarOrderByTime(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }
}