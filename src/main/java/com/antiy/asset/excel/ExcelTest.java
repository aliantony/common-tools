package com.antiy.asset.excel;


import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetDepartmentDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetDepartment;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.templet.AssetDepartmentEntity;
import com.antiy.asset.vo.templet.AssetEntity;
import com.antiy.asset.vo.templet.AssetSoftwareEntity;
import com.antiy.asset.vo.templet.AssetUserEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelTest {

    @Resource
    IAssetDepartmentService assetDepartmentService;

    @Resource
    IAssetService assetService;

    @Resource
    AssetSoftwareDao assetSoftwareDao;

    @Resource
    AssetUserDao assetUserDao;
    @Resource
    AssetDepartmentDao assetDepartmentDao;
    @Resource
    IAssetSoftwareService assetSoftwareService;

    @Test
    public void test() throws Throwable {
//        exportTemplete();
//        importFileToDataBase3();
//        import2();
//        exportToFile();
//        importFileToDataBase4();
//        exportToFile();
    }

    public static void export() {
        List<User> dataList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            dataList.add(new User("用户" + i, 45, "成都" + i, new Date()));
        }
        ExcelUtils.exportToFile(User.class, "用户信息表", "用户信息", dataList, "d:");
    }

    public void exportTemplete() {
        ExcelUtils.exportTemplet(AssetDepartmentEntity.class, "部门表", "部门信息", "d:");
    }

//    public void exportToFile() {
//        ExcelUtils.exportToFile(AssetUserEntity.class, "用户表", "用户信息", BeanConvert.convert(assetUserDao.getAll(), AssetUserEntity.class), "d:");
//    }


    public void importFileToDataBase1() {
        List dataList = ExcelUtils.importExcelFromFile(AssetUserEntity.class, "d://部门用户表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                AssetUser assetUser = (AssetUser) BeanConvert.convert(AssetUserEntity.class, AssetUser.class, dataList.get(i));
                System.out.println(assetUser);
                assetUserDao.insert(assetUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            assetService.insert((Asset) dataList.get(i));
        }
    }

    public void importFileToDataBase2() {
        List dataList = ExcelUtils.importExcelFromFile(AssetEntity.class, "d://硬件信息表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                Asset asset = (Asset) BeanConvert.convert(AssetEntity.class, Asset.class, dataList.get(i));
                System.out.println(asset);
//                assetSoftwareService.insert(assetSoftware);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            assetService.insert((Asset) dataList.get(i));
        }
    }

    public void importFileToDataBase3() {
        List dataList = ExcelUtils.importExcelFromFile(AssetSoftwareEntity.class, "d://资产软件表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                AssetSoftware assetSoftware = (AssetSoftware) BeanConvert.convert(AssetSoftwareEntity.class, AssetSoftware.class, dataList.get(i));
                System.out.println(assetSoftware);
                assetSoftwareService.insert(assetSoftware);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            assetService.insert((Asset) dataList.get(i));
        }
    }

    public void importFileToDataBase4() {
        List dataList = ExcelUtils.importExcelFromFile(AssetDepartmentEntity.class, "d://部门表.xlsx", 1, 0).getDataList();
        for (int i = 0; i < dataList.size(); i++) {
            try {
                AssetDepartment assetDepartment = (AssetDepartment) BeanConvert.convert(AssetDepartmentEntity.class, AssetDepartment.class, dataList.get(i));
                System.out.println(assetDepartment);
                assetDepartmentService.insert(assetDepartment);
//                assetSoftwareService.insert(assetSoftware);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}