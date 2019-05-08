package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.sun.istack.NotNull;
import org.apache.poi.ss.formula.functions.T;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AssetLinkRelationServiceImplTest {

    /**
     * 被测试service
     */
    @InjectMocks
    private AssetLinkRelationServiceImpl service;

    /**
     * 模拟对象
     */
    @Mock
    private AssetLinkRelationDao assetLinkRelationDao;
    @Mock
    private AssetLinkRelationQuery assetLinkRelationQuery;
    @Mock
    private IBaseDao<T> baseDao;
    @Mock
    private AssetNetworkEquipmentDao assetNetworkEquipmentDao;
    @Mock
    private IAssetCategoryModelService assetCategoryModelService;
    @Mock
    private IAssetCategoryModelService iAssetCategoryModelService;
    /**
     * 执行具体逻辑对象，不可删除
     */
    @Spy
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation> requestConverter;
    @Spy
    private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;

    /**
     * 注入假用户信息到上下文
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setLoginUser(getLoginUser());
    }

    /**
     * 从上下文删除用户信息
     * 不同test中注入的假用户信息会互相影响
     * 必须删除
     */
    @After
    public void tearDown() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * 保存测试
     * 传入请求数据，向数据库写入数据，返回写入结果
     * 断言写入结果
     * @throws Exception except
     */
    @Test
    public void saveAssetLinkRelation() throws Exception {
        AssetLinkRelationRequest request = new AssetLinkRelationRequest();
        request.setAssetId("2333");
        request.setParentAssetId("666");
        request.setAssetPort("443");
        request.setAssetIp("0.0.0.110");
        request.setParentAssetIp("0.0.0.111");
        request.setParentAssetPort("998");
        List<String> strings = new ArrayList<>();
        strings.add("0.0.0.110");
        strings.add("0.0.0.111");
        Mockito.when(assetLinkRelationDao.queryIpAddressByAssetId(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString())).thenReturn(strings);
        Mockito.when(assetLinkRelationDao.insert(Mockito.any())).thenReturn(110);

        boolean s = service.saveAssetLinkRelation(request);
        assertThat(s, equalTo(false));
    }

    /**
     * 更新测试
     * 传入请求数据，向数据库更新数据，返回更新结果
     * 断言更新结果
     * @throws Exception except
     */
    @Test
    public void updateAssetLinkRelation() throws Exception {
        AssetLinkRelationRequest request = new AssetLinkRelationRequest();
        request.setAssetId("2333");
        request.setParentAssetId("666");
        request.setAssetPort("443");
        request.setAssetIp("0.0.0.110");
        request.setParentAssetIp("0.0.0.111");
        request.setParentAssetPort("998");
        List<String> strings = new ArrayList<>();
        strings.add("0.0.0.110");
        strings.add("0.0.0.111");
        Mockito.when(assetLinkRelationDao.update(Mockito.any())).thenReturn(2333);
        Mockito.when(assetLinkRelationDao.queryIpAddressByAssetId(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString())).thenReturn(strings);

        String s = service.updateAssetLinkRelation(request);
        assertThat(s, equalTo("2333"));
    }

    /**
     * 列表查询测试
     * 传入查询参数，向数据库查询信息，返回信息列表
     * 断言返回信息列表大小
     * @throws Exception except
     */
    @Test
    public void queryListAssetLinkRelation() throws Exception {
        List<AssetLinkRelation> assetLinkRelationList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
//        assetLinkRelation.setAssetId(2333);
        assetLinkRelationList.add(assetLinkRelation);
        Mockito.when(assetLinkRelationDao.findQuery(Mockito.any())).thenReturn(assetLinkRelationList);

        List<AssetLinkRelationResponse> list = service.queryListAssetLinkRelation(new AssetLinkRelationQuery());
        assertThat(list.size(), equalTo(1));
    }

    /**
     * 分页查询测试
     * 传入查询参数，向数据库查询数据，返回分页查询结果
     * 断言分页总记录
     * @throws Exception except
     */
    @Test
    public void queryPageAssetLinkRelation() throws Exception {
        List<AssetLinkRelation> assetLinkRelationList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
//        assetLinkRelation.setAssetId(2333);
        assetLinkRelationList.add(assetLinkRelation);
        Mockito.when(assetLinkRelationDao.findQuery(Mockito.any())).thenReturn(assetLinkRelationList);
        Mockito.when(baseDao.findCount(Mockito.any())).thenReturn(110);

        PageResult<AssetLinkRelationResponse> result = service.queryPageAssetLinkRelation(new AssetLinkRelationQuery());
        assertThat(result.getTotalRecords(), equalTo(110));
    }

    /**
     * 通过id查询测试
     * 传入查询参数，向数据库查询数据，返回查询结果
     * 断言查询结果id
     * @throws Exception except
     */
    @Test
    public void queryAssetLinkRelationById() throws Exception {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setPrimaryKey("2333");
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
//        assetLinkRelation.setAssetId(1);
        assetLinkRelation.setId(66666);
        Mockito.when(assetLinkRelationDao.getById(Mockito.any())).thenReturn(assetLinkRelation);

        AssetLinkRelationResponse assetLinkRelationResponse = service.queryAssetLinkRelationById(queryCondition);
        assertThat(assetLinkRelationResponse.getStringId(), equalTo("66666"));
    }

    /**
     * 通过id删除测试
     * 传入查询参数，从数据库查询数据，返回删除结果
     * 断言删除结果
     * @throws Exception except
     */
    @Test
    public void deleteAssetLinkRelationById() throws Exception {
        Mockito.when(assetLinkRelationDao.deleteById(Mockito.any())).thenReturn(2333);
        BaseRequest request = new BaseRequest();
        request.setStringId("110");

        String s = service.deleteAssetLinkRelationById(request);
        assertThat(s, equalTo("2333"));
    }

    /**
     * 分页查询测试
     * 情景1：传入查询参数的主键不为空，数据库存在关联资产和关联资产信息，返回分页查询结果
     * 断言分页查询结果记录
     */
    @Test
    public void queryAssetPage() {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setPrimaryKey("2333");
        List<String> strings = new ArrayList<>();
        strings.add("123");
        strings.add("321");
        List<Asset> assetResponseList = new ArrayList<>();
        Asset asset = new Asset();
        asset.setAreaId("998");
        assetResponseList.add(asset);
        Mockito.when(assetLinkRelationDao.queryAssetList(Mockito.any())).thenReturn(assetResponseList);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetById(Mockito.anyString())).thenReturn(strings);

        PageResult<AssetResponse> result = service.queryAssetPage(assetQuery);
        assertThat(result.getTotalRecords(), equalTo(1));
    }

    /**
     * 分页参数测试
     * 情景2：传入查询参数的主键不为空，数据库不存在关联资产和关联资产信息，返回空
     * 断言查询结果为空
     */
    @Test
    public void queryAssetPageEmpty() {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setPrimaryKey("2333");
        Mockito.when(assetLinkRelationDao.queryAssetList(Mockito.any())).thenReturn(null);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetById(Mockito.anyString())).thenReturn(null);

        PageResult<AssetResponse> result = service.queryAssetPage(assetQuery);
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 分页查询测试
     * 情景3：传入查询参数的主键为空，数据库中存在资产，不存在资产信息，返回空
     * 断言查询结果为空
     */
    @Test
    public void queryAssetPageEmptyKey() {
        AssetQuery assetQuery = new AssetQuery();
        Mockito.when(assetLinkRelationDao.queryAssetList(Mockito.any())).thenReturn(null);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetWithoutId()).thenReturn(null);

        PageResult<AssetResponse> result = service.queryAssetPage(assetQuery);
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 分页查询测试
     * 情景4：传入查询参数的主键为空，数据库中不存在资产和资产信息，返回空
     * 断言查询结果为空
     */
    @Test
    public void queryAssetPageEmptyKey1() {
        AssetQuery assetQuery = new AssetQuery();
        List<String> strings = new ArrayList<>();
        strings.add("123");
        strings.add("321");
        Mockito.when(assetLinkRelationDao.queryAssetList(Mockito.any())).thenReturn(null);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetWithoutId()).thenReturn(strings);

        PageResult<AssetResponse> result = service.queryAssetPage(assetQuery);
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 查询列表测试
     * 情景1：传入查询参数，数据库存在对应信息，返回查询结果列表
     * 断言查询结果
     */
    @Test
    public void queryAssetList() {
        List<Asset> assetResponseList = new ArrayList<>();
        Asset asset = new Asset();
        asset.setAreaId("998");
        assetResponseList.add(asset);
        AssetQuery assetQuery = new AssetQuery();
        Mockito.when(assetLinkRelationDao.queryAssetList(Mockito.any())).thenReturn(assetResponseList);

        List<AssetResponse> list = service.queryAssetList(assetQuery);
        assertThat(list.get(0).getAreaId(), equalTo("998"));
    }

    /**
     * 查询列表测试
     * 情景2：传入查询参数，数据库不存在对应信息，返回空列表
     * 断言查询结果为空
     */
    @Test
    public void queryAssetListEmpty() {
        AssetQuery assetQuery = new AssetQuery();
        Mockito.when(assetLinkRelationDao.queryAssetList(Mockito.any())).thenReturn(null);

        List<AssetResponse> list = service.queryAssetList(assetQuery);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 分页查询链接列表测试
     * 情景1：传入查询参数，数据库存在对应信息，返回分页查询结果
     * 断言分页查询结果
     */
    @Test
    public void queryLinekedRelationPage() {
        List<AssetLinkRelation> assetResponseList = new ArrayList<>();
        AssetLinkRelation relation = new AssetLinkRelation();
//        relation.setAssetId(10010);
        assetResponseList.add(relation);
        Mockito.when(assetLinkRelationDao.queryLinekedRelationList(Mockito.any())).thenReturn(assetResponseList);

        PageResult<AssetLinkRelationResponse> result = service.queryLinekedRelationPage(assetLinkRelationQuery);
        assertThat(result.getCurrentPage(), equalTo(1));
    }

    /**
     * 分页查询链接列表测试
     * 情景2：传入查询参数，数据库不存在对应信息，返回分页结果为空
     * 断言分页结果为空
     */
    @Test
    public void queryLinekedRelationPageEmpty() {
        Mockito.when(assetLinkRelationDao.queryLinekedRelationList(Mockito.any())).thenReturn(null);

        PageResult<AssetLinkRelationResponse> result = service.queryLinekedRelationPage(assetLinkRelationQuery);
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 查询链接列表测试
     * 情景1：传入查询参数，数据库存在对应信息，返回查询结果列表
     * 断言返回列表大小
     */
    @Test
    public void queryLinekedRelationList() {
        List<AssetLinkRelation> assetResponseList = new ArrayList<>();
        AssetLinkRelation relation = new AssetLinkRelation();
//        relation.setAssetId(10010);
        assetResponseList.add(relation);
        Mockito.when(assetLinkRelationDao.queryLinekedRelationList(Mockito.any())).thenReturn(assetResponseList);

        List<AssetLinkRelationResponse> list = service.queryLinekedRelationList(assetLinkRelationQuery);
        assertThat(list.size(), equalTo(1));
    }

    /**
     * 查询链接列表测试
     * 情景2：传入查询参数，数据库无对应信息，返回空列表
     * 断言返回列表为空
     */
    @Test
    public void queryLinekedRelationListNull() {
        Mockito.when(assetLinkRelationDao.queryLinekedRelationList(Mockito.any())).thenReturn(null);

        List<AssetLinkRelationResponse> list = service.queryLinekedRelationList(assetLinkRelationQuery);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 通过AssetId查询Ip测试
     * 传入id和bool值，从数据库查询，返回查询结果列表
     * 断言查询结果的值
     * @throws Exception except
     */
    @Test
    public void queryIpAddressByAssetId() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("123");
        Mockito.when(assetLinkRelationDao.queryIpAddressByAssetId(Mockito.anyString(), Mockito.anyBoolean(), Mockito.any())).thenReturn(list);

        List<String> strings = service.queryIpAddressByAssetId("id", true);
        assertThat(strings.get(0), equalTo("123"));
    }

    /**
     * 通过id查询端口测试
     * 传入查询参数，返回未被占用端口组成的列表响应
     * 断言返回结果不为空
     */
    @Test
    public void queryPortById() {
        List<Integer> list = new ArrayList<>();
        list.add(233);
        list.add(23);
        QueryCondition queryCondition= new QueryCondition();
        queryCondition.setPrimaryKey("1");
        Mockito.when(assetNetworkEquipmentDao.findPortAmount(Mockito.any())).thenReturn(998);
        Mockito.when(assetLinkRelationDao.findUsePort(Mockito.anyString())).thenReturn(list);

        List<SelectResponse> responses = service.queryPortById(queryCondition);
        assertThat(responses, notNullValue());
    }

    /**
     * 通过id查询端口测试
     * 传入查询参数，无可用端口，返回空
     * 断言返回为空
     */
    @Test
    public void queryPortByIdNull() {
        List<Integer> list = new ArrayList<>();
        list.add(233);
        list.add(23);
        QueryCondition queryCondition= new QueryCondition();
        queryCondition.setPrimaryKey("1");
        Mockito.when(assetNetworkEquipmentDao.findPortAmount(Mockito.any())).thenReturn(null);
        Mockito.when(assetLinkRelationDao.findUsePort(Mockito.anyString())).thenReturn(list);

        List<SelectResponse> responses = service.queryPortById(queryCondition);
        assertThat(responses.size(), equalTo(0));
    }

    /**
     * 通过id查询端口测试
     * 传入查询参数，可用端口为1，返回空
     * 断言返回为空
     */
    @Test
    public void queryPortByIdLess() {
        List<Integer> list = new ArrayList<>();
        list.add(233);
        list.add(23);
        QueryCondition queryCondition= new QueryCondition();
        queryCondition.setPrimaryKey("1");
        Mockito.when(assetNetworkEquipmentDao.findPortAmount(Mockito.any())).thenReturn(1);
        Mockito.when(assetLinkRelationDao.findUsePort(Mockito.anyString())).thenReturn(list);

        List<SelectResponse> responses = service.queryPortById(queryCondition);
        assertThat(responses.size(), equalTo(0));
    }

    /**
     * 分页查询资产联系测试
     * 情景1：传入查询参数，数据库存在关联数据，返回关联数据
     * 断言返回结果不为空
     * @throws Exception except
     */
    @Test
    public void queryAssetLinkedCountPage() throws Exception {
        List<Integer> integers = new ArrayList<>();
        integers.add(233);
        integers.add(456);
        List<AssetCategoryModel> list = new ArrayList<>();
        AssetCategoryModel model = new AssetCategoryModel();
        list.add(model);
        Map<String, String> category = new HashMap<>();
        category.put("1", "计算设备");
        category.put("2", "网络设备");
        List<AssetLinkedCount> counts = new ArrayList<>();
        AssetLinkedCount count = new AssetLinkedCount();
        count.setName("1");
        counts.add(count);
        Mockito.when(assetCategoryModelService.findAssetCategoryModelIdsById(Mockito.anyInt())).thenReturn(integers);
        Mockito.when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(category);
        Mockito.when(assetLinkRelationDao.queryAssetLinkedCountList(Mockito.any())).thenReturn(counts);
        Mockito.when(iAssetCategoryModelService.getSecondCategoryMap()).thenReturn(category);
        Mockito.when(iAssetCategoryModelService.getAll()).thenReturn(list);

        PageResult<AssetLinkedCountResponse> result = service.queryAssetLinkedCountPage(new AssetLinkRelationQuery());
        assertThat(result, notNullValue());
    }

    /**
     * 分页查询资产联系测试
     * 情景2：传入查询参数，数据库不存在关联数据，返回空
     * 断言返回结果为空
     * @throws Exception except
     */
    @Test
    public void queryAssetLinkedCountPageEmpty() throws Exception {
        Map<String, String> category = new HashMap<>();
        category.put("1", "计算设备");
        category.put("2", "网络设备");
        Mockito.doThrow(new NullPointerException()).when(assetCategoryModelService).findAssetCategoryModelIdsById(Mockito.anyInt());
        Mockito.when(assetCategoryModelService.getSecondCategoryMap()).thenReturn(category);
        Mockito.when(assetLinkRelationDao.queryAssetLinkedCountList(Mockito.any())).thenReturn(null);

        PageResult<AssetLinkedCountResponse> result = service.queryAssetLinkedCountPage(new AssetLinkRelationQuery());
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 查找资产联系列表测试
     * 情景1：传入查询参数，上下文中不存在用户信息，数据库中不存在对应信息，返回空列表
     * 断言返回列表为空
     * @throws Exception except
     */
    @Test
    public void queryAssetLinkedCountList() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        List<Integer> integers = new ArrayList<>();
        integers.add(233);
        integers.add(456);
        Mockito.when(assetCategoryModelService.findAssetCategoryModelIdsById(Mockito.anyInt())).thenReturn(integers);
        Mockito.when(assetLinkRelationDao.queryAssetLinkedCountList(Mockito.any())).thenReturn(null);

        List<AssetLinkedCountResponse> result = service.queryAssetLinkedCountList(new AssetLinkRelationQuery());
        assertThat(result.size(), equalTo(0));
    }

    /**
     * 查找资产联系列表测试
     * 情景2：传入查询参数，上下文中存在用户信息，数据库中存在对应信息，返回列表
     * 断言返回列表大小
     * @throws Exception except
     */
    @Test
    public void queryAssetLinkedCountListIf() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        List<Integer> integers = new ArrayList<>();
        integers.add(233);
        integers.add(456);
        List<AssetLinkedCount> list = new ArrayList<>();
        AssetLinkedCount count = new AssetLinkedCount();
        AssetLinkedCount count1 = new AssetLinkedCount();
        count1.setCategoryModel("123");
        list.add(count1);
        list.add(count);
        list.add(count1);
        Mockito.when(assetCategoryModelService.findAssetCategoryModelIdsById(Mockito.anyInt())).thenReturn(integers);
        Mockito.when(assetLinkRelationDao.queryAssetLinkedCountList(Mockito.any())).thenReturn(list);
        Mockito.when(iAssetCategoryModelService.recursionSearchParentCategory(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("");

        List<AssetLinkedCountResponse> result = service.queryAssetLinkedCountList(new AssetLinkRelationQuery());
        assertThat(result.size(), equalTo(3));
    }

    /**
     * 通过资产id查询资产关联数据列表测试
     * 情景1：传入查询参数，数据库存在关联数据，返回关联数据列表
     * 断言列表大小
     */
    @Test
    public void queryLinkedAssetListByAssetId() throws Exception {
        List<AssetLinkRelation> assetResponseList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        AssetLinkRelation assetLinkRelation1 = new AssetLinkRelation();
        assetLinkRelation1.setCategoryModel("123");
        assetResponseList.add(assetLinkRelation1);
        assetResponseList.add(assetLinkRelation);
        assetResponseList.add(assetLinkRelation1);
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setPrimaryKey("110");
        Mockito.when(assetLinkRelationDao.queryPortSize(Mockito.any())).thenReturn(233);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetListByAssetId(Mockito.any())).thenReturn(assetResponseList);
        Mockito.when(iAssetCategoryModelService.recursionSearchParentCategory(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("123");

        List<AssetLinkRelationResponse> list = service.queryLinkedAssetListByAssetId(query);
        assertThat(list.size(), equalTo(3));
    }

    /**
     * 通过资产id查询资产关联数据列表测试
     * 情景2：传入查询参数，数据库存在关联数据，返回关联数据列表
     * 断言列表大小
     */
    @Test
    public void queryLinkedAssetListByAssetIdIf() throws Exception {
        List<AssetLinkRelation> assetResponseList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        assetResponseList.add(assetLinkRelation);
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setPrimaryKey("110");
        Mockito.when(assetLinkRelationDao.queryPortSize(Mockito.any())).thenReturn(233);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetListByAssetId(Mockito.any())).thenReturn(assetResponseList);

        List<AssetLinkRelationResponse> list = service.queryLinkedAssetListByAssetId(query);
        assertThat(list.size(), equalTo(1));
    }

    /**
     * 通过资产id查询资产关联数据列表测试
     * 情景1：传入查询参数，数据库不存在关联数据，返回空列表
     * 断言列表大小为空
     */
    @Test
    public void queryLinkedAssetListByAssetIdEmpty() throws Exception {
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setPrimaryKey("110");
        Mockito.when(assetLinkRelationDao.queryPortSize(Mockito.any())).thenReturn(233);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetListByAssetId(Mockito.any())).thenReturn(null);

        List<AssetLinkRelationResponse> list = service.queryLinkedAssetListByAssetId(query);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 通过资产id分页查询资产关联数据测试
     * 情景1：传入查询参数，数据库存在关联数据，返回分页查询结果
     * 断言返回结果大小
     */
    @Test
    public void queryLinkedAssetPageByAssetId() throws Exception {
        List<AssetLinkRelation> assetResponseList = new ArrayList<>();
        AssetLinkRelation assetLinkRelation = new AssetLinkRelation();
        assetResponseList.add(assetLinkRelation);
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setPrimaryKey("110");
        Mockito.when(assetLinkRelationDao.queryPortSize(Mockito.any())).thenReturn(233);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetListByAssetId(Mockito.any())).thenReturn(assetResponseList);

        PageResult<AssetLinkRelationResponse>result = service.queryLinkedAssetPageByAssetId(query);
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 通过资产id分页查询资产关联数据测试
     * 情景1：传入查询参数，数据库不存在关联数据，返回空结果
     * 断言返回结果大小为空
     */
    @Test
    public void queryLinkedAssetPageByAssetIdEmpty() throws Exception {
        AssetLinkRelationQuery query = new AssetLinkRelationQuery();
        query.setPrimaryKey("110");
        Mockito.when(assetLinkRelationDao.queryPortSize(Mockito.any())).thenReturn(233);
        Mockito.when(assetLinkRelationDao.queryLinkedAssetListByAssetId(Mockito.any())).thenReturn(null);

        PageResult<AssetLinkRelationResponse>result = service.queryLinkedAssetPageByAssetId(query);
        assertThat(result.getTotalRecords(), equalTo(0));
    }

    /**
     * 保存资产关联数据列表测试
     * 情景1：传入查询参数，传入资产列表不为空，储存并返回结果
     * 断言返回成功
     */
    @Test
    public void saveAssetLinkRelationList() {
        List<AssetLinkRelationRequest> relationRequests = new ArrayList<>();
        AssetLinkRelationRequest request = new AssetLinkRelationRequest();
        request.setParentAssetPort("1234");
        request.setParentAssetIp("1332");
        relationRequests.add(request);
        Mockito.when(assetLinkRelationDao.insertBatch(Mockito.any())).thenReturn(123);

        ActionResponse response = service.saveAssetLinkRelationList(relationRequests);
        assertThat(response.getBody(), equalTo(null));
    }

    /**
     * 保存资产关联数据列表测试
     * 情景2：传入查询参数，传入资产列表为空，返回结果
     * 断言返回成功
     */
    @Test
    public void saveAssetLinkRelationListCase1() {
        Mockito.when(assetLinkRelationDao.insertBatch(Mockito.any())).thenReturn(123);

        ActionResponse response = service.saveAssetLinkRelationList(null);
        assertThat(response.getBody(), equalTo(null));
    }

    /**
     * 查询被使用ip测试
     * 情景1：传入查询参数，不是规定设备，返回结果
     * 断言返回结果大小为空
     */
    @Test
    public void queryUseableIpCase1() {
        CategoryType type = new CategoryType("其它设备");
        UseableIpRequest useableIpRequest = new UseableIpRequest();
        useableIpRequest.setCategoryType(type);

        List<String> list = service.queryUseableIp(useableIpRequest);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 查询被使用ip测试
     * 情景2：传入查询参数，数据库存在关联数据，返回结果
     * 断言返回结果大小为空
     */
    @Test
    public void queryUseableIpCase2() {
        CategoryType type = new CategoryType("计算设备");
        UseableIpRequest useableIpRequest = new UseableIpRequest();
        useableIpRequest.setCategoryType(type);
        Mockito.when(assetLinkRelationDao.queryUseableIp(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());
        List<String> list = service.queryUseableIp(useableIpRequest);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 查询被使用ip测试
     * 情景3：传入查询参数，数据库存在关联数据，返回结果
     * 断言返回结果大小为空
     */
    @Test
    public void queryUseableIpCase3() {
        CategoryType type = new CategoryType("网络设备");
        UseableIpRequest useableIpRequest = new UseableIpRequest();
        useableIpRequest.setCategoryType(type);
        Mockito.when(assetLinkRelationDao.queryUseableIp(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());
        List<String> list = service.queryUseableIp(useableIpRequest);
        assertThat(list.size(), equalTo(0));
    }

    /**
     * 创建假用户信息到上下文
     * @param loginUser 登录用户信息
     */
    private void setLoginUser(@NotNull LoginUser loginUser) {
        Map<String, String> map = new HashMap<>();
        map.put("username", loginUser.getUsername());
        map.put("id", String.valueOf(loginUser.getId()));
        map.put("name", loginUser.getName());
        map.put("password", loginUser.getPassword());
        Set<String> set = new HashSet<>(1);
        set.add("none");
        OAuth2Request oAuth2Request = new OAuth2Request(map, "1", null, true, set, null, "", null, null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(map, "2333");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("principal", map);
        authentication.setDetails(map1);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
    }

    /**
     * 返回虚拟用户信息
     * @return LoginUser
     */
    private LoginUser getLoginUser() {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setUsername("name");
        loginUser.setPassword("123456");
        loginUser.setName("Leo");
        return loginUser;
    }
}