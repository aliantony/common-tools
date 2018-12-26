package com.antiy.asset.test;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.excel.ExcelUtils;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.templet.AssetSoftwareEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SoftwareExcelTest {
    @Resource
    AssetSoftwareDao assetSoftwareDao;
    @Test
    public void test() throws Throwable {
        exportTemplete();

    }
    @Test
    public void exportTemplete() {
        ExcelUtils.exportTemplet(AssetSoftwareEntity.class, "资产软件表", "资产软件信息", "d:");
    }

    @Test
    public void exportToFile() throws Exception {
        ExcelUtils.exportToFile(AssetSoftwareEntity.class, "资产软件表", "资产软件信息", BeanConvert.convert(assetSoftwareDao.getAll(), AssetSoftwareEntity.class), "d:");
    }

    @Test
    public void importFileToDataBase() {
        List dataList = ExcelUtils.importExcelFromFile(AssetSoftwareEntity.class, "d://资产软件表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                AssetSoftware assetSoftware = (AssetSoftware) BeanConvert.convert(AssetSoftwareEntity.class, AssetSoftware.class, dataList.get(i));
                System.out.println(assetSoftware);
                assetSoftwareDao.insert(assetSoftware);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
