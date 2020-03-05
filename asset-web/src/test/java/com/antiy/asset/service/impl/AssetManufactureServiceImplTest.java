package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.AssetApplication;
import com.antiy.asset.dao.AssetManufactureDao;
import com.antiy.asset.entity.AssetManufacture;
import com.antiy.asset.util.SnowFlakeUtil;

/**
 * @author zhangyajun
 * @create 2020-03-05 10:27
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssetManufactureServiceImplTest {

    @Resource
    private AssetManufactureDao manufactureDao;

    @Resource
    private AssetManufactureServiceImpl manufactureService;

    @Test
    public void initManufacture() throws Exception {

        List<AssetManufacture> manufactureList = manufactureDao.initManufacture();
        System.out.println(String.valueOf(System.currentTimeMillis()));
        for (AssetManufacture assetManufacture : manufactureList) {
            assetManufacture.setPid("1");
            assetManufacture.setProductName(assetManufacture.getProductName());
            assetManufacture.setVersion(assetManufacture.getVersion());
            assetManufacture.setBusinessId(assetManufacture.getBusinessId());
            assetManufacture.setUniqueId(SnowFlakeUtil.getSnowId());
            manufactureDao.insert(assetManufacture);
        }
    }

    @Test
    public void getManufacture() throws Exception {

        System.out.println(JSON.toJSONString(manufactureService.safetyManufacture()));
    }
}