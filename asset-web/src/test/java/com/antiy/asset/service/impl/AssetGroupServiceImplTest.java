package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.convert.SelectConvert;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.request.RemoveAssociateAssetRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.poi.ss.formula.functions.T;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ BeanConvert.class, LoginUserUtil.class, LogUtils.class })
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
public class AssetGroupServiceImplTest {
    @InjectMocks
    private AssetGroupServiceImpl                         assetGroupService;
    @Mock
    private AssetGroupDao                                 assetGroupDao;
    @Mock
    private BaseConverter<AssetGroup, AssetGroupResponse> assetGroupToResponseConverter;
    @Mock
    private BaseConverter<AssetGroupRequest, AssetGroup>  assetGroupToAssetGroupConverter;
    @Mock
    private AesEncoder                                    aesEncoder;
    @Spy
    private SelectConvert                                 selectConvert;
    @Mock
    private AssetGroupRelationDao                         assetGroupRelationDao;
    @Mock
    private AssetDao                                      assetDao;
    @Mock
    private RedisUtil                                     redisUtil;
    @Mock
    private IBaseDao<T>                                   baseDao;

    public ExpectedException expectedException = ExpectedException.none();
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // 模拟用户登录
        LoginUser loginUser = JSONObject.parseObject(
            "{ \"id\":8, \"username\":\"zhangbing\", \"password\":\"$2a$10$hokzLPdz15q9XFuNB8HA0ObV9j301oxkFBlsJUCe/8iWBvql5gBdO\", \"name\":\"张冰\", \"duty\":\"部门经历\", \"department\":\"A是不\", \"phone\":\"123\", \"email\":\"string123@email\", \"status\":1, \"errorCount\":4, \"lastLoginTime\":1553737022175, \"lastModifiedPassword\":1550657104216, \"sysRoles\":[ { \"id\":9, \"code\":\"config_admin\", \"name\":\"配置管理员\", \"description\":\"\" } ], \"areas\":[ { \"id\":10, \"parentId\":2, \"levelType\":2, \"fullName\":\"金牛区\", \"shortName\":\"1\", \"fullSpell\":\"1\", \"shortSpell\":\"1\", \"status\":1, \"demo\":\"\" }, { \"id\":112, \"parentId\":0, \"levelType\":1, \"fullName\":\"四川省成都市\", \"status\":1, \"demo\":\"\" } ], \"enabled\":true, \"accountNonExpired\":true, \"accountNonLocked\":true, \"credentialsNonExpired\":true } ",
            LoginUser.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, "123");
        Map<String, Object> map = new HashMap<>();
        map.put("principal", loginUser);
        token.setDetails(map);

        OAuth2Authentication authentication = Mockito.mock(OAuth2Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);
        SecurityContextHolder.setContext(securityContext);

