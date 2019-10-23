package com.antiy.asset.controller;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.SysArea;

/**
 * @Author: lvliang
 * @Date: 2019/10/22 14:05
 */
@RunWith(PowerMockRunner.class)
public class AssetUserControllerTest {
    @Mock
    public IAssetUserService       iAssetUserService;
    @Mock
    public IAssetDepartmentService iAssetDepartmentService;
    @Mock
    private RedisUtil              redisUtil;
    @InjectMocks
    private AssetUserController    assetUserController;

    @Test
    public void saveSingle() throws Exception {
        AssetUserRequest assetUser = new AssetUserRequest();
        assetUserController.saveSingle(assetUser);
    }

    @Test
    public void updateSingle() throws Exception {
        AssetUserRequest assetUser = new AssetUserRequest();
        assetUserController.updateSingle(assetUser);
    }

    @Test
    public void queryList() throws Exception {
        AssetUserQuery assetUser = new AssetUserQuery();
        assetUser.setDepartmentId("1");
        List<AssetDepartmentResponse> responses = Lists.newArrayList();
        AssetDepartmentResponse assetDepartmentResponse = new AssetDepartmentResponse();
        assetDepartmentResponse.setStringId("1");
        responses.add(assetDepartmentResponse);
        Mockito
            .when(iAssetDepartmentService
                .findAssetDepartmentById(DataTypeUtils.stringToInteger(assetUser.getDepartmentId())))
            .thenReturn(responses);
        assetUserController.queryList(assetUser);
    }

    @Test
    public void queryById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        AssetUser assetUser = new AssetUser();
        Mockito.when(iAssetUserService.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())))
            .thenReturn(assetUser);

        assetUser.setAddress("1");
        String key = "1";
        SysArea sysArea = null;
        Mockito.when(iAssetUserService.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())))
            .thenReturn(assetUser);
        Mockito.when(redisUtil.getObject(key, SysArea.class)).thenReturn(sysArea);
        assetUserController.queryById(queryCondition);

        sysArea = new SysArea();
        sysArea.setFullName("aaa");
        Mockito.when(redisUtil.getObject(key, SysArea.class)).thenReturn(sysArea);
        assetUserController.queryById(queryCondition);
    }

    @Test
    public void cancelUser() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        assetUserController.cancelUser(baseRequest);
    }

    @Test
    public void queryUserInAsset() throws Exception {
        assetUserController.queryUserInAsset();
    }
}