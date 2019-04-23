package com.antiy.asset.controller;


import com.antiy.asset.service.IAssetGroupService;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetGroupControllerTest {

    @MockBean
    private IAssetGroupService iAssetGroupService;


    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void saveSingle() throws Exception {
        AssetGroupRequest assetGroup=new AssetGroupRequest ();
        when(iAssetGroupService.saveAssetGroup(any())).thenReturn("1");
         mvc.perform(post("/api/v1/asset/group/save/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetGroup)))
                 .andExpect(status().isOk());
    }

    @Test
    public void updateSingle() throws Exception {
        AssetGroupRequest assetGroup=new AssetGroupRequest ();
        when(iAssetGroupService.updateAssetGroup(any())).thenReturn(1);
        mvc.perform(post("/api/v1/asset/group/update/single")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(assetGroup)))
                .andExpect(status().isOk());
    }

    @Test
    public void queryList() throws Exception {
        AssetGroupRequest assetGroup=new AssetGroupRequest ();
        PageResult<AssetGroupResponse> page=new PageResult<AssetGroupResponse>(10,100,1,null);
        when( iAssetGroupService.findPageAssetGroup(any())).thenReturn(page);
        mvc.perform(get("/api/v1/asset/group/query/list")
               .param("name","chen"))
                .andExpect(status().isOk());
    }
    @Test
    public void queryById() throws Exception {
        when( iAssetGroupService.findGroupById(any())).thenReturn(new  AssetGroupResponse ());
        mvc.perform(get("/api/v1/asset/group/query/id")
                .param("primaryKey","chen"))
                .andExpect(status().isOk());
    }
    @Test
    public void queryCreateUser() throws Exception {
        mvc.perform(get("/api/v1/asset/group/query/createUser")
               )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        BaseRequest baseRequest=new BaseRequest ();
        baseRequest.setStringId("1");
        mvc.perform(post("/api/v1/asset/group/delete/id")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.object2Json(baseRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void queryGroupInfo() throws Exception {

        mvc.perform(get("/api/v1/asset/group/query/groupInfo")
             )
                .andExpect(status().isOk());
    }
    @Test
    public void queryUnconnectedGroupInfo() throws Exception {

        mvc.perform(get("/api/v1/asset/group/query/unconnectedGroupInfo")
        )
                .andExpect(status().isOk());
    }


}
