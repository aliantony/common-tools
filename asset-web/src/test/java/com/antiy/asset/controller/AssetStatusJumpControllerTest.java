package com.antiy.asset.controller;

import com.antiy.asset.service.impl.AssetStatusChangeFlowProcessImpl;
import com.antiy.asset.service.impl.SoftWareStatusChangeProcessImpl;
import com.antiy.common.base.ActionResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetStatusJumpControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private SoftWareStatusChangeProcessImpl softWareStatusChangeProcess;

    @MockBean
    private AssetStatusChangeFlowProcessImpl assetStatusChangeFlowProcess;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testStatusJump() throws Exception {
        Mockito.when(softWareStatusChangeProcess.changeStatus(Mockito.any())).thenReturn(ActionResponse.success());
        Mockito.when(assetStatusChangeFlowProcess.changeStatus(Mockito.any())).thenReturn(ActionResponse.success());

        // software: true
        String json = "{\n" +
                "  \"activityHandleRequest\": {\n" +
                "    \"formData\": \"1\",\n" +
                "    \"taskId\": \"1\"\n" +
                "  },\n" +
                "  \"agree\": false,\n" +
                "  \"assetFlowCategoryEnum\": \"HARDWARE_REGISTER\",\n" +
                "  \"assetId\": \"1\",\n" +
                "  \"assetStatus\": \"WATI_REGSIST\",\n" +
                "  \"manualStartActivityRequest\": {\n" +
                "    \"assignee\": \"1\",\n" +
                "    \"businessId\": \"1\",\n" +
                "    \"formData\": \"1\",\n" +
                "    \"processDefinitionKey\": \"1\"\n" +
                "  },\n" +
                "  \"schemeRequest\": {\n" +
                "    \"content\": \"1\",\n" +
                "    \"extension\": \"1\",\n" +
                "    \"fileInfo\": \"1\",\n" +
                "    \"id\": \"1\",\n" +
                "    \"memo\": \"1\",\n" +
                "    \"putintoTime\": 0,\n" +
                "    \"putintoUser\": \"1\",\n" +
                "    \"putintoUserId\": \"1\",\n" +
                "    \"schemeSource\": 0,\n" +
                "    \"type\": 0\n" +
                "  },\n" +
                "  \"software\": true,\n" +
                "  \"softwareStatusEnum\": \"WATI_REGSIST\",\n" +
                "  \"workOrderVO\": {\n" +
                "    \"content\": \"1\",\n" +
                "    \"endTime\": \"9000000\",\n" +
                "    \"executeUserId\": \"1\",\n" +
                "    \"name\": \"1\",\n" +
                "    \"orderSource\": 0,\n" +
                "    \"orderType\": 0,\n" +
                "    \"relatedSourceId\": \"1\",\n" +
                "    \"startTime\": \"321312\",\n" +
                "    \"workLevel\": 0,\n" +
                "    \"workOrderAttachments\": [\n" +
                "      0\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        mockMvc.perform(
                post("/api/v1/asset/statusjump")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(json.getBytes()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        // software: false
        json = "{\n" +
                "  \"activityHandleRequest\": {\n" +
                "    \"formData\": \"1\",\n" +
                "    \"taskId\": \"1\"\n" +
                "  },\n" +
                "  \"agree\": false,\n" +
                "  \"assetFlowCategoryEnum\": \"HARDWARE_REGISTER\",\n" +
                "  \"assetId\": \"1\",\n" +
                "  \"assetStatus\": \"WATI_REGSIST\",\n" +
                "  \"manualStartActivityRequest\": {\n" +
                "    \"assignee\": \"1\",\n" +
                "    \"businessId\": \"1\",\n" +
                "    \"formData\": \"1\",\n" +
                "    \"processDefinitionKey\": \"1\"\n" +
                "  },\n" +
                "  \"schemeRequest\": {\n" +
                "    \"content\": \"1\",\n" +
                "    \"extension\": \"1\",\n" +
                "    \"fileInfo\": \"1\",\n" +
                "    \"id\": \"1\",\n" +
                "    \"memo\": \"1\",\n" +
                "    \"putintoTime\": 0,\n" +
                "    \"putintoUser\": \"1\",\n" +
                "    \"putintoUserId\": \"1\",\n" +
                "    \"schemeSource\": 0,\n" +
                "    \"type\": 0\n" +
                "  },\n" +
                "  \"software\": false,\n" +
                "  \"softwareStatusEnum\": \"WATI_REGSIST\",\n" +
                "  \"workOrderVO\": {\n" +
                "    \"content\": \"1\",\n" +
                "    \"endTime\": \"9000000\",\n" +
                "    \"executeUserId\": \"1\",\n" +
                "    \"name\": \"1\",\n" +
                "    \"orderSource\": 0,\n" +
                "    \"orderType\": 0,\n" +
                "    \"relatedSourceId\": \"1\",\n" +
                "    \"startTime\": \"321312\",\n" +
                "    \"workLevel\": 0,\n" +
                "    \"workOrderAttachments\": [\n" +
                "      0\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        mockMvc.perform(
                post("/api/v1/asset/statusjump")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(json.getBytes()))
                    .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
}
