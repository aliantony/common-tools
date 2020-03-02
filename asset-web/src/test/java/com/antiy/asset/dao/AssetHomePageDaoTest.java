package com.antiy.asset.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.antiy.asset.AssetApplication;
import com.antiy.asset.entity.AssetImportanceDegreeCondition;
import com.antiy.asset.factory.ConditionFactory;
import com.antiy.asset.util.StatusEnumUtil;
import com.google.common.collect.Lists;

/**
 * @author zhangyajun
 * @create 2020-03-02 12:56
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssetHomePageDaoTest {

    @Resource
    private AssetHomePageDao homePageDao;

    @Test
    public void assetImportanceDegreePie() throws Exception {
        List<String> areaList = Lists.newArrayList("001001001001");
        List<Integer> statusList = StatusEnumUtil.getAssetTypeStatus();
        AssetImportanceDegreeCondition condition = ConditionFactory
            .createCondition(AssetImportanceDegreeCondition.class);
        condition.setAreaList(areaList);
        condition.setStatusList(statusList);

        try {
            homePageDao.assetImportanceDegreePie(condition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}