package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
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
@PrepareForTest({BeanConvert.class, LoginUserUtil.class, LogHandle.class, AssetNetworkCardServiceImpl.class})
public class AssetNetworkCardServiceImplTest {
    @Mock
    private AssetNetworkCardDao assetNetworkCardDao;
    @Mock
    private BaseConverter<AssetNetworkCard, AssetNetworkCardResponse> responseConverter;
    @InjectMocks
    private AssetNetworkCardServiceImpl assetNetworkCardService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        assetNetworkCardService = PowerMockito.spy(assetNetworkCardService);
    }

    @Test
    @Ignore
    public void saveAssetNetworkCardTest() throws Exception {
        AssetNetworkCardRequest request = new AssetNetworkCardRequest();
        AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
        Integer expect = 1;
        assetNetworkCard.setId(expect);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convertBean(request, AssetNetworkCard.class)).thenReturn(assetNetworkCard);
        Mockito.when(assetNetworkCardDao.insert(Mockito.any())).thenReturn(1);
        Assert.assertEquals(expect, assetNetworkCardService.saveAssetNetworkCard(request));

    }

    @Test
    @Ignore
    public void updateAssetNetworkCardTest() throws Exception {
        AssetNetworkCardRequest request = new AssetNetworkCardRequest();
        AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
        Integer expect = 1;
        assetNetworkCard.setId(expect);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convertBean(request, AssetNetworkCard.class)).thenReturn(assetNetworkCard);
        Mockito.when(assetNetworkCardDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetNetworkCardService.updateAssetNetworkCard(request));

    }

    @Test
    public void findListAssetNetworkCardTest() throws Exception {
        AssetNetworkCardQuery query = new AssetNetworkCardQuery();
        List<AssetNetworkCard> list = new ArrayList<>();
        List<AssetNetworkCardResponse> expect = new ArrayList<>();
        PowerMockito.when(responseConverter.convert(list, AssetNetworkCardResponse.class)).thenReturn(expect);
        Mockito.when(assetNetworkCardDao.findListAssetNetworkCard(query)).thenReturn(list);
        Assert.assertEquals(expect, assetNetworkCardService.findListAssetNetworkCard(query));
    }

    @Test
    public void findCountAssetNetworkCardTest() throws Exception {
        AssetNetworkCardQuery query = new AssetNetworkCardQuery();
        Integer expect = 1;
        Mockito.when(assetNetworkCardDao.findCount(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetNetworkCardService.findCountAssetNetworkCard(query));
    }

    @Test
    public void findPageAssetNetworkCardTest() throws Exception {
        AssetNetworkCardQuery query = new AssetNetworkCardQuery();
        List<AssetNetworkCardResponse> expectList = new ArrayList<>();
        Integer expect = 1;
        PowerMockito.doReturn(expect).when(assetNetworkCardService, "findCountAssetNetworkCard", Mockito.any());
        PowerMockito.doReturn(expectList).when(assetNetworkCardService, "findListAssetNetworkCard", Mockito.any());
        Assert.assertEquals(expect.intValue(), assetNetworkCardService.findPageAssetNetworkCard(query).getTotalRecords());
        Assert.assertEquals(expectList, assetNetworkCardService.findPageAssetNetworkCard(query).getItems());

    }
}
