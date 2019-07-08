package com.antiy.asset.service.impl;

import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.ActionResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceImplTest {
    @Mock
    private RedisUtil             redisUtil;

    @Mock
    private OperatingSystemClient operatingSystemClient;

    @InjectMocks
    @Spy
    public RedisServiceImpl       redisService;

    @Before
    public void login() {
        MockitoAnnotations.initMocks(this);
        LoginUtil.generateDefaultLoginUser();
    }

    @Test
    public void getAllSystemOsTest() throws Exception {
        // redis 为空的情况
        Mockito.when(redisUtil.getObjectList(Mockito.any(), Mockito.any())).thenReturn(null);
        List<LinkedHashMap> linkedHashMaps = Arrays.asList(new LinkedHashMap(), new LinkedHashMap<>());
        ActionResponse actionResponse = ActionResponse.success(linkedHashMaps);
        Mockito.when(operatingSystemClient.getOperatingSystem()).thenReturn(actionResponse);
        Assert.assertEquals(2, redisService.getAllSystemOs().size());
    }

    @Test
    public void getAllSystemOsTest2() throws Exception {
        // redis 不为空的情况
        List<LinkedHashMap> linkedHashMaps = Arrays.asList(new LinkedHashMap(), new LinkedHashMap<>());
        Mockito.when(redisUtil.getObjectList(Mockito.any(), Mockito.any()))
            .thenReturn(Arrays.asList(new LinkedHashMap<>()));

        ActionResponse actionResponse = ActionResponse.success(linkedHashMaps);
        Mockito.when(operatingSystemClient.getOperatingSystem()).thenReturn(actionResponse);
        Assert.assertEquals(1, redisService.getAllSystemOs().size());
    }

}
