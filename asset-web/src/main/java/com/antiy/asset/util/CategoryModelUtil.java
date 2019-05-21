package com.antiy.asset.util;

import java.util.ArrayList;
import java.util.List;

import com.antiy.asset.entity.AssetCategoryModel;

/**
 * @author zhangyajun
 * @create 2019-05-21 15:41
 **/
public class CategoryModelUtil {
    // 子节点
    private static List<AssetCategoryModel> categoryModelList = new ArrayList<AssetCategoryModel>();

    /**
     * 获取某个父节点下面的所有子节点
     * @param modelListList
     * @param pid
     * @return
     */
    public static String categoryModelList(List<AssetCategoryModel> modelListList, int pid) {
        for (AssetCategoryModel categoryModel : modelListList) {
            // 遍历出父id等于参数的id，add进子节点集合
            if (Integer.valueOf(categoryModel.getParentId()) == pid) {
                // 递归遍历下一级
                categoryModelList(modelListList, Integer.valueOf(categoryModel.getId()));
                categoryModelList.add(categoryModel);
            }
        }
        return categoryModelList.toString();
    }

    public static String getIdString(List<AssetCategoryModel> modelListList, int pid) {
        return StringUtils.trim(CategoryModelUtil.categoryModelList(modelListList, pid), "[", "]");
    }
}
