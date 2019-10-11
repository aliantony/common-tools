package com.antiy.asset.service.impl;

import java.util.Arrays;

import com.antiy.asset.entity.Asset;
import com.antiy.common.base.LoginUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.AssetLinkedCountResponse;
import com.antiy.asset.vo.response.IpPortResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
                  LogHandle.class, ZipUtil.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetLinkRelationServiceImplTest {

    private static final Logger                                        logger = LogUtils.get();

    @Mock
    private AssetLinkRelationDao                                       assetLinkRelationDao;
    @InjectMocks
    private AssetLinkRelationServiceImpl                               assetLinkRelationService;
    @Mock
    private AssetNetworkEquipmentDao                                   assetNetworkEquipmentDao;
    @Mock
    private IAssetService                                              assetService;
    @Mock
    private AssetDao                                                   assetDao;
    @Mock
    private RedisUtil                                                  redisUtil;
    @Mock
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation> requestConverter;

    @Test
    public void saveAssetLinkRelation() throws Exception {
        AssetLinkRelationRequest request = new AssetLinkRelationRequest();
        request.setAssetId("1");
        request.setAssetIp("192.168.1.1");
        request.setParentAssetId("123");
        request.setParentAssetIp("192.168.2.3");
        request.setParentAssetPort("20");
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        assetLinkRelation.setAssetId("1");
        assetLinkRelation.setAssetIp("192.168.1.1");
        assetLinkRelation.setParentAssetId("123");
        assetLinkRelation.setParentAssetIp("192.168.2.3");
        assetLinkRelation.setParentAssetPort("20");
        when(requestConverter.convert(request, AssetLinkRelation.class)).thenReturn(assetLinkRelation);
        mockStatic(LoginUserUtil.class);
        LoginUser loginUser = mock(LoginUser.class);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        when(loginUser.getId()).thenReturn(1);
        Asset asset = new Asset();
        asset.setNumber("1");
        when(assetDao.getById(request.getAssetId())).thenReturn(asset);
        assetLinkRelationService.saveAssetLinkRelation(request);
    }

    @Test
    public void checkAssetIp() {
        // 1.校验子资产IP是否可用
        Mockito.when(assetLinkRelationDao.checkIp(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(0);
    }

    @Test
    public void queryAssetLinkedCountPage() throws Exception {
        Mockito.when(assetLinkRelationDao.queryAssetLinkedCount(Mockito.any())).thenReturn(0);
        Mockito.when(assetLinkRelationDao.queryAssetLinkedCountList(Mockito.any()))
            .thenReturn(Arrays.asList(new AssetLinkedCount()));
        PageResult<AssetLinkedCountResponse> result = assetLinkRelationService
            .queryAssetLinkedCountPage(new AssetLinkRelationQuery());
        // Assert.assertNotNull(result);
    }

    @Test
    public void queryLinkedAssetPageByAssetId() throws Exception {
        AssetLinkRelationQuery assetLinkRelationQuery = new AssetLinkRelationQuery();
        assetLinkRelationQuery.setAssetId("1");
        Mockito.when(assetLinkRelationDao.queryLinkedCountAssetByAssetId(assetLinkRelationQuery)).thenReturn(0);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetListByAssetId(assetLinkRelationQuery))
            .thenReturn(Arrays.asList(new AssetLinkRelation()));
        PageResult<AssetLinkRelationResponse> result = assetLinkRelationService
            .queryLinkedAssetPageByAssetId(assetLinkRelationQuery);
        // Assert.assertNotNull(result);
    }

    @Test
    public void queryUseableIp() {
        UseableIpRequest useableIpRequest = new UseableIpRequest();
        useableIpRequest.setAssetId("1");
        Mockito.when(assetLinkRelationDao.queryUseableIp(useableIpRequest.getAssetId()))
            .thenReturn(Arrays.asList(new IpPortResponse()));
    }

    @Test
    public void deleteAssetLinkRelationById() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        Mockito.when(assetLinkRelationDao.deleteById(baseRequest.getStringId())).thenReturn(0);
    }
}
