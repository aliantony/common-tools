package com.antiy.asset.controller;

import com.antiy.asset.service.impl.AssetServiceImpl;
import com.antiy.asset.vo.request.AreaIdRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author zhangyajun
 * @create 2019-10-10 13:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetBusinessControllerTest {

    @Resource
    private AssetServiceImpl assetService;

    @Test
    public void queryUuidByAreaId() {
    }

    @Test
    public void queryIdByAreaId() {

    }
}