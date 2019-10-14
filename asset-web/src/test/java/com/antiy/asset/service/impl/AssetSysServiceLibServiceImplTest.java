package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetServiceDependDao;
import com.antiy.asset.dao.AssetSysServiceLibDao;
import com.antiy.asset.entity.AssetSysServiceLib;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.asset.vo.query.AssetSysServiceLibQuery;
import com.antiy.asset.vo.request.AssetSysServiceLibRequest;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LoginUserUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
// @SpringBootTest
@PowerMockIgnore({ "javax.*.*", "com.sun.*", "org.xml.*", "org.apache.*" })
@PrepareForTest(LoginUserUtil.class)
public class AssetSysServiceLibServiceImplTest {
    @Mock
    private IBaseDao<AssetSysServiceLib>                                  iBaseDao;
    @Mock
    private AssetSysServiceLibDao                                         sysServiceLibDao;

    @Spy
    private BaseConverter<AssetSysServiceLibRequest, AssetSysServiceLib>  requestConverter;
    @Spy
    private BaseConverter<AssetSysServiceLib, AssetSysServiceLibResponse> responseConverter;
    @Mock
    private AssetServiceDependDao                                         serviceDependDao;
    @Mock
    private LoginUserUtil                                                 loginUserUtil;
    @InjectMocks
    private AssetSysServiceLibServiceImpl                                 sysServiceLibService;

    @Before
    public void init() {
        // 模拟用户登录
        LoginUser loginUser = JSONObject.parseObject(
            "{ \"id\":8, \"username\":\"zhangbing\", \"password\":\"$2a$10$hokzLPdz15q9XFuNB8HA0ObV9j301oxkFBlsJUCe/8iWBvql5gBdO\", \"name\":\"张冰\", \"duty\":\"部门经历\", \"department\":\"A是不\", \"phone\":\"123\", \"email\":\"string123@email\", \"status\":1, \"errorCount\":4, \"lastLoginTime\":1553737022175, \"lastModifiedPassword\":1550657104216, \"sysRoles\":[ { \"id\":9, \"code\":\"config_admin\", \"name\":\"配置管理员\", \"description\":\"\" } ], \"areas\":[ { \"id\":10, \"parentId\":2, \"levelType\":2, \"fullName\":\"金牛区\", \"shortName\":\"1\", \"fullSpell\":\"1\", \"shortSpell\":\"1\", \"status\":1, \"demo\":\"\" }, { \"id\":112, \"parentId\":0, \"levelType\":1, \"fullName\":\"四川省成都市\", \"status\":1, \"demo\":\"\" } ], \"enabled\":true, \"accountNonExpired\":true, \"accountNonLocked\":true, \"credentialsNonExpired\":true } ",
            LoginUser.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, "123");
        Map<String, Object> map = new HashMap<>();
        map.put("principal", loginUser);
        token.setDetails(map);

        OAuth2Authentication authentication = Mockito.mock(OAuth2Authentication.class);
        org.springframework.security.core.context.SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getUserAuthentication()).thenReturn(token);
        SecurityContextHolder.setContext(securityContext);
        PowerMockito.mockStatic(LoginUserUtil.class);
        loginUser = new LoginUser();
        loginUser.setUsername("a");
        loginUser.setAreas(Arrays.asList(new SysArea()));
        PowerMockito.when(LoginUserUtil.getLoginUser()).thenReturn(loginUser);
    }

    @Test
    public void queryListAssetSysServiceLib() throws Exception {
        AssetSysServiceLib assetSysServiceLib = new AssetSysServiceLib();
        assetSysServiceLib.setService("1");
        Mockito.when(sysServiceLibDao.findQuery(Mockito.any())).thenReturn(Arrays.asList(assetSysServiceLib));
        Assert.assertEquals("1",
            sysServiceLibService.queryListAssetSysServiceLib(new AssetSysServiceLibQuery()).get(0).getService());
    }

    @Test
    public void queryPageAssetSysServiceLib() throws Exception {
        AssetSysServiceLib assetSysServiceLib = new AssetSysServiceLib();
        assetSysServiceLib.setService("1");
        Mockito.when(sysServiceLibDao.findQuery(Mockito.any())).thenReturn(Arrays.asList(assetSysServiceLib));
        Mockito.when(sysServiceLibDao.findCount(Mockito.any())).thenReturn(1);
        Assert.assertEquals("1", sysServiceLibService.queryPageAssetSysServiceLib(new AssetSysServiceLibQuery())
            .getItems().get(0).getService());
    }

    @Test
    public void queryPageAssetSysServiceLib1() throws Exception {
        AssetSysServiceLib assetSysServiceLib = new AssetSysServiceLib();
        assetSysServiceLib.setService("1");
        Mockito.when(sysServiceLibDao.findCount(Mockito.any())).thenReturn(0);
        Assert.assertEquals(0,
            sysServiceLibService.queryPageAssetSysServiceLib(new AssetSysServiceLibQuery()).getItems().size());
    }

    @Test
    public void queryAssetSysServiceLibById() throws Exception {
        AssetSysServiceLib assetSysServiceLib = new AssetSysServiceLib();
        assetSysServiceLib.setService("1");
        Mockito.when(sysServiceLibDao.getById(Mockito.any())).thenReturn(assetSysServiceLib);
        QueryCondition condition = new QueryCondition();
        condition.setPrimaryKey("1");
        Assert.assertEquals("1", sysServiceLibService.queryAssetSysServiceLibById(condition).getService());
    }
}
