package com.antiy.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.antiy.asset.AssetApplication;
import com.antiy.asset.service.IHomePageService;
import com.antiy.asset.vo.response.AssetCountIncludeResponse;

/**
 * @author zhangyajun
 * @create 2020-02-28 12:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IHomePageServiceImplTest {

    @Resource
    private IHomePageService homePageService;

    @Test
    public void countIncludeManage() throws Exception {
        AssetCountIncludeResponse countIncludeResponse = homePageService.countIncludeManage();

        System.out.println(countIncludeResponse.toString());
    }
}