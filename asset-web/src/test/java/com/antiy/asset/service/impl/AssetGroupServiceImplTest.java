package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.poi.ss.formula.functions.T;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(PowerMockRunner.class)
@SpringBootTest
@PrepareForTest(BeanConvert.class)
public class AssetGroupServiceImplTest {
    @InjectMocks
    private AssetGroupServiceImpl assetGroupService;
    @Mock
    private AssetGroupDao assetGroupDao;
    @Mock
    private BaseConverter<AssetGroup, AssetGroupResponse> assetGroupToResponseConverter;
    @Mock
    private BaseConverter<AssetGroupRequest, AssetGroup> assetGroupToAssetGroupConverter;
    @Mock
    private AesEncoder aesEncoder;
    @Mock
    private SelectConvert selectConvert;
    @Mock
    private AssetGroupRelationDao assetGroupRelationDao;
    @Mock
    private AssetDao assetDao;
    @Mock
    private RedisUtil redisUtil;
    @Mock
    private IBaseDao<T> baseDao;
    @Mock
    private IAssetCategoryModelService assetCategoryModelService;

    @Before
    public void setUp()throws Exception{
        MockitoAnnotations.initMocks(this);
        LoginUtil.generateDefaultLoginUser();

    }

    @Test
    public void saveAssetGroupTest()throws Exception{
        AssetGroupRequest request = new AssetGroupRequest();
        request.setName("zichan");
        String [] ids = {"1"};
        request.setAssetIds(ids);
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setName("zichan");
        assetGroup.setId(1);

        List<String> assetGroupNameList = new ArrayList<>();
        assetGroupNameList.add("zichan");
        Mockito.when(assetGroupToAssetGroupConverter.convert(request, AssetGroup.class)).thenReturn(assetGroup);
        Mockito.when(assetGroupDao.removeDuplicate(Mockito.any())).thenReturn(false);
        Mockito.when( assetGroupDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupRelationDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(assetGroupRelationDao
                .findAssetGroupNameByAssetId(Mockito.any())).thenReturn(assetGroupNameList);
        Mockito.when( assetDao.updateAssetGroupNameWithAssetId(Mockito.any())).thenReturn(1);
        Mockito.when(aesEncoder.encode(assetGroup.getStringId(), LoginUserUtil.getLoginUser().getUsername())).thenReturn("200");
        String actual = assetGroupService.saveAssetGroup(request);
        Assert.assertEquals("200",actual);
    }

    @Test
    public void updateAssetGroupTest()throws Exception{
        AssetGroupRequest request = new AssetGroupRequest();
        request.setName("zichan");
        request.setId("1");
        String[] ids = {"1"};
        request.setAssetIds(ids);
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setName("zicha");
        assetGroup.setId(1);
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
        int result = assetGroupService.updateAssetGroup(request);
        Assert.assertEquals(1, result);
    }

    @Test
    public void findListAssetGroupTest()throws Exception{
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
        Mockito.when( assetGroupDao.findQuery(Mockito.any())).thenReturn(assetGroupList);
        Mockito.when(assetGroupToResponseConverter.convert(assetGroupList,
                AssetGroupResponse.class)).thenReturn(assetResponseList);
        Mockito.when(assetGroupRelationDao
                .findAssetNameByAssetGroupId(Mockito.any())).thenReturn(assetList);
        List<AssetGroupResponse> actual = assetGroupService.findListAssetGroup(query);
        Assert.assertTrue(assetResponseList.size()==actual.size());
    }
    @Test
    public void findPageAssetGroup()throws Exception{
        AssetGroupQuery query = new AssetGroupQuery();
        query.setCreateUser("test");

        List<AssetGroupResponse> assetResponseList = new ArrayList<>();
        Mockito.when(baseDao.findCount(Mockito.any())).thenReturn(1);
        PageResult<AssetGroupResponse> pageResult = new PageResult<>(10,1,1,assetResponseList);
        PageResult<AssetGroupResponse> actual = assetGroupService.findPageAssetGroup(query);
        Assert.assertEquals(actual.getItems().size(),pageResult.getItems().size());
    }
    @Test
    public void queryGroupInfoTest()throws Exception{
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
        Assert.assertTrue(selectResponseList.size() ==actual.size());
    }
    @Test
    public void queryUnconnectedGroupInfoTest()throws Exception{
        AssetCategoryModel assetCategoryModel = new AssetCategoryModel();
        assetCategoryModel.setId(1);
        List<AssetCategoryModel> all = new ArrayList<>();
        all.add(assetCategoryModel);
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("1","计算设备");
        categoryMap.put("2","网络设备");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setId(1);
        assetGroup.setName("test");
        assetGroup.setCreateUser(1);
        List<AssetGroup> assetGroupList = new ArrayList<>();
        assetGroupList.add(assetGroup);
        String secondCategoryName="second";
        Mockito.when(assetCategoryModelService.getAll()).thenReturn(all);
        Mockito.when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(categoryMap);
        Mockito.when(assetCategoryModelService.findAssetCategoryModelIdsById(1)).thenReturn(list);
        Mockito.when(assetCategoryModelService.findAssetCategoryModelIdsById(2)).thenReturn(list2);
        Mockito.when(assetGroupDao.findPulldownUnconnectedGroup(Mockito.any())).thenReturn(assetGroupList);
        List<SelectResponse> actual = assetGroupService.queryUnconnectedGroupInfo(secondCategoryName);
    }
    @Test
    public void findGroupByIdTest()throws Exception{
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setName("test");
        AssetGroupResponse assetGroupResponse = new AssetGroupResponse();
        assetGroupResponse.setName("test");
        Mockito.when(assetGroupDao.getById(Mockito.any())).thenReturn(assetGroup);
        Mockito.when(assetGroupToResponseConverter.convert(assetGroup, AssetGroupResponse.class)).thenReturn(assetGroupResponse);
        AssetGroupResponse actual = assetGroupService.findGroupById("1");
        Assert.assertEquals(assetGroupResponse.getName(),actual.getName());
    }

    @Test
    public void queryCreateUser()throws Exception{
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
        Assert.assertTrue(selectResponseList.size() == actual.size());
    }
}
