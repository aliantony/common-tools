package com.antiy.asset.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.service.impl.LoginUtil;
import com.antiy.asset.templet.AssetUserEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LoginUserUtil;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, BeanConvert.class, LoginUserUtil.class })
public class AssetUserControllerTest {
    @InjectMocks
    private AssetUserController    assetUserController;
    @Mock
    public IAssetUserService       iAssetUserService;
    @Mock
    public IAssetDepartmentService iAssetDepartmentService;
    @Mock
    private RedisUtil              redisUtil;

    private MockMvc                mvc;

    @Rule
    public ExpectedException       expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        mvc = MockMvcBuilders.standaloneSetup(assetUserController).build();

        LoginUser loginUser = new LoginUser();
        loginUser.setId(8);
        loginUser.setUsername("admin");
        loginUser.setPassword("123456@antiy");
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

    }

    @Test
    public void saveSingle() throws Exception {
        AssetUserRequest assetUser = new AssetUserRequest();
        assetUser.setName("chen");
        assetUser.setDepartmentId("T");
        when(iAssetUserService.saveAssetUser(any())).thenReturn("success");
        mvc.perform(post("/api/v1/asset/user/save/single").contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.object2Json(assetUser))).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void exportTemplet() throws Exception {
        mvc.perform(get("/api/v1/asset/user/exportTemplet").contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    public void exportData() throws Exception {

        List<AssetDepartmentResponse> responses = new ArrayList<>();
        AssetDepartmentResponse assetDepartment_1 = new AssetDepartmentResponse();
        assetDepartment_1.setStringId("1");
        AssetDepartmentResponse assetDepartment_2 = new AssetDepartmentResponse();
        assetDepartment_2.setStringId("2");
        responses.add(assetDepartment_1);
        responses.add(assetDepartment_2);
        when(iAssetDepartmentService.findAssetDepartmentById(any())).thenReturn(responses);
        List<AssetUser> assetUserResponseList = new ArrayList<>();
        AssetUser assetUser1 = new AssetUser();
        assetUser1.setAddress("12");
        assetUser1.setCreateUser(1);
        assetUser1.setDepartmentName("w");
        AssetUser assetUser2 = new AssetUser();
        assetUser2.setAddress("12");
        assetUser2.setCreateUser(1);
        assetUser2.setDepartmentName("w");
        assetUserResponseList.add(assetUser1);
        assetUserResponseList.add(assetUser2);
        when(iAssetUserService.findExportListAssetUser(any())).thenReturn(assetUserResponseList);
        mvc.perform(get("/api/v1/asset/user/exportData").param("departmentId", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(get("/api/v1/asset/user/exportData")).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void updateSingle() throws Exception {
        AssetUserRequest assetUser = new AssetUserRequest();
        assetUser.setName("chen");
        assetUser.setDepartmentId("1");
        when(iAssetUserService.updateAssetUser(any())).thenReturn(1);

        mvc.perform(post("/api/v1/asset/user/update/single").contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.object2Json(assetUser))).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void queryList() throws Exception {
        AssetUserQuery query = new AssetUserQuery();
        query.setDepartmentId("1");

        List<AssetDepartmentResponse> responses = new ArrayList<>();
        AssetDepartmentResponse assetDepartment_1 = new AssetDepartmentResponse();
        assetDepartment_1.setStringId("1");
        AssetDepartmentResponse assetDepartment_2 = new AssetDepartmentResponse();
        assetDepartment_2.setStringId("2");
        responses.add(assetDepartment_1);
        responses.add(assetDepartment_2);
        when(iAssetDepartmentService.findAssetDepartmentById(any())).thenReturn(responses);
        PageResult<AssetUserResponse> pageResult = new PageResult<>(10, 100, 1, null);
        when(iAssetUserService.findPageAssetUser(any())).thenReturn(pageResult);
        mvc.perform(post("/api/v1/asset/user/query/list").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.object2Json(query))).andExpect(MockMvcResultMatchers.status().isOk());
        // mvc.perform(get("/api/v1/asset/user/query/list")).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void queryById() throws Exception {

        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("11");

        AssetUser assetUser = new AssetUser();
        assetUser.setAddress("1");
        when(iAssetUserService.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())))
            .thenReturn(assetUser);
        SysArea sysArea = new SysArea();
        sysArea.setFullName("Hello");
        when(redisUtil.getObject(Mockito.any(), Mockito.any())).thenReturn(sysArea);

        mvc.perform(post("/api/v1/asset/user/query").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.object2Json(queryCondition))).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void cancelUser() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("11");
        mvc.perform(post("/api/v1/asset/user/cancel").contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.object2Json(baseRequest))).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void queryUserInAsset() throws Exception {

        mvc.perform(post("/api/v1/asset/user/query/userInAsset")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    // @Ignore
    public void importUser() throws Exception {
        LoginUtil.generateDefaultLoginUser();

        // 文件之外的参数
        ImportResult<AssetUserEntity> importResult = new ImportResult<AssetUserEntity>();
        List<AssetUserEntity> assetUserEntityList = new ArrayList<>();
        AssetUserEntity assetUserEntity = new AssetUserEntity();
        assetUserEntity.setDepartmentName("sss");
        assetUserEntity.setName("Hello");
        importResult.setDataList(assetUserEntityList);
        assetUserEntityList.add(assetUserEntity);
        importResult.setDataList(assetUserEntityList);
        MockMultipartFile firstFile = new MockMultipartFile("file", "用户信息表.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Hello".getBytes());
        when(iAssetDepartmentService.getIdByName(any())).thenReturn("111");
        PowerMockito.mockStatic(ExcelUtils.class);
        PowerMockito.when(ExcelUtils.importExcelFromClient(AssetUserEntity.class, (MultipartFile) firstFile, 0, 0))
            .thenReturn(importResult);
        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/user/importUser").file(firstFile)// 文件
        )// 参数
            .andExpect(MockMvcResultMatchers.status().isOk());

        importResult.setDataList(null);
        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/asset/user/importUser").file(firstFile)// 文件
        )// 参数
            .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
