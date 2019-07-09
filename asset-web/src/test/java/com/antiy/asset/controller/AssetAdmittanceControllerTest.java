package com.antiy.asset.controller;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.convert.AccessExportConvert;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.AccessExport;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AdmittanceRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssetAdmittanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAssetService assetService;

    @Mock
    private AccessExportConvert accessExportConvert;

    @Mock
    private ExcelDownloadUtil excelDownloadUtil;

    @InjectMocks
    private AssetAdmittanceController assetAdmittanceController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetAdmittanceController).build();
    }

    @Test
    public void queryList() throws Exception {
        AssetQuery asset = new AssetQuery();
        this.mockMvc
            .perform(post("/api/v1/asset/admittance/query/list")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSON.toJSONString(asset)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(equalTo(null))));
    }

    @Test
    public void anagement() throws Exception {
        AdmittanceRequest admittance = new AdmittanceRequest();
        admittance.setAdmittanceStatus(2);
        admittance.setStringId("1");

        Asset log = new Asset();
        log.setName("asset_name");
        log.setAssetStatus(7);

        Mockito.when(assetService.getById(Mockito.anyString())).thenReturn(log);

        this.mockMvc
            .perform(
                post("/api/v1/asset/admittance/access/anagement")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(admittance))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.body", is(0)));

        admittance.setAdmittanceStatus(3);
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

        List<AssetResponse> assetList = new ArrayList<>();
        assetList.add(new AssetResponse());

        List<AccessExport> accessExportList = new ArrayList<>();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(assetService.findListAsset(Mockito.any(), Mockito.any())).thenReturn(assetList);
        Mockito.when(accessExportConvert.convert(assetList, AccessExport.class)).thenReturn(accessExportList);
        Mockito.doNothing().when(excelDownloadUtil).excelDownload(Mockito.anyObject(), Mockito.anyObject(), Mockito.anyString(), Mockito.anyList());

        this.mockMvc
            .perform(get("/api/v1/asset/admittance/access/export")
            .param("status", "1")
            .param("start", "1")
            .param("end", "2")
            .param("request", JsonUtil.object2Json(request))
            .param("response", JsonUtil.object2Json(response)))
            .andExpect(status().isOk());
    }

    @Test
    public void exportNoData2() throws Exception {

        List<AssetResponse> assetList = new ArrayList<>();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(assetService.findListAsset(Mockito.any(), Mockito.any())).thenReturn(assetList);

        this.mockMvc
                .perform(get("/api/v1/asset/admittance/access/export")
                        .param("status", "1")
                        .param("start", "1")
                        .param("end", "2")
                        .param("request", JsonUtil.object2Json(request))
                        .param("response", JsonUtil.object2Json(response)))
                .andExpect(status().isOk());
    }
}