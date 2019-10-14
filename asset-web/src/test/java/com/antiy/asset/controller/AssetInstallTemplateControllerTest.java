package com.antiy.asset.controller;

import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.biz.util.CodeRedisUtil;
import com.antiy.biz.util.RedisUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

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
}
