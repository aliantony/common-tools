package com.antiy.asset.controller;

import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.service.IAssetNetworkCardService;
import com.antiy.asset.util.ControllerUtil;
import com.antiy.asset.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetNetworkCardControllerTest {

    @InjectMocks
    private AssetNetworkCardController assetNetworkCardController;

    @Mock
    private IAssetNetworkCardService iAssetNetworkCardService;

    private MockMvc mockMvc;

    private BaseRequest baseRequest;

    private AssetNetworkCardRequest assetNetworkCardRequest;

    private QueryCondition queryCondition;

    private AssetNetworkCard assetNetworkCard;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(assetNetworkCardController).build();
    }

    @Test
    public void saveSingle()throws Exception {

        String responseStr = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}";

        assetNetworkCardRequest = new AssetNetworkCardRequest();

        when(iAssetNetworkCardService.saveAssetNetworkCard(Mockito.any())).thenReturn(Mockito.anyInt());

        MockHttpServletResponse sResponse = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/asset/networkcard/save/single")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.object2Json(assetNetworkCardRequest)))
                .andReturn()
                .getResponse();

        String content = sResponse.getContentAsString();

        Assert.assertEquals(content, responseStr);
    }

    @Test
    public void updateSingle() throws Exception {

        String responseStr = "{\"head\":{\"code\":\"200\",\"result\":\"成功\"},\"body\":null}";

        assetNetworkCardRequest = new AssetNetworkCardRequest();

        when(iAssetNetworkCardService.updateAssetNetworkCard(Mockito.any())).thenReturn(Mockito.anyInt());

        MockHttpServletResponse sResponse = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/asset/networkcard/update/single")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.object2Json(assetNetworkCardRequest)))
                .andReturn()
                .getResponse();

        String content = sResponse.getContentAsString();

        Assert.assertEquals(content, responseStr);
    }

    @Test
    public void queryList() throws Exception {

        assetNetworkCardRequest = new AssetNetworkCardRequest();

        String responseResult = "{pageSize=10, totalRecords=1, currentPage=1, items=[{stringId=null, assetId=null, brand=null, model=null, serial=null, ipAddress=null, macAddress=null, defaultGateway=null, networkAddress=null, subnetMask=null}], totalPages=1}";

        AssetNetworkCardResponse assetNetworkCardResponse = new AssetNetworkCardResponse();
        List<AssetNetworkCardResponse> list = new ArrayList<>();
        list.add(assetNetworkCardResponse);
        PageResult<AssetNetworkCardResponse> pageResult = new PageResult<>(10, 1, 1, list);

        when(iAssetNetworkCardService.findPageAssetNetworkCard(Mockito.any())).thenReturn(pageResult);

        MockHttpServletResponse sResponse = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/asset/networkcard/query/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.object2Json(assetNetworkCardRequest)))
                .andReturn()
                .getResponse();

        String content = sResponse.getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);
        String result = actionResponse.getBody().toString();

        Assert.assertEquals(responseResult, result);
    }

    @Test
    public void queryById() throws Exception {

        queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");

        assetNetworkCard = new AssetNetworkCard();

        when(iAssetNetworkCardService.getById(Mockito.anyString())).thenReturn(assetNetworkCard);

        MockHttpServletResponse sResponse = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/asset/networkcard/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.object2Json(queryCondition)))
                .andReturn()
                .getResponse();

        String content = sResponse.getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);

        Assert.assertEquals(actionResponse.getHead().getCode(), "200");
    }


    @Test
    public void deleteById() throws Exception{

        baseRequest = new BaseRequest();
        baseRequest.setStringId("1");

        when(iAssetNetworkCardService.deleteById(Mockito.any())).thenReturn(Mockito.anyInt());

        MockHttpServletResponse sResponse = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/asset/networkcard/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.object2Json(baseRequest)))
                .andReturn()
                .getResponse();

        String content = sResponse.getContentAsString();
        ActionResponse actionResponse = JsonUtil.json2Object(content, ActionResponse.class);

        Assert.assertEquals(actionResponse.getHead().getCode(), "200");

    }
}