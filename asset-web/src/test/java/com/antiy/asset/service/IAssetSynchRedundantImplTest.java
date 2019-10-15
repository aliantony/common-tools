package com.antiy.asset.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.antiy.asset.vo.query.AssetSynchCpeQuery;

/**
 * @author zhangyajun
 * @create 2019-10-15 9:01
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class IAssetSynchRedundantImplTest {

    @Resource
    private IAssetSynchRedundant assetSynchRedundant;

    @Test
    public void synchRedundantAsset() {
        AssetSynchCpeQuery query = new AssetSynchCpeQuery();
        query.setStart(1566459394659L);
        query.setEnd(1566459454000L);
        try {
            assetSynchRedundant.synchRedundantAsset(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}