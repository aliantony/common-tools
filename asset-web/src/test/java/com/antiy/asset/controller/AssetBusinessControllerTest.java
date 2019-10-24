package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSynchRedundant;
import com.antiy.asset.service.impl.AssetServiceImpl;
import com.antiy.asset.vo.query.AssetSynchCpeQuery;
import com.antiy.asset.vo.request.AreaIdRequest;
import com.antiy.asset.vo.request.AssetIdRequest;
import com.antiy.asset.vo.request.AssetMatchRequest;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import util.ControllerUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author zhangyajun
 * @create 2019-10-10 13:42
 **/
@RunWith(PowerMockRunner.class)
public class AssetBusinessControllerTest {


    @InjectMocks
    private AssetBusinessController assetBusinessController;

    @Mock
    public IAssetService assetService;

    @Mock
    private IAssetSynchRedundant synchRedundant;

    private MockMvc mockMvc;


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetBusinessController).build();

    }

    @Test
    public void queryUuidByAreaId() throws Exception {
        AreaIdRequest request = new AreaIdRequest();
        List<String> results = new ArrayList<>();
        results.add("1");
        when(assetService.queryUuidByAreaIds(request)).thenReturn(results);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/uuidByAreaId")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(request))).andReturn();
        Assert.assertEquals("200", ControllerUtil.getResponse(mvcResult).getHead().getCode());
    }

    @Test
    public void queryIdByAreaId() throws Exception {
        AreaIdRequest request = new AreaIdRequest();
        List<String> results = new ArrayList<>();
        results.add("1");
        when(assetService.queryIdByAreaIds(request)).thenReturn(results);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/idByAreaId")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(request))).andReturn();
        Assert.assertEquals("200", ControllerUtil.getResponse(mvcResult).getHead().getCode());
    }

    @Test
    public void matchAssetByIpMac() throws Exception {
        AssetMatchRequest request = new AssetMatchRequest();
        when(assetService.matchAssetByIpMac(request)).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/match/assetByIpMac")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(request))).andReturn();
        Assert.assertEquals("200", ControllerUtil.getResponse(mvcResult).getHead().getCode());
    }

    @Test
    public void queryUuidByAssetId() throws Exception {
        AssetIdRequest request = new AssetIdRequest();
        when(assetService.queryUuidByAssetId(request)).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/query/uuidByAssetId")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(request))).andReturn();
        Assert.assertEquals("200", ControllerUtil.getResponse(mvcResult).getHead().getCode());
    }

    /**
     * 更新冗余字段接口
     */
    @Test
    public void redundantFiled() throws Exception {
        AssetSynchCpeQuery query = new AssetSynchCpeQuery();
        when(synchRedundant.synchRedundantAsset(Mockito.any(AssetSynchCpeQuery.class))).thenReturn(1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/update/redundantFiled")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.object2Json(query))).andReturn();
        Assert.assertEquals(Integer.valueOf(1), ControllerUtil.getResponse(mvcResult).getBody());
    }



}