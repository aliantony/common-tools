package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetInstallTemplateDao;
import com.antiy.asset.entity.AssetInstallTemplate;
import com.antiy.asset.entity.PatchInfo;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.ZipUtil;
import com.antiy.asset.vo.enums.AssetInstallTemplateStatusEnum;
import com.antiy.asset.vo.query.AssetInstallTemplateQuery;
import com.antiy.asset.vo.query.PrimaryKeyQuery;
import com.antiy.asset.vo.request.AssetInstallTemplateCheckRequest;
import com.antiy.asset.vo.request.AssetInstallTemplateRequest;
import com.antiy.asset.vo.request.BatchQueryRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.LicenseUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ExcelUtils.class, RequestContextHolder.class, LoginUserUtil.class, LicenseUtil.class, LogUtils.class,
        LogHandle.class, ZipUtil.class, RedisKeyUtil.class, BeanConvert.class})
@PowerMockIgnore({"javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*"})
@SpringBootTest
public class AssetInstallTemplateServiceImplTest {
    @MockBean
    private AssetInstallTemplateDao assetInstallTemplateDao;
    @InjectMocks
    private AssetInstallTemplateServiceImpl assetInstallTemplateServiceImpl;
    @Mock
    private BaseConverter<AssetInstallTemplateRequest, AssetInstallTemplate> requestConverter;
    @MockBean
    public IAssetHardSoftLibService iAssetHardSoftLibService;
    @MockBean
    private AesEncoder aesEncoder;
    @Mock
    private BaseConverter<AssetInstallTemplate, AssetInstallTemplateResponse> responseConverter;
    @MockBean
    private RedisUtil redisUtil;
    @Mock
    private BaseConverter<SysUser, AssetSysUserResponse> userConverter;
    @MockBean
    private ActivityClient activityClient;
    @Mock
    private IBaseDao<AssetInstallTemplate> baseDao;

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
        Mockito.doAnswer(invocation -> {
            List<AssetInstallTemplateOsResponse> osResponseList = new ArrayList<>();
            AssetInstallTemplateOsResponse osResponse = new AssetInstallTemplateOsResponse();
            osResponse.setOsName("windows7");
            osResponseList.add(osResponse);
            return osResponseList;
        }).when(assetInstallTemplateDao).queryOs(Mockito.anyString());
        Mockito.doAnswer(invocation -> "aaa").when(aesEncoder).decode(Mockito.anyString(), Mockito.anyString());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertTemplateCheckInfo(Mockito.any());
        PowerMockito.mockStatic(RedisKeyUtil.class, invocation -> "aa");
    }

    @Test
    public void saveAssetInstallTemplateTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        Integer expecet = 1;
        Mockito.doAnswer(invocation -> {
            AssetInstallTemplate installTemplate = new AssetInstallTemplate();
            installTemplate.setId(expecet);
            return installTemplate;
        }).when(requestConverter).convert(Mockito.eq(request), Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insert(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.saveAssetInstallTemplate(request)).isEqualTo(expecet.toString());
    }


    @Test
    public void queryTemplateByAssetId() throws Exception {
        AssetTemplateRelationResponse assetTemplateRelationResponse = new AssetTemplateRelationResponse();
        assetTemplateRelationResponse.setDescription("csdf");
        assetTemplateRelationResponse.setName("che");
        assetTemplateRelationResponse.setPatchNum(100);
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
        Assertions.assertThat(assetInstallTemplateServiceImpl.querySoftPage(query).getItems()).isEmpty();

        when(assetInstallTemplateDao.querySoftCount(query)).thenReturn(1);
        when(assetInstallTemplateDao.querySoftList(query)).thenReturn(new ArrayList<>());
        List<AssetHardSoftLibResponse> expect = new ArrayList<>();
        PowerMockito.mockStatic(BeanConvert.class, invocation -> expect);
        Assertions.assertThat(assetInstallTemplateServiceImpl.querySoftPage(query).getItems()).isEqualTo(expect);
    }

    @Test
    public void queryPatchPage() {
        PrimaryKeyQuery query = new PrimaryKeyQuery();
        when(assetInstallTemplateDao.queryPatchCount(query)).thenReturn(0);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPatchPage(query).getItems()).isEmpty();

        when(assetInstallTemplateDao.queryPatchCount(query)).thenReturn(1);
        when(assetInstallTemplateDao.queryPatchList(query)).thenReturn(new ArrayList<>());
        List<PatchInfoResponse> expect = new ArrayList<>();
        PowerMockito.mockStatic(BeanConvert.class, invocation -> expect);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPatchPage(query).getItems()).isEqualTo(expect);
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
        Assert.assertEquals(Integer.valueOf(0), assetInstallTemplateServiceImpl.queryNumberCode(null));
        Integer expect = 1;
        Mockito.doAnswer(invocation -> expect).when(assetInstallTemplateDao).queryNumberCode(Mockito.anyString());
        Assert.assertEquals(expect, assetInstallTemplateServiceImpl.queryNumberCode("xxa"));
    }


    @Test
    public void updateAssetInstallTemplateExceptionTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        request.setIsUpdateStatus(null);
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
        request.setIsUpdateStatus(0);
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(2);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        request.setUpdateStatus(null);
        assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("状态非法修改");

        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(3);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        request.setUpdateStatus(3);
        assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("状态非法修改");

        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(4);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        request.setUpdateStatus(4);
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
     * 模板编辑异常测试
     */
    @Test
    public void updateAssetInstallTemplateErrorTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        Set<String> set = new HashSet<>();
        request.setSoftBussinessIds(set);
        request.setPatchIds(set);
        request.setIsUpdateStatus(1);
        AssetInstallTemplate installTemplate = new AssetInstallTemplate();
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(2);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        Mockito.doAnswer(invocation -> installTemplate).when(requestConverter).convert(Mockito.eq(request), Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("请至少选择一个软件或者一个补丁");

        set.add("1");
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("请选择下一步执行人");

        request.setNextExecutor(set);
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).update(Mockito.any());
        Mockito.doAnswer(invocation -> set.stream().map(v -> {
            AssetHardSoftLibResponse response = new AssetHardSoftLibResponse();
            response.setBusinessId(v);
            return response;
        }).collect(Collectors.toList())).when(iAssetHardSoftLibService).querySoftsRelations(Mockito.anyString());
        Mockito.doAnswer(invocation -> set).when(assetInstallTemplateDao).queryPatchIds(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).updateStatus(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("非流程任务发起人不能编辑");

        request.setTaskId("1");
        set.add("1");
        Set<String> set1 = new HashSet<>();
        set1.add("1");
        request.setNextExecutor(set1);
        Mockito.doAnswer(invocation -> null).when(activityClient).completeTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");
        set.add("1");
        Mockito.doAnswer(invocation -> ActionResponse.fail(RespBasicCode.PARAMETER_ERROR)).when(activityClient).completeTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");
    }

    @Test
    public void updateAssetInstallTemplateTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        request.setIsUpdateStatus(1);
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("3");
        request.setNextExecutor(set);
        request.setSoftBussinessIds(set);
        request.setPatchIds(set);
        request.setTaskId("1");
        AssetInstallTemplate installTemplate = new AssetInstallTemplate();
        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(2);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        Mockito.doAnswer(invocation -> installTemplate).when(requestConverter).convert(Mockito.eq(request), Mockito.any());

        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).update(Mockito.any());
        Mockito.doAnswer(invocation -> {
            List<AssetHardSoftLibResponse> responses = new ArrayList<>();
            AssetHardSoftLibResponse response = new AssetHardSoftLibResponse();
            response.setBusinessId("1");
            responses.add(response);
            AssetHardSoftLibResponse response2 = new AssetHardSoftLibResponse();
            response2.setBusinessId("2");
            responses.add(response2);
            return responses;
        }).when(iAssetHardSoftLibService).querySoftsRelations(Mockito.anyString());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).deleteBatchSoft(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertBatchSoft(Mockito.any());
        Mockito.doAnswer(invocation -> {
            Set<String> prePatchIds = new HashSet<>();
            prePatchIds.add("1");
            prePatchIds.add("2");
            return prePatchIds;
        }).when(assetInstallTemplateDao).queryPatchIds(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).deleteBatchPatch(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertBatchPatch(Mockito.any());
        ActionResponse expect = ActionResponse.success();
        Mockito.doAnswer(invocation -> expect).when(activityClient).completeTask(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).updateStatus(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request)).isEqualTo(expect);

        Mockito.doAnswer(invocation -> {
            installTemplate.setCurrentStatus(3);
            return installTemplate;
        }).when(assetInstallTemplateDao).getById(Mockito.anyString());
        Mockito.doAnswer(invocation -> expect).when(activityClient).manualStartProcess(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request)).isEqualTo(expect);

        Mockito.doAnswer(invocation -> null).when(activityClient).manualStartProcess(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

        Mockito.doAnswer(invocation -> ActionResponse.fail(RespBasicCode.PARAMETER_ERROR)).when(activityClient).manualStartProcess(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

        Mockito.doAnswer(invocation -> 0).when(assetInstallTemplateDao).updateStatus(Mockito.any());
        request.setSoftBussinessIds(new HashSet<>());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request).getHead().getCode()).isEqualTo(RespBasicCode.BUSSINESS_EXCETION.getResultCode());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request).getHead().getResult()).isEqualTo(RespBasicCode.BUSSINESS_EXCETION.getResultDes());
        Assertions.assertThat(assetInstallTemplateServiceImpl.updateAssetInstallTemplate(request).getBody()).isEqualTo("编辑失败");
    }

    /**
     * 查找模板列表（不过滤黑白名单）
     *
     * @throws Exception
     */
    @Test
    public void queryPageAssetInstallTemplateTest() throws Exception {
        AssetInstallTemplateQuery query = new AssetInstallTemplateQuery();
        Mockito.doAnswer(invocation -> null).when(baseDao).findCount(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getTotalRecords()).isEqualTo(0);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEmpty();

        Mockito.doAnswer(invocation -> 0).when(baseDao).findCount(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getTotalRecords()).isEqualTo(0);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEmpty();

        query.setBaselineId("1");
        Mockito.doAnswer(invocation -> 0).when(assetInstallTemplateDao).queryBaselineTemplateType(Mockito.any());
        List<AssetInstallTemplateResponse> expect = new ArrayList<>();
        Mockito.doAnswer(invocation -> {
            AssetInstallTemplateResponse response = new AssetInstallTemplateResponse();
            response.setStringId("1");
            expect.add(response);
            return expect;
        }).when(assetInstallTemplateDao).queryTemplateInfo(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(baseDao).findCount(Mockito.any());
        Mockito.doAnswer(invocation -> ActionResponse.success(null)).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getTotalRecords()).isEqualTo(1);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEqualTo(expect);


        Mockito.doAnswer(invocation -> {
            List<WaitingTaskReponse> waitingTaskReponseList = new ArrayList<>();
            return ActionResponse.success(waitingTaskReponseList);
        }).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getTotalRecords()).isEqualTo(1);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEqualTo(expect);

        Mockito.doAnswer(invocation -> {
            List<WaitingTaskReponse> waitingTaskReponseList = new ArrayList<>();
            WaitingTaskReponse reponse = new WaitingTaskReponse();
            reponse.setBusinessId("1");
            waitingTaskReponseList.add(reponse);
            return ActionResponse.success(waitingTaskReponseList);
        }).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getTotalRecords()).isEqualTo(1);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEqualTo(expect);

        Mockito.doAnswer(invocation -> {
            List<WaitingTaskReponse> waitingTaskReponseList = new ArrayList<>();
            WaitingTaskReponse reponse = new WaitingTaskReponse();
            reponse.setBusinessId("1");
            WaitingTaskReponse reponse2 = new WaitingTaskReponse();
            reponse2.setBusinessId("1");
            waitingTaskReponseList.add(reponse);
            waitingTaskReponseList.add(reponse2);
            return ActionResponse.success(waitingTaskReponseList);
        }).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query))
                .isInstanceOf(BusinessException.class)
                .hasMessage("同一代办任务存在多条，脏数据未处理");

    }

    /**
     * 查找模板列表（过滤黑名单）
     */
    @Test
    public void queryPageAssetInstallTemplateTest2() throws Exception {
        AssetInstallTemplateQuery query = new AssetInstallTemplateQuery();
        query.setBaselineId("1");
        List<AssetInstallTemplateResponse> expect = new ArrayList<>();
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).queryBaselineTemplateType(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).CountFilterBlackItemTemplate(Mockito.any());
        Mockito.doAnswer(invocation -> expect).when(assetInstallTemplateDao).FilterBlackItemTemplate(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEqualTo(expect);
    }

    /**
     * 查找模板列表（查找白名单）
     */
    @Test
    public void queryPageAssetInstallTemplateTest3() throws Exception {
        AssetInstallTemplateQuery query = new AssetInstallTemplateQuery();
        query.setBaselineId("1");
        List<AssetInstallTemplateResponse> expect = new ArrayList<>();
        Mockito.doAnswer(invocation -> 2).when(assetInstallTemplateDao).queryBaselineTemplateType(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).CountWhiteItemTemplate(Mockito.any());
        Mockito.doAnswer(invocation -> expect).when(assetInstallTemplateDao).findWhiteItemTemplate(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPageAssetInstallTemplate(query).getItems()).isEqualTo(expect);
    }

    @Test
    public void queryAssetInstallTemplateByIdTest() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        AssetInstallTemplate template = new AssetInstallTemplate();
        AssetInstallTemplateResponse expect = new AssetInstallTemplateResponse();
        Mockito.doAnswer(invocation -> expect).when(responseConverter).convert(Mockito.eq(template), Mockito.any());
        Mockito.doAnswer(invocation -> {
            template.setExecutor("all");
            return template;
        }).when(assetInstallTemplateDao).getById(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryAssetInstallTemplateById(queryCondition)).isEqualTo(expect);

        Mockito.doAnswer(invocation -> {
            template.setExecutor("1");
            return template;
        }).when(assetInstallTemplateDao).getById(Mockito.any());
        SysUser user = new SysUser();
        Mockito.doThrow(new Exception("覆盖异常")).doAnswer(invocation -> user).when(redisUtil).getObject(Mockito.anyString(), Mockito.any());
        Mockito.doAnswer(invocation -> "****").when(aesEncoder).encode(Mockito.anyString(), Mockito.anyString());
        AssetSysUserResponse userResponseExpect = new AssetSysUserResponse();
        Mockito.doAnswer(invocation -> userResponseExpect).when(userConverter).convert(Mockito.eq(user), Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryAssetInstallTemplateById(queryCondition)).isEqualTo(expect);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryAssetInstallTemplateById(queryCondition).getExecutor().get(0)).isEqualTo(userResponseExpect);
    }

    @Test
    public void deleteAssetInstallTemplateByIdTest() {
        BatchQueryRequest request = new BatchQueryRequest();
        List<String> ids = new ArrayList<>();
        ids.add("1");
        List<String> processInstanceIds = new ArrayList<>();
        processInstanceIds.add("");
        processInstanceIds.add("");
        request.setIds(ids);
        request.setProcessInstanceIds(processInstanceIds);
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.deleteAssetInstallTemplateById(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("请确保参数正确");

        processInstanceIds.remove(1);
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).batchDeleteTemplate(Mockito.anyList(), Mockito.anyLong(), Mockito.anyString());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.deleteAssetInstallTemplateById(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("请确保拒绝模板存在代办任务");

        processInstanceIds.set(0, "1");
        Mockito.doAnswer(invocation -> ActionResponse.fail(RespBasicCode.PARAMETER_ERROR)).when(activityClient).deleteProcessInstance(Mockito.anyList());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.deleteAssetInstallTemplateById(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");
        Mockito.doAnswer(invocation -> null).when(activityClient).deleteProcessInstance(Mockito.anyList());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.deleteAssetInstallTemplateById(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

        ids.add("2");
        processInstanceIds.add("");
        Mockito.doAnswer(invocation -> ActionResponse.success()).when(activityClient).deleteProcessInstance(Mockito.anyList());
        Assertions.assertThat(assetInstallTemplateServiceImpl.deleteAssetInstallTemplateById(request)).isEqualTo("已经删除状态为拒绝的模板");
        Mockito.doAnswer(invocation -> {
            processInstanceIds.set(1, "2");
            return 2;
        }).when(assetInstallTemplateDao).batchDeleteTemplate(Mockito.anyList(), Mockito.anyLong(), Mockito.anyString());
        Assertions.assertThat(assetInstallTemplateServiceImpl.deleteAssetInstallTemplateById(request)).isEqualTo("删除成功");
    }

    @Test
    public void queryPatchsTest() {
        List<PatchInfo> list = new ArrayList<>();
        Mockito.doAnswer(invocation -> list).when(assetInstallTemplateDao).queryPatchRelations(Mockito.anyString());
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPatchs("12")).isEmpty();

        list.add(new PatchInfo());
        List<PatchInfoResponse> expect = new ArrayList<>();
        PowerMockito.mockStatic(BeanConvert.class, invocation -> expect);
        Assertions.assertThat(assetInstallTemplateServiceImpl.queryPatchs("12")).isEqualTo(expect);
    }

    @Test
    public void submitTemplateInfoErrorTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        Set<String> set = new HashSet<>();
        request.setSoftBussinessIds(set);
        request.setPatchIds(set);
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.submitTemplateInfo(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("请至少选择一个软件或者一个补丁");

        request.setSoftBussinessIds(new HashSet<>());
        set.add("1");
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).queryNumberCode(Mockito.anyString());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.submitTemplateInfo(request))
                .isInstanceOf(RequestParamValidateException.class)
                .hasMessage("模板编号已经存在");
    }

    @Test
    public void submitTemplateInfoTest() throws Exception {
        AssetInstallTemplateRequest request = getRequest();
        Set<String> set = new HashSet<>();
        set.add("1");
        request.setSoftBussinessIds(set);
        set.add("2");
        request.setNextExecutor(set);
        request.setPatchIds(set);
        request.setCreateUser("186");
        request.setStringId(null);
        Mockito.doAnswer(invocation -> 0).when(assetInstallTemplateDao).queryNumberCode(Mockito.anyString());
        Mockito.doAnswer(invocation -> new AssetInstallTemplate()).when(requestConverter).convert(Mockito.eq(request), Mockito.any());
        Mockito.doAnswer(invocation -> {
            List<AssetInstallTemplateOsResponse> list = new ArrayList<>();
            AssetInstallTemplateOsResponse response = new AssetInstallTemplateOsResponse();
            list.add(response);
            return list;
        }).when(assetInstallTemplateDao).queryOs(Mockito.anyString());
        ActionResponse expect = ActionResponse.success();
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insert(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertBatchPatch(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertBatchSoft(Mockito.any());
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertTemplateCheckInfo(Mockito.any());
        Mockito.doAnswer(invocation -> expect).when(activityClient).manualStartProcess(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.submitTemplateInfo(request)).isEqualTo(expect);

        request.setStringId("11");
        request.setSoftBussinessIds(new HashSet<>());
        Assertions.assertThat(assetInstallTemplateServiceImpl.submitTemplateInfo(request)).isEqualTo(expect);

        request.setPatchIds(new HashSet<>());
        request.setSoftBussinessIds(set);
        Assertions.assertThat(assetInstallTemplateServiceImpl.submitTemplateInfo(request)).isEqualTo(expect);

    }

    @Test
    public void checkTemplateTest() throws Exception {
        AssetInstallTemplateCheckRequest request = new AssetInstallTemplateCheckRequest();
        request.setStringId("1");
        request.setResult(0);
        List<WaitingTaskReponse> list = new ArrayList<>();
        Mockito.doAnswer(invocation -> null).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.checkTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

        Mockito.doAnswer(invocation -> ActionResponse.fail(RespBasicCode.PARAMETER_ERROR)).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.checkTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

        Mockito.doAnswer(invocation -> ActionResponse.success(list)).when(activityClient).queryAllWaitingTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.checkTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("该项操作暂无权限，请联系管理员");

        WaitingTaskReponse reponse = new WaitingTaskReponse();
        reponse.setBusinessId("1");
        list.add(reponse);
        Mockito.doAnswer(invocation -> 0).when(assetInstallTemplateDao).insertTemplateCheckInfo(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.checkTemplate(request)).isEqualTo("审核结果提交失败");

        Mockito.doAnswer(invocation -> null).when(assetInstallTemplateDao).insertTemplateCheckInfo(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.checkTemplate(request)).isEqualTo("审核结果提交失败");

        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).insertTemplateCheckInfo(Mockito.any());
        Mockito.doAnswer(invocation -> null).when(assetInstallTemplateDao).update(Mockito.any());
        AssetInstallTemplate template = new AssetInstallTemplate();
        template.setExecutor("1");
        Mockito.doAnswer(invocation -> template).when(assetInstallTemplateDao).getById(Mockito.any());
        AssetInstallTemplateResponse assetInstallTemplateResponse = new AssetInstallTemplateResponse();
        Mockito.doAnswer(invocation -> assetInstallTemplateResponse).when(responseConverter).convert(Mockito.eq(template), Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.checkTemplate(request)).isEqualTo("审核结果提交失败");

        Mockito.doAnswer(invocation -> 0).when(assetInstallTemplateDao).update(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.checkTemplate(request)).isEqualTo("审核结果提交失败");

        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).update(Mockito.any());
        Mockito.doAnswer(invocation -> ActionResponse.success()).when(activityClient).completeTask(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.checkTemplate(request)).isEqualTo("审核结果提交成功");

        Mockito.doAnswer(invocation -> ActionResponse.fail(RespBasicCode.PARAMETER_ERROR)).when(activityClient).completeTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.checkTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

        Mockito.doAnswer(invocation -> null).when(activityClient).completeTask(Mockito.any());
        Assertions.assertThatThrownBy(() -> assetInstallTemplateServiceImpl.checkTemplate(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("调用流程引擎出错");

    }

    @Test
    public void deleteBatchPatchTest() {
        AssetInstallTemplateRequest request = new AssetInstallTemplateRequest();
        Assertions.assertThat(assetInstallTemplateServiceImpl.deleteBatchPatch(request)).isEqualTo(0);

        Set<String> patchCode = new HashSet<>();
        patchCode.add("APL-2019-14983");
        request.setPatchIds(patchCode);
        Mockito.doAnswer(invocation -> 1).when(assetInstallTemplateDao).deleteBatchPatchByPatchCode(Mockito.any());
        Assertions.assertThat(assetInstallTemplateServiceImpl.deleteBatchPatch(request)).isEqualTo(1);
    }

    private AssetInstallTemplateRequest getRequest() {
        AssetInstallTemplateRequest request = new AssetInstallTemplateRequest();
        request.setIsUpdateStatus(0);
        request.setStringId("111");
        //设置禁用状态
        request.setUpdateStatus(4);
        request.setOperationSystem(101011L);
        request.setName("windows7");
        request.setNumberCode("xx1");
        request.setNextExecutor(new HashSet<>());
        return request;
    }
}