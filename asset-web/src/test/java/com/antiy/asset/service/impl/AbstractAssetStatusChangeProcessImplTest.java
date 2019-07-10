package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.request.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.RequestParamValidateException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractAssetStatusChangeProcessImplTest {

    @InjectMocks
    private AbstractAssetStatusChangeProcessImpl assetStatusChangeProcessService = Mockito
        .mock(AbstractAssetStatusChangeProcessImpl.class, Mockito.CALLS_REAL_METHODS);
    @Mock
    private AssetOperationRecordDao              assetOperationRecordDao;
    @Mock
    private SchemeDao                            schemeDao;
    @Mock
    private AssetDao                             assetDao;
    @Mock
    private AssetSoftwareDao                     assetSoftwareDao;
    @Mock
    private BaseConverter<SchemeRequest, Scheme> schemeRequestToSchemeConverter;
    @Mock
    private AesEncoder                           aesEncoder;
    @Mock
    private ActivityClient                       activityClient;
    @Mock
    private WorkOrderClient                      workOrderClient;
    @Mock
    private AssetLinkRelationDao                 assetLinkRelationDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        LoginUtil.generateDefaultLoginUser();
        Mockito.when(activityClient.completeTask(Mockito.any())).thenReturn(ActionResponse.success());
        Mockito.when(workOrderClient.createWorkOrder(Mockito.any())).thenReturn(ActionResponse.success());
        Mockito.when(activityClient.manualStartProcess(Mockito.any())).thenReturn(ActionResponse.success());
    }

    @Test
    public void changeStatusTest() throws Exception {
        SchemeRequest schemeRequest = new SchemeRequest();
        schemeRequest.setId("2");
        schemeRequest.setPutintoUserId("2");

        ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
        activityHandleRequest.setTaskId("1");

        Class<WorkOrderVO> workOrderVOClass = WorkOrderVO.class;
        Constructor<WorkOrderVO> declaredConstructor = workOrderVOClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        WorkOrderVO workOrderVO = declaredConstructor.newInstance();
        workOrderVO.setName("工单");
        workOrderVO.setContent("创建工单");
        workOrderVO.setEndTime("1550000000000");
        workOrderVO.setExecuteUserId("1");
        workOrderVO.setOrderSource(1);
        workOrderVO.setOrderType(1);
        workOrderVO.setRelatedSourceId("1");
        workOrderVO.setStartTime("1550000000000");
        workOrderVO.setWorkLevel(1);

        AssetStatusReqeust assetStatusReqeust = new AssetStatusReqeust();
        assetStatusReqeust.setSchemeRequest(schemeRequest);
        assetStatusReqeust.setAssetId("1");
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_CHECK);
        assetStatusReqeust.setSoftwareStatusEnum(SoftwareStatusEnum.ALLOW_INSTALL);
        assetStatusReqeust.setAgree(true);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_REGISTER);
        assetStatusReqeust.setActivityHandleRequest(activityHandleRequest);
        assetStatusReqeust.setManualStartActivityRequest(new ManualStartActivityRequest());
        assetStatusReqeust.setWorkOrderVO(workOrderVO);

        Scheme scheme = new Scheme();
        scheme.setAssetId("1");
        scheme.setCreateUser(1);
        scheme.setType(1);
        scheme.setContent("Schema content!");
        scheme.setFileInfo("[{\"id\":355,\"fileName\":\"新建文本文档.rar\"}]");

        Asset asset = new Asset();
        asset.setId(1);
        AssetSoftware software = new AssetSoftware();
        software.setSoftwareStatus(1);

        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setAreaId("1");
        assetOperationRecord.setId(1);

        Mockito.when(schemeRequestToSchemeConverter.convert(schemeRequest, Scheme.class)).thenReturn(scheme);
        Mockito.when(schemeDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetSoftwareDao.getById(Mockito.any())).thenReturn(software);
        Mockito.when(assetDao.getById(Mockito.any())).thenReturn(asset);
        Mockito.when(assetOperationRecordDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(aesEncoder.decode(Mockito.any(), Mockito.any())).thenReturn("1");

        // 情景一：硬件资产登记
        ActionResponse actionResponse = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200", actionResponse.getHead().getCode());

        // 情景二：硬件入网
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_NET);
        actionResponse = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200", actionResponse.getHead().getCode());

        // 情景三：硬件入网
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_CHECK);
        actionResponse = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200", actionResponse.getHead().getCode());

        // 情景四：硬件退役
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_RETIRE);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_IMPL_RETIRE);
        actionResponse = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200", actionResponse.getHead().getCode());

        // 情景五：硬件实施退役
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_RETIRE);
        actionResponse = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200", actionResponse.getHead().getCode());

        // 情景二：软件资产登记
        assetStatusReqeust.setSoftware(true);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.SOFTWARE_REGISTER);
        ActionResponse actionResponse2 = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200", actionResponse2.getHead().getCode());

        // 情景三：软件资产实施退役流程
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.SOFTWARE_IMPL_RETIRE);
        assetStatusReqeust.setSoftwareStatusEnum(SoftwareStatusEnum.WAIT_ANALYZE_RETIRE);
        ActionResponse actionResponse3 = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("416", actionResponse3.getHead().getCode());

        // 情景四：软件资产实施卸载流程
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.SOFTWARE_IMPL_UNINSTALL);
        ActionResponse actionResponse4 = assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        Assert.assertEquals("416", actionResponse4.getHead().getCode());
    }

    @Test
    public void changeStatusTest2() throws Exception {
        SchemeRequest schemeRequest = new SchemeRequest();
        schemeRequest.setId("2");
        schemeRequest.setPutintoUserId("2");
        schemeRequest.setContent(" ");
        schemeRequest.setMemo("备注信息不能为空");

        AssetStatusReqeust assetStatusReqeust = new AssetStatusReqeust();
        assetStatusReqeust.setSchemeRequest(schemeRequest);
        assetStatusReqeust.setAgree(false);
        assetStatusReqeust.setAssetId("1");

        Scheme scheme = new Scheme();
        scheme.setAssetId("1");
        scheme.setCreateUser(1);
        scheme.setType(1);
        scheme.setContent("Schema content!");
        scheme.setFileInfo("[{\"id\":355,\"fileName\":\"新建文本文档.rar\"}]");

        Asset asset = new Asset();
        asset.setId(1);
        AssetSoftware software = new AssetSoftware();
        software.setSoftwareStatus(1);

        Mockito.when(schemeRequestToSchemeConverter.convert(schemeRequest, Scheme.class)).thenReturn(scheme);
        Mockito.when(assetDao.getById(Mockito.any())).thenReturn(asset);

        // 情景一：硬件资产登记
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_CHECK);
        try{
            assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        }catch (Exception e){}

        // 情景二：硬件入网
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_NET);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_IMPL_RETIRE);
        try{
            assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        }catch (Exception e){}

        // 情景三：硬件入网
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_CHECK);
        assetStatusReqeust.setSchemeRequest(null);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_IMPL_RETIRE);
        try{
            assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        }catch (RequestParamValidateException e){}

        // 情景四：硬件退役
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.NET_IN);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_IMPL_RETIRE);
        try{
            assetStatusChangeProcessService.changeStatus(assetStatusReqeust);
        }catch (RequestParamValidateException e){}
    }

}
