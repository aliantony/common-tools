package com.antiy.asset.service.impl;

import com.antiy.asset.convert.CategoryRequestConvert;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelNodeResponse;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.*;

import static org.mockito.AdditionalMatchers.eq;


@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginUserUtil.class, LogHandle.class, BusinessExceptionUtils.class, AssetCategoryModelServiceImpl.class, CategoryResponseConvert.class})
public class AssetCategoryModelServiceImplTest {

    @Mock
    private AssetCategoryModelDao assetCategoryModelDao;
    @Mock
    private AssetDao assetDao;
    @Mock
    private CategoryRequestConvert requestConverter;
    @Mock
    private CategoryRequestConvert categoryRequestConvert;
    @Mock
    private CategoryResponseConvert responseConverter;
    @Mock
    private BaseConverter<AssetCategoryModel, AssetCategoryModelResponse> baseConverter;
    @Mock
    private AesEncoder aesEncoder;
    @InjectMocks
    private AssetCategoryModelServiceImpl assetCategoryModelServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        assetCategoryModelServiceImpl = PowerMockito.spy(assetCategoryModelServiceImpl);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        PowerMockito.mockStatic(BusinessExceptionUtils.class);
        PowerMockito.doNothing().when(BusinessExceptionUtils.class, "isTrue", Mockito.anyBoolean(), Mockito.anyString());
    }

    @Test
    public void saveAssetCategoryModelTest() throws Exception {
        AssetCategoryModelRequest request = getAssetCategoryModelRequest();
        AssetCategoryModel assetCategoryModel = getAssetCategoryModel();
        String expect = "测试成功";
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("zhangsan");
        loginUser.setId(1);
        PowerMockito.mockStatic(LoginUserUtil.class);
        Mockito.when(requestConverter.convert(request, AssetCategoryModel.class)).thenReturn(assetCategoryModel);
        Mockito.when(assetCategoryModelDao.findRepeatName(Mockito.anyInt(), Mockito.anyString())).thenReturn(1);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        Mockito.when(assetCategoryModelDao.insert(Mockito.any())).thenReturn(1);
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
        Mockito.when(aesEncoder.encode(Mockito.anyString(), Mockito.anyString())).thenReturn(expect);
        Assert.assertEquals(expect, assetCategoryModelServiceImpl.saveAssetCategoryModel(request).getBody());

    }

    @Test
    public void updateAssetCategoryModelTest() throws Exception {
        AssetCategoryModelRequest request = getAssetCategoryModelRequest();
        AssetCategoryModel assetCategoryModel = getAssetCategoryModel();
        Integer expect = 1;
        Mockito.when(categoryRequestConvert.convert(request, AssetCategoryModel.class)).thenReturn(assetCategoryModel);
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        Mockito.when(assetCategoryModelDao.update(Mockito.any())).thenReturn(expect);
        Assert.assertEquals(expect, assetCategoryModelServiceImpl.updateAssetCategoryModel(request).getBody());
    }

    @Test
    public void findPageAssetCategoryModelTest() throws Exception {
        AssetCategoryModelQuery query = new AssetCategoryModelQuery();
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        List<AssetCategoryModelResponse> listExpect = getAssetCategoryModelResponseList();
        Mockito.when(assetCategoryModelDao.findListAssetCategoryModel(query)).thenReturn(list);
        Mockito.when(assetCategoryModelDao.findCount(query)).thenReturn(1);
        Mockito.when(responseConverter.convert(list, AssetCategoryModelResponse.class)).thenReturn(listExpect);
        Assert.assertEquals(1, assetCategoryModelServiceImpl.findPageAssetCategoryModel(query).getTotalRecords());
        Assert.assertEquals(listExpect, assetCategoryModelServiceImpl.findPageAssetCategoryModel(query).getItems());
    }

    @Test
    public void DeleteTest() throws Exception {
        AssetCategoryModel assetCategoryModel = getAssetCategoryModel();
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        Integer expect = 1;
        Mockito.when(assetCategoryModelDao.getById(Mockito.anyInt())).thenReturn(assetCategoryModel);
        Mockito.when(assetCategoryModelDao.getAll()).thenReturn(list);
        Mockito.when(assetDao.findCountByCategoryModel(Mockito.any())).thenReturn(expect);
        Mockito.when(assetCategoryModelDao.delete(Mockito.anyList())).thenReturn(expect);

        Assert.assertEquals(expect, assetCategoryModelServiceImpl.delete(101).getBody());
    }

    @Test
    public void queryCategoryNodeTest() throws Exception {
        List<AssetCategoryModelNodeResponse> assetDepartmentNodeResponses = new ArrayList<>();
        AssetCategoryModelNodeResponse expect = new AssetCategoryModelNodeResponse();
        assetDepartmentNodeResponses.add(expect);
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        NodeUtilsConverter<AssetCategoryModel, AssetCategoryModelNodeResponse> nodeConverter = Mockito.mock(NodeUtilsConverter.class);
        PowerMockito.whenNew(NodeUtilsConverter.class).withNoArguments().thenReturn(nodeConverter);
        Mockito.when(nodeConverter.columnToNode(list, AssetCategoryModelNodeResponse.class)).thenReturn(assetDepartmentNodeResponses);
        Mockito.when(assetCategoryModelDao.findListAssetCategoryModel(Mockito.any())).thenReturn(list);
        Assert.assertEquals(expect, assetCategoryModelServiceImpl.queryCategoryNode());
    }

    @Test
    public void queryComputeAndNetCategoryNodeTest() throws Exception {
        List<AssetCategoryModelResponse> secondList = hardwareResponseList();
        List<AssetCategoryModel> assetCategoryModels = getAssetCategoryModelList();
        assetCategoryModels.add(getAssetCategoryModel());
        PowerMockito.doReturn(secondList).when(assetCategoryModelServiceImpl).getNextLevelCategoryByName(Mockito.anyString());
        Mockito.when(assetCategoryModelDao.findListAssetCategoryModel(Mockito.any())).thenReturn(assetCategoryModels);
        assetCategoryModelServiceImpl.queryComputeAndNetCategoryNode(1);
    }

    @Test
    public void queryCategoryNodeByTypeTest() throws Exception {
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        List<AssetCategoryModel> modelRootList = new ArrayList<>();
        modelRootList.add(getAssetCategoryModel());
        Integer type = 2;
        Mockito.when(assetCategoryModelDao.findListAssetCategoryModel(Mockito.any())).thenReturn(list).thenReturn(modelRootList);
        Assert.assertEquals("硬件", assetCategoryModelServiceImpl.queryCategoryNode(type).getName());
    }

    @Test
    @Ignore
    public void querySecondCategoryNodeTest() throws Exception {
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        String[] types = new String[]{"4", "5", "6", "7", "8"};
        AssetCategoryModelNodeResponse expect = new AssetCategoryModelNodeResponse();
        Map<String, String> initMap = new HashMap<>(1);
        Mockito.when(assetCategoryModelDao.findListAssetCategoryModel(Mockito.any())).thenReturn(list);
        Assert.assertEquals(expect, assetCategoryModelServiceImpl.querySecondCategoryNode(types, initMap));
    }

    @Test
    @Ignore
    public void querySecondCategoryNodeTest2() throws Exception {
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        String[] types = new String[]{"4", "5", "6", "7", "8"};
        List<AssetCategoryModelResponse> listExpect = getAssetCategoryModelResponseList();
        Mockito.when(assetCategoryModelDao.findListAssetCategoryModel(Mockito.any())).thenReturn(list);
        Mockito.when(assetCategoryModelDao.getNextLevelCategoryByName(Mockito.anyString())).thenReturn(list);
        Mockito.when(responseConverter.convert(list, AssetCategoryModelResponse.class)).thenReturn(listExpect);
//        Assert.assertNull(assetCategoryModelServiceImpl.querySecondCategoryNode(types));
    }

    @Test
    public void getNextLevelCategoryByNameTest() throws Exception {
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        AssetCategoryModelResponse expect = getAssetCategoryModelResponse();
        List<AssetCategoryModelResponse> listExpect = getAssetCategoryModelResponseList();
        Mockito.when(assetCategoryModelDao.getNextLevelCategoryByName(Mockito.anyString())).thenReturn(list);
        Mockito.when(responseConverter.convert(list, AssetCategoryModelResponse.class)).thenReturn(listExpect);
        Assert.assertEquals(expect.toString(), assetCategoryModelServiceImpl.getNextLevelCategoryByName("计算设备").get(0).toString());
    }

    @Test
    @Ignore
    public void findAssetCategoryModelByIdTest() throws Exception {
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        Mockito.when(assetCategoryModelDao.getAll()).thenReturn(list);
        List<AssetCategoryModelResponse> listExpect = getAssetCategoryModelResponseList();
        Mockito.when(responseConverter.convert(list, AssetCategoryModelResponse.class)).thenReturn(listExpect);
        Assert.assertEquals(listExpect, assetCategoryModelServiceImpl.findAssetCategoryModelById(101));
    }

    @Test
    public void getSecondCategoryModelNodeResponseTest() throws Exception {
        AssetCategoryModelNodeResponse expect = new AssetCategoryModelNodeResponse();
        Map<String, String> initMap = new HashMap<>();
        initMap.put("2", "硬件");
        PowerMockito.doReturn(expect).when(assetCategoryModelServiceImpl, "getCategoryByNameFromList", Mockito.any(), Mockito.anyString());
        Object result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "getSecondCategoryModelNodeResponse", expect, "2", initMap);
        Assert.assertEquals(expect, result);
    }

    @Test
    public void getCategoryByNameFromListTest() throws Exception {
        String name = "软件";
        AssetCategoryModelNodeResponse expect = new AssetCategoryModelNodeResponse();
        expect.setName(name);
        List<AssetCategoryModelNodeResponse> list = new ArrayList<>();
        list.add(expect);
        Object result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "getCategoryByNameFromList", list, name);
        Assert.assertEquals(expect, result);
    }

    @Test
    public void recursionSearchParentCategoryTest() throws Exception {
        Object result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "recursionSearchParentCategory", "3", getAssetCategoryModelList(), new TreeSet<String>());
        Assert.assertNull(result);
    }

    @Test
    public void findAssetCategoryModelIdsByIdTest1() throws Exception {
        Integer id = 1;
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        List<AssetCategoryModelResponse> responseslist = getAssetCategoryModelResponseList();
        List<Integer> expect = new ArrayList<>();
        Mockito.when(assetCategoryModelDao.getAll()).thenReturn(list);
        PowerMockito.doReturn(list).when(assetCategoryModelServiceImpl, "recursionSearch", list, id);
        Mockito.when(responseConverter.convert(list, AssetCategoryModelResponse.class)).thenReturn(responseslist);
        PowerMockito.doReturn(expect).when(assetCategoryModelServiceImpl, "getSonCategory", responseslist);
        Object result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "findAssetCategoryModelIdsById", id);
        Assert.assertEquals(expect, result);
    }

    @Test
    public void findAssetCategoryModelIdsByIdTest2() throws Exception {
        Integer id = 1;
        List<AssetCategoryModel> list = getAssetCategoryModelList();
        List<AssetCategoryModelResponse> responseslist = getAssetCategoryModelResponseList();
        List<Integer> expect = new ArrayList<>();
        PowerMockito.doReturn(list).when(assetCategoryModelServiceImpl, "recursionSearch", list, id);
        Mockito.when(responseConverter.convert(list, AssetCategoryModelResponse.class)).thenReturn(responseslist);
        PowerMockito.doReturn(expect).when(assetCategoryModelServiceImpl, "getSonCategory", responseslist);
        Object result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "findAssetCategoryModelIdsById", id, list);
        Assert.assertEquals(expect, result);
    }

    @Test
    public void getSonCategoryTest() throws Exception {
        List<AssetCategoryModelResponse> responseslist = getAssetCategoryModelResponseList();
        List<Integer> result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "getSonCategory", responseslist);
        Assert.assertEquals(responseslist.size(), result.size());
        Assert.assertEquals(getAssetCategoryModelResponse().getStringId(), result.get(0).toString());
    }

    @Test
    @Ignore
    public void getCategoryIdListTest() throws Exception {
        List<String> result = Whitebox.invokeMethod(assetCategoryModelServiceImpl, "getCategoryIdList", getAssetCategoryModelList());
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(getAssetCategoryModel().getStringId(), result.get(0));
    }

    @Test
    public void convertTest() {
        responseConverter = Mockito.spy(new CategoryResponseConvert());
//        PowerMockito.mock(CategoryResponseConvert.)
        responseConverter.convert(getAssetCategoryModel(), getAssetCategoryModelResponse());
//       Mockito.verify(responseConverter).convert(getAssetCategoryModel(),);
    }

    private AssetCategoryModelRequest getAssetCategoryModelRequest() {
        AssetCategoryModelRequest request = new AssetCategoryModelRequest();
        request.setParentId("101");
        request.setName(Constants.ROOT_CATEGORY_NAME);
        request.setStringId("101");
        return request;
    }

    protected AssetCategoryModel getAssetCategoryModel() {
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setName(Constants.ROOT_CATEGORY_NAME);
        assetCategoryModel.setIsDefault(Constants.NOT_SYSTEM_DEFAULT_CATEGORY);
        assetCategoryModel.setParentId("0");
        assetCategoryModel.setAssetType(0);
        assetCategoryModel.setType(1);
        assetCategoryModel.setId(1);
        return assetCategoryModel;
    }

    protected List<AssetCategoryModel> getAssetCategoryModelList() {
        List<AssetCategoryModel> list = new ArrayList<>();
        //  list.add(getAssetCategoryModel());
        AssetCategoryModel model1 = new AssetCategoryModel();
        model1.setName("交换机");
        model1.setParentId("5");
        model1.setAssetType(2);
        model1.setType(1);
        model1.setId(16);
        AssetCategoryModel model2 = new AssetCategoryModel();
        model2.setName("路由器");
        model2.setParentId("5");
        model2.setAssetType(2);
        model2.setType(1);
        model2.setId(17);
        AssetCategoryModel model3 = new AssetCategoryModel();
        model3.setName("计算设备");
        model3.setParentId("2");
        model3.setAssetType(2);
        model3.setType(1);
        model3.setId(4);
        AssetCategoryModel model4 = new AssetCategoryModel();
        model4.setName("网络设备");
        model4.setParentId("2");
        model4.setAssetType(2);
        model4.setType(1);
        model4.setId(5);
        AssetCategoryModel model5 = new AssetCategoryModel();
        model5.setName("硬件");
        model5.setParentId("1");
        model5.setAssetType(2);
        model5.setType(1);
        model5.setId(2);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
//        list.add(model6);
        return list;
    }

    private AssetCategoryModelResponse getAssetCategoryModelResponse() {
        AssetCategoryModelResponse response = new AssetCategoryModelResponse();
        response.setName(Constants.ROOT_CATEGORY_NAME);
        response.setParentId("11");
        response.setStringId("3");
        return response;
    }

    private List<AssetCategoryModelResponse> getAssetCategoryModelResponseList() {
        List<AssetCategoryModelResponse> list = new ArrayList<>();
        list.add(getAssetCategoryModelResponse());
        return list;
    }

    private List<AssetCategoryModelResponse> hardwareResponseList() {
        List<AssetCategoryModelResponse> list = new ArrayList<>();
        AssetCategoryModelResponse response = new AssetCategoryModelResponse();
        response.setStringId("5");
        response.setName("网络设备");
        response.setParentId("2");
        list.add(response);
        AssetCategoryModelResponse response2 = new AssetCategoryModelResponse();
        response2.setStringId("4");
        response2.setName("计算设备");
        response2.setParentId("2");
        list.add(response2);
        return list;
    }

}
