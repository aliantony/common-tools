package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetStatusChangeFlowProcessImplTest {

    @MockBean
    private AssetDao assetDao;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @MockBean
    private AssetSoftwareDao assetSoftwareDao;
    @MockBean
    private ActivityClient activityClient;

    @MockBean
    private AssetOperationRecordDao assetOperationRecordDao;
    @MockBean
    private SchemeDao schemeDao;


    @SpyBean
    private AssetStatusChangeFlowProcessImpl assetStatusChangeFlowProcess;

    @Before
    public void login(){
        LoginUtil.generateDefaultLoginUser();
    }
    @Test
    public void changeStatus() throws Exception {
        AssetStatusReqeust assetStatusReqeust=new AssetStatusReqeust();
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WATI_REGSIST);
        assetStatusReqeust.setAgree(true);
        assetStatusReqeust.setAssetId("1");
        SchemeRequest schemeRequest=new SchemeRequest();
        schemeRequest.setExtension("{\"baseline\":false}");
        assetStatusReqeust.setSchemeRequest(schemeRequest);

        //流程引擎为空,直接返回错误信息
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_REGISTER);
        assetStatusReqeust.setSoftwareStatusEnum(SoftwareStatusEnum.WATI_REGSIST);
        Asset asesetStatus = new Asset();
        asesetStatus.setStatus(3);
        asesetStatus.setAssetStatus(2);
        when(assetDao.getById(any())).thenReturn(asesetStatus);
        when(assetSoftwareDao.getById(any())).thenReturn(new AssetSoftware());
        ActionResponse actionResponse=new ActionResponse();
        actionResponse.setBody("nihao");
        ActionResponse result = assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
        Assert.assertEquals("416",result.getHead().getCode());
        asesetStatus.setFirstEnterNett(new Date().getTime());
        when( activityClient.completeTask(any())).thenReturn(actionResponse);
        ActionResponse result1 = assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
        Assert.assertEquals("nihao",result1.getBody());

        //返回正常信息
        when( activityClient.completeTask(any())).thenReturn(ActionResponse.success());
        ActionResponse result2 = assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200",result2.getHead().getCode());
        schemeRequest.setExtension("");
        ActionResponse result3 = assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200",result3.getHead().getCode());
       // schemeRequest.setExtension("{\"baseline\":false}");

        assetStatusReqeust.setSchemeRequest(null);
        ActionResponse result5 = assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200",result5.getHead().getCode());

        //抛异常--重复提交
        when( activityClient.completeTask(any())).thenReturn(ActionResponse.success());
        asesetStatus.setAssetStatus(3);
        expectedEx.expect(BusinessException.class);
        expectedEx.expectMessage("请勿重复提交，当前资产状态是：待配置");
        assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);

        //抛异常--软件资产跃迁状态获取失败
        asesetStatus.setAssetStatus(2);
        assetStatusReqeust.setSoftwareStatusEnum(null);
        expectedEx.expect(BusinessException.class);
        expectedEx.expectMessage("软件资产跃迁状态获取失败");
        assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
    }
    @Test
    public void changeStatus2() throws Exception{
        AssetStatusReqeust assetStatusReqeust=new AssetStatusReqeust();
        ActivityHandleRequest activityHandleRequest=new ActivityHandleRequest();
        //activityHandleRequest.setFormData("{\"resultCheckUserId\":\"110\",\"result\":\"1\"}");
        activityHandleRequest.setTaskId("222");
        assetStatusReqeust.setActivityHandleRequest(activityHandleRequest);
        assetStatusReqeust.setAssetId("13");
        assetStatusReqeust.setAssetStatus(AssetStatusEnum.WAIT_NET);
        assetStatusReqeust.setAgree(true);
        assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_REGISTER);
        SchemeRequest schemeRequest=new SchemeRequest();
        schemeRequest.setSchemeSource(1);
        schemeRequest.setType(1);
        schemeRequest.setExtension("{\"baseline\": false}");
        assetStatusReqeust.setSchemeRequest(schemeRequest);
        assetStatusReqeust.setSoftware(false);
        Asset asesetStatus = new Asset();
        asesetStatus.setStatus(3);
        asesetStatus.setAssetStatus(2);
        when(assetDao.getById(any())).thenReturn(asesetStatus);
        when( activityClient.completeTask(any())).thenReturn(ActionResponse.success());
        ActionResponse result2 = assetStatusChangeFlowProcess.changeStatus(assetStatusReqeust);
        Assert.assertEquals("200",result2.getHead().getCode());
    }
}
