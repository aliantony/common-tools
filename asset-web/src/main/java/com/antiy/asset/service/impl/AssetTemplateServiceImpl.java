package com.antiy.asset.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetTemplateService;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.service.IRedisService;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @author: zhangbing
 * @date: 2019/5/5 15:10
 * @description:
 */
@Service
public class AssetTemplateServiceImpl implements IAssetTemplateService {
    @Resource
    private IAssetCategoryModelService iAssetCategoryModelService;

    @Resource
    private IAssetUserService          iAssetUserService;

    @Resource
    private IRedisService              redisService;

    @Override
    public List<String> queryAllCategoryModels() throws Exception {
        return iAssetCategoryModelService.getAll().stream().map(assetCategoryModel -> assetCategoryModel.getName())
            .collect(Collectors.toList());
    }

    @Override
    public List<String> queryAllArea() throws Exception {
        return LoginUserUtil.getLoginUser().getAreas().stream().filter(sysArea -> !"行政区划".equals(sysArea.getFullName()))
            .map(sysArea -> sysArea.getFullName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllUser() throws Exception {
        return iAssetUserService.getAll().stream().map(allUser -> allUser.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllSystemOs() throws Exception {
        return redisService.getAllSystemOs().stream()
            .map(linkedHashMap -> linkedHashMap.get("name") != null ? linkedHashMap.get("name").toString() : null)
            .collect(Collectors.toList());
    }
}
