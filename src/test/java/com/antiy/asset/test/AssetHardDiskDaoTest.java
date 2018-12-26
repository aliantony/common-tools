package com.antiy.asset.test;


import com.antiy.asset.dao.AssetHardDiskDao;
import com.antiy.asset.entity.AssetHardDisk;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetHardDiskDaoTest {
    @Resource
    AssetHardDiskDao assetHardDiskDao;

    @Test
    public void testByAssetID() {
        HashMap<String, Object> map = new HashMap();
        map.put("assetId", 1);
        List<AssetHardDisk> list = null;
        try {
            list = assetHardDiskDao.getByWhere(map);
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
            List<AssetHardDisk> list = assetHardDiskDao.getByWhere(map);
            System.out.println(list.size());
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
