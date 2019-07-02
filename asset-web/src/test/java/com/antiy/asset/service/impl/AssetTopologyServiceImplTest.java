package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLink;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.LoginUser;
import com.antiy.common.encoder.AesEncoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetTopologyServiceImplTest {
    @Mock
    private AssetLinkRelationDao                assetLinkRelationDao;
    @Mock
    private AssetDao                            assetDao;
    @Mock
    private IAssetCategoryModelService          iAssetCategoryModelService;
    @Mock
    private AssetTopologyDao                    assetTopologyDao;
    @Mock
    private EmergencyClient                     emergencyClient;
    @Mock
    private BaseConverter<Asset, AssetResponse> converter;
    @Mock
    private RedisUtil                           redisUtil;
    @Mock
    private AssetCategoryModelDao               assetCategoryModelDao;
    @Mock
    private AesEncoder                          aesEncoder;
    @Mock
    private OperatingSystemClient               operatingSystemClient;
    @Mock
    private IAssetService                       iAssetService;
    @InjectMocks
    private AssetTopologyServiceImpl            assetTopologyService;

    private List<Double>                        middlePoint        = Arrays.asList(0d, 0d, 0d);
    private List<Double>                        cameraPos          = Arrays.asList(-3000d, 1200d, 4800d);
    private List<Double>                        targetPos          = Arrays.asList(-1000d, 800d, 600d);
    private Double                              firstLevelSpacing  = 200d;
    private Double                              firstLevelHeight   = 1800d;
    private Double                              secondLevelSpacing = 1000d;
    private Double                              secondLevelHeight  = 1300d;
    private Double                              thirdLevelSpacing  = 100d;
    private Double                              thirdLevelHeight   = 675d;

    @Test
    public void queryCategoryModelsTest() {
        when(assetLinkRelationDao.queryCategoryModes()).thenReturn(new ArrayList());
        Assert.assertEquals(1, assetTopologyService.queryCategoryModels().size());
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

    }

    @Test
    public void queryAssetNodeInfoTest() throws Exception {
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        AssetResponse assetResponse = new AssetResponse();
        assetResponse.setName("abc");
        assetOuterResponse.setAsset(assetResponse);
        when(iAssetService.getByAssetId(any())).thenReturn(assetOuterResponse);
        Assert.assertEquals("abc", assetTopologyService.queryAssetNodeInfo("1").getData().get(0).getAsset_name());
    }

    public AssetLink generateAssetLink(String parentId, String assetId) {
        AssetLink assetLink = new AssetLink();
        assetLink.setParentAssetId(parentId);
        assetLink.setAssetId(assetId);
        return assetLink;

    }

    @Test
    public void countAssetTopology() throws Exception {
        when(assetDao.findCountByCategoryModel(any())).thenReturn(100);
        List<AssetLink> assetLinks = new ArrayList<>();
        assetLinks.add(generateAssetLink("1", "2"));
        assetLinks.add(generateAssetLink("2", "3"));
        when(assetLinkRelationDao.findLinkRelation(any())).thenReturn(assetLinks);
        List<IdCount> idCounts = new ArrayList<>();
        idCounts.add(new IdCount("1", "2"));
        idCounts.add(new IdCount("2", "2"));
        idCounts.add(new IdCount("3", "2"));
        when(assetDao.queryAlarmCountByAssetIds(any())).thenReturn(idCounts);
        Assert.assertEquals(2, assetTopologyService.countAssetTopology().getList().get(0).get("warning"));
    }

}
