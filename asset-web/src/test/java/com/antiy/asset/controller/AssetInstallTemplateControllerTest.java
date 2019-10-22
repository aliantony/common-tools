package com.antiy.asset.controller;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetInstallTemplateCheckService;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.vo.response.AssetInstallTemplateCheckResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateResponse;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author liulusheng
 * @since 2019/10/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetInstallTemplateControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private AssetInstallTemplateController controller;
    @Mock
    private IAssetInstallTemplateService iAssetInstallTemplateService;
    private final String URL_PREFIX = "/api/v1/asset/assetinstalltemplate";
    @Mock
    private IAssetHardSoftLibService iAssetHardSoftLibService;
    @Mock
    private IAssetInstallTemplateCheckService iAssetInstallTemplateCheckService;
    @Mock
    private AssetInstallTemplateDao assetInstallTemplateDao;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void queryStatusListTest() throws Exception {
        Mockito.doAnswer(invocation -> new ArrayList<>()).when(iAssetInstallTemplateService).queryTemplateStatus();
        postResultActions("/query/statusList")
                .andExpect(jsonPath("$.body", Matchers.hasSize(0)));
    }

    @Test
    public void queryOsListTest() throws Exception {
        Mockito.doAnswer(invocation -> new ArrayList<>()).when(iAssetInstallTemplateService).queryTemplateOs();
        postResultActions("/query/osList")
                .andExpect(jsonPath("$.body", Matchers.hasSize(0)));
    }

    @Test
    public void queryNumberCodeTest() throws Exception {
        Integer expect = 1;
        Mockito.doAnswer(invocation -> expect).when(iAssetInstallTemplateService).queryNumberCode(Mockito.anyString());
        postResultActions("/query/numberCode", "{\"numberCode\":\"xx1\"}")
                .andExpect(jsonPath("$.body").value(expect));
    }

    @Test
    public void queryOsTest() throws Exception {
        Mockito.doAnswer(invocation -> new ArrayList<>()).when(iAssetInstallTemplateService).queryOs(Mockito.anyString());
        postResultActions("/query/os")
                .andExpect(jsonPath("$.body", Matchers.hasSize(0)));
    }

    @Test
    public void updateSingleTest() throws Exception {
        String expect = "更新状态成功";
        Mockito.doAnswer(invocation -> ActionResponse.success(expect)).when(iAssetInstallTemplateService).updateAssetInstallTemplate(any());
        postResultActions("/update/single", "{\"numberCode\":\"xx1\"}")
                .andExpect(jsonPath("$.body").value(expect));
    }

    @Test
    public void queryListTest() throws Exception {
        int pageSize = 3;
        int currentPage = 2;
        int totalRecord = 5;
        Mockito.doAnswer(invocation -> new PageResult<>(pageSize, totalRecord, currentPage, new ArrayList<>())).when(iAssetInstallTemplateService).queryPageAssetInstallTemplate(any());
        postResultActions("/query/list", "{\"numberCode\":\"xx1\"}")
                .andExpect(jsonPath("$.body.pageSize").value(pageSize))
                .andExpect(jsonPath("$.body.currentPage").value(currentPage))
                .andExpect(jsonPath("$.body.totalRecords").value(totalRecord))
                .andExpect(jsonPath("$.body.items", Matchers.hasSize(0)));
    }

    @Test
    public void queryByIdTest() throws Exception {
        String expect = "xx模板";
        Mockito.doAnswer(invocation -> {
            AssetInstallTemplateResponse response = new AssetInstallTemplateResponse();
            response.setName(expect);
            return response;
        }).when(iAssetInstallTemplateService).queryAssetInstallTemplateById(any());
        postResultActions("/query/id", "{\"primaryKey\":\"xx1\"}")
                .andExpect(jsonPath("$.body.name").value(expect));
    }

    @Test
    public void deleteByIdTest() throws Exception {
        String expect = "删除成功";
        Mockito.doAnswer(invocation -> expect).when(iAssetInstallTemplateService).deleteAssetInstallTemplateById(any());
        postResultActions("/delete/id", "{\"ids\":[\"1\",\"2\",\"3\"]}")
                .andExpect(jsonPath("$.body").value(expect));
    }

    @Test
    public void querySoftsRelationsTest() throws Exception {
        Mockito.doAnswer(invocation -> new ArrayList<>()).when(iAssetHardSoftLibService).querySoftsRelations(Mockito.anyString());
        postResultActions("/query/softs", "{\"stringId\":\"123\"}")
                .andExpect(jsonPath("$.body").value(Matchers.hasSize(0)));
    }

    @Test
    public void queryPatchsRelationsTest() throws Exception {
        Mockito.doAnswer(invocation -> new ArrayList<>()).when(iAssetInstallTemplateService).queryPatchs(Mockito.anyString());
        postResultActions("/query/patchs", "{\"stringId\":\"123\"}")
                .andExpect(jsonPath("$.body").value(Matchers.hasSize(0)));
    }

    @Test
    public void createInstallTemplateTest() throws Exception {
        String expect = "提交成功";
        Mockito.doAnswer(invocation -> ActionResponse.success(expect)).when(iAssetInstallTemplateService).submitTemplateInfo(any());
        postResultActions("/submit", "{\"numberCode\":\"xx1\",\"name\": \"模板3\",\"operationSystem\": \"614053224013365248\"}")
                .andExpect(jsonPath("$.body").value(expect));
    }

    @Test
    public void checkInstallTemplateTest() throws Exception {
        String expect = "审核成功";
        Mockito.doAnswer(invocation -> expect).when(iAssetInstallTemplateService).checkTemplate(any());
        postResultActions("/check", "{\"stringId\":\"123\"}")
                .andExpect(jsonPath("$.body").value(expect));
    }

    private ResultActions postResultActions(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(URL_PREFIX + url)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.head.code").value(200));
    }

    private ResultActions postResultActions(String url, String json) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(URL_PREFIX + url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.head.code").value(200))
                .andExpect(jsonPath("$.head.result").value("成功"));
    }

    @Test
    public void queryTemplateByAssetId() throws Exception {

        AssetTemplateRelationResponse assetTemplateRelationResponse = new AssetTemplateRelationResponse();
        assetTemplateRelationResponse.setPatchNum(1);
        assetTemplateRelationResponse.setName("zai");
        when(iAssetInstallTemplateService
                .queryTemplateByAssetId(any())).thenReturn(assetTemplateRelationResponse);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("123");
        postResultActions("/query/relationInfo", JsonUtil.object2Json(queryCondition))
                .andExpect(jsonPath("$.body.name").value("zai"))
                .andExpect(jsonPath("$.body.patchNum").value(1));
    }

    @Test
    public void queryTemplateCheckByTemplateId() throws Exception {
        List<AssetInstallTemplateCheckResponse> templateCheckResponses = new ArrayList<>();
        AssetInstallTemplateCheckResponse assetInstallTemplateCheckResponse = new AssetInstallTemplateCheckResponse();
        assetInstallTemplateCheckResponse.setName("c");
        assetInstallTemplateCheckResponse.setAdvice("不符合需求");
        assetInstallTemplateCheckResponse.setResult(1);
        templateCheckResponses.add(assetInstallTemplateCheckResponse);
        when(iAssetInstallTemplateCheckService
                .queryTemplateCheckByTemplateId(any())).thenReturn(templateCheckResponses);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("123");
        String contentAsString = postResultActions("/query/auditInfo", JsonUtil.object2Json(queryCondition)).andReturn().getResponse().getContentAsString();
        String s = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":[{\"stringId\":null,\"userId\":null,\"advice\":\"不符合需求\",\"result\":1,\"resultStr\":null,\"gmtCreate\":null,\"name\":\"c\"}]}";
        Assert.assertEquals(s, contentAsString);
    }

    @Test
    public void queryOsListByAssetIdTest() throws Exception {
        Mockito.doAnswer(invocation -> new ArrayList<>()).when(assetInstallTemplateDao).findByAssetIds(Mockito.anyList());
        postResultActions("/query/assetList", "{}").andExpect(jsonPath("$.body").value(Matchers.hasSize(0)));
    }

    @Test
    public void saveSingleTest() throws Exception {
        String expect = "保存成功";
        Mockito.doAnswer(invocation -> expect).when(iAssetInstallTemplateService).saveAssetInstallTemplate(Mockito.any());
        postResultActions("/save/single", "{}").andExpect(jsonPath("$.body").value(expect));
    }

    @Test
    public void querySoftListTest() throws Exception {
        int pageSize = 3;
        int currentPage = 2;
        int totalRecord = 5;
        Mockito.doAnswer(invocation -> new PageResult<>(pageSize, totalRecord, currentPage, new ArrayList<>())).when(iAssetInstallTemplateService).querySoftPage(any());
        postResultActions("/query/softList", "{\"pid\":\"1\"}")
                .andExpect(jsonPath("$.body.pageSize").value(pageSize))
                .andExpect(jsonPath("$.body.currentPage").value(currentPage))
                .andExpect(jsonPath("$.body.totalRecords").value(totalRecord))
                .andExpect(jsonPath("$.body.items", Matchers.hasSize(0)));
    }
    @Test
    public void queryPatchListTest() throws Exception {
        int pageSize = 3;
        int currentPage = 2;
        int totalRecord = 5;
        Mockito.doAnswer(invocation -> new PageResult<>(pageSize, totalRecord, currentPage, new ArrayList<>())).when(iAssetInstallTemplateService).queryPatchPage(any());
        postResultActions("/query/patchList", "{\"pid\":\"1\"}")
                .andExpect(jsonPath("$.body.pageSize").value(pageSize))
                .andExpect(jsonPath("$.body.currentPage").value(currentPage))
                .andExpect(jsonPath("$.body.totalRecords").value(totalRecord))
                .andExpect(jsonPath("$.body.items", Matchers.hasSize(0)));
    }
}
