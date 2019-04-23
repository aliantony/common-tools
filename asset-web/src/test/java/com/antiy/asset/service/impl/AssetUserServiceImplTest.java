package com.antiy.asset.service.impl;

import com.antiy.asset.convert.UserSelectResponseConverter;
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
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetUserServiceImplTest {

    @InjectMocks
    private AssetUserServiceImpl assetUserService;
    @Mock
    private BaseConverter<AssetUserRequest, AssetUser> requestConverter;
    @Mock
    private BaseConverter<AssetUser, AssetUserResponse> responseConverter;
    @Mock
    private UserSelectResponseConverter userSelectResponseConverter;
    @Mock
    private AssetUserDao assetUserDao;
    @Mock
    private RedisUtil redisUtil;
    @Mock
    private AesEncoder aesEncoder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        LoginUtil.generateDefaultLoginUser();
    }
    @Test
    public void saveAssetUserTest()throws Exception{
        AssetUserRequest request = new AssetUserRequest();
        request.setId("1");
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);

        Mockito.when(requestConverter.convert(request, AssetUser.class)).thenReturn(assetUser);
        Mockito.when(assetUserDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(aesEncoder.encode(assetUser.getStringId(), LoginUserUtil.getLoginUser().getUsername())).thenReturn("200");
        String actual = assetUserService.saveAssetUser(request);
        Assert.assertEquals("200",actual);
    }

    @Test
    public void updateAssetUserTest()throws Exception{
        AssetUserRequest request = new AssetUserRequest();
        request.setId("1");
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);
        Mockito.when(requestConverter.convert(request, AssetUser.class)).thenReturn(assetUser);
        Mockito.when(assetUserDao.update(Mockito.any())).thenReturn(1);
        int actual = assetUserService.updateAssetUser(request);
        Assert.assertEquals(1,actual);
    }

    @Test
    public void findListAssetUserTest()throws Exception{
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
        Mockito.when(redisUtil.getObject("system:SysArea:1",SysArea.class)).thenReturn(sysArea);
        Mockito.when(responseConverter.convert(assetUserList, AssetUserResponse.class)).thenReturn(assetUserResponseList);
        List<AssetUserResponse> actual = assetUserService.findListAssetUser(query);
        Assert.assertTrue(assetUserResponseList.size()==actual.size());
    }

    @Test
    public void findPageAssetUserTest()throws Exception{
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
    public void queryUserInAssetTest()throws Exception{
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setName("test");
        assetUserList.add(assetUser);
        SelectResponse selectResponse = new SelectResponse();
        selectResponse.setValue("test");
        List<SelectResponse> selectResponseList = new ArrayList<>();
        selectResponseList.add(selectResponse);

        Mockito.when(assetUserDao.findUserInAsset()).thenReturn(assetUserList);
        Mockito.when(userSelectResponseConverter.convert(assetUserList, SelectResponse.class)).thenReturn(selectResponseList);
        List<SelectResponse> actual = assetUserService.queryUserInAsset();
        Assert.assertTrue(selectResponseList.size() == actual.size());
    }


    @Test
    public void findExportListAssetUser(){
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
    public void importUserTest(){
        List<AssetUser> assetUserList = new ArrayList<>();
        AssetUser assetUser = new AssetUser();
        assetUser.setName("test");
        assetUserList.add(assetUser);
        assetUserService.importUser(assetUserList);
        Mockito.verify(assetUserDao).insertBatch(Mockito.any());
    }
}
