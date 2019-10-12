package com.antiy.asset.controller;

import com.antiy.asset.service.IAssetLinkRelationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AssetLinkRelationControllerTest {
    private MockMvc mockMvc;

    /**
     * 被测试controller
     */
    @InjectMocks
    private AssetLinkRelationController controller;

    /**
     * 模拟service层返回
     */
    @Mock
    public IAssetLinkRelationService iAssetLinkRelationService;

    private final String NULL_RESULT = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}";

    /**
     * 初始化mockMvc
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * 保存接口测试
     * 构造post请求，controller调用service从数据库获取数据，返回读取结果
     * 断言响应内容
     * @throws Exception except
     */
    @Test
    public void saveSingle() throws Exception {
        Mockito.when(iAssetLinkRelationService.saveAssetLinkRelation(Mockito.any())).thenReturn(true);

        content("{\"assetId\":\"998\"}", "/save/single", "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":true}");
    }

    /**
     * 查询已关联资产关系列表测试
     * 构造get请求，controller调用service从数据库查询数据，返回查询结果
     * 断言响应内容
     * @throws Exception e
     */
    @Test
    public void queryAssetLinkedList() throws Exception {
        Mockito.when(iAssetLinkRelationService.queryLinekedRelationPage(Mockito.any())).thenReturn(null);
        content("{\"key\":\"value\"}", "/query/assetLinkedList", NULL_RESULT);

    }

    /**
     * 资产通联数量列表查询测试
     * 构造get请求，controller调用service从数据库查询数据，返回查询结果
     * 断言响应内容
     * @throws Exception e
     */
    @Test
    public void queryAssetLinkedCount() throws Exception {
        Mockito.when(iAssetLinkRelationService.queryAssetLinkedCountPage(Mockito.any())).thenReturn(null);

        content("{\"primaryKey\":\"998\"}", "/query/assetLinkedCount", NULL_RESULT);

    }

    /**
     * 与当前资产通联的资产列表查询测试
     * 构造get请求，controller调用service从数据库查询数据，返回查询结果
     * 断言响应内容
     * @throws Exception e
     */
    @Test
    public void queryLinkedAssetListByAssetId() throws Exception {
        Mockito.when(iAssetLinkRelationService.queryLinkedAssetPageByAssetId(Mockito.any())).thenReturn(null);

        content("{\"primaryKey\":\"998\"}", "/query/linkedAssetListByAssetId", NULL_RESULT);
    }

    /**
     * 查询剩余的ip接口测试
     * 构造post请求，controller调用service从数据库获取数据，返回读取结果
     * 断言响应内容
     * @throws Exception except
     */
    @Test
    public void queryUseableIp() throws Exception {
        Mockito.when(iAssetLinkRelationService.queryUseableIp(Mockito.any())).thenReturn(null);

        content("{\"assetId\":\"23333\"}", "/query/useableip", "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}");
    }

    /**
     * 构造post请求
     * @param s JSON请求体
     * @param url 请求接口
     * @param result 响应体内容
     * @throws Exception e
     */
    private void content(String s, String url, String result) throws Exception {
        byte[] json = s.getBytes(StandardCharsets.UTF_8);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/asset/linkrelation" + url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(result))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 构造get请求
     * @param key 查询参数键
     * @param value 查询参数值
     * @param url 请求接口
     * @param result 响应体内容
     * @throws Exception e
     */
    private void param(String key, String value, String url, String result) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/asset/linkrelation" + url)
                .param(key, value))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(result))
                .andDo(MockMvcResultHandlers.print());
    }
}