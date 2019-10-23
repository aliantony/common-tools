package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetSynchCpeQuery;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.LoginUser;
import com.antiy.common.utils.LogUtils;
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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ RedisKeyUtil.class, LoginUserUtil.class, LogUtils.class, LogHandle.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class IAssetSynchRedundantImplTest {
    @Mock
    private AssetHardSoftLibDao      hardSoftLibDao;

    @Mock
    private AssetDao                 assetDao;

    @InjectMocks
    private IAssetSynchRedundantImpl iAssetSynchRedundant;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // LoginUtil.generateDefaultLoginUser();
        PowerMockito.mockStatic(LoginUserUtil.class);

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("小李");
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any());

    }

    @Test
    public void synchRedundantAsset1() {
        AssetSynchCpeQuery cpeQuery = new AssetSynchCpeQuery();
        try {
            iAssetSynchRedundant.synchRedundantAsset(cpeQuery);
        } catch (Exception e) {
            Assert.assertEquals("参数不能为空, 请检查参数", e.getMessage());
        }
    }

    @Test
    public void synchRedundantAsset2() {
        AssetSynchCpeQuery cpeQuery = new AssetSynchCpeQuery();
        cpeQuery.setStartStamp(2L);
        cpeQuery.setEndStamp(1L);
        try {
            iAssetSynchRedundant.synchRedundantAsset(cpeQuery);
        } catch (Exception e) {
            Assert.assertEquals("开始时间应小于结束时间", e.getMessage());
        }
    }

    @Test
    public void synchRedundantAsset3() throws Exception {
        AssetSynchCpeQuery cpeQuery = new AssetSynchCpeQuery();
        cpeQuery.setStartStamp(1L);
        cpeQuery.setEndStamp(2L);
        Mockito.when(hardSoftLibDao.getCpeByTime(Mockito.any())).thenReturn(Collections.singletonList(new AssetHardSoftLib()));
        Mockito.when(assetDao.updateRedundantFiled(Mockito.anyList())).thenReturn(1);
        Assert.assertEquals("1", iAssetSynchRedundant.synchRedundantAsset(cpeQuery) + "");

    }

    @Test
    public void synchRedundantAsset4() throws Exception {
        AssetSynchCpeQuery cpeQuery = new AssetSynchCpeQuery();
        cpeQuery.setStartStamp(1L);
        cpeQuery.setEndStamp(2L);
        Assert.assertNull(iAssetSynchRedundant.synchRedundantAsset(cpeQuery));
    }
}
