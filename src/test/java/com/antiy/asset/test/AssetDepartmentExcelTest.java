package com.antiy.asset.test;

import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.vo.templet.AssetDepartmentEntity;
import com.antiy.asset.entity.vo.templet.AssetEntity;
import com.antiy.asset.excel.ExcelUtils;
import com.antiy.asset.util.BeanConvert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetDepartmentExcelTest {
    @Resource
    com.antiy.asset.dao.AssetDepartmentDao AssetDepartmentDao;

    @Test
    public void test() throws Throwable {

    }
    @Test
    public void exportTemplete() {
        ExcelUtils.exportTemplet(AssetEntity.class, "部门表", "部门信息", "d:");
    }
    @Test
    public void exportToFile() throws Exception {
        ExcelUtils.exportToFile(AssetEntity.class, "部门表", "部门信息", BeanConvert.convert(AssetDepartmentDao.getAll(), AssetDepartmentEntity.class), "d:");
    }
    @Test
    public void importFileToDataBase() {
        List dataList = ExcelUtils.importExcelFromFile(AssetEntity.class, "d://部门表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                AssetDepartment AssetDepartment = (AssetDepartment) BeanConvert.convert(AssetDepartmentEntity.class, AssetDepartment.class, dataList.get(i));
                System.out.println(AssetDepartment);
                AssetDepartmentDao.insert(AssetDepartment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
