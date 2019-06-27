package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.enums.FileUseEnum;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetCpuResponse;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.EnumCountResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetControllerTest {

    @InjectMocks
    private AssetController assetController;
    private MockMvc mockMvc;
    @Mock
    public IAssetService iAssetService;
    @Mock
    private ActivityClient activityClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetController).build();
    }

    @Test
    public void saveSingle() throws Exception {
        //构造参数
        AssetOuterRequest asset = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setId("1");
        asset.setAsset(assetRequest);
        Mockito.when(iAssetService.saveAsset(Mockito.any())).thenReturn(ActionResponse.success("1"));
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/save/single").
                        contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }

    @Test
    public void queryList() throws Exception {
        AssetQuery asset = new AssetQuery();
        String[] id = {"1"};
        asset.setIds(id);
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setStringId("1");
        List<AssetResponse> assetResponseList = new ArrayList();
        assetResponseList.add(assetResponse);
        PageResult<AssetResponse> pageResult = new PageResult<>(10, 1, 1, assetResponseList);
        Mockito.when(iAssetService.findPageAsset(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/query/list")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String json = JsonUtil.object2Json(actionResponse.getBody());
        Assert.assertTrue(pageResult.getItems().size() == JsonUtil.json2Object(json, PageResult.class).getItems().size());
    }

    @Test
    public void findUnconnectedAsset() throws Exception {
        AssetQuery asset = new AssetQuery();
        String[] id = {"1"};
        asset.setIds(id);
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setStringId("1");
        List<AssetResponse> assetResponseList = new ArrayList();
        assetResponseList.add(assetResponse);
        PageResult<AssetResponse> pageResult = new PageResult<>(10, 1, 1, assetResponseList);

        Mockito.when(iAssetService.findUnconnectedAsset(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/query/unconnectedList")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String json = JsonUtil.object2Json(actionResponse.getBody());
        Assert.assertTrue(pageResult.getItems().size() == JsonUtil.json2Object(json, PageResult.class).getItems().size());
    }

    @Test
    public void queryAssetCountByAreaIds() throws Exception {
        Mockito.when(iAssetService.queryAssetCountByAreaIds(Mockito.any())).thenReturn(0);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/query/queryAssetCountByAreaIds")
                        .param("areaIds", "1")).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(), containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":0}"));
    }

    @Test
    public void queryById() throws Exception {
        AssetDetialCondition asset = new AssetDetialCondition();
        asset.setPrimaryKey("1");
        asset.setIsNeedCpu(true);

        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setStringId("1");
        assetResponse.setAreaId("1");
        assetOuterResponse.setAsset(assetResponse);
        List<AssetCpuResponse> assetCpuResponses = new ArrayList<>();
        AssetCpuResponse assetCpuResponse = new AssetCpuResponse();
        assetCpuResponse.setAssetId("1");
        assetCpuResponses.add(assetCpuResponse);
        assetOuterResponse.setAssetCpu(assetCpuResponses);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/query/id").param("primaryKey", "1")).andReturn();
        Mockito.verify(iAssetService).getByAssetId(Mockito.any());
    }

    @Test
    public void updateSingle() throws Exception {
        AssetOuterRequest assetOuterRequest = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setId("1");
        assetOuterRequest.setAsset(assetRequest);
        Mockito.when(iAssetService.changeAsset(assetOuterRequest)).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/change/asset")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetOuterRequest))).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(), containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    @Test
    public void deleteById() throws Exception {
        BaseRequest request = new BaseRequest();
        request.setStringId("1");
        Mockito.when(iAssetService.deleteById(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/delete")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(request))).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(), containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":1}"));

    }

    @Test
    public void export() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/export/file")
                        .param("assetName", "test")).andReturn();
//        Mockito.verify(iAssetService).exportData(Mockito.any(), Mockito.any());
    }

    @Test
    public void exportTemplate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/export/template")
                        .param("type", "1")).andReturn();
        Mockito.verify(iAssetService).exportTemplate(Mockito.any());
    }

    @Test
    public void changeStatus() throws Exception {
        Mockito.when(iAssetService.changeStatus(Mockito.any(), Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/changeStatus/batch")
                        .param("ids", "1").param("targetStatus", "1")).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void changeStatusById() throws Exception {
        Mockito.when(iAssetService.changeStatus(Mockito.any(), Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/changeStatusById")
                        .param("id", "1").param("targetStatus", "1")).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void pulldownManufacturer() throws Exception {
        List<String> manufacturer = new ArrayList<>();
        manufacturer.add("test");
        Mockito.when(iAssetService.pulldownManufacturer()).thenReturn(manufacturer);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/query/pulldown/manufacturer")).andReturn();
        ActionResponse actual = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(), ActionResponse.class);
        Assert.assertEquals(manufacturer, actual.getBody());

    }

    @Test
    public void pulldownUnconnectedManufacturer() throws Exception {
        List<String> manufacturer = new ArrayList<>();
        manufacturer.add("test");
//        Mockito.when(iAssetService.pulldownUnconnectedManufacturer(Mockito.any())).thenReturn(manufacturer);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/query/pulldown/unconnectedManufacturer")).andReturn();
        ActionResponse actual = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(), ActionResponse.class);
        Assert.assertEquals(manufacturer, actual.getBody());
    }

    @Test
    public void queryAssetByIds() throws Exception {

    }

    @Test
    public void countAssetByCategory() throws Exception {
        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();
        EnumCountResponse enumCountResponse = new EnumCountResponse();
        enumCountResponse.setMsg("test");
        enumCountResponseList.add(enumCountResponse);
        Mockito.when(iAssetService.countCategory()).thenReturn(enumCountResponseList);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/count/category")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(), ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void countAssetByStatus() throws Exception {
        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();
        EnumCountResponse enumCountResponse = new EnumCountResponse();
        enumCountResponse.setMsg("test");
        enumCountResponseList.add(enumCountResponse);
        Mockito.when(iAssetService.countStatus()).thenReturn(enumCountResponseList);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/count/status")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(), ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void countAssetByManufacturer() throws Exception {
        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();
        EnumCountResponse enumCountResponse = new EnumCountResponse();
        enumCountResponse.setMsg("test");
        enumCountResponseList.add(enumCountResponse);
        Mockito.when(iAssetService.countManufacturer()).thenReturn(enumCountResponseList);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/count/manufacturer")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(), ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void importPc() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("fileList", "test", "text/plain",
                "test file 12312312312".getBytes());
        AssetImportRequest importRequest = new AssetImportRequest();
        importRequest.setAreaId("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/import/computer")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(multipartFile))
                        .content(JSONObject.toJSONString(importRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void importNet() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("fileList", "test", "text/plain",
                "test file 12312312312".getBytes());
        AssetImportRequest importRequest = new AssetImportRequest();
        importRequest.setAreaId("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/import/net")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(multipartFile))
                        .content(JSONObject.toJSONString(importRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void importSafety() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("fileList", "test", "text/plain",
                "test file 12312312312".getBytes());
        AssetImportRequest importRequest = new AssetImportRequest();
        importRequest.setAreaId("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/import/safety")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(multipartFile))
                        .content(JSONObject.toJSONString(importRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void importStorage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("fileList", "test", "text/plain",
                "test file 12312312312".getBytes());
        AssetImportRequest importRequest = new AssetImportRequest();
        importRequest.setAreaId("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/import/storage")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(multipartFile))
                        .content(JSONObject.toJSONString(importRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void importOhters() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("fileList", "test", "text/plain",
                "test file 12312312312".getBytes());
        AssetImportRequest importRequest = new AssetImportRequest();
        importRequest.setAreaId("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/start/process")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(multipartFile))
                        .content(JSONObject.toJSONString(importRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void manualStartProcess() throws Exception {
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        manualStartActivityRequest.setBusinessId("1");
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(ActionResponse.success());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/start/process")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(manualStartActivityRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void completeTask() throws Exception {
        ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
        activityHandleRequest.setTaskId("1");
        Mockito.when(activityClient.completeTask(Mockito.any())).thenReturn(ActionResponse.success());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/deal/process")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(activityHandleRequest))).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}