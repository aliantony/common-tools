package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.IAssetCpuService;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.asset.vo.request.AssetCpuRequest;
import com.antiy.asset.vo.response.AssetCpuResponse;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetCpuControllerTest {
    @InjectMocks
    private AssetCpuController assetCpuController;
    @Mock
    private IAssetCpuService iAssetCpuService;
    private MockMvc mockMvc;
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(assetCpuController).build();
    }
    @Test
    public void saveSingle()throws Exception {
        AssetCpuRequest assetCpu = new AssetCpuRequest();
        assetCpu.setAssetId("1");
        Mockito.when(iAssetCpuService.saveAssetCpu(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/cpu/save/single")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetCpu))).andReturn();
        Mockito.verify(iAssetCpuService).saveAssetCpu(Mockito.any());
    }

    @Test
    public void updateSingle()throws Exception {
        AssetCpuRequest assetCpu = new AssetCpuRequest();
        assetCpu.setAssetId("1");
        Mockito.when(iAssetCpuService.updateAssetCpu(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/cpu/update/single")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetCpu))
        ).andReturn();
        Mockito.verify(iAssetCpuService).updateAssetCpu(Mockito.any());
    }

    @Test
    public void queryList()throws Exception {
        AssetCpuQuery assetCpu = new AssetCpuQuery();
        assetCpu.setAssetId("1");
        List<AssetCpuResponse> assetCpuResponseList = new ArrayList<>();
        AssetCpuResponse assetCpuResponse = new AssetCpuResponse();
        assetCpuResponse.setAssetId("1");
        assetCpuResponseList.add(assetCpuResponse);
        PageResult<AssetCpuResponse> pageResult = new PageResult<>(10,1,1,assetCpuResponseList);
        Mockito.when(iAssetCpuService.findPageAssetCpu(Mockito.any())).thenReturn(pageResult);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/cpu/query/list")
                        .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(assetCpu))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String json = JsonUtil.object2Json(actionResponse.getBody());
        PageResult<AssetCpuResponse> actual = JsonUtil.json2Object(json,PageResult.class);
        Assert.assertTrue(pageResult.getItems().size() == actual.getItems().size());
    }

    @Test
    public void queryById()throws Exception {
        AssetCpuResponse assetCpuResponse = new AssetCpuResponse();
        assetCpuResponse.setAssetId("1");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/asset/cpu/query").param("primaryKey","1")
        ).andReturn();
        Mockito.verify(iAssetCpuService).getById(Mockito.any());
    }

    @Test
    public void deleteById()throws Exception {
        BaseRequest request = new BaseRequest();
        request.setStringId("1");
        Mockito.when(iAssetCpuService.deleteById(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/asset/cpu/delete").contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(request))).andReturn();
        Assert.assertThat(mvcResult.getResponse().getContentAsString(),containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":1}"));
    }
}