package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetInstallRequest;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
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
import org.powermock.reflect.Whitebox;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class, AssetSoftwareRelationServiceImpl.class, BeanConvert.class})
public class AssetSoftwareRelationServiceImplTest {
    @Mock
    private BaseConverter<AssetSoftware, AssetSoftwareResponse> responseSoftConverter;
    @Mock
    private BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationResponse> responseConverter;
    @Mock
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Mock
    private TransactionTemplate transactionTemplate;
    @Mock
    private BaseConverter<AssetSoftwareRelationRequest, AssetSoftwareRelation> requestConverter;
    @InjectMocks
    private AssetSoftwareRelationServiceImpl assetSoftwareRelationService;
    @Mock
    private LoginUser loginUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(LoginUserUtil.class);
        loginUser.setId(1);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        assetSoftwareRelationService = PowerMockito.spy(assetSoftwareRelationService);
    }

    @Test
    public void saveAssetSoftwareRelationTest() throws Exception {
        AssetSoftwareRelationRequest request = new AssetSoftwareRelationRequest();
        AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
        Integer expect = 1;
        assetSoftwareRelation.setId(expect);
        Mockito.when(requestConverter.convert(request, AssetSoftwareRelation.class)).thenReturn(assetSoftwareRelation);
        Mockito.when(assetSoftwareRelationDao.insert(assetSoftwareRelation)).thenReturn(1);
        Assert.assertEquals(expect, assetSoftwareRelationService.saveAssetSoftwareRelation(request));
    }

    @Test
    public void updateAssetSoftwareRelationTest() throws Exception {
        AssetSoftwareRelationRequest request = new AssetSoftwareRelationRequest();
        AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
        Integer expect = 1;
        Mockito.when(requestConverter.convert(request, AssetSoftwareRelation.class)).thenReturn(assetSoftwareRelation);
        Mockito.when(assetSoftwareRelationDao.update(assetSoftwareRelation)).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.updateAssetSoftwareRelation(request));
    }

    @Test
    public void findListAssetSoftwareRelationTest() throws Exception {
        AssetSoftwareRelationQuery query = new AssetSoftwareRelationQuery();
        List<AssetSoftwareRelation> assetSoftwareRelationList = new ArrayList<>();
        List<AssetSoftwareRelationResponse> expect = new ArrayList<>();
        Mockito.when(assetSoftwareRelationDao.findQuery(query)).thenReturn(assetSoftwareRelationList);
        Mockito.when(responseConverter.convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class)).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.findListAssetSoftwareRelation(query));
    }

    @Test
    public void findPageAssetSoftwareRelationTest() throws Exception {
        AssetSoftwareRelationQuery query = new AssetSoftwareRelationQuery();
        Integer expect = 1;
        List<AssetSoftwareRelationResponse> expectList = new ArrayList<>();

        PowerMockito.doReturn(expect).when(assetSoftwareRelationService).findCount(Mockito.any());
        PowerMockito.doReturn(expectList).when(assetSoftwareRelationService).findListAssetSoftwareRelation(Mockito.any());
        Assert.assertEquals(expect.intValue(), assetSoftwareRelationService.findPageAssetSoftwareRelation(query).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareRelationService.findPageAssetSoftwareRelation(query).getItems());
    }

    @Test
    public void getSoftByAssetIdTest() {
        List<AssetSoftware> assetSoftwareRelationList = new ArrayList<>();
        List<AssetSoftwareResponse> expect = new ArrayList<>();
        Mockito.when(assetSoftwareRelationDao.getSoftByAssetId(Mockito.anyInt())).thenReturn(assetSoftwareRelationList);
        Mockito.when(responseSoftConverter.convert(assetSoftwareRelationList, AssetSoftwareResponse.class)).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.getSoftByAssetId(1));
    }

    @Test
    public void getSimpleSoftwarePageByAssetIdTest() throws Exception {
        AssetSoftwareRelationQuery query = new AssetSoftwareRelationQuery();
        query.setAssetId("1");
        Integer expect = 1;
        PowerMockito.doReturn(expect).when(assetSoftwareRelationService, "countByAssetId", Mockito.anyInt());
        List<AssetSoftware> assetSoftwareRelationList = new ArrayList<>();
        List<AssetSoftwareResponse> expectList = new ArrayList<>();
        Mockito.when(assetSoftwareRelationDao.getSimpleSoftwareByAssetId(Mockito.any())).thenReturn(assetSoftwareRelationList);
        Mockito.when(responseSoftConverter.convert(assetSoftwareRelationList, AssetSoftwareResponse.class)).thenReturn(expectList);
        Assert.assertEquals(expect.intValue(), assetSoftwareRelationService.getSimpleSoftwarePageByAssetId(query).getTotalRecords());
        Assert.assertEquals(expectList, assetSoftwareRelationService.getSimpleSoftwarePageByAssetId(query).getItems());
    }

    @Test
    public void countAssetBySoftIdTest() {
        Integer expect = 1;
        Mockito.when(assetSoftwareRelationDao.countAssetBySoftId(Mockito.anyInt())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.countAssetBySoftId(1));
    }

    @Test
    public void findOSTest() throws Exception {
        List<Integer> list = new ArrayList<>();
        List<String> expect = new ArrayList<>();
        Mockito.when(loginUser.getAreaIdsOfCurrentUser()).thenReturn(list);
        Mockito.when(assetSoftwareRelationDao.findOS(Mockito.anyList())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.findOS());
    }

    @Test
    public void changeSoftwareStatusTest() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Integer expect = 1;
        Mockito.when(assetSoftwareRelationDao.changeSoftwareStatus(Mockito.anyMap())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.changeSoftwareStatus(map));
    }

    @Test
    public void installArtificialTest() {
        List<AssetSoftwareRelationRequest> list = new ArrayList<>();
        List<AssetSoftwareRelation> assetSoftwareRelation = new ArrayList<>();
        Integer expect = 1;
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(list, AssetSoftwareRelation.class)).thenReturn(assetSoftwareRelation);
        Mockito.when(assetSoftwareRelationDao.installArtificial(Mockito.anyList())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.installArtificial(list));
    }

    @Test
    public void installAautoTest() throws Exception {
        List<AssetSoftwareRelationRequest> list = new ArrayList<>();
        List<AssetSoftwareRelation> assetSoftwareRelation = new ArrayList<>();
        Integer expect = 1;
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(list, AssetSoftwareRelation.class)).thenReturn(assetSoftwareRelation);
        Mockito.when(assetSoftwareRelationDao.installAauto(Mockito.anyList())).thenReturn(expect);
        Assert.assertEquals(expect, assetSoftwareRelationService.installAauto(list));
    }

    @Test
    public void installSoftwareTest1() {
        AssetSoftwareRelationList assetSoftwareRelationList = new AssetSoftwareRelationList();
        AssetInstallRequest request = new AssetInstallRequest();
        request.setConfigureStatus(3);
        request.setAssetId("1");
        List<AssetInstallRequest> list = new ArrayList<>();
        list.add(request);
        assetSoftwareRelationList.setAssetInstallRequestList(list);
        assetSoftwareRelationList.setInstallType(0);
        Mockito.when(transactionTemplate.execute(Mockito.any())).thenReturn(1);
        Mockito.when(assetSoftwareRelationDao.installSoftware(Mockito.anyList())).thenReturn(1);
        assetSoftwareRelationService.installSoftware(assetSoftwareRelationList);
        Mockito.verify(transactionTemplate).execute(Mockito.any());
    }

    @Test
    public void installSoftwareTest2() {
        AssetSoftwareRelationList assetSoftwareRelationList = new AssetSoftwareRelationList();
        AssetInstallRequest request = new AssetInstallRequest();
        request.setConfigureStatus(3);
        request.setAssetId("1");
        List<AssetInstallRequest> list = new ArrayList<>();
        list.add(request);
        assetSoftwareRelationList.setAssetInstallRequestList(list);
        assetSoftwareRelationList.setInstallType(1);
        Mockito.when(transactionTemplate.execute(Mockito.any())).thenReturn(1);
        Mockito.when(assetSoftwareRelationDao.installSoftware(Mockito.anyList())).thenReturn(1);
        assetSoftwareRelationService.installSoftware(assetSoftwareRelationList);
        Mockito.verify(transactionTemplate).execute(Mockito.any());
    }

    @Test
    public void countByAssetIdTest() throws Exception {
        Integer expect = 1;
        Mockito.when(assetSoftwareRelationDao.countSoftwareByAssetId(Mockito.anyInt())).thenReturn(expect);
        Integer actual = Whitebox.invokeMethod(assetSoftwareRelationService, "countByAssetId", 1);
        Assert.assertEquals(expect, actual);
    }

}