        PowerMockito.mockStatic(LoginUserUtil.class);
        when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);

        PowerMockito.mockStatic(LogUtils.class);
        PowerMockito.doNothing().when(LogUtils.class, "recordOperLog", Mockito.any(BusinessData.class));


    }

    @Test
    public void saveAssetGroupTest() throws Exception {
        AssetGroupRequest request = new AssetGroupRequest();
        request.setName("zichan");
        String[] ids = { "1" };
        request.setAssetIds(ids);
        AssetGroup assetGroup = getAssetGroup("zichan");

        List<String> assetGroupNameList = new ArrayList<>();
        assetGroupNameList.add("zichan");
        Mockito.when(assetGroupToAssetGroupConverter.convert(request, AssetGroup.class)).thenReturn(assetGroup);
        Mockito.when(assetGroupDao.removeDuplicate(Mockito.any())).thenReturn(false);
        Mockito.when(assetGroupDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupRelationDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any())).thenReturn(assetGroupNameList);
        Mockito.when(assetDao.updateAssetGroupNameWithAssetId(Mockito.any())).thenReturn(1);
        Mockito.when(aesEncoder.encode(assetGroup.getStringId(), LoginUserUtil.getLoginUser().getUsername()))
            .thenReturn("200");
        String actual = assetGroupService.saveAssetGroup(request);
        Assert.assertEquals("200", actual);

        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any())).thenReturn(new ArrayList<>());
        actual = assetGroupService.saveAssetGroup(request);
        Assert.assertEquals("200", actual);

        request.setAssetIds(null);
        Assert.assertEquals("200", actual);
        Mockito.when(assetGroupDao.insert(Mockito.any())).thenReturn(0);
        assetGroupService.saveAssetGroup(request);

        // case
        Mockito.when(assetGroupDao.insert(Mockito.any())).thenReturn(0);
        assetGroupService.saveAssetGroup(request);

        // 名称重复情况
        Mockito.when(assetGroupDao.removeDuplicate(Mockito.anyString())).thenReturn(true);
        Assertions.assertThatThrownBy(() -> assetGroupService.saveAssetGroup(request)).isInstanceOf(BusinessException.class).hasMessage("资产组名称重复");

    }

    @Test
    public void updateAssetGroupTest() throws Exception {
        AssetGroupRequest request = getAssetGroupRequest();
        AssetGroup assetGroup = getAssetGroup("zicha");
        List<String> assetGroupNameList = new ArrayList<>();
        assetGroupNameList.add("test");
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(request, AssetGroup.class)).thenReturn(assetGroup);
        Mockito.when(assetGroupDao.getById(Mockito.any())).thenReturn(assetGroup);

        Mockito.when(assetGroupDao.removeDuplicate(Mockito.any())).thenReturn(false);
        Mockito.when(assetGroupRelationDao.deleteByAssetGroupId(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupDao.update(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any())).thenReturn(assetGroupNameList);
        Mockito.when(assetDao.updateAssetGroupNameWithAssetId(Mockito.anyMap())).thenReturn(1);
        Mockito.when(assetGroupRelationDao.insertBatch(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupRelationDao.findAssetIdByAssetGroupId(Mockito.anyString()))
            .thenReturn(Arrays.asList("1", "2"));
        int result = assetGroupService.updateAssetGroup(request);
        Assert.assertEquals(1, result);

        request.setAssetIds(null);
        result = assetGroupService.updateAssetGroup(request);
        Assert.assertEquals(1, result);

        request.setDeleteAssetIds(new String[] { "1", "2", "3" });
        result = assetGroupService.updateAssetGroup(request);
        Assert.assertEquals(1, result);
        // 名称重复情况
        Mockito.when(assetGroupDao.removeDuplicate(Mockito.anyString())).thenReturn(true);
        try {
            assetGroupService.updateAssetGroup(request);
        } catch (Exception e) {
            Assert.assertEquals("资产组名称重复", e.getMessage());
        }

        // if (!request.getName().equals(groupName))    false
        request = getAssetGroupRequest();
        request.setName(assetGroup.getName());
        String[] ids = { "1" };
        request.setAssetIds(ids);
        PowerMockito.when(BeanConvert.convert(request, AssetGroup.class)).thenReturn(assetGroup);
        result = assetGroupService.updateAssetGroup(request);
        Assert.assertEquals(1, result);

        //  assetGroupDao.update == 0
        Mockito.when(assetGroupDao.update(Mockito.any())).thenReturn(0);
        result = assetGroupService.updateAssetGroup(request);
        Assert.assertEquals(0, result);

        // case: 资产关联的资产组超过10个

        Mockito.when(assetGroupDao.removeDuplicate(Mockito.anyString())).thenReturn(false);
        assetGroupNameList.add("test1");
        assetGroupNameList.add("test2");
        assetGroupNameList.add("test3");
        assetGroupNameList.add("test4");
        assetGroupNameList.add("test5");
        assetGroupNameList.add("test6");
        assetGroupNameList.add("test7");
        assetGroupNameList.add("test8");
        assetGroupNameList.add("test9");
        assetGroupNameList.add("test10");
        assetGroupNameList.add("test11");
        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any())).thenReturn(assetGroupNameList);
        AssetGroupRequest finalRequest = request;
        PowerMockito.when(BeanConvert.convert(finalRequest, AssetGroup.class)).thenReturn(assetGroup);

        Assertions.assertThatThrownBy(() -> assetGroupService.updateAssetGroup(finalRequest)).hasMessage("资产关联的资产组不能超过10个");
    }

    /**
     * 关联关系库中不存在,需要新增
     * @throws Exception
     */
    @Test
    public void updateAssetGroupTest2() throws Exception {
        AssetGroupRequest request = getAssetGroupRequest();
        AssetGroup assetGroup = getAssetGroup("zicha");

        Mockito.when(assetGroupDao.getById(Mockito.any())).thenReturn(assetGroup);

        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(request, AssetGroup.class)).thenReturn(assetGroup);

        // case1: 库中不存在,需要新增
        List<AssetGroupRelation> existedRelationList = new ArrayList<>();
        Mockito.when(assetGroupRelationDao.listRelationByGroupId(Mockito.eq(DataTypeUtils.stringToInteger(request.getId()))))
                .thenReturn(existedRelationList);

        Mockito.when(assetGroupRelationDao.insertBatch(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupDao.update(Mockito.any(AssetGroup.class))).thenReturn(1);
        // 更新资产主表信息
        List<String> assetGroupNameList = new ArrayList<>();
        assetGroupNameList.add("test");
        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any())).thenReturn(assetGroupNameList);
        Assert.assertEquals(Integer.valueOf(1), assetGroupService.updateAssetGroup(request));

        // case2: 库中不存在,需要新增
        AssetGroupRelation relation = new AssetGroupRelation();
        relation.setAssetId("3");
        AssetGroupRelation relation2 = new AssetGroupRelation();
        relation2.setAssetId("1");
        existedRelationList.add(relation);
        existedRelationList.add(relation2);
        Mockito.when(assetGroupRelationDao.listRelationByGroupId(Mockito.eq(DataTypeUtils.stringToInteger(request.getId()))))
                .thenReturn(existedRelationList);
        Mockito.when(assetGroupRelationDao.deleteBatch(Mockito.anyList())).thenReturn(1);

        Assert.assertEquals(Integer.valueOf(1), assetGroupService.updateAssetGroup(request));

        // case 删除返回  0
        Mockito.when(assetGroupRelationDao.deleteBatch(Mockito.anyList())).thenReturn(0);
        Assert.assertEquals(Integer.valueOf(1), assetGroupService.updateAssetGroup(request));

        // case： 没有资产Id
        request.setAssetIds(null);
        Assert.assertEquals(Integer.valueOf(1), assetGroupService.updateAssetGroup(request));



        // 大于10个
        List<String> assetIdList = new ArrayList<>();
        assetIdList.add("1");
        assetIdList.add("2");
        when(assetGroupRelationDao.findAssetIdByAssetGroupId(request.getId())).thenReturn(assetIdList);

        assetGroupNameList.add("test1");
        assetGroupNameList.add("test2");
        assetGroupNameList.add("test3");
        assetGroupNameList.add("test4");
        assetGroupNameList.add("test5");
        assetGroupNameList.add("test6");
        assetGroupNameList.add("test7");
        assetGroupNameList.add("test8");
        assetGroupNameList.add("test9");
        assetGroupNameList.add("test10");
        assetGroupNameList.add("test11");
        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any())).thenReturn(assetGroupNameList);
        PowerMockito.when(BeanConvert.convert(request, AssetGroup.class)).thenReturn(assetGroup);

        Assertions.assertThatThrownBy(() -> assetGroupService.updateAssetGroup(request)).hasMessage("资产关联的资产组不能超过10个");

    }

    /**
     * case: 参数中没有资产ids;
     * findAssetGroupNameByAssetId异常
     */
    @Test
    public void updateAssetGroupTest3() throws Exception {
        AssetGroupRequest request = getAssetGroupRequest();
        request.setAssetIds(null);
        AssetGroup assetGroup = getAssetGroup("zicha");
        PowerMockito.mockStatic(BeanConvert.class);
        PowerMockito.when(BeanConvert.convert(request, AssetGroup.class)).thenReturn(assetGroup);
        Mockito.when(assetGroupDao.getById(Mockito.any())).thenReturn(assetGroup);

        Mockito.when(assetGroupDao.removeDuplicate(Mockito.any())).thenReturn(false);
        // case1: 库中不存在,需要新增
        List<AssetGroupRelation> existedRelationList = new ArrayList<>();
        Mockito.when(assetGroupRelationDao.listRelationByGroupId(Mockito.eq(DataTypeUtils.stringToInteger(request.getId()))))
                .thenReturn(existedRelationList);
        Mockito.when(assetGroupDao.update(Mockito.any())).thenReturn(1);
        List<String> assetIdList = new ArrayList<>();
        assetIdList.add("1");
        Mockito.when(assetGroupRelationDao.findAssetIdByAssetGroupId(Mockito.anyString())).thenReturn(assetIdList);
        Mockito.when(assetGroupRelationDao.findAssetGroupNameByAssetId(Mockito.any()))
                .thenThrow(new BusinessException("123"));

        expectedException.expectMessage("123");
        assetGroupService.updateAssetGroup(request);

    }


    private AssetGroup getAssetGroup(String zicha) {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setName(zicha);
        assetGroup.setId(1);
        return assetGroup;
    }

    private AssetGroupRequest getAssetGroupRequest() {
        AssetGroupRequest request = new AssetGroupRequest();
        request.setName("zichan");
        request.setId("1");
        String[] ids = { "1" };
        request.setAssetIds(ids);
        return request;
    }

    @Test
    public void findListAssetGroupTest() throws Exception {
        AssetGroupQuery query = new AssetGroupQuery();
        query.setCreateUser("test");

        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setCreateUserName("test");
        List<AssetGroup> assetGroupList = new ArrayList<>();
        assetGroupList.add(assetGroup);
        AssetGroupResponse assetGroupResponse = new AssetGroupResponse();
        assetGroupResponse.setCreateUserName("test");
        assetGroupResponse.setStringId("1");
        List<AssetGroupResponse> assetResponseList = new ArrayList<>();
        assetResponseList.add(assetGroupResponse);
        List<String> assetList = new ArrayList<>();
        assetList.add("test");
        SysUser sysUser = new SysUser();
        sysUser.setName("test");
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
            assetGroup.getCreateUser());
        Mockito.when(redisUtil.getObject(key, SysUser.class)).thenReturn(sysUser);
        Mockito.when(assetGroupDao.findCreateUser()).thenReturn(assetGroupList);
        Mockito.when(assetGroupDao.findQuery(Mockito.any())).thenReturn(assetGroupList);
        Mockito.when(assetGroupToResponseConverter.convert(assetGroupList, AssetGroupResponse.class))
            .thenReturn(assetResponseList);
        Mockito.when(assetGroupRelationDao.findAssetNameByAssetGroupId(Mockito.any())).thenReturn(assetList);
        List<AssetGroupResponse> actual = assetGroupService.findListAssetGroup(query);
        Assert.assertEquals(assetResponseList.size(), actual.size());

        Mockito.when(assetGroupRelationDao.findAssetNameByAssetGroupId(Mockito.any()))
            .thenReturn(Arrays.asList("1", "2", "3"));
        Assert.assertEquals(assetResponseList.size(), actual.size());

        // 资产关联多个资产组,需要拼接多个名称
        assetList.add("test2");
        Mockito.when(assetGroupRelationDao.findAssetNameByAssetGroupId(Mockito.any())).thenReturn(assetList);
        actual = assetGroupService.findListAssetGroup(query);
        Assert.assertEquals(assetResponseList.size(), actual.size());

        // redis user为null
        Mockito.when(redisUtil.getObject(key, SysUser.class)).thenReturn(null);
        actual = assetGroupService.findListAssetGroup(query);

        Assert.assertEquals(assetResponseList.size(), actual.size());
    }

    @Test
    public void removeAssociateAssetTest() {
        Mockito.when(assetGroupRelationDao.batchDeleteById(Mockito.any())).thenReturn(1);
        Assert.assertEquals(1 + "", assetGroupService.removeAssociateAsset(new RemoveAssociateAssetRequest()) + "");
    }

    @Test
    public void findPageAssetGroup() throws Exception {
        AssetGroupQuery query = new AssetGroupQuery();
        query.setCreateUser("test");

        List<AssetGroupResponse> assetResponseList = new ArrayList<>();
        Mockito.when(baseDao.findCount(Mockito.any())).thenReturn(1);
        PageResult<AssetGroupResponse> pageResult = new PageResult<>(10, 1, 1, assetResponseList);
        PageResult<AssetGroupResponse> actual = assetGroupService.findPageAssetGroup(query);
        Assert.assertEquals(actual.getItems().size(), pageResult.getItems().size());
    }

    @Test
    public void queryGroupInfoTest() throws Exception {
        List<AssetGroup> assetGroupList = new ArrayList<>();
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setCreateUserName("test");
        assetGroupList.add(assetGroup);
        List<SelectResponse> selectResponseList = new ArrayList<>();
        SelectResponse selectResponse = new SelectResponse();
        selectResponse.setValue("test");
        selectResponseList.add(selectResponse);
        Mockito.when(assetGroupDao.findPulldownGroup()).thenReturn(assetGroupList);
        Mockito.when(selectConvert.convert(assetGroupList, SelectResponse.class)).thenReturn(selectResponseList);

        List<SelectResponse> actual = assetGroupService.queryGroupInfo();
        Assert.assertEquals(selectResponseList.size(), actual.size());
    }

    @Test
    public void queryUnconnectedGroupInfoTest() throws Exception {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setId(1);
        assetGroup.setName("test");
        assetGroup.setCreateUser(1);
        List<AssetGroup> assetGroupList = new ArrayList<>();
        assetGroupList.add(assetGroup);
        Mockito.when(assetGroupDao.findPulldownUnconnectedGroup(Mockito.any())).thenReturn(assetGroupList);
        List<SelectResponse> actual = assetGroupService.queryUnconnectedGroupInfo(1, "1");
        Assert.assertEquals("test", actual.get(0).getValue());

        actual = assetGroupService.queryUnconnectedGroupInfo(0, "1");
        Assert.assertEquals("test", actual.get(0).getValue());

        actual = assetGroupService.queryUnconnectedGroupInfo(null, "1");
        Assert.assertEquals("test", actual.get(0).getValue());

        actual = assetGroupService.queryUnconnectedGroupInfo(2, "1");
        Assert.assertEquals("test", actual.get(0).getValue());
    }

    @Test
    public void findGroupByIdTest() throws Exception {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setName("test");
        AssetGroupResponse assetGroupResponse = new AssetGroupResponse();
        assetGroupResponse.setName("test");
        Mockito.when(assetGroupDao.getById(Mockito.any())).thenReturn(assetGroup);
        Mockito.when(assetGroupToResponseConverter.convert(assetGroup, AssetGroupResponse.class))
            .thenReturn(assetGroupResponse);
        AssetGroupResponse actual = assetGroupService.findGroupById("1");
        Assert.assertEquals(assetGroupResponse.getName(), actual.getName());
    }

    @Test
    public void queryCreateUser() throws Exception {
        List<AssetGroup> assetGroupList = new ArrayList<>();
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setCreateUserName("admin");
        assetGroup.setCreateUser(1);
        assetGroupList.add(assetGroup);
        List<SelectResponse> selectResponseList = new ArrayList<>();
        SelectResponse selectResponse = new SelectResponse();
        selectResponse.setValue("test");
        selectResponseList.add(selectResponse);

        SysUser sysUser = new SysUser();
        sysUser.setName("test");
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
            assetGroup.getCreateUser());
        Mockito.when(redisUtil.getObject(key, SysUser.class)).thenReturn(sysUser);
        Mockito.when(assetGroupDao.findCreateUser()).thenReturn(assetGroupList);

        List<SelectResponse> actual = assetGroupService.queryCreateUser();
        Assert.assertEquals(selectResponseList.size(), actual.size());

        // case:redis获取用户失败
        Mockito.when(redisUtil.getObject(key, SysUser.class)).thenReturn(null);
        Mockito.when(assetGroupDao.findCreateUser()).thenReturn(assetGroupList);
        Assert.assertEquals(0,  assetGroupService.queryCreateUser().size());

    }

    @Test
    public void deleteByIdTest() throws Exception {
        Mockito.when(assetGroupRelationDao.existRelateAssetInGroup(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupDao.deleteById(Mockito.any())).thenReturn(1);
        try {
            assetGroupService.deleteById("1");
        } catch (Exception e) {
            Assert.assertEquals("您已关联对应资产，无法进行注销", e.getMessage());
        }

        Mockito.when(assetGroupRelationDao.existRelateAssetInGroup(Mockito.any())).thenReturn(0);
        Assert.assertEquals(1 + "", assetGroupService.deleteById("1") + "");

    }

}
