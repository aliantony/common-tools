package com.antiy.asset.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetGroupRelationDetailQuery;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.vo.response.AssetGroupRelationResponse;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.PageResult;

@RunWith(PowerMockRunner.class)
@SpringBootTest
@PrepareForTest(BeanConvert.class)
public class AssetGroupRelationServiceImplTest {
    @InjectMocks
    public AssetGroupRelationServiceImpl assetGroupRelationService;
    @Mock
    private AssetGroupRelationDao assetGroupRelationDao;
    @Mock
    private AssetNetworkCardDao assetNetworkCardDao;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveAssetGroupRelation()throws Exception{
        AssetGroupRelationRequest request = new AssetGroupRelationRequest();
        request.setAssetId("1");
        request.setAssetGroupId("1");
        request.setId("1");
        AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
        assetGroupRelation.setId(1);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(request, AssetGroupRelation.class)).thenReturn(assetGroupRelation);
        when(assetGroupRelationDao.insert(Mockito.any())).thenReturn(1);
        int result = assetGroupRelationService.saveAssetGroupRelation(request);
        Assert.assertEquals(1, result);

    }
    @Test
    public void updateAssetGroupRelationTest()throws Exception{
        AssetGroupRelationRequest request = new AssetGroupRelationRequest();
        request.setAssetId("1");
        request.setAssetGroupId("1");
        request.setId("1");
        AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
        assetGroupRelation.setId(1);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(request, AssetGroupRelation.class)).thenReturn(assetGroupRelation);
        when(assetGroupRelationDao.update(Mockito.any())).thenReturn(1);
        int result = assetGroupRelationService.updateAssetGroupRelation(request);
        Assert.assertEquals(1, result);
    }
    @Test
    public void findAssetDetailByAssetGroupIdTest()throws Exception{
        AssetGroupRelationQuery query = new AssetGroupRelationQuery();
        query.setAssetGroupId("1");
        List<AssetGroupRelation> assetGroupRelationList = new ArrayList<>();
        AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
        assetGroupRelation.setAssetId("1");
        assetGroupRelation.setId(1);
        assetGroupRelationList.add(assetGroupRelation);

        AssetGroupRelationDetailQuery assetGroupRelationDetailQuery = new AssetGroupRelationDetailQuery();
        assetGroupRelationDetailQuery.setAssetGroupId("1");

        List<AssetNetworkCard> assetNetworkCardList = new ArrayList<>();
        AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
        assetNetworkCard.setAssetId("1");
        assetNetworkCardList.add(assetNetworkCard);

        List<AssetNetworkCardResponse> assetNetworkCardResponseList = new ArrayList<>();
        AssetNetworkCardResponse assetNetworkCardResponse = new AssetNetworkCardResponse();
        assetNetworkCardResponse.setAssetId("1");
        assetNetworkCardResponseList.add(assetNetworkCardResponse);


        List<AssetGroupRelationResponse> assetGroupRelationResponseList = new ArrayList<>();
        AssetGroupRelationResponse assetGroupRelationResponse = new AssetGroupRelationResponse();
        assetGroupRelationResponse.setAssetId("1");
        assetGroupRelationResponse.setStringId("1");
        assetGroupRelationResponseList.add(assetGroupRelationResponse);

        when(assetGroupRelationDao.findAssetDetailByAssetGroupId(Mockito.any())).thenReturn(assetGroupRelationList);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(query, AssetGroupRelationDetailQuery.class)).thenReturn(assetGroupRelationDetailQuery);
        PowerMockito.when(BeanConvert.convert(assetGroupRelationList, AssetGroupRelationResponse.class)).thenReturn(assetGroupRelationResponseList);
        PowerMockito.when(BeanConvert.convert(assetNetworkCardList, AssetNetworkCardResponse.class)).thenReturn(assetNetworkCardResponseList);
        when(assetNetworkCardDao.findNetworkCardByAssetId(Mockito.any())).thenReturn(assetNetworkCardList);

        List<AssetGroupRelationResponse> actual = assetGroupRelationService.findAssetDetailByAssetGroupId(query);
        Assert.assertTrue(assetGroupRelationResponseList.size()==actual.size());
    }

    @Test
    public void findPageAssetByAssetGroupIdTest()throws Exception{
        AssetGroupRelationQuery query = new AssetGroupRelationQuery();
        query.setAssetGroupId("1");
        AssetGroupRelationDetailQuery assetGroupRelationDetailQuery = new AssetGroupRelationDetailQuery();
        assetGroupRelationDetailQuery.setAssetGroupId("1");
        PageResult<AssetGroupRelationResponse> pageResult = new PageResult<>();
        List<AssetGroupRelationResponse> assetGroupRelationResponseList = new ArrayList<>();
        AssetGroupRelationResponse assetGroupRelationResponse = new AssetGroupRelationResponse();
        assetGroupRelationResponse.setStringId("1");
        assetGroupRelationResponse.setAssetId("1");
        assetGroupRelationResponseList.add(assetGroupRelationResponse);
        pageResult.setItems(assetGroupRelationResponseList);

        List<AssetGroupRelation> assetGroupRelationList = new ArrayList<>();
        AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
        assetGroupRelation.setId(1);
        assetGroupRelationList.add(assetGroupRelation);

        List<AssetNetworkCard> assetNetworkCardList = new ArrayList<>();
        AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
        assetNetworkCard.setAssetId("1");
        assetNetworkCardList.add(assetNetworkCard);
        List<AssetNetworkCardResponse> assetNetworkCardResponseList = new ArrayList<>();
        AssetNetworkCardResponse assetNetworkCardResponse = new AssetNetworkCardResponse();
        assetNetworkCardResponse.setAssetId("1");
        assetNetworkCardResponseList.add(assetNetworkCardResponse);

        when(assetNetworkCardDao.findNetworkCardByAssetId(Mockito.any())).thenReturn(assetNetworkCardList);
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(query, AssetGroupRelationDetailQuery.class)).thenReturn(assetGroupRelationDetailQuery);
        PowerMockito.when(BeanConvert.convert(assetGroupRelationList, AssetGroupRelationResponse.class)).thenReturn(assetGroupRelationResponseList);
        PowerMockito.when(BeanConvert
                .convert(assetNetworkCardList, AssetNetworkCardResponse.class)).thenReturn(assetNetworkCardResponseList);
        when(assetNetworkCardDao.findNetworkCardByAssetId(Mockito.any())).thenReturn(assetNetworkCardList);
        when(assetGroupRelationDao.findAssetDetailByAssetGroupId(Mockito.any())).thenReturn(assetGroupRelationList);
        when(assetGroupRelationDao.findCountDetailByGroupId(Mockito.any())).thenReturn(1);

        PageResult<AssetGroupRelationResponse> actual = assetGroupRelationService.findPageAssetByAssetGroupId(query);
        Assert.assertTrue(pageResult.getItems().size() == actual.getItems().size());
    }

    @Test
    public void findCountDetailByGroupIdTest()throws Exception {
        AssetGroupRelationQuery assetGroupRelationQuery = new AssetGroupRelationQuery();
        assetGroupRelationQuery.setAssetGroupId("1");
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(Mockito.any(AssetGroupRelationQuery.class),Mockito.any())).thenReturn(100);
        when(assetGroupRelationDao.findCountDetailByGroupId(Mockito.any())).thenReturn(100);
        int result = assetGroupRelationService.findCountDetailByGroupId(assetGroupRelationQuery);
        Assert.assertEquals(100,result);
    }
    @Test
    public void findCountAssetGroupRelationTest()throws Exception{
        AssetGroupRelationQuery query = new AssetGroupRelationQuery();
        query.setAssetGroupId("1");
        when(assetGroupRelationDao.findCount(Mockito.any())).thenReturn(1);
        int actual = assetGroupRelationService.findCountAssetGroupRelation(query);
        Assert.assertEquals(1,actual);
    }

    @Test
    public void findListAssetGroupRelationTes() throws Exception {
        AssetGroupRelationQuery query = new AssetGroupRelationQuery();
        Assert.assertEquals(0, assetGroupRelationService.findListAssetGroupRelation(query).size());
    }

    @Test
    public void hasRealtionAssetTest() {
        when(assetGroupRelationDao.hasRealtionAsset("1")).thenReturn(1);
        Assert.assertEquals(new Integer(1), assetGroupRelationService.hasRealtionAsset("1"));
    }

    @Test
    public void findPageAssetGroupRelationTest() throws Exception {
        AssetGroupRelationQuery query = new AssetGroupRelationQuery();
        Assert.assertEquals(0, assetGroupRelationService.findPageAssetGroupRelation(query).getItems().size());

    }

}
