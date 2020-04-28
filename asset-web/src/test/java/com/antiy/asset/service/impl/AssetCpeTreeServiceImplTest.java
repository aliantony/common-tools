package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.service.IAssetCpeTreeService;
import com.antiy.asset.vo.query.AssetCpeTreeCondition;
import com.antiy.asset.vo.response.AssetCpeTreeResponse;

/**
 * @author zhangyajun
 * @create 2020-03-16 12:18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetCpeTreeServiceImplTest {

    @Resource
    private IAssetCpeTreeService cpeTreeService;

    @Test
    public void queryNextNodeById() throws Exception {
        AssetCpeTreeCondition condition = new AssetCpeTreeCondition();
        condition.setPid("688792976662462464");
        List<AssetCpeTreeResponse> list = cpeTreeService.queryNextNodeById(condition);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void queryTree() throws Exception {
//        List<AssetCpeTreeResponse> list = cpeTreeService.queryTree();

        System.out.println("ok");
    }
}