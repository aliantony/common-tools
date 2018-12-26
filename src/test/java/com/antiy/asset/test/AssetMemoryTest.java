package com.antiy.asset.test;

import com.antiy.asset.dao.AssetMemoryDao;
import com.antiy.asset.entity.AssetMemory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetMemoryTest {
    @Resource
    AssetMemoryDao assetMemoryDao;

    @Test
    public void testByAssetID() {
        HashMap<String, Object> map = new HashMap();
        map.put("assetId", 1);
        List<AssetMemory> list = null;
        try {
            list = assetMemoryDao.getByWhere(map);
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
            List<AssetMemory> list = assetMemoryDao.getByWhere(map);
            System.out.println(list.size());
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
