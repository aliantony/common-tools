package com.antiy.asset.test;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.excel.ExcelUtils;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.templet.AssetEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetExcelTest {

    @Resource
    AssetDao assetDao;

    @Test
    public void test() throws Throwable {

    }
    @Test
    public void exportTemplete() {
        ExcelUtils.exportTemplet(AssetEntity.class, "资产硬件表", "资产硬件信息", "d:");
    }
    @Test
    public void exportToFile() throws Exception {
        ExcelUtils.exportToFile(AssetEntity.class, "资产硬件表", "资产硬件信息", BeanConvert.convert(assetDao.getAll(), AssetEntity.class), "d:");
    }
    @Test
    public void importFileToDataBase() {
        List dataList = ExcelUtils.importExcelFromFile(AssetEntity.class, "d://资产硬件表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                Asset asset = (Asset) BeanConvert.convert(AssetEntity.class, Asset.class, dataList.get(i));
                System.out.println(asset);
                assetDao.insert(asset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
