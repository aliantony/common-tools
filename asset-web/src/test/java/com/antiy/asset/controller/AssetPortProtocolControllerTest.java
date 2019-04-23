package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetPortProtocolControllerTest {

    /**
     * 模拟Service
     */
    @MockBean
    private IAssetPortProtocolService iAssetPortProtocolService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Gson gson = new Gson();
    private MockMvc mockMvc;

    /**
     * @description 运行每一个测试方法之前，会先运行此方法
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 保存接口测试
     * 断言response
     * @throws Exception e
     */
    @Test
    public void saveSingle() throws Exception {
        AssetPortProtocolRequest assetPortProtocolRequest = new AssetPortProtocolRequest();
        Integer[] port = {3306};
        assetPortProtocolRequest.setAssetSoftId("1");
        assetPortProtocolRequest.setId("1");
        assetPortProtocolRequest.setPort(port);
        assetPortProtocolRequest.setProtocol("http");
        assetPortProtocolRequest.setDescription("ss");
        String json = gson.toJson(assetPortProtocolRequest);
        String result = mockMvc.perform(post("/api/v1/asset/portprotocol/save/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 修改接口测试
     * 断言response
     * @throws Exception e
     */
    @Test
    public void updateSingle() throws Exception {
        AssetPortProtocolRequest assetPortProtocolRequest = new AssetPortProtocolRequest();
        Integer[] port = {3306};
        assetPortProtocolRequest.setAssetSoftId("1");
        assetPortProtocolRequest.setId("1");
        assetPortProtocolRequest.setPort(port);
        assetPortProtocolRequest.setProtocol("http");
        assetPortProtocolRequest.setDescription("ss");
        String json = gson.toJson(assetPortProtocolRequest);
        String result = mockMvc.perform(post("/api/v1/asset/portprotocol/update/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 条件查询接口测试
     * 断言response
     * @throws Exception e
     */
    @Test
    public void queryList() throws Exception {
        String result = mockMvc.perform(get("/api/v1/asset/portprotocol/query/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("orderByField","id")
                .param("asc","True"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据id查询
     * 断言response
     * @throws Exception
     */
    @Test
    public void queryById() throws Exception {
        String result = mockMvc.perform(get("/api/v1/asset/portprotocol/query/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("primaryKey","1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}"));
    }

    /**
     * 根据Id删除
     * 断言response
     * @throws Exception
     */
    @Test
    public void deleteById() throws Exception {
        String result = mockMvc.perform(post("/api/v1/asset/portprotocol/delete/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("primaryKey", "3"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertThat(result,containsString("{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":0}"));
    }
}