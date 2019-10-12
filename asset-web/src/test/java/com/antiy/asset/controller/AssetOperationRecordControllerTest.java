package com.antiy.asset.controller;

import com.antiy.asset.manage.CommonManager;
import com.antiy.asset.manage.controller.SoftwareControllerManager;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetOperationRecordService;
import com.antiy.asset.support.MockContext;
import com.antiy.asset.vo.query.AssetHardSoftOperQuery;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.encoder.AesEncoder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouye
 * 资产动态/cpe信息相关单元测试
 */
public class AssetOperationRecordControllerTest extends MockContext {
    private final static String       URL_PREFIX = "/api/v1/asset/changeLog";
    @Autowired
    private AssetOperationRecordController operationRecordController;
    @Autowired
    private AssetHardSoftLibController hardSoftLibController;
    @MockBean
    private IAssetOperationRecordService   assetOperationRecordService;
    @MockBean
    private IAssetHardSoftLibService hardSoftLibService;
    private MockMvc                        mvc;
    @Autowired
    private SoftwareControllerManager controllerManager;
    @Autowired
    private CommonManager commonManager;
    @Autowired
    private AesEncoder encoder;
    LoginUser loginUser;
    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(operationRecordController,hardSoftLibController).build();
        loginUser = new LoginUser();
        loginUser.setUsername("test");
        loginUser.setPassword("test");
        mockLoginUser(loginUser);
    }

    /**
     * 查询资产动态
     * @throws Exception 业务异常
     */
    @Test
    public void queryList() throws Exception {

        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey(encoder.encode("2",loginUser.getPassword()));
        MockHttpServletRequestBuilder requestBuilder = commonManager.postAction(URL_PREFIX + "/query",condition);
        String result = commonManager.getResult(mvc, requestBuilder);
        System.out.println(result);

    }
    /**
     * 批量查询上一步备注
     */
    @Test
    public void batchQueryPreStatusInfo() throws Exception{
        List<String> ids = new ArrayList<>();
        for (String id : Arrays.asList("1", "2")) {
            ids.add(encoder.encode(id, loginUser.getPassword()));
        }
        BatchQueryRequest batchQueryRequest = new BatchQueryRequest(ids);
        String result = commonManager.getResult(mvc,  commonManager.postAction(URL_PREFIX + "/preNote/batch",batchQueryRequest));
        System.out.println(result);
    }
    /**
     *
     */
    @Test
    public void queryAllList() throws Exception{
        AssetHardSoftOperQuery query = new AssetHardSoftOperQuery();
        query.setProductName("1024");
        String result = commonManager.getResult(mvc,  commonManager.postAction("/api/v1/asset/assethardsoftlib/query/all",query));
        //System.out.println(result);
    }
}