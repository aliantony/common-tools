package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetMainboradDao;
import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.vo.query.AssetMainboradQuery;
import com.antiy.asset.vo.request.AssetMainboradRequest;
import com.antiy.asset.vo.response.AssetMainboradResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.IBaseDao;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AssetMainboradServiceImplTest {

    /**
     * 被测试service
     */
    @InjectMocks
    private AssetMainboradServiceImpl mainboradService;

    /**
     * 模拟对象
     */
    @Mock
    private AssetMainboradDao assetMainboradDao;
    @Mock
    private IBaseDao<T> baseDao;
    @Mock
    private BaseConverter<AssetMainboradRequest, AssetMainborad> requestConverter;
    /**
     * 执行具体逻辑对象，不可删除
     */
    @Spy
    private BaseConverter<AssetMainborad, AssetMainboradResponse> responseConverter;

    /**
     * 注入假用户信息到上下文
     */
    @Before
    public void setUp() {
        setLoginUser(getLoginUser());
        MockitoAnnotations.initMocks(this);
        AssetMainborad assetMainborad = new AssetMainborad();
        ReflectionTestUtils.setField(assetMainborad, "id", 10086);
        Mockito.when(requestConverter.convert(Mockito.any(AssetMainboradRequest.class), Mockito.eq(AssetMainborad.class))).thenReturn(assetMainborad);
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
    public void saveAssetMainborad() throws Exception {
        Mockito.when(assetMainboradDao.insert(Mockito.any())).thenReturn(10086);

        int num = mainboradService.saveAssetMainborad(getRequest());
        assertThat(num, equalTo(10086));
    }

    /**
     * 更新测试
     * 传入请求数据，向数据库更新数据，返回更新结果
     * 断言更新结果
     * @throws Exception except
     */
    @Test
    public void updateAssetMainborad() throws Exception {
        Mockito.when(assetMainboradDao.update(Mockito.any())).thenReturn(10010);

        int num = mainboradService.updateAssetMainborad(getRequest());
        assertThat(num, equalTo(10010));
    }

    /**
     * 列表查询测试
     * 传入查询参数，向数据库查询信息，返回信息列表
     * 断言返回信息列表数据
     * @throws Exception except
     */
    @Test
    public void findListAssetMainborad() throws Exception {
        List<AssetMainborad> list = new ArrayList<>();
        AssetMainborad mainborad = new AssetMainborad();
        mainborad.setAssetId("1");
        list.add(mainborad);
        Mockito.when(assetMainboradDao.findListAssetMainborad(Mockito.any())).thenReturn(list);

        List<AssetMainboradResponse> responses = mainboradService.findListAssetMainborad(getQuery());
        assertThat(responses.get(0).getAssetId(), equalTo("1"));
    }

    /**
     * 查询数量测试
     * 传入查询参数，向数据库查询信息，返回查询数量
     * 断言查询数量结果
     * @throws Exception except
     */
    @Test
    public void findCountAssetMainborad() throws Exception {
        Mockito.when(assetMainboradDao.findCount(Mockito.any())).thenReturn(2333);

        int num = mainboradService.findCountAssetMainborad(getQuery());
        assertThat(num, equalTo(2333));
    }

    /**
     * 分页查询测试
     * 传入查询参数，向数据库查询数据，返回分页查询结果
     * 断言分页CurrentPage
     * @throws Exception except
     */
    @Test
    public void findPageAssetMainborad() throws Exception {
        List<AssetMainborad> list = new ArrayList<>();
        AssetMainborad mainborad = new AssetMainborad();
        mainborad.setAssetId("1");
        list.add(mainborad);
        Mockito.when(assetMainboradDao.findListAssetMainborad(Mockito.any())).thenReturn(list);
        Mockito.when(assetMainboradDao.findCount(Mockito.any())).thenReturn(2333);

        PageResult<AssetMainboradResponse> result = mainboradService.findPageAssetMainborad(getQuery());
        assertThat(result.getCurrentPage(), equalTo(111));
    }

    /**
     * 删除测试
     * 传入查询参数，从数据库删除数据，返回数据删除结果
     * 断言删除结果
     * @throws Exception except
     */
    @Test
    public void deleteById() throws Exception {
        Mockito.when(baseDao.deleteById(Mockito.any())).thenReturn(2333);

        int num = mainboradService.deleteById(66666);
        assertThat(num, equalTo(2333));
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

    /**
     * 返回虚拟请求参数
     * @return request
     */
    private AssetMainboradRequest getRequest() {
        AssetMainboradRequest request = new AssetMainboradRequest();
        request.setAssetId("1");
        request.setId("1");
        return request;
    }

    /**
     * 返回虚拟查询参数
     * @return query
     */
    private AssetMainboradQuery getQuery() {
        AssetMainboradQuery query = new AssetMainboradQuery();
        query.setAssetId("1");
        query.setCurrentPage(111);
        return query;
    }
}