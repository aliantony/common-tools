package com.antiy.asset.service.impl;

import static org.powermock.api.mockito.PowerMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @Author: lvliang
 * @Date: 2019/10/22 16:38
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginUserUtil.class, LogUtils.class, LogHandle.class, RedisKeyUtil.class })
public class AssetAdmittanceServiceImplTest {
    @Mock
    private AssetDao                            assetDao;
    @Mock
    private BaseConverter<Asset, AssetResponse> responseConverter;
    @Mock
    private AssetGroupRelationDao               assetGroupRelationDao;
    @Mock
    private RedisUtil                           redisUtil;
    @InjectMocks
    private AssetAdmittanceServiceImpl          admittanceService;

    @Test
    public void findPageAsset() throws Exception {
        mockStatic(LoginUserUtil.class);
        mockStatic(RedisKeyUtil.class);
        AssetQuery query = new AssetQuery();
        query.setAssociateGroup(true);
        query.setGroupId("1");
        query.setEnterControl(true);
        LoginUser loginUser = mock(LoginUser.class);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        when(loginUser.getAreaIdsOfCurrentUser()).thenReturn(new ArrayList<>());
        List<Asset> assetList = Lists.newArrayList();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAreaId("1");
        assetList.add(asset);
        List<String> associateAssetIdList = Lists.newArrayList();
        associateAssetIdList.add("1");
        when(assetGroupRelationDao.findAssetIdByAssetGroupId(query.getGroupId())).thenReturn(associateAssetIdList);
        when(assetDao.findCount(query)).thenReturn(0);
        admittanceService.findPageAsset(query);

        query.setEnterControl(false);
        admittanceService.findPageAsset(query);

        when(assetDao.findCount(query)).thenReturn(1);
        when(assetDao.findListAsset(query)).thenReturn(assetList);
        String key = "1";
        SysArea sysArea = new SysArea();
        sysArea.setFullName("aaa");
        when(RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
            DataTypeUtils.stringToInteger(asset.getAreaId()))).thenReturn(key);
        when(redisUtil.getObject(key, SysArea.class)).thenReturn(sysArea);
        try {
            admittanceService.findPageAsset(query);
        } catch (Exception e) {

        }

        when(redisUtil.getObject(key, SysArea.class)).thenThrow(new Exception());
        try {
            admittanceService.findPageAsset(query);
        } catch (Exception e) {

        }
    }
}