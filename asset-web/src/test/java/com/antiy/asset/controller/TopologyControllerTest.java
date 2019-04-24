package com.antiy.asset.controller;
import com.antiy.asset.service.ITopologyService;
import com.antiy.asset.vo.response.TopologyResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TopologyControllerTest {
    @MockBean
    private ITopologyService iTopologyService;

    private MockMvc mockMvc;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testQueryTopologyInit() throws Exception {
        when(iTopologyService.queryTopologyInit())
                .thenReturn(Stream.of(new TopologyResponse()).collect(Collectors.toList()));

        mockMvc.perform(
                get("/api/v1/asset/topology/query/init")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveTopology() throws Exception {
        when(iTopologyService.saveTopology(any())).thenReturn(0);

        String json = "{\"dataJson\":\"{}\",\"topologyType\":\"PHYSICS\"}";

        mockMvc.perform(
                post("/api/v1/asset/topology/save")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testQueryTopology() throws Exception {
        when(iTopologyService.queryTopology(anyInt())).thenReturn("queryTopologyResponse");

        mockMvc.perform(
                get("/api/v1/asset/topology/query?topologyType=PHYSICS")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
