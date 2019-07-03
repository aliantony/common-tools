package com.antiy.asset.service.impl;

import static reactor.core.publisher.Mono.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
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

import com.antiy.asset.convert.UserSelectResponseConverter;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.SysArea;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.encoder.AesUtils;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.slf4j.Logger;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginUserUtil.class, LogUtils.class, RedisKeyUtil.class, ParamterExceptionUtils.class,
                  DataTypeUtils.class, AesUtils.class, BusinessPhaseEnum.class })
public class AssetUserServiceImplTest {

    @InjectMocks
    private AssetUserServiceImpl                        assetUserService;
    @Mock
    private BaseConverter<AssetUserRequest, AssetUser>  requestConverter;
    @Mock
    private BaseConverter<AssetUser, AssetUserResponse> responseConverter;
    @Mock
    private UserSelectResponseConverter                 userSelectResponseConverter;
    @Mock
    private AssetUserDao                                assetUserDao;
    @Mock
    private AssetDao                                    assetDao;
    @Mock
    private RedisUtil                                   redisUtil;
    @Mock
    private AesEncoder                                  aesEncoder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // LoginUtil.generateDefaultLoginUser();
        PowerMockito.mockStatic(LoginUserUtil.class);
        PowerMockito.mockStatic(LogUtils.class);

        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessPhaseEnum.class));
        PowerMockito.doNothing().when(LogUtils.class, "error", Mockito.any(), Mockito.anyString(), Mockito.anyString());
        PowerMockito.doNothing().when(LogUtils.class, "info", Mockito.any(), Mockito.anyString(), Mockito.anyString());
        PowerMockito.doNothing().when(LogUtils.class, "warn", Mockito.any(), Mockito.anyString(), Mockito.anyString());

        LoginUser loginUser = new LoginUser();
        loginUser.setId(8);
        loginUser.setUsername("admin");
        loginUser.setPassword("123456@antiy");
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
    }

    @Test
    public void saveAssetUserTest() throws Exception {

        AssetUserRequest request = new AssetUserRequest();
        request.setId("1");
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);

        Mockito.when(requestConverter.convert(request, AssetUser.class)).thenReturn(assetUser);
        Mockito.when(assetUserDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(aesEncoder.encode(assetUser.getStringId(), LoginUserUtil.getLoginUser().getUsername()))
            .thenReturn("200");
        String actual = assetUserService.saveAssetUser(request);
        Assert.assertEquals("200", actual);

    }

    @Test
    public void updateAssetUserTest() throws Exception {
        AssetUserRequest request = new AssetUserRequest();
        request.setId("1");
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);
        Mockito.when(requestConverter.convert(request, AssetUser.class)).thenReturn(assetUser);
        Mockito.when(assetUserDao.update(Mockito.any())).thenReturn(1);
        int actual = assetUserService.updateAssetUser(request);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void findListAssetUserTest() throws Exception {
        AssetUserQuery query = new AssetUserQuery();
        query.setName("test");
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);
        assetUser.setName("test");
        assetUser.setAddress("1");
        assetUserList.add(assetUser);
        SysArea sysArea = new SysArea();
        sysArea.setFullName("test");
        List<AssetUserResponse> assetUserResponseList = new ArrayList<>();
        AssetUserResponse assetUserResponse = new AssetUserResponse();
        assetUserResponse.setStringId("1");
        assetUserResponseList.add(assetUserResponse);
        Mockito.when(assetUserDao.queryUserList(Mockito.any())).thenReturn(assetUserList);
        Mockito.when(redisUtil.getObject("system:SysArea:1", SysArea.class)).thenReturn(sysArea);
        Mockito.when(responseConverter.convert(assetUserList, AssetUserResponse.class))
            .thenReturn(assetUserResponseList);
        List<AssetUserResponse> actual = assetUserService.findListAssetUser(query);
        Assert.assertTrue(assetUserResponseList.size() == actual.size());
    }

    /**
     * address为空
     */
    @Test
    public void findListAssetUserTest02() throws Exception {
        AssetUserQuery query = new AssetUserQuery();
        query.setName("test");
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);
        assetUser.setName("test");
        assetUserList.add(assetUser);
        SysArea sysArea = new SysArea();
        sysArea.setFullName("test");
        List<AssetUserResponse> assetUserResponseList = new ArrayList<>();
        AssetUserResponse assetUserResponse = new AssetUserResponse();
        assetUserResponse.setStringId("1");
        assetUserResponseList.add(assetUserResponse);
        Mockito.when(assetUserDao.queryUserList(Mockito.any())).thenReturn(assetUserList);
        Mockito.when(redisUtil.getObject("system:SysArea:1", SysArea.class)).thenReturn(sysArea);
        Mockito.when(responseConverter.convert(assetUserList, AssetUserResponse.class))
            .thenReturn(assetUserResponseList);
        List<AssetUserResponse> actual = assetUserService.findListAssetUser(query);
        Assert.assertTrue(assetUserResponseList.size() == actual.size());
    }

    /**
     * redis获取异常
     */
    @Test
    public void findListAssetUserTest03() throws Exception {
        AssetUserQuery query = new AssetUserQuery();
        query.setName("test");
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);
        assetUser.setName("test");
        assetUser.setAddress("1");
        assetUserList.add(assetUser);
        Mockito.when(assetUserDao.queryUserList(Mockito.any())).thenReturn(assetUserList);

        SysArea sysArea = new SysArea();
        sysArea.setFullName("test");
        List<AssetUserResponse> assetUserResponseList = new ArrayList<>();
        AssetUserResponse assetUserResponse = new AssetUserResponse();
        assetUserResponse.setStringId("1");
        assetUserResponseList.add(assetUserResponse);
        Mockito.when(responseConverter.convert(assetUserList, AssetUserResponse.class))
            .thenReturn(assetUserResponseList);
        Mockito.when(redisUtil.getObject(Mockito.anyString(), Mockito.any()))
            .thenThrow(new RuntimeException("redis异常"));

        Assert.assertEquals(assetUserResponseList.size(), assetUserService.findListAssetUser(query).size());

    }

    @Test
    public void findPageAssetUserTest() throws Exception {
        AssetUserQuery query = new AssetUserQuery();
        query.setName("test");

        List<AssetUserResponse> assetUserResponseList = new ArrayList<>();
        AssetUserResponse assetUserResponse = new AssetUserResponse();
        assetUserResponse.setStringId("1");
        assetUserResponseList.add(assetUserResponse);
        PageResult<AssetUserResponse> pageResult = new PageResult<>();
        pageResult.setPageSize(10);
        pageResult.setItems(assetUserResponseList);

        Mockito.when(assetUserDao.findCount(Mockito.any())).thenReturn(1);
        Mockito.when(assetUserService.findListAssetUser(Mockito.any())).thenReturn(assetUserResponseList);
        PageResult<AssetUserResponse> actual = assetUserService.findPageAssetUser(query);
        Assert.assertTrue(pageResult.getItems().size() == actual.getItems().size());
    }

    @Test
    public void queryUserInAssetTest() throws Exception {
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setName("test");
        assetUserList.add(assetUser);
        SelectResponse selectResponse = new SelectResponse();
        selectResponse.setValue("test");
        List<SelectResponse> selectResponseList = new ArrayList<>();
        selectResponseList.add(selectResponse);

        Mockito.when(assetUserDao.findUserInAsset()).thenReturn(assetUserList);
        Mockito.when(userSelectResponseConverter.convert(assetUserList, SelectResponse.class))
            .thenReturn(selectResponseList);
        List<SelectResponse> actual = assetUserService.queryUserInAsset();
        Assert.assertTrue(selectResponseList.size() == actual.size());
    }

    @Test
    public void findExportListAssetUser() {
        AssetUserQuery query = new AssetUserQuery();
        query.setName("test");
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setName("test");
        assetUserList.add(assetUser);
        Mockito.when(assetUserDao.findExportListAssetUser(Mockito.any())).thenReturn(assetUserList);
        List<AssetUser> actual = assetUserService.findExportListAssetUser(query);
        Assert.assertTrue(assetUserList.size() == actual.size());
    }

    @Test
    public void importUserTest() {
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setName("test");
        assetUserList.add(assetUser);
        assetUserService.importUser(assetUserList);
        Mockito.verify(assetUserDao).insertBatch(Mockito.any());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        HashMap<String, Object> param = new HashMap<>(1);
        when(assetDao.getByWhere(Mockito.any(HashMap.class))).thenReturn(new ArrayList<>());
        Assert.assertEquals("200", assetUserService.deleteUserById(1).getHead().getCode());
    }

    @Test
    public void deleteUserByIdTest02() throws Exception {
        HashMap<String, Object> param = new HashMap<>(1);
        when(assetDao.getByWhere(Mockito.any(HashMap.class))).thenReturn(new ArrayList<>());
        AssetUser userInfo = new AssetUser();
        userInfo.setId(1);
        Mockito.when(assetUserDao.getById(Mockito.anyInt())).thenReturn(userInfo);
        Assert.assertEquals("200", assetUserService.deleteUserById(1).getHead().getCode());
    }
}
