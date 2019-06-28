package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetReportDao;
import com.antiy.asset.entity.AssetGroupEntity;
import com.antiy.asset.manage.Service.AssetReportServiceManager;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.ReportDateUtils;
import com.antiy.asset.vo.enums.ShowCycleType;

import com.antiy.asset.vo.response.AssetReportResponse;
import com.antiy.asset.vo.response.AssetReportTableResponse;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.SysArea;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class })
@SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetReportServiceImplTest {
    /**
     * 模拟Service，会发生真实调用
     */
    @SpyBean
    private IAssetReportService       iAssetReportService;

    /**
     * 模拟Dao，将其注入上下文
     */
    @MockBean
    private AssetReportDao            assetReportDao;
    @MockBean
    private AssetCategoryModelDao     categoryModelDao;
    @Autowired
    private AssetReportServiceManager assetReportServiceManager;
    private LoginUser                 loginUser;

    /**
     * 模拟登陆用户信息
     */
    @Before
    public void initMock() {
        SysArea sysArea = new SysArea();
        sysArea.setId(1);
        List<SysArea> sysAreaList = new ArrayList<>();
        sysAreaList.add(sysArea);
        loginUser = new LoginUser();
        loginUser.setId(11);
        loginUser.setName("日常安全管理员");
        loginUser.setUsername("routine_admin");
        loginUser.setPassword("123456");
        loginUser.setAreas(sysAreaList);
        PowerMockito.mockStatic(RequestContextHolder.class);

    }

    /**
     * 场景一：展示周期为本年
     * @throws Exception
     */
    @Test
    public void queryCategoryCountByTimeWithYear() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("计算设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("计算设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getCurrentMonthOfYear())));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getCurrentMonthOfYear())));

        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        AssetReportResponse result = iAssetReportService.queryCategoryCountByTime(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_YEAR, 1551422114000L, 1551422114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景二：展示周期为本月
     * @throws Exception
     */
    @Test
    public void queryCategoryCountByTimeWithYMonth() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("存储设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("存储设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getWeekOfMonth())));
        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getWeekOfMonth())));
        AssetReportResponse result = iAssetReportService.queryCategoryCountByTime(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_MONTH, 1551422114000L, 1551422114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    private String getKeyFromMap(Map<String, String> map) {
        for (Map.Entry m : map.entrySet()) {
            return (String) m.getKey();
        }
        return "";
    }

    /**
     * 场景三：展示周期为本季度
     * @throws Exception
     */
    @Test
    public void queryCategoryCountByTimeWithQuarter() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("其它设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("其它设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getSeason())));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getSeason())));

        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        AssetReportResponse result = iAssetReportService.queryCategoryCountByTime(assetReportServiceManager
            .initCategoryCountQuery(ShowCycleType.THIS_QUARTER, 1551422114000L, 1551422114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景四：展示周期为指定日期
     * @throws Exception
     */
    @Test
    public void queryCategoryCountByTimeWithAssignTime() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("安全设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("安全设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(assetReportServiceManager
            .initCategoryEntityList(getKeyFromMap(ReportDateUtils.getMonthWithDate(1551422114000L, 1551423114000L))));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(assetReportServiceManager
            .initCategoryEntityList(getKeyFromMap(ReportDateUtils.getMonthWithDate(1551422114000L, 1551423114000L))));

        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        AssetReportResponse result = iAssetReportService.queryCategoryCountByTime(assetReportServiceManager
            .initCategoryCountQuery(ShowCycleType.ASSIGN_TIME, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景五：展示周期为本周
     * @throws Exception
     */
    @Test
    public void queryCategoryCountByTimeWithWeek() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("网络设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("网络设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getDayOfWeek())));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getDayOfWeek())));

        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        AssetReportResponse result = iAssetReportService.queryCategoryCountByTime(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_WEEK, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：展示周期为本周
     * @throws Exception
     */
    @Test
    public void exportCategoryCountWithWeek() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("网络设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("网络设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getDayOfWeek())));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getDayOfWeek())));
        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        HttpServletRequest httpServletRequest = getRequest();
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));
        iAssetReportService.exportCategoryCount(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_WEEK, 1551422114000L, 1551423114000L),
            httpServletRequest);
        Mockito.verify(categoryModelDao).findAllCategory();
        Mockito.verify(assetReportDao).findCategoryCountByTime(Mockito.any());
    }

    private HttpServletRequest getRequest() {
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();
        ((MockHttpServletRequest) httpServletRequest).addHeader("user-agent", "msie");
        return httpServletRequest;
    }

    /**
     * 场景二：展示周期为本月
     * @throws Exception
     */
    @Test
    public void exportCategoryCountWithMonth() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("存储设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("存储设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getWeekOfMonth())));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getWeekOfMonth())));
        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        iAssetReportService.exportCategoryCount(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_MONTH, 1551422114000L, 1551422114000L),
            getRequest());
        Mockito.verify(categoryModelDao).findAllCategory();
        Mockito.verify(assetReportDao).findCategoryCountByTime(Mockito.any());
    }

    /**
     * 场景三：展示周期为本季度
     * @throws Exception
     */
    @Test
    public void exportCategoryCountWithQuater() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("其它设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("其它设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getSeason())));
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getSeason())));
        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        iAssetReportService.exportCategoryCount(assetReportServiceManager
            .initCategoryCountQuery(ShowCycleType.THIS_QUARTER, 1551422114000L, 1551422114000L), getRequest());
        Mockito.verify(categoryModelDao).findAllCategory();
        Mockito.verify(assetReportDao).findCategoryCountByTime(Mockito.any());
    }

    /**
     * 场景四：展示周期为本年
     * @throws Exception
     */
    @Test
    public void exportCategoryCountWithYear() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("计算设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("计算设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getCurrentMonthOfYear())));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(
            assetReportServiceManager.initCategoryEntityList(getKeyFromMap(ReportDateUtils.getCurrentMonthOfYear())));
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        iAssetReportService.exportCategoryCount(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_YEAR, 1551422114000L, 1551422114000L),
            getRequest());
        Mockito.verify(categoryModelDao).findAllCategory();
        Mockito.verify(assetReportDao).findCategoryCountByTime(Mockito.any());
    }

    /**
     * 场景五：展示周期为指定日期
     * @throws Exception
     */
    @Test
    public void exportCategoryCountWithAssignTime() throws Exception {
        mockLoginUser(loginUser);
        Mockito.when(categoryModelDao.findAllCategory())
            .thenReturn(assetReportServiceManager.initCategoryModeList("安全设备", 1));
        Mockito.when(categoryModelDao.getByWhere(Mockito.any()))
            .thenReturn(assetReportServiceManager.initCategoryModeList("安全设备", 2));
        Mockito.when(assetReportDao.findCategoryCountByTime(Mockito.any())).thenReturn(assetReportServiceManager
            .initCategoryEntityList(getKeyFromMap(ReportDateUtils.getMonthWithDate(1551422114000L, 1551423114000L))));
        Mockito.when(assetReportDao.findCategoryCountPrevious(Mockito.any())).thenReturn(assetReportServiceManager
            .initCategoryEntityList(getKeyFromMap(ReportDateUtils.getMonthWithDate(1551422114000L, 1551423114000L))));
        when(RequestContextHolder.getRequestAttributes())
                .thenReturn(new ServletRequestAttributes(getRequest(), new MockHttpServletResponse()));

        Mockito.when(assetReportDao.findCategoryCountAmount(Mockito.anyMap())).thenReturn(1);
        iAssetReportService.exportCategoryCount(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.ASSIGN_TIME, 1551422114000L, 1551423114000L),
            getRequest());
        Mockito.verify(categoryModelDao).findAllCategory();
        Mockito.verify(assetReportDao).findCategoryCountByTime(Mockito.any());
    }

    /**
     * 场景一：展示周期为指定本周
     * @throws Exception
     */
    @Test
    public void getAssetConutWithGroupWithWeek() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportResponse result = iAssetReportService
            .getAssetConutWithGroup(assetReportServiceManager.initReportQueryRequest("1"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景二：展示周期为指定本周
     * @throws Exception
     */
    @Test
    public void getAssetConutWithGroupWithMonth() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportResponse result = iAssetReportService
            .getAssetConutWithGroup(assetReportServiceManager.initReportQueryRequest("2"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景三：展示周期为指定本周
     * @throws Exception
     */
    @Test
    public void getAssetConutWithGroupWithQuarter() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportResponse result = iAssetReportService
            .getAssetConutWithGroup(assetReportServiceManager.initReportQueryRequest("3"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景四：展示周期为指定本季度
     * @throws Exception
     */
    @Test
    public void getAssetConutWithGroupWithYear() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportResponse result = iAssetReportService
            .getAssetConutWithGroup(assetReportServiceManager.initReportQueryRequest("4"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景五：展示周期为指定指定时间
     * @throws Exception
     */
    @Test
    public void getAssetConutWithGroupWithAssignTime() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportResponse result = iAssetReportService
            .getAssetConutWithGroup(assetReportServiceManager.initReportQueryRequest("5"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    // @Test
    // public void getNewAssetWithGroup() throws Exception {
    // List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
    // Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
    // Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
    // AssetReportResponse result = iAssetReportService.
    // getNewAssetWithGroup(assetReportServiceManager.initReportQueryRequest("1"));
    // Assert.assertThat(result, Matchers.notNullValue());
    // }

    @Test
    public void queryCategoryCountByTimeToTableYear() throws Exception {
        mockLoginUser(loginUser);
        AssetReportTableResponse result = iAssetReportService.queryCategoryCountByTimeToTable(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_YEAR, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    @Test
    public void queryCategoryCountByTimeToTableSeason() throws Exception {
        mockLoginUser(loginUser);
        AssetReportTableResponse result = iAssetReportService.queryCategoryCountByTimeToTable(assetReportServiceManager
            .initCategoryCountQuery(ShowCycleType.THIS_QUARTER, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    @Test
    public void queryCategoryCountByTimeToTableMonth() throws Exception {
        mockLoginUser(loginUser);
        AssetReportTableResponse result = iAssetReportService.queryCategoryCountByTimeToTable(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_MONTH, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    @Test
    public void queryCategoryCountByTimeToTableWeek() throws Exception {
        mockLoginUser(loginUser);
        AssetReportTableResponse result = iAssetReportService.queryCategoryCountByTimeToTable(
            assetReportServiceManager.initCategoryCountQuery(ShowCycleType.THIS_WEEK, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    @Test
    public void queryCategoryCountByTimeToTableAssTime() throws Exception {
        mockLoginUser(loginUser);
        AssetReportTableResponse result = iAssetReportService.queryCategoryCountByTimeToTable(assetReportServiceManager
            .initCategoryCountQuery(ShowCycleType.ASSIGN_TIME, 1551422114000L, 1551423114000L));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：展示周期为本周
     * @throws Exception
     */
    @Test
    public void getAssetGroupReportTableWithWeek() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        List<AssetGroupEntity> assetGroupEntityList1 = assetReportServiceManager.initGroupEntityList();
        AssetGroupEntity assetGroupEntity = new AssetGroupEntity();
        assetGroupEntity.setDate("2018,5,1");
        assetGroupEntity.setGroupCount(1);
        assetGroupEntity.setGroupId(1);
        assetGroupEntity.setName("sss");
        assetGroupEntityList1.add(assetGroupEntity);
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList1);
        AssetReportTableResponse result = iAssetReportService
            .getAssetGroupReportTable(assetReportServiceManager.initReportQueryRequest("1"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景二：展示周期为本月
     * @throws Exception
     */
    @Test
    public void getAssetGroupReportTableWithMonth() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportTableResponse result = iAssetReportService
            .getAssetGroupReportTable(assetReportServiceManager.initReportQueryRequest("2"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景三：展示周期为本季度
     * @throws Exception
     */
    @Test
    public void getAssetGroupReportTableWithQuarter() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportTableResponse result = iAssetReportService
            .getAssetGroupReportTable(assetReportServiceManager.initReportQueryRequest("3"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景四：展示周期为本年
     * @throws Exception
     */
    @Test
    public void getAssetGroupReportTableWithYear() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportTableResponse result = iAssetReportService
            .getAssetGroupReportTable(assetReportServiceManager.initReportQueryRequest("4"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景五：展示周期为指定时间
     * @throws Exception
     */
    @Test
    public void getAssetGroupReportTableWithAssignTime() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        AssetReportTableResponse result = iAssetReportService
            .getAssetGroupReportTable(assetReportServiceManager.initReportQueryRequest("5"));
        Assert.assertThat(result, Matchers.notNullValue());
    }

    /**
     * 场景一：展示周期为本周
     * @throws Exception
     */
    @Test
    public void exportAssetGroupTableWithWeek() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));
        iAssetReportService.exportAssetGroupTable(assetReportServiceManager.initReportQueryRequest("1"));
        Mockito.verify(assetReportDao).getNewAssetWithGroup(Mockito.any());
    }

    /**
     * 场景二：展示周期为本月
     * @throws Exception
     */
    @Test
    public void exportAssetGroupTableWithMonth() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));

        iAssetReportService.exportAssetGroupTable(assetReportServiceManager.initReportQueryRequest("2"));
        Mockito.verify(assetReportDao).getNewAssetWithGroup(Mockito.any());
    }

    /**
     * 场景三：展示周期为本季度
     * @throws Exception
     */
    @Test
    public void exportAssetGroupTableWithQuarter() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));

        iAssetReportService.exportAssetGroupTable(assetReportServiceManager.initReportQueryRequest("3"));
        Mockito.verify(assetReportDao).getNewAssetWithGroup(Mockito.any());
    }

    /**
     * 场景四：展示周期为本年
     * @throws Exception
     */
    @Test
    public void exportAssetGroupTableWithYear() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));

        iAssetReportService.exportAssetGroupTable(assetReportServiceManager.initReportQueryRequest("4"));
        Mockito.verify(assetReportDao).getNewAssetWithGroup(Mockito.any());
    }

    /**
     * 场景五：展示周期为指定时间
     * @throws Exception
     */
    @Test
    public void exportAssetGroupTableWithAssignTime() throws Exception {
        List<AssetGroupEntity> assetGroupEntityList = assetReportServiceManager.initGroupEntityList();
        Mockito.when(assetReportDao.getAssetConutWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        Mockito.when(assetReportDao.getNewAssetWithGroup(Mockito.any())).thenReturn(assetGroupEntityList);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-agent", "   ");
        when(RequestContextHolder.getRequestAttributes())
            .thenReturn(new ServletRequestAttributes(request, new MockHttpServletResponse()));

        iAssetReportService.exportAssetGroupTable(assetReportServiceManager.initReportQueryRequest("5"));
        Mockito.verify(assetReportDao).getNewAssetWithGroup(Mockito.any());
    }

    /**
     * 提供的当前用户信息mock
     *
     * @param loginUser 假用户信息
     */
    protected void mockLoginUser(LoginUser loginUser) {
        Map<String, String> map = new HashMap<>();
        map.put("username", loginUser.getUsername());
        map.put("id", String.valueOf(loginUser.getId()));
        map.put("password", loginUser.getPassword());
        map.put("name", loginUser.getName());
        Set<String> set = new HashSet<>(1);
        set.add("none");
        OAuth2Request oAuth2Request = new OAuth2Request(map, "1", null, true, set, null, "", null, null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(map, "2333");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("principal", map);
        authentication.setDetails(map1);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);

    }
}