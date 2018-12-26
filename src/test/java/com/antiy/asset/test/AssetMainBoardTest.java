package com.antiy.asset.test;

import com.antiy.asset.dao.AssetMainboradDao;
import com.antiy.asset.entity.AssetMainborad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetMainBoardTest {
    @Resource
    AssetMainboradDao assetMainboradDao;

    @Test
    public void testByAssetID() {
        HashMap<String, Object> map = new HashMap();
        map.put("assetId", 1);
        List<AssetMainborad> list = null;
        try {
            list = assetMainboradDao.getByWhere(map);
            System.out.println(list.size());
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        HashMap<String, Object> map = new HashMap();
        try {
            List<AssetMainborad> list = assetMainboradDao.getByWhere(map);
            System.out.println(list.size());
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
