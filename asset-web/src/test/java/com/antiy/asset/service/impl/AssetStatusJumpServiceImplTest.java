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
import com.antiy.asset.vo.request.AssetStatusChangeRequest;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

    static {
        jumpRequest.setAgree(Boolean.TRUE);
        List<StatusJumpAssetInfo> assetInfoList = new ArrayList<>();
        StatusJumpAssetInfo assetInfo = new StatusJumpAssetInfo();
        assetInfo.setAssetId("1");
        assetInfo.setTaskId("12");
        assetInfoList.add(assetInfo);
        jumpRequest.setAssetInfoList(assetInfoList);
        // jumpRequest.setActivityHandleRequest(new ActivityHandleRequest());
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.TO_WAIT_RETIRE);
    }

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
        AssetStatusJumpServiceImpl assetStatusChangeFlowProcess = null;
        AssetDao assetDao = null;
        AssetLinkRelationDao assetLinkRelationDao = null;
        AssetOperationRecordDao assetOperationRecordDao = null;
        AesEncoder aesEncoder = null;
        BaseLineClient baseLineClient = null;
        ActivityClient activityClient = null;
        TransactionTemplate transactionTemplate = null;
    }

    /**
     * 登记
     */
    @Test
    public void changeStatus() throws Exception {
        AssetStatusJumpRequest jumpRequest = new AssetStatusJumpRequest();
        jumpRequest.setAgree(Boolean.TRUE);
        List<StatusJumpAssetInfo> assetInfoList = new ArrayList<>();
        StatusJumpAssetInfo assetInfo = new StatusJumpAssetInfo();
        assetInfo.setAssetId("1");
        assetInfo.setTaskId("12");
        assetInfoList.add(assetInfo);
        jumpRequest.setAssetInfoList(assetInfoList);
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.REGISTER);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    /**
     * 模板实施
     */
    @Test
    public void changeStatus1() throws Exception {
        AssetStatusJumpRequest jumpRequest = new AssetStatusJumpRequest();
        jumpRequest.setAgree(Boolean.TRUE);
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        List<StatusJumpAssetInfo> assetInfoList = new ArrayList<>();
        StatusJumpAssetInfo assetInfo = new StatusJumpAssetInfo();
        assetInfo.setAssetId("1");
        assetInfo.setTaskId("12");
        assetInfoList.add(assetInfo);
        jumpRequest.setAssetInfoList(assetInfoList);
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.TEMPLATE_IMPL);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        statusChangeFlowProcess.changeStatus(jumpRequest);

        // 拒绝
        asset.setAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
        jumpRequest.setAgree(Boolean.FALSE);
        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    /**
     * 入网
     * @throws Exception
     */
    @Test
    public void changeStatus2() throws Exception {
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.NET_IN);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.WAIT_NET.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    /**
     * 待退役
     * @throws Exception
     */
    @Test
    public void changeStatus3() throws Exception {
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.TO_WAIT_RETIRE);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        statusChangeFlowProcess.changeStatus(jumpRequest);
    }


    /**
     * 退役-成功
     * @throws Exception
     */
    @Test
    public void changeStatus5() throws Exception {
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.RETIRE);
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        jumpRequest.setManualStartActivityRequest(manualStartActivityRequest);

        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);
        when(activityClient.manualStartProcess(Mockito.any(ManualStartActivityRequest.class))).thenReturn(ActionResponse.success());
        statusChangeFlowProcess.changeStatus(jumpRequest);

        when(activityClient.manualStartProcess(Mockito.any(ManualStartActivityRequest.class))).thenReturn(ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION));
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请刷新后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);
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
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.RETIRE);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenThrow(new RuntimeException(("SQL")));
        when(assetLinkRelationDao.deleteByAssetIdList(Mockito.anyList())).thenThrow(new RuntimeException("one"));

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请刷新页面后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    /**
     * 数据库操作更新失败 updateAssetStatusWithLock
     * @throws Exception
     */
    @Test
    public void changeStatusException3() throws Exception {
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.RETIRE);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.WAIT_RETIRE.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(0);

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("操作失败,请刷新页面后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);
    }


    /**
     * 流程操作与资产状态不匹配
     * @throws Exception
     */
    @Test
    public void changeStatus6() throws Exception {
        jumpRequest.setAssetFlowEnum(AssetFlowEnum.RETIRE);
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setId(1);
        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        assets.add(asset);
        when(assetDao.findByIds(Mockito.anyList())).thenReturn(assets);
        when(assetDao.updateAssetStatusWithLock(Mockito.any(Asset.class), Mockito.anyInt())).thenReturn(1);

        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("当前选中的资产已被其他人员操作,请刷新页面后重试");
        statusChangeFlowProcess.changeStatus(jumpRequest);
    }

    /**
     * 不予登记
     */
    @Test
    public void noRegist(){
        AssetStatusChangeRequest assetStatusChangeRequest=new AssetStatusChangeRequest();
        assetStatusChangeRequest.setAssetId(new String[]{"1","2"});
        List<Asset> assetList=new ArrayList<>();
        Asset asset=new Asset();
        asset.setId(1);
        asset.setName("chen");

        when(assetDao.getAssetStatusListByIds(any())).thenReturn(null);
    }
}
