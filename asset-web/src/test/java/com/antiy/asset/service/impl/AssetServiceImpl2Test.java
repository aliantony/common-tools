package com.antiy.asset.service.impl;

/**
 * @author zhangyajun
 * @create 2019-10-10 15:10
 **/

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Collections;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.vo.request.AssetMatchRequest;
import com.antiy.common.base.LoginUser;
import com.antiy.common.utils.LoginUserUtil;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class AssetServiceImpl2Test {
    @Resource
    private AssetServiceImpl assetService;
    @Resource
    private AssetDao         assetdao;
    @Mock
    private LoginUserUtil    loginUserUtil;

    @Before
    public void setUp() {
        // when(loginUserUtil.get).thenReturn();
        // when(loginUser.getBh()).thenReturn("0");
    }

    @Test
    public void matchAssetByIpMac() {
        AssetMatchRequest matchRequest = new AssetMatchRequest();
        // matchRequest.setIp("192.168.1.1");
        // matchRequest.setMac("10-01-6C-06-A6-27");

        try {
            when(assetdao.matchAssetByIpMac(matchRequest)).thenReturn(true);
            assetService.matchAssetByIpMac(matchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
