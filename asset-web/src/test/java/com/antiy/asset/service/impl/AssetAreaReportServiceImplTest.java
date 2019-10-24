package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.asset.vo.response.ReportTableHead;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.SysArea;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LogUtils.class, RedisKeyUtil.class })
// @SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetAreaReportServiceImplTest {

    @InjectMocks
    private AssetAreaReportServiceImpl assetAreaReportService;

    @Mock
    private AssetReportDao             assetReportDao;

    @Mock
    private RedisUtil                  redisUtil;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RequestContextHolder.class);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));

        Logger logger = Mockito.mock(Logger.class);
        PowerMockito.when(LogUtils.class, "get").thenReturn(logger);
    }

    @Test
    public void getAssetWithAreaTest() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse expected = new AssetReportResponse();
        List<String> list = new ArrayList<>();
        list.add("1");
        ReportData reportData = new ReportData();
        List<Integer> date = new ArrayList<>();
        date.add(1);
        reportData.setData(date);
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(reportData);
        date.add(2);
        reportData.setData(date);
        reportDataList.add(reportData);
        expected.setList(reportDataList);
        List<Integer> allDataList = new ArrayList<>();
        allDataList.add(2);
        expected.setAlldata(allDataList);
        reportRequest.setTopAreaId("5");
        reportRequest.setTopAreaName("1");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(new SysArea());
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        // 情景一：TOP5为true
        AssetReportResponse actual = assetAreaReportService.getAssetWithArea(reportRequest);
        Assert.assertEquals(expected.getAlldata().get(0), actual.getAlldata().get(0));

        // 情景二：TOP5为false
        reportRequest.setTopFive(false);
        AssetReportResponse actual2 = assetAreaReportService.getAssetWithArea(reportRequest);

    }

    @Test
    public void getAssetWithAreaTest1() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse expected = new AssetReportResponse();
        List<String> list = new ArrayList<>();
        list.add("1");
        ReportData reportData = new ReportData();
        List<Integer> date = new ArrayList<>();
        date.add(1);
        reportData.setData(date);
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(reportData);
        date.add(2);
        reportData.setData(date);
        reportDataList.add(reportData);
        expected.setList(reportDataList);
        List<Integer> allDataList = new ArrayList<>();
        allDataList.add(2);
        expected.setAlldata(allDataList);
        reportRequest.setTopAreaId("5");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(sysArea);
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);

        AssetReportResponse actual = assetAreaReportService.getAssetWithArea(reportRequest);
        Assert.assertEquals("4", actual.getAlldata().get(0) + "");

    }

    @Test
    public void exportAreaTableTest2() throws Exception {
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        reportRequest.setTopAreaId("5");

        // -----------
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        report.setStringId("1");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        assetAreaId.add(report);

        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");

        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);
        // -----------end

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);

        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any())).thenReturn(addData);

        // AssetReportResponse expected = new AssetReportResponse();
        // ReportData reportData = new ReportData();
        // List<Integer> date = new ArrayList<>();
        // date.add(1);
        // reportData.setData(date);
        // List<ReportData> reportDataList = new ArrayList<>();
        // reportDataList.add(reportData);
        // date.add(2);
        // reportData.setData(date);
        // reportDataList.add(reportData);
        // expected.setList(reportDataList);
        // List<Integer> allDataList = new ArrayList<>();
        // allDataList.add(2);
        // expected.setAlldata(allDataList);

        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(sysArea);

        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);

        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));
        assetAreaReportService.exportAreaTable(reportRequest);
    }

    @Test
    public void getAssetWithAreaTest3() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse expected = new AssetReportResponse();
        List<String> list = new ArrayList<>();
        list.add("1");
        ReportData reportData = new ReportData();
        List<Integer> date = new ArrayList<>();
        date.add(1);
        reportData.setData(date);
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(reportData);
        date.add(2);
        reportData.setData(date);
        reportDataList.add(reportData);
        expected.setList(reportDataList);
        List<Integer> allDataList = new ArrayList<>();
        allDataList.add(2);
        expected.setAlldata(allDataList);
        reportRequest.setTopAreaId("5");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(null);
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        try {
            assetAreaReportService.getAssetWithArea(reportRequest);
        } catch (Exception e) {
            Assert.assertEquals("获取顶级区域名称失败", e.getMessage());
        }

    }

    @Test
    public void getAssetWithAreaTest4() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse expected = new AssetReportResponse();
        List<String> list = new ArrayList<>();
        list.add("1");
        ReportData reportData = new ReportData();
        List<Integer> date = new ArrayList<>();
        date.add(1);
        reportData.setData(date);
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(reportData);
        date.add(2);
        reportData.setData(date);
        reportDataList.add(reportData);
        expected.setList(reportDataList);
        List<Integer> allDataList = new ArrayList<>();
        allDataList.add(2);
        expected.setAlldata(allDataList);
        reportRequest.setTopAreaId("5");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenThrow(new BusinessException("获取失败"));
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        try {
            assetAreaReportService.getAssetWithArea(reportRequest);
        } catch (Exception e) {
            Assert.assertNull(e.getMessage());
        }
    }

    @Test
    public void getAssetWithAreaTest2() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse expected = new AssetReportResponse();
        List<String> list = new ArrayList<>();
        list.add("1");
        ReportData reportData = new ReportData();
        List<Integer> date = new ArrayList<>();
        date.add(1);
        reportData.setData(date);
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(reportData);
        date.add(2);
        reportData.setData(date);
        reportDataList.add(reportData);
        expected.setList(reportDataList);
        List<Integer> allDataList = new ArrayList<>();
        allDataList.add(2);
        expected.setAlldata(allDataList);
        reportRequest.setTopAreaId("5");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(sysArea);
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(null);
        try {
            assetAreaReportService.getAssetWithArea(reportRequest);
        } catch (Exception e) {
            Assert.assertEquals(null, e.getMessage());
        }

    }

    @Test
    public void getAssetWithAreaTest5() throws Exception {
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setTopAreaId("0");
        reportRequest.setTopAreaName("topArea");

        List<AssetAreaReportRequest> assetAreaReportRequests = new ArrayList<>();
        AssetAreaReportRequest areaReportRequest = new AssetAreaReportRequest();
        areaReportRequest.setParentAreaName("parent");
        areaReportRequest.setParentAreaId("1");
        assetAreaReportRequests.add(areaReportRequest);

        reportRequest.setAssetAreaIds(assetAreaReportRequests);

        // mock begin
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);

        // 初始化数据,开始时间之前的数据
        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);

        // 新增数据
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any())).thenReturn(addData);

        assetAreaReportService.getAssetWithArea(reportRequest);
        // getTopFive方法中返回的areaId是请求参数中ParentName已存在的
        // top5的区域名称信息在请求参数中不存在
    }

    @Test
    public void queryAreaTableTest() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");

        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("a", 1);
        allAssetCountMap.put("b", 2);
        allAssetCountMap.put("c", 3);
        allAssetCountMap.put("d", 4);
        allAssetCountMap.put("e", 5);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse assetReportResponse = new AssetReportResponse();
        List<String> dates = new ArrayList<>();
        dates.add("1");
        assetReportResponse.setDate(dates);

        AssetReportTableResponse assetReportTableResponse = new AssetReportTableResponse();
        assetReportTableResponse.setFormName("资产区域统计表格");
        List<ReportTableHead> reportTableHeads = new ArrayList<>();
        ReportTableHead reportTableHead = new ReportTableHead();
        reportTableHead.setKey("1");
        reportTableHead.setName("1");
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(100L);
        reportTableHeads.add(reportTableHead);
        assetReportTableResponse.setChildren(reportTableHeads);
        reportRequest.setTopAreaId("1");
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        AssetReportTableResponse actual = assetAreaReportService.queryAreaTable(reportRequest);
        Assert.assertEquals(assetReportTableResponse.getFormName(), actual.getFormName());
    }

    @Test
    public void queryAreaTableTest2() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<String> childrenAradId = new ArrayList<>();
        childrenAradId.add("2");
        childrenAradId.add("3");
        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
        report2.setParentAreaId("1");
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(0L);
        reportRequest.setEndTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("areaId", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map.put("assetCount", 1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("areaId", "1");
        dataMap.put("date", "1");
        dataMap.put("parent", "1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent", 1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2", 2);
        allAssetCount.add(allAssetCountMap);
        allAssetCount.add(allAssetCountMap2);
        allAssetCount.add(allAssetCountMap);

        AssetReportResponse expected = new AssetReportResponse();
        List<String> list = new ArrayList<>();
        list.add("1");
        ReportData reportData = new ReportData();
        List<Integer> date = new ArrayList<>();
        date.add(1);
        reportData.setData(date);
        List<ReportData> reportDataList = new ArrayList<>();
        reportDataList.add(reportData);
        date.add(2);
        reportData.setData(date);
        reportDataList.add(reportData);
        expected.setList(reportDataList);
        List<Integer> allDataList = new ArrayList<>();
        allDataList.add(2);
        expected.setAlldata(allDataList);
        reportRequest.setTopAreaId("5");
        SysArea sysArea = new SysArea();
        sysArea.setFullName("123");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(sysArea);
        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);

        assetAreaReportService.queryAreaTable(reportRequest);
    }

    private HttpServletRequest getRequest() {
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();
        ((MockHttpServletRequest) httpServletRequest).addHeader("user-agent", "msie");
        return httpServletRequest;
    }

    @Test
    public void exportAreaTableTest() throws Exception {

        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<String> id = new ArrayList<>();
        id.add("1");
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTopAreaId("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("a", 1);
        allAssetCountMap.put("b", 2);
        allAssetCountMap.put("c", 3);
        allAssetCountMap.put("d", 4);
        allAssetCountMap.put("e", 5);
        allAssetCount.add(allAssetCountMap);
        reportRequest.setStartTime(1L);
        reportRequest.setEndTime(3L);
        ReportForm expected = new ReportForm();
        expected.setTitle("资产区域报表数据");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        reportRequest.setTimeType("1");
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        assetAreaReportService.exportAreaTable(reportRequest);
        Mockito.verify(assetReportDao).queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any());


        // header user-agent为""
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("user-agent", "");
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(httpServletRequest, new MockHttpServletResponse()));
        reportRequest.setTimeType("2");
        assetAreaReportService.exportAreaTable(reportRequest);
        Mockito.verify(assetReportDao, times(2)).queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any());

        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        reportRequest.setTimeType("3");
        assetAreaReportService.exportAreaTable(reportRequest);
        Mockito.verify(assetReportDao, times(3)).queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any());

        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        reportRequest.setTimeType("4");
        assetAreaReportService.exportAreaTable(reportRequest);
        Mockito.verify(assetReportDao, times(4)).queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any());

        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));
        reportRequest.setTimeType("5");
        assetAreaReportService.exportAreaTable(reportRequest);
        Mockito.verify(assetReportDao, times(5)).queryAssetWithAreaByDate(Mockito.any(), Mockito.any(), Mockito.any());


        // case assetReportDao.getAllAssetWithArea > 5
        allAssetCountMap.put("f", 6);
        allAssetCount.add(allAssetCountMap);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));
        reportRequest.setTimeType("5");
        assetAreaReportService.exportAreaTable(reportRequest);


        // case
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));
        reportRequest.setTimeType("");

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("timeType参数异常");
        assetAreaReportService.exportAreaTable(reportRequest);
    }

    @Test
    public void getAreaNameByIdTest() throws Exception {
        AssetAreaReportRequest report = new AssetAreaReportRequest();
        report.setParentAreaId("1");
        report.setParentAreaName("parent");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);

        String parentAreaNameExpected = "parent";
        Method getAreaNameById = PowerMockito.method(AssetAreaReportServiceImpl.class, "getAreaNameById");
        Object parentAreaNameActual = getAreaNameById.invoke(assetAreaReportService, 1, assetAreaId);
        Assert.assertEquals(parentAreaNameExpected, parentAreaNameActual.toString());
    }
}
