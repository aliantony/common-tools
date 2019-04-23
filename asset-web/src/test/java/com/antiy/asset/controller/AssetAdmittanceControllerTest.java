package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.request.AdmittanceRequest;
import com.antiy.asset.vo.response.AssetResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssetAdmittanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAssetService assetService;

    @InjectMocks
    private AssetAdmittanceController assetAdmittanceController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetAdmittanceController).build();
    }

    @Test
    public void queryList() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/admittance/query/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void anagement() throws Exception {
        AdmittanceRequest admittance = new AdmittanceRequest();
        admittance.setAdmittanceStatus(0);
        admittance.setStringId("1");
        this.mockMvc
            .perform(
                post("/api/v1/asset/admittance/access/anagement")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(admittance))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(0)));
    }

    @Test
    public void exportNoData() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/asset/admittance/access/export"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is("没有数据可以导出")));
    }

    // @Test
    public void exportHasData() throws Exception {
        List<AssetResponse> assetList = new ArrayList<>();
        AssetResponse response = new AssetResponse();
        assetList.add(response);
        when(assetService.findListAsset(any())).thenReturn(assetList);

        String fileName = "资产准入管理.xlsx";
        this.mockMvc
            .perform(get("/api/v1/asset/admittance/access/export"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Disposition",
                "attachment; filename=" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1")));
    }
}