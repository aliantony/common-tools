package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetAssemblyLibDao;
import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.entity.AssetAssemblyLib;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.request.AssetAssemblyLibRequest;
import com.antiy.asset.vo.response.AssetAssemblyLibResponse;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ RedisKeyUtil.class, LoginUserUtil.class, LogUtils.class, LogHandle.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetAssemblyLibServiceImplTest {

    @Mock
    private AssetAssemblyLibDao                                       assetAssemblyLibDao;
    @Spy
    private BaseConverter<AssetAssemblyLibRequest, AssetAssemblyLib>  requestConverter;
    @Spy
    private BaseConverter<AssetAssemblyLib, AssetAssemblyLibResponse> responseConverter;
    @Spy
    private BaseConverter<AssetAssembly, AssetAssemblyResponse>       assemblyResponseBaseConverter;
    @InjectMocks
    AssetAssemblyLibServiceImpl                                       assetAssemblyLibService;

    @Test
    public void queryAssemblyByHardSoftIdTest() {
        List<AssetAssembly> list = new ArrayList<>();
        AssetAssembly assetAssembly = new AssetAssembly();
        assetAssembly.setProductName("1");
        list.add(assetAssembly);
        Mockito.when(assetAssemblyLibDao.queryAssemblyByHardSoftId(Mockito.any())).thenReturn(list);
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        Assert.assertEquals("1", assetAssemblyLibService.queryAssemblyByHardSoftId(condition).get(0).getProductName());
    }
}
