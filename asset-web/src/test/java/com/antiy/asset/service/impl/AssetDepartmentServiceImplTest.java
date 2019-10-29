package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.util.NodeUtilsConverter;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.request.AssetDepartmentRequest;
import com.antiy.asset.vo.response.AssetDepartmentNodeResponse;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ RedisKeyUtil.class, LoginUserUtil.class, LogUtils.class, LogHandle.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetDepartmentServiceImplTest {
    @InjectMocks
    private AssetDepartmentServiceImpl                                       assetDepartmentService;
    @Mock
    private AssetDepartmentDao                                               assetDepartmentDao;
    @Spy
    private BaseConverter<AssetDepartmentRequest, AssetDepartment>           requestConverter;
    @Spy
    private BaseConverter<AssetDepartment, AssetDepartmentResponse>          responseConverter;
    @Mock
    private NodeUtilsConverter<AssetDepartment, AssetDepartmentNodeResponse> nodeConverter;
    @Mock
    private AesEncoder                                                       aesEncoder;
    @Mock
    private AssetUserDao                                                     assetUserDao;

    @Rule
    public ExpectedException                                                 expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // LoginUtil.generateDefaultLoginUser();
        PowerMockito.mockStatic(LoginUserUtil.class);

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("小李");
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.mockStatic(LogHandle.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));
        PowerMockito.doNothing().when(LogHandle.class, "log", Mockito.any(), Mockito.any(), Mockito.any(),
            Mockito.any());

    }

    @Test
    public void saveAssetDepartmentTest() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setName("test");
        request.setParentId("0");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("0");
        assetDepartment.setName("test");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(assetDepartment);
        ActionResponse response = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(null);
        ActionResponse response2 = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response2.getHead().getCode());
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(0);
        ActionResponse response3 = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response3.getHead().getCode());
    }

    @Test
    public void saveAssetDepartmentTest1() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setParentId("1");
        request.setName("1");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("1");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(assetDepartment);
        ActionResponse response = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());
    }

    @Test
    public void saveAssetDepartmentTest2() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setParentId("1");
        request.setName("1");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("1");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.findRepeatName(Mockito.any(), Mockito.any())).thenReturn(10);

        try {
            ActionResponse response = assetDepartmentService.saveAssetDepartment(request);
        } catch (Exception e) {
            Assert.assertEquals("该部门名已存在", e.getMessage());
        }
    }

    @Test
    public void saveAssetDepartmentTest3() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setParentId("1");
        request.setName("1");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("1");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.findRepeatName(Mockito.any(), Mockito.any())).thenReturn(0);

        ActionResponse response = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());
    }

    @Test
    public void saveAssetDepartmentTest4() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setParentId("1");
        request.setId("1");
        request.setName("1");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("1");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.findRepeatName(Mockito.any(), Mockito.any())).thenReturn(0);

        ActionResponse response = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());
    }


    @Test
    public void saveAssetDepartmentTest5() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setParentId("1");
        request.setId("1");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("1");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.findRepeatName(Mockito.any(), Mockito.any())).thenReturn(0);

        ActionResponse response = assetDepartmentService.saveAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());
    }
    @Test
    public void updateAssetDepartmentTest() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setName("test");
        request.setId("1");
        request.setParentId("0");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("0");
        assetDepartment.setName("test");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.update(Mockito.any())).thenReturn(1);

        ActionResponse response = assetDepartmentService.updateAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());

        Mockito.when(assetDepartmentDao.update(Mockito.any())).thenReturn(0);
        Assert.assertEquals("200", response.getHead().getCode());
        request.setParentId("1");
        expectedEx.expect(BusinessException.class);
        expectedEx.expectMessage("上级部门不能为自身");
        assetDepartmentService.updateAssetDepartment(request);

    }

    @Test
    public void updateAssetDepartmentTest1() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setName("test");
        request.setId("1");
        request.setParentId("0");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("0");
        assetDepartment.setName("test");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.update(Mockito.any())).thenReturn(10);

        ActionResponse response = assetDepartmentService.updateAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());

    }


    @Test
    public void updateAssetDepartmentTest2() throws Exception {
        AssetDepartmentRequest request = new AssetDepartmentRequest();
        request.setName("test");
        request.setId("1");
        request.setParentId("0");
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("0");
        assetDepartment.setName("test");

        Mockito.when(requestConverter.convert(request, AssetDepartment.class)).thenReturn(assetDepartment);
        Mockito.when(assetDepartmentDao.update(Mockito.any())).thenReturn(0);

        ActionResponse response = assetDepartmentService.updateAssetDepartment(request);
        Assert.assertEquals("200", response.getHead().getCode());

    }

    @Test
    public void findListAssetDepartmentTest() throws Exception {
        AssetDepartmentQuery query = new AssetDepartmentQuery();
        query.setParentId("0");
        query.setStatus(1);
        query.setName("test");

        List<AssetDepartment> assetDepartmentList = new ArrayList<>();
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId("0");
        assetDepartment.setStatus(1);
        assetDepartmentList.add(assetDepartment);

        List<AssetDepartmentResponse> assetDepartmentResponseList = new ArrayList<>();
        AssetDepartmentResponse assetDepartmentResponse = new AssetDepartmentResponse();
        assetDepartmentResponse.setParentId("0");
        assetDepartmentResponse.setStatus(1);
        assetDepartmentResponse.setName("test");
        assetDepartmentResponseList.add(assetDepartmentResponse);

        Mockito.when(assetDepartmentDao.findListAssetDepartment(Mockito.any())).thenReturn(assetDepartmentList);
        Mockito.when(responseConverter.convert(assetDepartmentList, AssetDepartmentResponse.class))
            .thenReturn(assetDepartmentResponseList);
        List<AssetDepartmentResponse> actual = assetDepartmentService.findListAssetDepartment(query);
        Assert.assertTrue(assetDepartmentResponseList.size() == actual.size());
    }

    @Test
    public void findAssetDepartmentByIdTest() throws Exception {
        List<AssetDepartment> assetDepartmentList = Arrays.asList(getAssetDepartment(1, "0"),
            getAssetDepartment(2, "1"), getAssetDepartment(3, "2"), getAssetDepartment(4, "3"));
        List<AssetDepartmentResponse> assetDepartmentResponseList = new ArrayList<>();
        AssetDepartmentResponse assetDepartmentResponse = new AssetDepartmentResponse();
        assetDepartmentResponse.setParentId("0");
        assetDepartmentResponse.setStatus(1);
        assetDepartmentResponse.setName("test");
        assetDepartmentResponseList.add(assetDepartmentResponse);
        Mockito.when(assetDepartmentDao.getAll()).thenReturn(assetDepartmentList);
        Mockito.when(responseConverter.convert(assetDepartmentList, AssetDepartmentResponse.class))
            .thenReturn(assetDepartmentResponseList);
        List<AssetDepartmentResponse> actual = assetDepartmentService.findAssetDepartmentById(1);
        Assert.assertTrue(assetDepartmentResponseList.size() == actual.size());
    }

    private AssetDepartment getAssetDepartment(int id, String parentId) {
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setParentId(parentId);
        assetDepartment.setStatus(1);
        assetDepartment.setId(id);
        return assetDepartment;
    }

    @Test
    public void findPageAssetDepartmentTest() throws Exception {
        AssetDepartmentResponse assetDepartmentResponse = new AssetDepartmentResponse();
        assetDepartmentResponse.setParentId("0");
        assetDepartmentResponse.setStatus(1);
        assetDepartmentResponse.setName("test");
        List<AssetDepartmentResponse> assetDepartmentResponseList = new ArrayList<>();
        assetDepartmentResponseList.add(assetDepartmentResponse);
        PageResult<AssetDepartmentResponse> pageResult = new PageResult<>();
        pageResult.setPageSize(10);
        pageResult.setItems(assetDepartmentResponseList);
        AssetDepartmentQuery query = new AssetDepartmentQuery();
        query.setParentId("0");
        query.setStatus(1);
        query.setName("test");

        Mockito.when(assetDepartmentDao.findCount(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentService.findListAssetDepartment(Mockito.any()))
            .thenReturn(assetDepartmentResponseList);

        PageResult<AssetDepartmentResponse> actual = assetDepartmentService.findPageAssetDepartment(query);
        Assert.assertTrue(pageResult.getItems().size() == actual.getItems().size());
    }

    @Test
    public void findDepartmentNodeTest() throws Exception {
        List<AssetDepartment> assetDepartmentList = Arrays.asList(getAssetDepartment(1, "0"));

        List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses = new ArrayList<>();
        AssetDepartmentNodeResponse assetDepartmentNodeResponse = new AssetDepartmentNodeResponse();
        List<AssetDepartmentNodeResponse> childrenCode = new ArrayList<>();
        AssetDepartmentNodeResponse children = new AssetDepartmentNodeResponse();
        children.setStringId("1");
        childrenCode.add(children);
        assetDepartmentNodeResponse.setChildrenNode(childrenCode);
        assetDepartmentNodeResponse.setParentId("0");
        assetDepartmentNodeResponse.setStatus(1);
        assetDepartmentNodeResponses.add(assetDepartmentNodeResponse);

        Mockito.when(assetDepartmentDao.findListAssetDepartment(Mockito.any())).thenReturn(assetDepartmentList);

        AssetDepartmentNodeResponse actual = assetDepartmentService.findDepartmentNode();
        Assert.assertEquals(assetDepartmentNodeResponse.getStatus(), actual.getStatus());
        Mockito.when(assetDepartmentDao.findListAssetDepartment(Mockito.any())).thenReturn(null);
        AssetDepartmentNodeResponse actual2 = assetDepartmentService.findDepartmentNode();
        Assert.assertNull(actual2);
    }

    @Test
    public void findDepartmentNodeTest1() throws Exception {
        List<AssetDepartment> assetDepartmentList = Arrays.asList(getAssetDepartment(1, "0"));

        List<AssetDepartmentNodeResponse> assetDepartmentNodeResponses = new ArrayList<>();
        AssetDepartmentNodeResponse assetDepartmentNodeResponse = new AssetDepartmentNodeResponse();
        List<AssetDepartmentNodeResponse> childrenCode = new ArrayList<>();
        AssetDepartmentNodeResponse children = new AssetDepartmentNodeResponse();
        children.setStringId("2");
        childrenCode.add(children);
        assetDepartmentNodeResponse.setChildrenNode(childrenCode);
        assetDepartmentNodeResponse.setParentId("1");
        assetDepartmentNodeResponse.setStatus(1);
        assetDepartmentNodeResponses.add(assetDepartmentNodeResponse);
        ReflectionTestUtils.setField(assetDepartmentService, "parentMap", new HashMap<>());

        Mockito.when(assetDepartmentDao.findListAssetDepartment(Mockito.any())).thenReturn(assetDepartmentList);
        AssetDepartmentNodeResponse actual = assetDepartmentService.findDepartmentNode();
        Assert.assertEquals(assetDepartmentNodeResponse.getStatus(), actual.getStatus());
    }

    @Test
    public void deleteAllByIdTest() throws Exception {
        List<AssetDepartment> assetDepartmentList = new ArrayList<>();
        AssetDepartment assetDepartment = getAssetDepartment(1, "0");
        assetDepartmentList.add(assetDepartment);
        AssetUser assetUser = new AssetUser();
        assetUser.setId(1);
        List<AssetUser> resultUser = new ArrayList<>();

        Mockito.when(assetDepartmentDao.getAll()).thenReturn(assetDepartmentList);
        Mockito.when(assetDepartmentDao.delete(Mockito.any())).thenReturn(1);
        Mockito.when(assetUserDao.findListAssetUser(Mockito.any())).thenReturn(resultUser);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(getDepartment());
        Assert.assertEquals("200", assetDepartmentService.deleteAllById(1).getHead().getCode());
        Mockito.when(assetDepartmentDao.delete(Mockito.any())).thenReturn(0);
        Assert.assertEquals(0, assetDepartmentService.deleteAllById(1).getBody());
        resultUser.add(assetUser);
        expectedEx.expectMessage("部门下存在关联用户，不能删除");
        expectedEx.expect(BusinessException.class);
        assetDepartmentService.deleteAllById(1);
    }

    public AssetDepartment getDepartment() {
        AssetDepartment assetDepartment = new AssetDepartment();
        assetDepartment.setName("123");
        assetDepartment.setId(1);
        assetDepartment.setParentId("2");
        return assetDepartment;
    }

    @Test
    public void getIdByNameTest() {
        Mockito.when(assetDepartmentDao.getIdByName(Mockito.any())).thenReturn("1");
        Assert.assertEquals("1", assetDepartmentService.getIdByName("test"));
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.when(assetDepartmentDao.delete(Mockito.any())).thenReturn(1);
        Mockito.when(assetDepartmentDao.getById(Mockito.any())).thenReturn(getDepartment());
        Assert.assertEquals("200", assetDepartmentService.delete(1).getHead().getCode());
        Mockito.when(assetDepartmentDao.delete(Mockito.any())).thenReturn(0);
        Assert.assertEquals(0, assetDepartmentService.delete(1).getBody());
    }
}
