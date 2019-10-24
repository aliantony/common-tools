package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetAssemblyDao;
import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.request.AssetAssemblyRequest;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
        LogHandle.class, ZipUtil.class })
// @SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetAssemblyServiceImplTest {

    @InjectMocks
    private AssetAssemblyServiceImpl assetAssemblyService;

    @Mock
    private AssetAssemblyDao assetAssemblyDao;
    @Mock
    private BaseConverter<AssetAssembly, AssetAssemblyResponse> responseConverter;
    @Mock
    private BaseConverter<AssetAssemblyRequest, AssetAssembly>  requestConverter2;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void queryPageAssetAssembly() throws Exception {
        AssetAssemblyQuery query = new AssetAssemblyQuery();
        query.setPageSize(10);
        query.setCurrentPage(1);
        List<String> excludeIds = Lists.newArrayList("1", "2", "3");
        query.setExcludeAssemblyIds(excludeIds);
        List<AssetAssembly> result = new ArrayList<>();
        when(assetAssemblyDao.findCount(query)).thenReturn(20);
        when(assetAssemblyDao.findQuery(query)).thenReturn(result);
        assetAssemblyService.queryPageAssetAssembly(query);
    }

    @Test
    public void saveAssetAssembly() throws Exception {
        AssetAssembly assetAssembly = new AssetAssembly();
        AssetAssemblyRequest request=new AssetAssemblyRequest();
        when(requestConverter2.convert(request,AssetAssembly.class)).thenReturn(assetAssembly);
        String s = assetAssemblyService.saveAssetAssembly(request);
        Assert.assertNull(s);
    }

    @Test
    public void updateAssetAssembly() throws Exception {
        AssetAssembly assetAssembly = new AssetAssembly();
        AssetAssemblyRequest request=new AssetAssemblyRequest();
        when(requestConverter2.convert(request,AssetAssembly.class)).thenReturn(assetAssembly);
        when(assetAssemblyDao.update(assetAssembly)).thenReturn(1);
        String s = assetAssemblyService.updateAssetAssembly(request);
        Assert.assertEquals("1",s);
    }

    @Test
    public void queryListAssetAssembly() throws Exception {
        AssetAssemblyQuery query=new AssetAssemblyQuery();
        List<AssetAssemblyResponse> assetAssemblyResponses = assetAssemblyService.queryListAssetAssembly(query);
        Assert.assertEquals(0,assetAssemblyResponses.size());
    }


    @Test
    public void queryAssetAssemblyById() throws Exception {
        QueryCondition queryCondition=new QueryCondition ();
        queryCondition.setPrimaryKey("134");
        AssetAssemblyResponse assetAssemblyResponse = assetAssemblyService.queryAssetAssemblyById(queryCondition);
        Assert.assertNull(assetAssemblyResponse);
        QueryCondition queryCondition2=new QueryCondition ();
        expectedEx.expectMessage("主键Id不能为空");
        expectedEx.expect(RequestParamValidateException.class);
        assetAssemblyService.queryAssetAssemblyById(queryCondition2);
    }

    @Test
    public void deleteAssetAssemblyById() throws Exception {

        BaseRequest baseRequest=new BaseRequest ();
        baseRequest.setStringId("123");
        when(assetAssemblyDao.deleteById(baseRequest.getStringId())).thenReturn(1);
        String s = assetAssemblyService.deleteAssetAssemblyById(baseRequest);
        Assert.assertEquals("1",s);
        BaseRequest baseRequest2=new BaseRequest ();
        expectedEx.expectMessage("主键Id不能为空");
        expectedEx.expect(RequestParamValidateException.class);
        assetAssemblyService.deleteAssetAssemblyById(baseRequest2);
    }
}