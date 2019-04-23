package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetAreaReportService;
import com.antiy.asset.service.IAssetReportService;
import com.antiy.asset.templet.ReportForm;
import com.antiy.asset.vo.request.AssetAreaReportRequest;
import com.antiy.asset.vo.request.ReportQueryRequest;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.SysArea;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetReportControllerTest {

    /**
     *模拟Service
     */
    @MockBean
    private IAssetReportService iAssetReportService;
    @MockBean
    private IAssetAreaReportService iAssetAreaReportService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private Gson gson = new Gson();
    private LoginUser loginUser;
    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
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
        //mock 当前登陆用户信息
    }

    /**
     * 根据时间条件查询分类统计资产数量接口测试
     * 断言response
     * @throws Exception e
     */
    @Test
    public void queryCategoryCountByTime() throws Exception {
        String result = mockMvc.perform(get("/api/v1/asset/report/query/categoryCountByTime")
        .param("showCycleType", "THIS_YEAR")
        .param("beginTime", "1551422114000")
        .param("endTime", "1551422114000"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据时间条件查询分类统计资产数量,返回表格数据
     * 断言response
     * @throws Exception e
     */
    @Test
    public void queryCategoryCountByTimeToTable() throws Exception {
        String result = mockMvc.perform(get("/api/v1/asset/report//query/categoryCountByTimeToTable")
                .param("showCycleType", "THIS_YEAR")
                .param("beginTime", "1551422114000")
                .param("endTime", "1551422114000"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据时间条件、区域资产查询数量
     * 断言response
     * @throws Exception e
     */
    @Test
    public void queryAreaCount() throws Exception {
        String json = gson.toJson(reportQueryRequestInit());
        String result= postAction("/api/v1/asset/report/query/queryAreaCount",json)
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据时间条件、区域资产查询表格数据
     * 断言response
     * @throws Exception e
     */
    @Test
    public void queryAreaTable() throws Exception {
        String json = gson.toJson(reportQueryRequestInit());
        String result= postAction("/api/v1/asset/report/query/queryAreaTable",json)
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据时间条件、区域导出资产表格数据
     * 断言方法是否调用
     * @throws Exception e
     */
    @Test
    public void exportAreaTable() throws Exception {
        String json = gson.toJson(reportQueryRequestInit());
        Mockito.when(iAssetAreaReportService.exportAreaTable(Mockito.any())).thenReturn(reportFormInit());
        postAction("/api/v1/asset/report/query/exportAreaTable",json);
        Mockito.verify(iAssetAreaReportService).exportAreaTable(Mockito.any());
    }

    /**
     * 根据时间条件查询分类统计资产新增数量
     * 断言方法是否调用
     * @throws Exception e
     */
    @Test
    public void getAssetConutWithGroup() throws Exception {
        mockLoginUser(loginUser);
        ReportQueryRequest reportQueryRequest = new ReportQueryRequest();
        reportQueryRequest.setAssetStatus(1);
        reportQueryRequest.setStartTime(1551422114000L);
        reportQueryRequest.setEndTime(1551423114000L);
        reportQueryRequest.setTimeType("1");
        String result = getAction("/api/v1/asset/report/query/groupCountTop",reportQueryRequest)
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据资产组查询资产新增数量信息
     * 断言方法是否调用
     * @throws Exception e
     */
    @Test
    public void getNewAssetWithGroup() throws Exception {
        mockLoginUser(loginUser);
        ReportQueryRequest reportQueryRequest = new ReportQueryRequest();
        reportQueryRequest.setAssetStatus(1);
        reportQueryRequest.setStartTime(1551422114000L);
        reportQueryRequest.setEndTime(1551423114000L);
        reportQueryRequest.setTimeType("1");
        String result = getAction("/api/v1/asset/report/query/groupNewAsset",reportQueryRequest)
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 导出资产品类型号报表
     * @throws Exception e
     */
    @Test
    public void getNewAssetWithGroup1() throws Exception {
        mockLoginUser(loginUser);
        mockMvc.perform(get("/api/v1/asset/report/export/category/newAsset")
                .param("showCycleType", "THIS_YEAR")
                .param("beginTime", "1551422114000")
                .param("endTime", "1551422114000"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 根据时间查询资产组表格
     * 断言response
     * @throws Exception e
     */
    @Test
    public void queryAssetGroupTable() throws Exception {
        ReportQueryRequest reportQueryRequest = new ReportQueryRequest();
        reportQueryRequest.setAssetStatus(1);
        reportQueryRequest.setStartTime(1551422114000L);
        reportQueryRequest.setEndTime(1551423114000L);
        reportQueryRequest.setTimeType("1");
        String result = getAction("/api/v1/asset/report/query/queryAssetGroupTable",reportQueryRequest)
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 导出资产组表格
     * 断言方法是否调用
     * @throws Exception e
     */
    @Test
    public void exportAssetGroupTable() throws Exception {
        String json = gson.toJson(reportQueryRequestInit());
        Mockito.when(iAssetReportService.exportAssetGroupTable(Mockito.any())).thenReturn(reportFormInit());
        mockMvc.perform(get("/api/v1/asset/report/query/exportAssetGroupTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        Mockito.verify(iAssetReportService).exportAssetGroupTable(Mockito.any());
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

    /**
     * post请求封装
     * @param url
     * @param content
     * @return postAction
     * @throws Exception
     */
    private ResultActions postAction(String url, String content) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                        .andExpect(status().isOk());
    }

    /**
     * get请求封装
     * @param url
     * @param obj
     * @return getAction
     * @throws Exception e
     */
    private ResultActions getAction(String url,Object obj) throws Exception {
        MultiValueMap<String,String> paramMap = new LinkedMultiValueMap<>();
        try {
            paramMap.setAll(BeanUtils.describe(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mockMvc.perform(get(url)
                        .params(paramMap))
                            .andExpect(status().isOk());
    }

    /**
     *模拟请求实体
     * @return 请求实体
     */
    private ReportQueryRequest reportQueryRequestInit(){
        List<Integer> childrenAreaIds = new ArrayList();
        childrenAreaIds.add(1);
        AssetAreaReportRequest assetAreaReportRequest = new AssetAreaReportRequest();
        assetAreaReportRequest.setParentAreaId(1);
        assetAreaReportRequest.setParentAreaName("ss");
        assetAreaReportRequest.setChildrenAradIds(childrenAreaIds);
        List<AssetAreaReportRequest> assetAreaReportRequests = new ArrayList<>();
        assetAreaReportRequests.add(assetAreaReportRequest);
        ReportQueryRequest reportQueryRequest = new ReportQueryRequest();
        reportQueryRequest.setAssetStatus(1);
        reportQueryRequest.setStartTime(1551422114000L);
        reportQueryRequest.setEndTime(1551423114000L);
        reportQueryRequest.setTimeType("1");
        reportQueryRequest.setAssetAreaIds(assetAreaReportRequests);
        return reportQueryRequest;
    }

    /**
     * 模拟Form实体
     * @return Form实体
     */
    private ReportForm reportFormInit(){
        ReportForm reportForm = new ReportForm();
        List<String> columnList = new ArrayList<>();
        columnList.add("列标题");
        List<String> headerList = new ArrayList<>();
        headerList.add("行标题");
        String[][] strings = {{"1"}};
        reportForm.setTitle("ss");
        reportForm.setColumnList(columnList);
        reportForm.setData(strings);
        reportForm.setHeaderList(headerList);
        return reportForm;
    }

}