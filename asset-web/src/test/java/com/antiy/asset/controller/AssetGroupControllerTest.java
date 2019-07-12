package com.antiy.asset.controller;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.request.UnconnectedManufacturerRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetGroupControllerTest {

    @MockBean
    private IAssetGroupService iAssetGroupService;

    private MockMvc            mvc;
    @InjectMocks
    AssetGroupController       assetGroupController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(assetGroupController).build();
    }

    @Test
    public void saveSingle() throws Exception {
        AssetGroupRequest assetGroup = new AssetGroupRequest();
        when(iAssetGroupService.saveAssetGroup(any())).thenReturn("1");
        Assert.assertEquals(200,
            mvc.perform(post("/api/v1/asset/group/save/single").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.object2Json(assetGroup))).andExpect(status().isOk()).andExpect(status().isOk())
                .andReturn().getResponse().getStatus());
    }

    @Test
    public void updateSingle() throws Exception {
        AssetGroupRequest assetGroup = new AssetGroupRequest();
        when(iAssetGroupService.updateAssetGroup(any())).thenReturn(1);
        Assert.assertEquals(200,
            mvc.perform(post("/api/v1/asset/group/update/single").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.object2Json(assetGroup))).andExpect(status().isOk()).andReturn().getResponse()
                .getStatus());
    }

    @Test
    public void queryList() throws Exception {
        PageResult<AssetGroupResponse> page = new PageResult<AssetGroupResponse>(10, 100, 1, null);
        AssetGroupQuery assetGroupQuery = new AssetGroupQuery();
        assetGroupQuery.setName("123");
        when(iAssetGroupService.findPageAssetGroup(any())).thenReturn(page);
        Assert.assertEquals(200,
            mvc.perform(post("/api/v1/asset/group/query/list").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(assetGroupQuery))).andExpect(status().isOk()).andReturn().getResponse()
                .getStatus());

    }

    @Test
    public void queryById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        when(iAssetGroupService.findGroupById(any())).thenReturn(new AssetGroupResponse());
        Assert.assertEquals(200,
            mvc.perform(post("/api/v1/asset/group/query/id").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(queryCondition))).andExpect(status().isOk()).andReturn().getResponse()
                .getStatus());

    }

    @Test
    public void queryCreateUser() throws Exception {

        Assert.assertEquals(200, mvc
            .perform(post("/api/v1/asset/group/query/createUser").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk()).andExpect(status().isOk()).andReturn().getResponse().getStatus());

    }

    @Test
    public void deleteById() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        Assert.assertEquals(200,
            mvc.perform(post("/api/v1/asset/group/delete/id").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.object2Json(baseRequest))).andExpect(status().isOk()).andReturn().getResponse()
                .getStatus());

    }

    @Test
    public void queryGroupInfo() throws Exception {

        Assert.assertEquals(200, mvc.perform(post("/api/v1/asset/group/query/groupInfo")).andExpect(status().isOk())
            .andReturn().getResponse().getStatus());

    }

    @Test
    public void queryUnconnectedGroupInfo() throws Exception {
        UnconnectedManufacturerRequest unconnectedManufacturerRequest = new UnconnectedManufacturerRequest();
        Assert.assertEquals(200, mvc
            .perform(post("/api/v1/asset/group/query/unconnectedGroupInfo").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.object2Json(unconnectedManufacturerRequest)))
            .andExpect(status().isOk()).andReturn().getResponse().getStatus());
    }

}
