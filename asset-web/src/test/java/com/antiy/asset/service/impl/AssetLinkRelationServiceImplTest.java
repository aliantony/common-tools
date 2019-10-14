package com.antiy.asset.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

import java.util.ArrayList;
import java.util.List;

import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.SysArea;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.LoginUser;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
                  LogHandle.class, ZipUtil.class, RedisKeyUtil.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetLinkRelationServiceImplTest {

    @Mock
    private AssetLinkRelationDao                                       assetLinkRelationDao;
    @InjectMocks
    private AssetLinkRelationServiceImpl                               assetLinkRelationService;
    @Mock
    private AssetDao                                                   assetDao;
    @Mock
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation> requestConverter;
    @Mock
    private RedisUtil                                                  redisUtil;

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
        Asset asset = mock(Asset.class);
        when(assetDao.getById(request.getAssetId())).thenReturn(asset);
        when(asset.getNumber()).thenReturn("1");
        try {
            assetLinkRelationService.saveAssetLinkRelation(request);
        } catch (BusinessException e) {
            throw e;
        }

    }

    @Test
    public void checkAssetIp() {
        AssetLinkRelationRequest request = new AssetLinkRelationRequest();
        request.setAssetId("1");
        request.setAssetIp("192.168.1.1");
        request.setParentAssetId("123");
        request.setParentAssetIp("192.168.2.3");
        request.setParentAssetPort("20");
        AssetLinkRelation assetLinkRelation = mock(AssetLinkRelation.class);
        try {
            when(assetLinkRelationDao.checkIp(request.getAssetId(), request.getAssetIp(), request.getAssetPort()))
                .thenReturn(1);
            assetLinkRelationService.checkAssetIp(request, assetLinkRelation);
        } catch (BusinessException e) {
        }
        try {
            when(assetLinkRelationDao.checkIp(request.getAssetId(), request.getAssetIp(), request.getAssetPort()))
                .thenReturn(0);
            when(assetLinkRelationDao.checkIp(request.getParentAssetId(), request.getParentAssetIp(),
                request.getParentAssetPort())).thenReturn(1);
            assetLinkRelationService.checkAssetIp(request, assetLinkRelation);
        } catch (BusinessException e) {
        }
    }

    @Test
    public void queryAssetLinkedCountPage() throws Exception {
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setAssetId("1");
        mockStatic(LoginUserUtil.class);
        LoginUser loginUser = mock(LoginUser.class);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        List<String> area = new ArrayList<>();
        area.add("1");
        when(loginUser.getAreaIdsOfCurrentUser()).thenReturn(area);

        when(assetLinkRelationDao.queryAssetLinkedCountList(query)).thenReturn(new ArrayList<>());
        assetLinkRelationService.queryAssetLinkedCountPage(query);

        List<AssetLinkedCount> assetLinkedCountList = new ArrayList<>();
        AssetLinkedCount assetLinkedCount = new AssetLinkedCount();
        assetLinkedCount.setAreaId("1");
        assetLinkedCountList.add(assetLinkedCount);
        mockStatic(RedisKeyUtil.class);
        String newAreaKey = "1";
        when(RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger("1"))).thenReturn(newAreaKey);
        when(redisUtil.getObject(newAreaKey, SysArea.class)).thenReturn(mock(SysArea.class));
        when(assetLinkRelationDao.queryAssetLinkedCountList(query)).thenReturn(assetLinkedCountList);
        assetLinkRelationService.queryAssetLinkedCountPage(query);

        when(assetLinkRelationDao.queryAssetLinkedCountList(query)).thenReturn(assetLinkedCountList);
        when(redisUtil.getObject(newAreaKey, SysArea.class)).thenThrow(mock(Exception.class));
        assetLinkRelationService.queryAssetLinkedCountPage(query);
    }

    @Test
    public void queryLinkedAssetPageByAssetId() throws Exception {
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setPrimaryKey("1");
        assetLinkRelationService.queryLinkedAssetPageByAssetId(query);
        List<AssetLinkRelation> assetLinkRelationList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        assetLinkRelationList.add(assetLinkRelation);
        when(assetLinkRelationDao.queryLinkedAssetListByAssetId(query)).thenReturn(assetLinkRelationList);
        assetLinkRelationService.queryLinkedAssetPageByAssetId(query);
    }

    @Test
    public void queryUseableIp() {
        UseableIpRequest useableIpRequest = new UseableIpRequest();
        useableIpRequest.setAssetId("1");
        assetLinkRelationService.queryUseableIp(useableIpRequest);
    }

    @Test
    public void deleteAssetLinkRelationById() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        AssetLinkRelation assetLinkRelation = mock(AssetLinkRelation.class);
        when(assetLinkRelationDao.getById(any())).thenReturn(assetLinkRelation);
        when(assetLinkRelation.getAssetId()).thenReturn("1");
        Asset asset = mock(Asset.class);
        when(assetDao.getById(any())).thenReturn(asset);
        when(asset.getNumber()).thenReturn("1");
        assetLinkRelationService.deleteAssetLinkRelationById(baseRequest);
    }

    @Test
    public void queryLinekedRelationPage() {
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        assetLinkRelationService.queryLinekedRelationPage(query);

        List<AssetLinkRelation> assetLinkRelationList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        assetLinkRelationList.add(assetLinkRelation);
        when(assetLinkRelationDao.queryLinekedRelationList(query)).thenReturn(assetLinkRelationList);
        assetLinkRelationService.queryLinekedRelationPage(query);
    }
}
