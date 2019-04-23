package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetPortProtocolDao;
import com.antiy.asset.entity.AssetPortProtocol;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.vo.response.AssetPortProtocolResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.base.PageResult;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetPortProtocolServiceImplTest {

    /**
     * 模拟Service，会发生真实调用
     */
    @SpyBean
    private IAssetPortProtocolService iAssetPortProtocolService;

    /**
     * 模拟Dao，将其注入上下文
     */
    @MockBean
    private AssetPortProtocolDao assetPortProtocolDao;
    private LoginUser loginUser;

    /**
     * 模拟登陆用户信息
     */
    @Before
    public void initMock(){
        loginUser = new LoginUser();
        loginUser.setId(11);
        loginUser.setName("日常安全管理员");
        loginUser.setUsername("routine_admin");
        loginUser.setPassword("123456");
        //mock 当前登陆用户信息
    }

    /**
     * 新增测试
     * @throws Exception
     */
    @Test
    public void saveAssetPortProtocol() throws Exception{
        AssetPortProtocolRequest assetPortProtocolRequest = new AssetPortProtocolRequest();
        Integer[] port = {3306};
        assetPortProtocolRequest.setAssetSoftId("1");
        assetPortProtocolRequest.setId("1");
        assetPortProtocolRequest.setPort(port);
        assetPortProtocolRequest.setProtocol("http");
        assetPortProtocolRequest.setDescription("ss");
        mockLoginUser(loginUser);
        AssetPortProtocol assetPortProtocol = new AssetPortProtocol();
        assetPortProtocol.setId(1);
        Mockito.when(assetPortProtocolDao.insert(Mockito.any())).thenReturn(1);
        Integer result = iAssetPortProtocolService.saveAssetPortProtocol(assetPortProtocolRequest);
        Assert.assertThat(result, Matchers.greaterThan(0));
    }

    /**
     * 修改测试
     * @throws Exception
     */
    @Test
    public void updateAssetPortProtocol() throws Exception{
        AssetPortProtocolRequest assetPortProtocolRequest = new AssetPortProtocolRequest();
        Integer[] port = {3306};
        assetPortProtocolRequest.setAssetSoftId("1");
        assetPortProtocolRequest.setId("1");
        assetPortProtocolRequest.setPort(port);
        assetPortProtocolRequest.setProtocol("http");
        assetPortProtocolRequest.setDescription("ss");
        mockLoginUser(loginUser);
        Mockito.when(assetPortProtocolDao.update(Mockito.any())).thenReturn(1);
        Integer result = iAssetPortProtocolService.updateAssetPortProtocol(assetPortProtocolRequest);
        Assert.assertThat(result, Matchers.greaterThan(0));
    }

    /**
     * 分页查询测试
     * @throws Exception
     */
    @Test
    public void findPageAssetPortProtocol() throws Exception{
        AssetPortProtocolQuery assetPortProtocolQuery = new AssetPortProtocolQuery();
        assetPortProtocolQuery.setAssetSoftId("1");
        assetPortProtocolQuery.setPort(3306);
        assetPortProtocolQuery.setProtocol("http");
        assetPortProtocolQuery.setGmtCreate(1551422114000L);
        assetPortProtocolQuery.setGmtModified(1551422114000L);
        AssetPortProtocol assetPortProtocol = new AssetPortProtocol();
        assetPortProtocol.setAssetSoftId("1");
        assetPortProtocol.setPort(3306);
        assetPortProtocol.setProtocol("http");
        assetPortProtocol.setDescription("ss");
        assetPortProtocol.setGmtCreate(1551422114000L);
        assetPortProtocol.setGmtModified(1551422114000L);
        assetPortProtocol.setMemo("ss");
        assetPortProtocol.setCreateUser(1);
        assetPortProtocol.setModifyUser(1);
        assetPortProtocol.setStatus(1);
        List<AssetPortProtocol> assetPortProtocolList = new ArrayList<>();
        assetPortProtocolList.add(assetPortProtocol);
        Mockito.when(assetPortProtocolDao.findCount(assetPortProtocolQuery)).thenReturn(1);
        Mockito.when(assetPortProtocolDao.findListAssetPortProtocol(Mockito.any())).thenReturn(assetPortProtocolList);
        PageResult<AssetPortProtocolResponse> result = iAssetPortProtocolService
                .findPageAssetPortProtocol(assetPortProtocolQuery);
        List<AssetPortProtocolResponse> items = result.getItems();
        Assert.assertThat(items, Matchers.notNullValue());
    }

    /**
     * 提供的当前用户信息mock
     *
     * @param loginUser 假用户信息
     */
    protected void mockLoginUser(LoginUser loginUser) {
        Map<String, String> map = new HashMap<>();
        map.put("username", loginUser.getUsername());
        map.put("password", loginUser.getPassword());
        map.put("id", String.valueOf(loginUser.getId()));
        map.put("name", loginUser.getName());
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
}