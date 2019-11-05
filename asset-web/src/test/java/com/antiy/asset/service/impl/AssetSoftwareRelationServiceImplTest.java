package com.antiy.asset.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.impl.CommandClientImpl;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.BaselineCategoryModelResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 资产软件关系信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
                  LogHandle.class, ZipUtil.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetSoftwareRelationServiceImplTest {
    @Mock
    private AssetSoftwareRelationDao         assetSoftwareRelationDao;
    @Mock
    private IRedisService                    redisService;
    @Mock
    private AssetSoftwareDao                 assetSoftwareDao;
    @Mock
    private AssetDao                         assetDao;
    @Mock
    private CommandClientImpl                commandClient;
    @Mock
    private AesEncoder                       aesEncoder;
    @Mock
    private BaseLineClient                   baseLineClient;
    @InjectMocks
    private AssetSoftwareRelationServiceImpl softwareRelationService;

    @Test
    public void countAssetBySoftId() {
        softwareRelationService.countAssetBySoftId(anyInt());
    }

    @Test
    public void findOS() throws Exception {
        mockStatic(LoginUserUtil.class);
        LoginUser loginUser = mock(LoginUser.class);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        when(loginUser.getAreaIdsOfCurrentUser()).thenReturn(new ArrayList<>());
        List<String> osList = new ArrayList<>();
        osList.add("1");
        when(assetSoftwareRelationDao.findOS(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()))
            .thenReturn(osList);
        List<BaselineCategoryModelResponse> categoryModelResponseList = new ArrayList<>();
        BaselineCategoryModelResponse baselineCategoryModelResponse = new BaselineCategoryModelResponse();
        baselineCategoryModelResponse.setStringId("1");
        categoryModelResponseList.add(baselineCategoryModelResponse);
        when(redisService.getAllSystemOs()).thenReturn(categoryModelResponseList);
        softwareRelationService.findOS();
    }

    @Test
    public void queryInstalledList() {
        QueryCondition query = new QueryCondition();
        query.setPrimaryKey("1");
        softwareRelationService.queryInstalledList(query);
    }

    @Test
    public void queryInstallableList() {
        InstallQuery installQuery = mock(InstallQuery.class);
        when(installQuery.getIsBatch()).thenReturn(false);
        when(assetSoftwareRelationDao.queryNameListType(installQuery)).thenReturn(1);
        List<AssetSoftwareInstallResponse> installedSoftIds = new ArrayList<>();
        AssetSoftwareInstallResponse assetSoftwareInstallResponse = new AssetSoftwareInstallResponse();
        assetSoftwareInstallResponse.setSoftwareId("1");
        installedSoftIds.add(assetSoftwareInstallResponse);
        when(assetSoftwareRelationDao.queryInstalledList(installQuery)).thenReturn(installedSoftIds);
        softwareRelationService.queryInstallableList(installQuery);

        when(assetSoftwareRelationDao.queryNameListType(installQuery)).thenReturn(2);
        softwareRelationService.queryInstallableList(installQuery);
        String os = "1";
        when(assetSoftwareRelationDao.queryOs(installQuery)).thenReturn(os);
        List<Long> softwareIds = new ArrayList<>();
        softwareIds.add(1L);
        List<String> s = new ArrayList<>();
        s.add("1");
        when(assetSoftwareRelationDao.queryNameListType(installQuery)).thenReturn(1);
        when(assetSoftwareRelationDao.queryInstallableCount(installQuery, 1, softwareIds, s, os)).thenReturn(1);
        softwareRelationService.queryInstallableList(installQuery);

        when(assetSoftwareRelationDao.queryInstallableCount(installQuery, 1, softwareIds, s, os)).thenReturn(0);
        softwareRelationService.queryInstallableList(installQuery);
    }

    @Test
    public void batchRelation() {
        AssetSoftwareReportRequest softwareReportRequest = new AssetSoftwareReportRequest();
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        Map formData = new HashMap();
        formData.put("baselineConfigUserId", "1");
        manualStartActivityRequest.setFormData(formData);
        softwareReportRequest.setManualStartActivityRequest(manualStartActivityRequest);
        List<Long> softIds = new ArrayList<>();
        softIds.add(1L);
        List<String> assetIds = new ArrayList<>();
        assetIds.add("1");
        softwareReportRequest.setAssetId(assetIds);
        softwareReportRequest.setSoftId(softIds);
        mockStatic(LoginUserUtil.class);
        LoginUser loginUser = mock(LoginUser.class);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        when(loginUser.getId()).thenReturn(1);
        when(aesEncoder.decode(
            (String) softwareReportRequest.getManualStartActivityRequest().getFormData().get("baselineConfigUserId"),
            LoginUserUtil.getLoginUser().getUsername())).thenReturn("1");
        when(assetDao.queryInstallType("1")).thenReturn(null);
        try {
            softwareRelationService.batchRelation(softwareReportRequest);
        } catch (BusinessException e) {
        }

        when(assetDao.queryInstallType("1")).thenReturn(1);
        softwareReportRequest.setSoftId(null);
        when(baseLineClient.baselineConfig(any())).thenReturn(new ActionResponse());
        try {
            softwareRelationService.batchRelation(softwareReportRequest);
        } catch (BusinessException e) {
        }

        when(baseLineClient.baselineConfig(any())).thenReturn(new ActionResponse(RespBasicCode.SUCCESS));
        try {
            softwareRelationService.batchRelation(softwareReportRequest);
        } catch (BusinessException e) {
        }
    }
}
