package com.antiy.asset.test;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetSoftware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetSoftwareTest {
    @Resource
    AssetSoftwareDao assetSoftwareDao;

    @Test
    public void testByAssetID() {
        HashMap<String, Object> map = new HashMap();
        List<AssetSoftware> list = null;
        try {
            // TODO 此处需要新增接口
            //list = assetSoftwareDao.getByAssetId(2);
            System.out.println(list.size());
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
