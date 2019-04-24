package com.antiy.asset.controller;

import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.PageResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SchemeControllerTest {
    @MockBean
    private ISchemeService iSchemeService;

    private MockMvc mockMvc;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testQueryList() throws Exception {
        PageResult<SchemeResponse> pageResult = new PageResult<>();
        pageResult.setPageSize(10);
        pageResult.setCurrentPage(1);
        pageResult.setTotalRecords(100);

        when(iSchemeService.findPageScheme(any())).thenReturn(pageResult);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("type", "1");
        map.add("putintoUser", "a");
        map.add("assetId", "1");
        map.add("assetStatus", "1");
        map.add("assetTypeEnum", "HARDWARE");

        mockMvc.perform(
                get("/api/v1/asset/scheme/query/list")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .params(map)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void testQueryByAssetIdAndType() throws Exception {
        when(iSchemeService.findSchemeByAssetIdAndType(any())).thenReturn(new SchemeResponse());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("type", "1");
        map.add("assetId", "1");

        mockMvc.perform(
                get("/api/v1/asset/scheme/query/AssetIdAndType")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(map)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void testQueryMemoByAssetId() throws Exception {
        when(iSchemeService.queryMemoById(any())).thenReturn(new SchemeResponse());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("type", "1");
        map.add("putintoUser", "a");
        map.add("assetId", "1");
        map.add("assetStatus", "1");
        map.add("assetTypeEnum", "HARDWARE");

        mockMvc.perform(
                get("/api/v1/asset/scheme/query/memoById")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(map)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
