package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetStatusTaskDao;
import com.antiy.asset.entity.AssetStatusTask;
import com.antiy.asset.vo.query.AssetStatusTaskQuery;
import com.antiy.asset.vo.request.AssetStatusTaskRequest;
import com.antiy.asset.vo.response.AssetStatusTaskResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.QueryCondition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
public class AssetStatusTaskServiceImplTest {
    @Mock
    private AssetStatusTaskDao                                      assetStatusTaskDao;
    @Mock
    private BaseConverter<AssetStatusTaskRequest, AssetStatusTask>  requestConverter;
    @Mock
    private BaseConverter<AssetStatusTask, AssetStatusTaskResponse> responseConverter;

    @InjectMocks
    AssetStatusTaskServiceImpl                                      assetStatusTaskService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        LoginUtil.generateDefaultLoginUser();
        AssetStatusTask assetStatusTask = new AssetStatusTask();
        assetStatusTask.setId(1);
        Mockito.when(requestConverter.convert(Mockito.any(AssetStatusTaskRequest.class), Mockito.any()))
            .thenReturn(assetStatusTask);
        Mockito.when(responseConverter.convert(Mockito.any(AssetStatusTask.class), Mockito.any()))
            .thenReturn(new AssetStatusTaskResponse());

    }

    @Test
    public void saveAssetStatusTask() throws Exception {
        AssetStatusTaskRequest assetStatusTaskRequest = new AssetStatusTaskRequest();
        Assert.assertEquals("1", assetStatusTaskService.saveAssetStatusTask(assetStatusTaskRequest));
    }

    @Test
    public void updateAssetStatusTask() throws Exception {
        AssetStatusTaskRequest assetStatusTaskRequest = new AssetStatusTaskRequest();
        Mockito.when(assetStatusTaskDao.update(Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", assetStatusTaskService.updateAssetStatusTask(assetStatusTaskRequest));

    }

    @Test
    public void queryListAssetStatusTask() throws Exception {
        AssetStatusTaskQuery assetStatusTaskRequest = new AssetStatusTaskQuery();
        Mockito.when(assetStatusTaskDao.findQuery(Mockito.any())).thenReturn(new ArrayList<>());
        Assert.assertNotNull(assetStatusTaskService.queryListAssetStatusTask(assetStatusTaskRequest));
    }

    @Test
    public void queryPageAssetStatusTask() throws Exception {
        AssetStatusTaskQuery assetStatusTaskRequest = new AssetStatusTaskQuery();
        Mockito.when(assetStatusTaskDao.findQuery(Mockito.any())).thenReturn(new ArrayList<>());
        Assert.assertNotNull(assetStatusTaskService.queryPageAssetStatusTask(assetStatusTaskRequest));
    }

    @Test
    public void queryAssetStatusTaskById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("1");
        Mockito.when(assetStatusTaskDao.getById(Mockito.any())).thenReturn(new AssetStatusTask());
        Assert.assertNotNull(assetStatusTaskService.queryAssetStatusTaskById(queryCondition));
    }

    @Test
    public void deleteAssetStatusTaskById() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setStringId("1");
        Mockito.when(assetStatusTaskDao.findQuery(Mockito.any())).thenReturn(new ArrayList<>());
        Mockito.when(assetStatusTaskDao.deleteById(Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", assetStatusTaskService.deleteAssetStatusTaskById(baseRequest));
    }
}
