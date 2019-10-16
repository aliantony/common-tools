package com.antiy.asset.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.vo.enums.AssetInstallTemplateStatusEnum;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.AssetInstallTemplateOsResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.response.AssetTemplateRelationResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
        LogHandle.class, ZipUtil.class, RedisKeyUtil.class})
@PowerMockIgnore({"javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*"})
public class AssetInstallTemplateServiceImplTest {
    @MockBean
    private AssetInstallTemplateDao assetInstallTemplateDao;
    @InjectMocks
    private AssetInstallTemplateServiceImpl assetInstallTemplateServiceImpl;
    @MockBean
    private BaseConverter<AssetInstallTemplateRequest, AssetInstallTemplate> requestConverter;
    @MockBean
    public IAssetHardSoftLibService iAssetHardSoftLibService;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(LoginUserUtil.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                LoginUser loginUser = new LoginUser();
                loginUser.setName("张三");
                loginUser.setId(11);
                return loginUser;
            }
        });
        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any());
        PowerMockito.doNothing().when(LogUtils.class, "info", Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void queryTemplateByAssetId() throws Exception {
        AssetTemplateRelationResponse assetTemplateRelationResponse = new AssetTemplateRelationResponse();
        assetTemplateRelationResponse.setDescription("csdf");
        assetTemplateRelationResponse.setName("che");
        assetTemplateRelationResponse.setPatchCount(100);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        when(assetInstallTemplateDao.queryTemplateById(any())).thenReturn(assetTemplateRelationResponse);
        AssetTemplateRelationResponse assetTemplateRelationResponse1 = assetInstallTemplateServiceImpl
                .queryTemplateByAssetId(queryCondition);
        Assert.assertNotNull(assetTemplateRelationResponse);
    }

    @Test
    public void querySoftPage() {
        PrimaryKeyQuery query = new PrimaryKeyQuery();
        when(assetInstallTemplateDao.querySoftCount(query)).thenReturn(0);
        assetInstallTemplateServiceImpl.querySoftPage(query);

        when(assetInstallTemplateDao.querySoftCount(query)).thenReturn(1);
        assetInstallTemplateServiceImpl.querySoftPage(query);
    }

    @Test
    public void queryPatchPage() {
        PrimaryKeyQuery query = new PrimaryKeyQuery();
        when(assetInstallTemplateDao.queryPatchCount(query)).thenReturn(0);
        assetInstallTemplateServiceImpl.queryPatchPage(query);

        when(assetInstallTemplateDao.queryPatchCount(query)).thenReturn(1);
        assetInstallTemplateServiceImpl.queryPatchPage(query);
    }

    @Test
    public void queryTemplateOsTest() {
        List<AssetInstallTemplateOsResponse> expect = new ArrayList<>();
        Mockito.doAnswer(invocation -> expect).when(assetInstallTemplateDao).queryTemplateOs();
        Assert.assertEquals(expect, assetInstallTemplateServiceImpl.queryTemplateOs());
    }

    @Test
    public void queryTemplateStatusTest() {
        Integer expectCode = 1;
        Mockito.doAnswer(invocation -> Arrays.asList(expectCode)).when(assetInstallTemplateDao).queryTemplateStatus();
        Assert.assertEquals(expectCode, assetInstallTemplateServiceImpl.queryTemplateStatus().get(0).getStatusCode());
        Assert.assertEquals(AssetInstallTemplateStatusEnum.getEnumByCode(1).getStatus(), assetInstallTemplateServiceImpl.queryTemplateStatus().get(0).getStatusName());
    }

    @Test
    public void queryNumberCodeTest() {
        Assert.assertEquals(Integer.valueOf(0), assetInstallTemplateServiceImpl.queryNumberCode(""));
        Integer expect = 1;
        Mockito.doAnswer(invocation -> expect).when(assetInstallTemplateDao).queryNumberCode(Mockito.anyString());
        Assert.assertEquals(expect, assetInstallTemplateServiceImpl.queryNumberCode("xxa"));
    }

    @Test
    public void queryOsTest() {
        List<AssetInstallTemplateOsResponse> expect = new ArrayList<>();
        Mockito.doAnswer(invocation -> expect).when(assetInstallTemplateDao).queryOs(Mockito.anyString());
        Assert.assertEquals(expect, assetInstallTemplateServiceImpl.queryOs("xxa"));
    }

    @Test
    public void updateAssetInstallTemplateExceptionTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        AssetInstallTemplate installTemplate = new AssetInstallTemplate();
        //断言 非法操作
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(1);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());

        assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("非法操作");

        //断言 状态非法修改
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(2);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());

        assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("状态非法修改");
    }

    /**
     * 模板启用禁用测试
     *
     * @throws Exception
     */
    @Test
    public void updateAssetInstallTemplateEnableTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        request.setUpdateStatus(3);
        AssetInstallTemplate installTemplate = new AssetInstallTemplate();
        //启用
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(4);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        Mockito.doAnswer(invocation -> installTemplate).when(requestConverter).convert(Mockito.eq(request), Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).updateStatus(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request).getBody()).isEqualTo("更新状态成功");

        //禁用
        request.setUpdateStatus(4);
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(3);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        Mockito.doAnswer(invocation -> 0).when(assetInstallTemplateDao).updateStatus(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request).getBody()).isEqualTo("更新状态失败");
    }

    /**
     * 模板编辑测试
     */
    @Test
    @Ignore
    public void updateAssetInstallTemplateTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        request.setIsUpdateStatus(1);
        AssetInstallTemplate installTemplate = new AssetInstallTemplate();
        //启用
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(2);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        Mockito.doAnswer(invocation -> installTemplate).when(requestConverter).convert(Mockito.eq(request), Mockito.any());
        List<AssetInstallTemplateOsResponse> osResponseList = new ArrayList<>();
        AssetInstallTemplateOsResponse osResponse = new AssetInstallTemplateOsResponse();
        osResponse.setOsName("windows7");
        osResponseList.add(osResponse);
        Mockito.doAnswer(invocation -> osResponseList).when(assetInstallTemplateDao).queryOs(Mockito.anyString());
        Mockito.doAnswer(invocation ->1).when(assetInstallTemplateDao).update(Mockito.any());
        Mockito.doAnswer(invocation -> {
            List<AssetHardSoftLibResponse> responses = new ArrayList<>();
            AssetHardSoftLibResponse response = new AssetHardSoftLibResponse();
            response.setBusinessId("123");
            responses.add(response);
            return responses;
        }).when(iAssetHardSoftLibService).querySoftsRelations(Mockito.anyString());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request).getBody()).isEqualTo("更新状态失败");
    }

    private AssetInstallTemplateRequest getRequest() {
        AssetInstallTemplateRequest request = new AssetInstallTemplateRequest();
        request.setIsUpdateStatus(0);
        request.setStringId("111");
        //设置禁用状态
        request.setUpdateStatus(4);
        request.setOperationSystem(101011L);
        return request;
    }
}