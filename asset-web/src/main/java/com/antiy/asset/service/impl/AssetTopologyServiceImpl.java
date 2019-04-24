package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.response.AssetNodeInfoResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * 资产拓扑管理
 *
 * @author zhangxin
 * @date 2019/4/23 11:21
 */
@Service
public class AssetTopologyServiceImpl implements IAssetTopologyService {

    @Resource
    private AssetLinkRelationDao       assetLinkRelationDao;
    @Resource
    private AssetDao                   assetDao;
    @Resource
    private IAssetCategoryModelService iAssetCategoryModelService;

    @Override
    public List<String> queryCategoryModels() {
        return assetLinkRelationDao.queryCategoryModes();
    }

    @Override
    public AssetNodeInfoResponse queryAssetNodeInfo(String assetId) {
        AssetNodeInfoResponse assetNodeInfoResponse = assetLinkRelationDao.queryAssetNodeInfo(assetId);
        assetNodeInfoResponse
            .setInstallTypeName(InstallType.getInstallTypeByCode(assetNodeInfoResponse.getInstallType()).getStatus());
        return assetNodeInfoResponse;
    }

    @Override
    public Map<String, Integer> countAssetTopology() throws Exception {
        Map<String, Integer> resultMap = new HashMap<>(2);
        // 1.查询资产总数
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        // 1.1资产状态为已入网和待退役
        List<Integer> assetStatusList = new ArrayList<>(2);
        assetStatusList.add(AssetStatusEnum.NET_IN.getCode());
        assetStatusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        assetQuery.setAssetStatusList(assetStatusList);
        // 1.2资产品类型号为计算设备和网络设备
        List<AssetCategoryModel> categoryModelList = iAssetCategoryModelService.getAll();
        List<Integer> categoryIdList = iAssetCategoryModelService.findAssetCategoryModelIdsById(4, categoryModelList);
        categoryIdList.addAll(iAssetCategoryModelService.findAssetCategoryModelIdsById(5, categoryModelList));
        assetQuery.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryIdList));
        resultMap.put("totalNum", assetDao.findCountByCategoryModel(assetQuery));
        // 2.查询已管控的数量(已建立关联关系的资产数量)
        AssetTopologyQuery assetTopologyQuery = new AssetTopologyQuery();
        assetTopologyQuery.setUserAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        resultMap.put("inControlNum", assetLinkRelationDao.countAssetTopology(assetTopologyQuery));
        return resultMap;
    }

    @Override
    public List<SelectResponse> queryGroupList() {
        AssetTopologyQuery assetTopologyQuery = new AssetTopologyQuery();
        assetTopologyQuery.setUserAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        List<AssetGroup> assetGroupList = assetLinkRelationDao.queryGroupList(assetTopologyQuery);
        List<SelectResponse> selectResponseList = new ArrayList<>(assetGroupList.size());
        assetGroupList.stream().forEach(e -> {
            SelectResponse selectResponse = new SelectResponse();
            selectResponse.setValue(e.getName());
            selectResponse.setId(e.getStringId());
            selectResponseList.add(selectResponse);
        });
        return selectResponseList;
    }
}
