package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLabelRelationDao;
import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.vo.query.AssetLabelRelationQuery;
import com.antiy.asset.vo.request.AssetLabelRelationRequest;
import com.antiy.asset.vo.response.AssetLabelRelationResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class, AssetLabelRelationServiceImpl.class})
//该测试的类被无效的controller调用，暂不写测试
public class AssetLabelRelationServiceImplTest {
    @Mock
    private BaseConverter<AssetLabelRelationRequest, AssetLabelRelation> requestConverter;
    @Mock
    private AssetLabelRelationDao assetLabelRelationDao;
    @InjectMocks
    AssetLabelRelationServiceImpl assetLabelRelationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        assetLabelRelationService = PowerMockito.spy(assetLabelRelationService);
    }

    @Test
    @Ignore //该测试的方法被无效的controller调用，暂不测试
    public void saveAssetLabelRelationTest() throws Exception {
        AssetLabelRelationRequest request = new AssetLabelRelationRequest();
        AssetLabelRelation assetLabelRelation = new AssetLabelRelation();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetLabelRelation.class)).thenReturn(assetLabelRelation);
        Mockito.when(assetLabelRelationDao.insert(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetLabelRelationService.saveAssetLabelRelation(request));

    }

    @Test
    @Ignore //该测试的方法被无效的controller调用，暂不测试
    public void updateAssetLabelRelationTest() throws Exception {
        AssetLabelRelationRequest request = new AssetLabelRelationRequest();
        AssetLabelRelation assetLabelRelation = new AssetLabelRelation();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetLabelRelation.class)).thenReturn(assetLabelRelation);
        Mockito.when(assetLabelRelationDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetLabelRelationService.updateAssetLabelRelation(request));

    }

    @Test
    @Ignore//该测试的方法被无效的controller调用，暂不测试
    public void findListAssetLabelRelationTest() throws Exception {
        AssetLabelRelationQuery query = new AssetLabelRelationQuery();
        List<AssetLabelRelation> list = new ArrayList<>();
        List<AssetLabelRelationResponse> expect = PowerMockito.mock(ArrayList.class);
        PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn((ArrayList) expect);
        Mockito.when(assetLabelRelationDao.findListAssetLabelRelation(Mockito.any())).thenReturn(list);
        Assert.assertEquals(expect, assetLabelRelationService.findListAssetLabelRelation(query));

    }

    @Test
    public void findCountAssetLabelRelationTest() throws Exception {
        AssetLabelRelationQuery query = new AssetLabelRelationQuery();
        Integer expect = 1;
        Mockito.when(assetLabelRelationDao.findCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetLabelRelationService.findCountAssetLabelRelation(query));

    }

    @Test
    public void findPageAssetLabelRelationTest() throws Exception {
        AssetLabelRelationQuery query = new AssetLabelRelationQuery();
        List<AssetLabelRelationResponse> expectList = new ArrayList<>();
        Integer expect = 1;
        PowerMockito.doReturn(expect).when(assetLabelRelationService).findCountAssetLabelRelation(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetLabelRelationService).findListAssetLabelRelation(Mockito.any());
        Assert.assertEquals(expect.intValue(), assetLabelRelationService.findPageAssetLabelRelation(query).getTotalRecords());
        Assert.assertEquals(expectList, assetLabelRelationService.findPageAssetLabelRelation(query).getItems());

    }
}
