package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecordBarPO;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import com.antiy.asset.vo.response.NameValueVo;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.exception.BusinessException;

@RunWith(PowerMockRunner.class)
// @PowerMockRunnerDelegate(SpringRunner.class)
public class AssetOperationRecordServiceImplTest {

    /**
     * 模拟Service，会发生真实调用
     */
    @InjectMocks
    private AssetOperationRecordServiceImpl                                           assetOperationRecordService;

    /**
     * 模拟Dao，将其注入上下文
     */
    @Mock
    private AssetOperationRecordDao                                                   assetOperationRecordDao;
    @Mock
    private SchemeDao                                                                 schemeDao;

    @Spy
    private BaseConverter<AssetOperationRecordBarPO, AssetOperationRecordBarResponse> operationRecordBarPOToResponseConverter = new BaseConverter<AssetOperationRecordBarPO, AssetOperationRecordBarResponse>();

    @Rule
    public ExpectedException                                                          expectedException                       = ExpectedException
        .none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 场景一：目标类型为硬件
     * @throws Exception
     */
    @Test
    public void queryStatusBar01() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.ASSET);
        assetOperationRecordQuery.setOriginStatus("ss");

        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent(AssetFlowEnum.HARDWARE_BASELINE_VALIDATE.getMsg());
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
        Mockito.when(schemeDao.findSchemeByAssetIdAndGmtCreateTime(Mockito.anyMap())).thenReturn(schemeList);

        List<NameValueVo> result = assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：目标类型为硬件-资产流程为入网实施HARDWARE_ENTER_IMPLEMENTATION
     * @throws Exception
     */
    @Test
    public void queryStatusBar02() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.ASSET);
        assetOperationRecordQuery.setOriginStatus("ss");

        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent(AssetFlowEnum.HARDWARE_ENTER_IMPLEMENTATION.getMsg());
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
        Mockito.when(schemeDao.findSchemeByAssetIdAndGmtCreateTime(Mockito.anyMap())).thenReturn(schemeList);

        List<NameValueVo> result = assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：目标类型为硬件-资产流程为效果检查-HARDWARE_EFFECT_CHECK
     * @throws Exception
     */
    @Test
    public void queryStatusBar03() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.ASSET);
        assetOperationRecordQuery.setOriginStatus("ss");

        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent(AssetFlowEnum.HARDWARE_EFFECT_CHECK.getMsg());
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
        Mockito.when(schemeDao.findSchemeByAssetIdAndGmtCreateTime(Mockito.anyMap())).thenReturn(schemeList);

        List<NameValueVo> result = assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：目标类型为硬件-资产流程为实施退役-VALIDATE_RETIRE_RESULT
     * @throws Exception
     */
    @Test
    public void queryStatusBar04() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.ASSET);
        assetOperationRecordQuery.setOriginStatus("ss");

        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent(AssetFlowEnum.VALIDATE_RETIRE_RESULT.getMsg());
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
        Mockito.when(schemeDao.findSchemeByAssetIdAndGmtCreateTime(Mockito.anyMap())).thenReturn(schemeList);

        List<NameValueVo> result = assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：目标类型为硬件-资产流程为实施退役-VALIDATE_RETIRE_RESULT-有附件信息
     * @throws Exception
     */
    @Test
    public void queryStatusBar05() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.ASSET);
        assetOperationRecordQuery.setOriginStatus("ss");

        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent(AssetFlowEnum.VALIDATE_RETIRE_RESULT.getMsg());
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
        scheme.setFileInfo(
            "[{\"uid\":0,\"name\":\"安全设备_0415.docx\",\"currFileName\":\"cb9d5cc085744c4bb007e00db2ebe4c0.docx\",\"fileSize\":382107,\"fileUrl\":\"/safety/20190528/cb9d5cc085744c4bb007e00db2ebe4c0.docx\",\"md5\":null,\"originFileName\":\"安全设备_0415.docx\"}]");
        List<Scheme> schemeList = new ArrayList<>();
        schemeList.add(scheme);
        assetOperationRecordBarPOList.add(assetOperationRecordBarPO);
        Mockito.when(assetOperationRecordDao.findAssetOperationRecordBarByAssetId(Mockito.any()))
            .thenReturn(assetOperationRecordBarPOList);
        Mockito.when(schemeDao.findSchemeByAssetIdAndGmtCreateTime(Mockito.anyMap())).thenReturn(schemeList);

        List<NameValueVo> result = assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景二：目标类型为软件
     *
     */
    @Test
    public void queryStatusBarWithSoftware() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.SOFTWARE);
        assetOperationRecordQuery.setOriginStatus("ss");
        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent("");
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
        Mockito.when(schemeDao.findSchemeByAssetIdAndGmtCreateTime(Mockito.anyMap())).thenReturn(schemeList);

        List<NameValueVo> result = assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景三：目标类型非软件/硬件-异常
     *
     */
    @Test
    public void queryStatusBarException() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.OPERATION_TYPE);
        assetOperationRecordQuery.setOriginStatus("ss");

        expectedException.expect(BusinessException.class);
        assetOperationRecordService.queryStatusBar(assetOperationRecordQuery);
    }

    @Test
    public void queryStatusBarOrderByTime() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery = new AssetOperationRecordQuery();
        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.SOFTWARE);
        assetOperationRecordQuery.setOriginStatus("ss");
        AssetOperationRecordBarPO assetOperationRecordBarPO = new AssetOperationRecordBarPO();
        assetOperationRecordBarPO.setGmtCreate(1551422114000L);
        assetOperationRecordBarPO.setProcessResult(1);
        assetOperationRecordBarPO.setId(1);
        assetOperationRecordBarPO.setContent("");
        List<AssetOperationRecordBarPO> assetOperationRecordBarPOList = new ArrayList<>();
        assetOperationRecordBarPOList.add(assetOperationRecordBarPO);
        Mockito.when(assetOperationRecordDao.findAssetOperationRecordBarByAssetId(Mockito.any()))
            .thenReturn(assetOperationRecordBarPOList);
        List<AssetOperationRecordBarResponse> result = assetOperationRecordService
            .queryStatusBarOrderByTime(assetOperationRecordQuery);
        Assert.assertThat(result, Matchers.notNullValue());
    }
}