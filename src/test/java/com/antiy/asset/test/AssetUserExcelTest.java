package com.antiy.asset.test;

import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.excel.ExcelUtils;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.templet.AssetUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetUserExcelTest {
    @Resource
    com.antiy.asset.dao.AssetUserDao AssetUserDao;

    @Test
    public void test() throws Throwable {

    }
    @Test
    public void exportTemplete() {
        ExcelUtils.exportTemplet(AssetUserEntity.class, "用户表", "用户信息", "d:");
    }
    @Test
    public void exportToFile() throws Exception {
        ExcelUtils.exportToFile(AssetUserEntity.class, "用户表", "用户信息", BeanConvert.convert(AssetUserDao.getAll(), AssetUserEntity.class), "d:");
    }
    @Test
    public void importFileToDataBase() {
        List dataList = ExcelUtils.importExcelFromFile(AssetUserEntity.class, "d://用户表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                AssetUser AssetUser = (AssetUser) BeanConvert.convert(AssetUserEntity.class, AssetUser.class, dataList.get(i));
                System.out.println(AssetUser);
                AssetUserDao.insert(AssetUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
