package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.query.AssetOperationRecordQuery;
import com.antiy.asset.vo.response.AssetOperationRecordBarResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetOperationRecordControllerTest {
    @MockBean
    private IAssetOperationRecordService assetOperationRecordService;


    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void queryList() throws Exception {
        AssetOperationRecordQuery assetOperationRecordQuery=new AssetOperationRecordQuery();


        assetOperationRecordQuery.setTargetObjectId("1");
        assetOperationRecordQuery.setTargetType(AssetOperationTableEnum.OPERATION_TYPE);
        List<AssetOperationRecordBarResponse> list=new ArrayList<>();
        when(assetOperationRecordService.queryStatusBarOrderByTime(any())).thenReturn(list);
        MvcResult mvcResult = mvc.perform(get("/api/v1/asset/operationrecord/query/list")
                .param("targetObjectId","1").param("targetType","OPERATION_TYPE")).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(contentAsString.contains("200"));
    }
}