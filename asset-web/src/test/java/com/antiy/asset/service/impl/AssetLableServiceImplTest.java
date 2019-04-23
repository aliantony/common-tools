package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLableDao;
import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.vo.query.AssetLableQuery;
import com.antiy.asset.vo.request.AssetLableRequest;
import com.antiy.asset.vo.response.AssetLableResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
import com.sun.istack.NotNull;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class AssetLableServiceImplTest {

    /**
     * 被测试service
     */
    @InjectMocks
    private AssetLableServiceImpl lableService;

    /**
     * 模拟dao层
     */
    @Mock
    private AssetLableDao assetLableDao;

    /**
     * 执行具体逻辑，不可删除
     */
    @Spy
    private BaseConverter<AssetLableRequest, AssetLable> requestConverter;
    @Spy
    private BaseConverter<AssetLable, AssetLableResponse> responseConverter;

    /**
     * 注入假用户信息到上下文
     */
    @Before
    public void setUp() {
        LoginUser loginUser = getLoginUser();
        setLoginUser(loginUser);
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
    public void saveAssetLable() throws Exception {
        AssetLableRequest request = getRequest();
        Mockito.when(assetLableDao.insert(Mockito.any())).thenReturn(10086);

        int num = lableService.saveAssetLable(request);
        assertThat(num, Matchers.equalTo(10086));
    }

    /**
     * 更新测试
     * 传入请求数据，向数据库更新数据，返回更新结果
     * 断言更新结果
     * @throws Exception except
     */
    @Test
    public void updateAssetLable() throws Exception {
        AssetLableRequest request = getRequest();
        Mockito.when(assetLableDao.update(Mockito.any())).thenReturn(10010);

        int num = lableService.updateAssetLable(request);
        assertThat(num, Matchers.equalTo(10010));
    }

    /**
     * 列表查询测试
     * 传入查询参数，向数据库查询信息，返回信息列表
     * 断言返回信息列表数据
     * @throws Exception except
     */
    @Test
    public void findListAssetLable() throws Exception {
        AssetLableQuery query = getQuery();
        List<AssetLable> list = new ArrayList<>();
        AssetLable response = new AssetLable();
        response.setName("Leo");
        list.add(response);
        Mockito.when(assetLableDao.findListAssetLable(Mockito.any())).thenReturn(list);

        List<AssetLableResponse> lableResponses = lableService.findListAssetLable(query);
        assertThat(lableResponses.get(0).getName(), Matchers.equalTo("Leo"));
    }

    /**
     * 查询数量测试
     * 传入查询参数，向数据库查询信息，返回查询数量
     * 断言查询数量结果
     * @throws Exception except
     */
    @Test
    public void findCountAssetLable() throws Exception {
        Mockito.when(assetLableDao.findCount(Mockito.any())).thenReturn(9527);

        int num = lableService.findCountAssetLable(getQuery());
        assertThat(num, Matchers.equalTo(9527));
    }

    /**
     * 分页查询测试
     * 传入查询参数，向数据库查询数据，返回分页查询结果
     * 断言分页CurrentPage
     * @throws Exception except
     */
    @Test
    public void findPageAssetLable() throws Exception {
        List<AssetLable> list = new ArrayList<>();
        AssetLable response = new AssetLable();
        response.setName("Leo");
        list.add(response);
        Mockito.when(assetLableDao.findListAssetLable(Mockito.any())).thenReturn(list);
        Mockito.when(assetLableDao.findCount(Mockito.any())).thenReturn(9527);

        PageResult<AssetLableResponse> result = lableService.findPageAssetLable(getQuery());
        assertThat(result.getCurrentPage(), Matchers.equalTo(2));
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
     * @return AssetLableRequest
     */
    private AssetLableRequest getRequest() {
        AssetLableRequest request = new AssetLableRequest();
        request.setId("1");
        request.setName("Leo");
        request.setLabelType(1);
        request.setDescription("123456789");

        return request;
    }

    /**
     * 返回虚拟查询参数
     * @return AssetLableQuery
     */
    private AssetLableQuery getQuery() {
        AssetLableQuery query = new AssetLableQuery();
        query.setName("Leo");
        query.setCurrentPage(2);

        return query;
    }
}