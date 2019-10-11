package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dto.StatusJumpAssetInfo;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;


/**
 * @author zhangxin
 * @date 2019/9/25
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class, LogUtils.class})
@PowerMockIgnore("javax.management.*")
public class AssetStatusJumpServiceImplTest {

    @InjectMocks
    private static AssetStatusJumpServiceImpl statusChangeFlowProcess = new AssetStatusJumpServiceImpl();

    @Mock
    private AssetDao assetDao;
    @Mock
    private AssetLinkRelationDao assetLinkRelationDao;
    @Mock
    private AssetOperationRecordDao assetOperationRecordDao;

    @Mock
    private AesEncoder aesEncoder;
    @Mock
    private BaseLineClient baseLineClient;
    @Mock
    private ActivityClient activityClient;
    @Mock
    private TransactionTemplate transactionTemplate;

    private static AssetStatusJumpRequest jumpRequest = new AssetStatusJumpRequest();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // static {
    //     jumpRequest.setAgree(Boolean.TRUE);
    //     List<StatusJumpAssetInfo> assetInfoList = new ArrayList<>();
    //     StatusJumpAssetInfo assetInfo = new StatusJumpAssetInfo();
    //     assetInfo.setAssetId("1");
    //     assetInfo.setTaskId("12");
    //     assetInfoList.add(assetInfo);
    //     jumpRequest.setAssetInfoList(assetInfoList);
    //     // jumpRequest.setActivityHandleRequest(new ActivityHandleRequest());
    //     jumpRequest.setAssetFlowEnum(AssetFlowEnum.TO_WAIT_RETIRE);
    // }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(AssetStatusJumpServiceImplTest.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId(8);
        loginUser.setUsername("admin");
        loginUser.setPassword("123456");
        PowerMockito.mockStatic(LoginUserUtil.class);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        // 操作日志
        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));
        Mockito.when(transactionTemplate.execute(Mockito.any())).thenAnswer((Answer) invocation -> {
            Object[] argument = invocation.getArguments();
            TransactionCallback arg= (TransactionCallback) argument[0];
            return arg.doInTransaction(new SimpleTransactionStatus());
        });

        when(activityClient.completeTaskBatch(Mockito.anyList())).thenReturn(ActionResponse.success());
    }

    @After
    public void after() {
        statusChangeFlowProcess = null;
        assetDao = null;
        assetLinkRelationDao = null;
        assetOperationRecordDao = null;
        aesEncoder = null;
        baseLineClient = null;
        activityClient = null;
        transactionTemplate = null;
    }

    /**
     * 登记
     */
    @Test
    public void changeStatus() throws Exception {
        AssetStatusJumpRequest jumpRequest = getJumpRequest(AssetFlowEnum.REGISTER);

        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_REGISTER));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }

    /**
     * 模板实施
     */
    @Test
    public void changeStatus1() throws Exception {
        // 通过
        AssetStatusJumpRequest jumpRequest= getJumpRequest(AssetFlowEnum.TEMPLATE_IMPL);

        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_TEMPLATE_IMPL));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());

        // 拒绝
        jumpRequest= getJumpRequest(AssetFlowEnum.TEMPLATE_IMPL);
        jumpRequest.setAgree(Boolean.FALSE);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_TEMPLATE_IMPL));
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }

    /**
     * 入网
     * @throws Exception
     */
    @Test
    public void changeStatus2() throws Exception {
        AssetStatusJumpRequest jumpRequest= getJumpRequest(AssetFlowEnum.NET_IN);

        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_NET));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }

    /**
     * 待退役
     * @throws Exception
     */
    @Test
    public void changeStatus3() throws Exception {
        AssetStatusJumpRequest jumpRequest = getJumpRequest(AssetFlowEnum.TO_WAIT_RETIRE);
        jumpRequest.setManualStartActivityRequest(new ManualStartActivityRequest());
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.NET_IN));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        when(activityClient.manualStartProcess(Mockito.any(ManualStartActivityRequest.class))).thenReturn(ActionResponse.success());

        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(), statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }

    /**
     * 退役
     * @throws Exception
     */
    @Test
    public void changeStatus5() throws Exception {
        AssetStatusJumpRequest jumpRequest= getJumpRequest(AssetFlowEnum.RETIRE);
        jumpRequest.setManualStartActivityRequest(new ManualStartActivityRequest());
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_RETIRE));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        when(activityClient.manualStartProcess(Mockito.any(ManualStartActivityRequest.class))).thenReturn(ActionResponse.success());
        Assert.assertEquals(RespBasicCode.SUCCESS.getResultCode(),statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());

        // 工作流请求异常
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_RETIRE));
        when(activityClient.completeTaskBatch(Mockito.anyList())).thenReturn(ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION));
        Assert.assertEquals(RespBasicCode.BUSSINESS_EXCETION.getResultCode(),statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }


    /**
     * 数据库操作更新失败
     * @throws Exception
     */
    // @Test
    // public void changeStatusException() throws Exception {
    //     jumpRequest.setAssetFlowEnum(AssetFlowEnum.TEMPLATE_IMPL);
    //     List<Asset> assets = new ArrayList<>();
    //     Asset asset = new Asset();
    //     asset.setId(1);
    //     asset.setAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
    //     assets.add(asset);
    //     when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
    //     when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
    //
    //     Mockito.when(assetLinkRelationDao.deleteByAssetIdList(Mockito.anyList())).thenThrow(new RuntimeException("one"));
    //
    //     expectedException.expect(BusinessException.class);
    //     expectedException.expectMessage("操作失败,请稍后重试");
    //     statusChangeFlowProcess.changeStatus(jumpRequest);
    // }

    /**
     * 数据库操作更新异常 updateAssetStatusWithLock
     * @throws Exception
     */
    @Test
    public void changeStatusException2() throws Exception {
        AssetStatusJumpRequest jumpRequest = getJumpRequest(AssetFlowEnum.RETIRE);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_RETIRE));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenThrow(new RuntimeException(("SQL")));
        when(assetLinkRelationDao.deleteByAssetIdList(Mockito.anyList())).thenThrow(new RuntimeException("one"));

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请刷新页面后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);


        when(assetLinkRelationDao.deleteByAssetIdList(Mockito.anyList())).thenThrow(new RuntimeException(("SQL")));
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请联系运维人员进行解决");
        statusChangeFlowProcess.changeStatus(getJumpRequest(AssetFlowEnum.RETIRE));
    }
    /**
     * 入网数据库操作更新异常 updateAssetBatch
     * @throws Exception
     */
    @Test
    public void changeStatusException2_1() throws Exception {
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_NET));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        when(assetDao.updateAssetBatch(Mockito.anyList())).thenThrow(new RuntimeException(("SQL")));

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请联系运维人员进行解决");
        statusChangeFlowProcess.changeStatus(getJumpRequest(AssetFlowEnum.NET_IN));
    }

    /**
     * 数据库操作更新失败,返回结果!=1 updateAssetStatusWithLock
     * @throws Exception
     */
    @Test
    public void changeStatusException3() throws Exception {
        AssetStatusJumpRequest jumpRequest = getJumpRequest(AssetFlowEnum.RETIRE);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_RETIRE));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(0);

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请刷新页面后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    /**
     * 工作流请求处理失败-非启动退役
     * @throws Exception
     */
    @Test
    public void changeStatusException4() throws Exception {
        // 1失败
        AssetStatusJumpRequest jumpRequest = getJumpRequest(AssetFlowEnum.NET_IN);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.WAIT_NET));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        when(activityClient.completeTaskBatch(Mockito.anyList())).thenReturn(ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION));

        Assert.assertEquals(RespBasicCode.BUSSINESS_EXCETION.getResultCode(), statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());

        // 2异常
        when(activityClient.completeTaskBatch(Mockito.anyList())).thenThrow(new BusinessException("HHH"));
        Assert.assertEquals(RespBasicCode.BUSSINESS_EXCETION.getResultCode(), statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }

    /**
     * 工作流请求处理失败-启动退役
     * @throws Exception
     */
    @Test
    public void changeStatusException5() throws Exception {
        // 1失败
        AssetStatusJumpRequest jumpRequest = getJumpRequest(AssetFlowEnum.TO_WAIT_RETIRE);
        jumpRequest.setManualStartActivityRequest(new ManualStartActivityRequest());
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.NET_IN));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        when(activityClient.manualStartProcess(Mockito.any(ManualStartActivityRequest.class))).thenReturn(ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION));

        Assert.assertEquals(RespBasicCode.BUSSINESS_EXCETION.getResultCode(), statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());

        // 2异常
        jumpRequest = getJumpRequest(AssetFlowEnum.TO_WAIT_RETIRE);
        jumpRequest.setManualStartActivityRequest(new ManualStartActivityRequest());
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.NET_IN));
        when(activityClient.manualStartProcess(Mockito.any(ManualStartActivityRequest.class))).thenThrow(new BusinessException("HHH"));
        Assert.assertEquals(RespBasicCode.BUSSINESS_EXCETION.getResultCode(), statusChangeFlowProcess.changeStatus(jumpRequest).getHead().getCode());
    }



    /**
     * 流程操作与资产状态不匹配
     * @throws Exception
     */
    @Test
    public void changeStatus6() throws Exception {
        AssetStatusJumpRequest jumpRequest= getJumpRequest(AssetFlowEnum.RETIRE);
        jumpRequest.setManualStartActivityRequest(new ManualStartActivityRequest());
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(getAssetInDb(AssetStatusEnum.NET_IN));
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("当前选中的资产已被其他人员操作,请刷新页面后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    public static AssetStatusJumpRequest getJumpRequest(AssetFlowEnum assetFlowEnum) {
        AssetStatusJumpRequest jumpRequest = new AssetStatusJumpRequest();
        jumpRequest.setAgree(Boolean.TRUE);
        List<StatusJumpAssetInfo> assetInfoList = new ArrayList<>();
        StatusJumpAssetInfo assetInfo = new StatusJumpAssetInfo();
        assetInfo.setAssetId("1");
        assetInfo.setTaskId("12");
        assetInfoList.add(assetInfo);
        jumpRequest.setFormData(new HashMap());
        jumpRequest.setAssetInfoList(assetInfoList);
        jumpRequest.setAssetFlowEnum(assetFlowEnum);
        return jumpRequest;
    }

    /**
     * 数据库中已存在的资产信息
     * @return
     */
    private List<Asset> getAssetInDb(AssetStatusEnum assetStatusEnum) {
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(assetStatusEnum.getCode());
        assets.add(asset);
        return assets;
    }
}
