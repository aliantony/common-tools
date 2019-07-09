package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.ControllerUtil;
import com.antiy.asset.vo.enums.FileUseEnum;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetControllerTest {

    @InjectMocks
    private AssetController assetController;
    private MockMvc         mockMvc;
    @Mock
    public IAssetService    iAssetService;
    @Mock
    private ActivityClient  activityClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetController).build();

    }

    @Test
    public void saveSingle() throws Exception {
        // 构造参数
        AssetOuterRequest asset = new AssetOuterRequest();
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setId("1");
        asset.setAsset(assetRequest);
        Mockito.when(iAssetService.saveAsset(Mockito.any())).thenReturn(ActionResponse.success("1"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/save/single")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        Assert.assertEquals("200", actionResponse.getHead().getCode());
    }

    @Test
    public void queryList() throws Exception {
        AssetQuery asset = new AssetQuery();
        String[] id = { "1" };
        asset.setIds(id);
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setStringId("1");
        List<AssetResponse> assetResponseList = new ArrayList();
        assetResponseList.add(assetResponse);
        PageResult<AssetResponse> pageResult = new PageResult<>(10, 1, 1, assetResponseList);
        Mockito.when(iAssetService.findPageAsset(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/list")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String json = JsonUtil.object2Json(actionResponse.getBody());
        Assert
            .assertTrue(pageResult.getItems().size() == JsonUtil.json2Object(json, PageResult.class).getItems().size());

        asset.setSortName("1");
        asset.setSortOrder("1");
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/list")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        json = JsonUtil.object2Json(actionResponse.getBody());
        Assert
            .assertTrue(pageResult.getItems().size() == JsonUtil.json2Object(json, PageResult.class).getItems().size());

    }

    @Test
    public void findUnconnectedAsset() throws Exception {
        AssetQuery asset = new AssetQuery();
        String[] id = { "1" };
        asset.setIds(id);
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setStringId("1");
        List<AssetResponse> assetResponseList = new ArrayList();
        assetResponseList.add(assetResponse);
        PageResult<AssetResponse> pageResult = new PageResult<>(10, 1, 1, assetResponseList);

        Mockito.when(iAssetService.findUnconnectedAsset(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/unconnectedList")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(asset))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String json = JsonUtil.object2Json(actionResponse.getBody());
        Assert
            .assertTrue(pageResult.getItems().size() == JsonUtil.json2Object(json, PageResult.class).getItems().size());
    }

    @Test
    public void queryAssetCountByAreaIds() throws Exception {
        Mockito.when(iAssetService.queryAssetCountByAreaIds(Mockito.any())).thenReturn(0);
        AssetCountByAreaIdsRequest assetCountByAreaIdsRequest = new AssetCountByAreaIdsRequest();
        assetCountByAreaIdsRequest.setStringId("1");
        assetCountByAreaIdsRequest.setAreaIds(Arrays.asList("1", "2"));
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/api/v1/asset/query/queryAssetCountByAreaIds")
                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetCountByAreaIdsRequest)))
            .andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),
            containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":0}"));
    }

    @Test
    public void queryById() throws Exception {
        Mockito.when(iAssetService.getByAssetId(Mockito.any())).thenReturn(new AssetOuterResponse());
        AssetDetialCondition assetDetialCondition = new AssetDetialCondition();
        assetDetialCondition.setPrimaryKey("1");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/id")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetDetialCondition)))
            .andReturn();
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
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),
            containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    @Test
    public void deleteById() throws Exception {
        BaseRequest request = new BaseRequest();
        request.setStringId("1");
        Mockito.when(iAssetService.deleteById(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/delete")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(request))).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),
            containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":1}"));

    }

    @Test
    public void export() throws Exception {
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.get("/api/v1/asset/export/file").param("assetName", "test")).andReturn();
        Mockito.verify(iAssetService).exportData(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void exportTemplate() throws Exception {
        ExportTemplateRequest exportTemplateRequest = new ExportTemplateRequest();
        exportTemplateRequest.setType(new String[] { "1", "2" });
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/export/template")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("type", "[1,2,3]")).andReturn();
        Mockito.verify(iAssetService).exportTemplate(Mockito.any());
    }

    @Test
    public void changeStatus() throws Exception {
        Mockito.when(iAssetService.changeStatus(Mockito.any(), Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/changeStatus/batch")
            .param("ids", "1").param("targetStatus", "1")).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void changeStatusById() throws Exception {
        Mockito.when(iAssetService.changeStatus(Mockito.any(), Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/asset/changeStatusById").param("id", "1").param("targetStatus", "1"))
            .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void pulldownManufacturer() throws Exception {
        List<String> manufacturer = new ArrayList<>();
        manufacturer.add("test");
        Mockito.when(iAssetService.pulldownManufacturer()).thenReturn(manufacturer);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/pulldown/manufacturer"))
            .andReturn();
        ActionResponse actual = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),
            ActionResponse.class);
        Assert.assertEquals(manufacturer, actual.getBody());
    }

    @Test
    public void pulldownUnconnectedManufacturer() throws Exception {
        List<String> manufacturer = new ArrayList<>();
        manufacturer.add("test");
        Mockito.when(iAssetService.pulldownUnconnectedManufacturer(Mockito.any(), Mockito.any()))
            .thenReturn(manufacturer);
        UnconnectedManufacturerRequest unconnectedManufacturerRequest = new UnconnectedManufacturerRequest();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/asset/query/pulldown/unconnectedManufacturer").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(unconnectedManufacturerRequest))).andReturn();
        ActionResponse actual = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),
            ActionResponse.class);
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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/count/category")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),
            ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void countAssetByStatus() throws Exception {
        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();
        EnumCountResponse enumCountResponse = new EnumCountResponse();
        enumCountResponse.setMsg("test");
        enumCountResponseList.add(enumCountResponse);
        Mockito.when(iAssetService.countStatus()).thenReturn(enumCountResponseList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/count/status")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),
            ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void queryNormalCount() throws Exception {
        Mockito.when(iAssetService.queryNormalCount()).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/normal/count")).andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),
            ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void countAssetByManufacturer() throws Exception {
        List<EnumCountResponse> enumCountResponseList = new ArrayList<>();
        EnumCountResponse enumCountResponse = new EnumCountResponse();
        enumCountResponse.setMsg("test");
        enumCountResponseList.add(enumCountResponse);
        Mockito.when(iAssetService.countManufacturer()).thenReturn(enumCountResponseList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/count/manufacturer"))
            .andReturn();
        ActionResponse actionResponse = JsonUtil.json2Object(mvcResult.getResponse().getContentAsString(),
            ActionResponse.class);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void importPc() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed",
            "132".getBytes());
        // 安装包-成功
        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.multipart("/api/v1/asset/import/computer").file(file).param("category", "3"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());

        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/computer").param("category", "3"));
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件为空，没有选择文件！",
                e.getMessage());
        }
        byte[] bytes = new byte[1048577 * 10];
        file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed", bytes);

        try {
            mockMvc
                .perform(
                    MockMvcRequestBuilders.multipart("/api/v1/asset/import/computer").file(file).param("category", "3"))
                .andReturn();
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件不超过10M！",
                e.getMessage());
        }

    }

    @Test
    public void importNet() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed",
            "132".getBytes());
        // 安装包-成功
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/net").file(file).param("category", "3"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());

        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/net").param("category", "3"));
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件为空，没有选择文件！",
                e.getMessage());
        }
        byte[] bytes = new byte[1048577 * 10];
        file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed", bytes);

        try {
            mockMvc
                .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/net").file(file).param("category", "3"))
                .andReturn();
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件不超过10M！",
                e.getMessage());
        }

    }

    @Test
    public void importSafety() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed",
            "132".getBytes());
        // 安装包-成功
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/safety").file(file).param("category", "3"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());

        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/safety").param("category", "3"));
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件为空，没有选择文件！",
                e.getMessage());
        }
        byte[] bytes = new byte[1048577 * 10];
        file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed", bytes);

        try {
            mockMvc
                .perform(
                    MockMvcRequestBuilders.multipart("/api/v1/asset/import/safety").file(file).param("category", "3"))
                .andReturn();
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件不超过10M！",
                e.getMessage());
        }

    }

    @Test
    public void importStorage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed",
            "132".getBytes());
        // 安装包-成功
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/storage").file(file).param("category", "3"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());

        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/storage").param("category", "3"));
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件为空，没有选择文件！",
                e.getMessage());
        }
        byte[] bytes = new byte[1048577 * 10];
        file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed", bytes);

        try {
            mockMvc
                .perform(
                    MockMvcRequestBuilders.multipart("/api/v1/asset/import/storage").file(file).param("category", "3"))
                .andReturn();
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件不超过10M！",
                e.getMessage());
        }

    }

    @Test
    public void importOhters() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed",
            "132".getBytes());
        // 安装包-成功
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/ohters").file(file).param("category", "3"))
            .andReturn();
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),
            ControllerUtil.getResponse(mvcResult).getHead().getCode());

        try {
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/import/ohters").param("category", "3"));
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件为空，没有选择文件！",
                e.getMessage());
        }
        byte[] bytes = new byte[1048577 * 10];
        file = new MockMultipartFile("file", "123.rar", "application/x-rar-compressed", bytes);

        try {
            mockMvc
                .perform(
                    MockMvcRequestBuilders.multipart("/api/v1/asset/import/ohters").file(file).param("category", "3"))
                .andReturn();
        } catch (Exception e) {
            Assert.assertEquals(
                "Request processing failed; nested exception is com.antiy.common.exception.BusinessException: 导入失败，文件不超过10M！",
                e.getMessage());
        }

    }

    @Test
    public void manualStartProcess() throws Exception {
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        manualStartActivityRequest.setBusinessId("1");
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(ActionResponse.success());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/start/process")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(manualStartActivityRequest)))
            .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void completeTask() throws Exception {
        ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
        activityHandleRequest.setTaskId("1");
        Mockito.when(activityClient.completeTask(Mockito.any())).thenReturn(ActionResponse.success());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/deal/process")
            .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(activityHandleRequest)))
            .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void queryWaitRegistCount() throws Exception {
        Mockito.when(iAssetService.queryWaitRegistCount()).thenReturn(0);
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/asset/query/waitRegistCount").contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAssetIds() throws Exception {
        Mockito.when(iAssetService.findAssetIds()).thenReturn(new IDResponse());
        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/asset/query/assetIds").contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    public void findAlarmAssetCount() throws Exception {
        Mockito.when(iAssetService.findAlarmAssetCount()).thenReturn(new HashMap());
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/api/v1/asset/alarm/count").contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());

    }
}