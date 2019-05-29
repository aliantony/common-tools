package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.asset.vo.response.ReportData;
import com.antiy.asset.vo.response.ReportTableHead;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetAreaReportServiceImplTest {

    @InjectMocks
    private AssetAreaReportServiceImpl assetAreaReportService;

    @Mock
    private AssetReportDao assetReportDao;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAssetWithAreaTest(){
        AssetAreaReportRequest report = new AssetAreaReportRequest();
//        report.setParentAreaId(1);
        report.setParentAreaName("parent");
        List<Integer> childrenAradId = new ArrayList<>();
        childrenAradId.add(2);
        childrenAradId.add(3);
//        report.setChildrenAradIds(childrenAradId);
        report.setStringId("1");
        AssetAreaReportRequest report2 = new AssetAreaReportRequest();
//        report2.setParentAreaId(1);
        report2.setParentAreaName("parent2");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<Integer> id = new ArrayList<>();
        id.add(1);
        reportRequest.setAreaIds(id);
        reportRequest.setStartTime(1554285690000L);
        reportRequest.setAssetStatus(1);
        reportRequest.setTopFive(true);
        reportRequest.setTimeType("1");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);
        assetAreaId.add(report2);

        reportRequest.setAssetAreaIds(assetAreaId);

        List<Map<String, Integer>> initData = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        map.put("areaId",1);
        Map<String,Integer> map2 = new HashMap<>();
        map.put("assetCount",1);
        initData.add(map);
        initData.add(map2);
        List<Map<String, String>> addData = new ArrayList<>();
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("areaId","1");
        addData.add(dataMap);
        List<Map<String, Integer>> allAssetCount = new ArrayList<>();
        Map<String, Integer> allAssetCountMap = new HashMap<>();
        allAssetCountMap.put("parent",1);
        Map<String, Integer> allAssetCountMap2 = new HashMap<>();
        allAssetCountMap.put("parent2",2);
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

        when(assetReportDao.queryAssetWithAreaByDate(Mockito.any(),
                Mockito.any())).thenReturn(initData);
        when(assetReportDao.queryAddAssetWithArea(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any())).thenReturn(addData);
        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        //情景一：TOP5为true
        AssetReportResponse actual = assetAreaReportService.getAssetWithArea(reportRequest);
        Assert.assertEquals(expected.getAlldata().get(0),actual.getAlldata().get(0));

        //情景二：TOP5为false
        reportRequest.setTopFive(false);
        AssetReportResponse actual2 = assetAreaReportService.getAssetWithArea(reportRequest);

    }

    @Test
    public void queryAreaTableTest(){
        AssetAreaReportRequest report = new AssetAreaReportRequest();
//        report.setParentAreaId(1);
        report.setParentAreaName("parent");

        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<Integer> id = new ArrayList<>();
        id.add(1);
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
        allAssetCountMap.put("a",1);
        allAssetCountMap.put("b",2);
        allAssetCountMap.put("c",3);
        allAssetCountMap.put("d",4);
        allAssetCountMap.put("e",5);
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
        reportTableHeads.add(reportTableHead);
        assetReportTableResponse.setChildren(reportTableHeads);

        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
        AssetReportTableResponse actual = assetAreaReportService.queryAreaTable(reportRequest);
        Assert.assertEquals(assetReportTableResponse.getFormName(),actual.getFormName());

    }

    @Test
    public void exportAreaTableTest(){
        AssetAreaReportRequest report = new AssetAreaReportRequest();
//        report.setParentAreaId(1);
        report.setParentAreaName("parent");
        ReportQueryRequest reportRequest = new ReportQueryRequest();
        List<Integer> id = new ArrayList<>();
        id.add(1);
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
        allAssetCountMap.put("a",1);
        allAssetCountMap.put("b",2);
        allAssetCountMap.put("c",3);
        allAssetCountMap.put("d",4);
        allAssetCountMap.put("e",5);
        allAssetCount.add(allAssetCountMap);

        ReportForm expected = new ReportForm();
        expected.setTitle("资产区域报表数据");

        when(assetReportDao.getAllAssetWithArea(Mockito.any(ReportQueryRequest.class))).thenReturn(allAssetCount);
//        ReportForm actual = assetAreaReportService.exportAreaTable(reportRequest);
//        Assert.assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    public void getAreaNameByIdTest()throws Exception{
        AssetAreaReportRequest report = new AssetAreaReportRequest();
//        report.setParentAreaId(1);
        report.setParentAreaName("parent");
        List<AssetAreaReportRequest> assetAreaId = new ArrayList<>();
        assetAreaId.add(report);

        String parentAreaNameExpected = "parent";
        Method getAreaNameById = PowerMockito.method(AssetAreaReportServiceImpl.class, "getAreaNameById");
        Object parentAreaNameActual = getAreaNameById.invoke(assetAreaReportService,1,assetAreaId);
        Assert.assertEquals(parentAreaNameExpected,parentAreaNameActual.toString());
    }
}
