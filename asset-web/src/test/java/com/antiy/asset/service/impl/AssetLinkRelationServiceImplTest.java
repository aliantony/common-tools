package com.antiy.asset.service.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.AssetLinkedCountResponse;
import com.antiy.asset.vo.response.IpPortResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetLinkRelationServiceImplTest {

    private static final Logger          logger = LogUtils.get();

    @MockBean
    private AssetLinkRelationDao         assetLinkRelationDao;
    @MockBean
    private AssetLinkRelationServiceImpl assetLinkRelationService;
    @MockBean
    private AssetNetworkEquipmentDao     assetNetworkEquipmentDao;
    @MockBean
    private IAssetService                assetService;
    @MockBean
    private AssetDao                     assetDao;
    @MockBean
    private RedisUtil                    redisUtil;

    @Test
    public void saveAssetLinkRelation() throws Exception {
        AssetLinkRelationRequest request = new AssetLinkRelationRequest();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        assetLinkRelationService.checkAssetIp(Mockito.any(), Mockito.any());
        Mockito.when(assetLinkRelationDao.insert(assetLinkRelation)).thenReturn(0);
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
        //Assert.assertNotNull(result);
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
        //Assert.assertNotNull(result);
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
