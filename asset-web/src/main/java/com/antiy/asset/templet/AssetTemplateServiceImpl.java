package com.antiy.asset.templet;

import com.antiy.asset.dao.AssetCpeTreeDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.service.IAssetDepartmentService;
import com.antiy.asset.service.IAssetNettypeManageService;
import com.antiy.asset.service.IAssetTemplateService;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.vo.query.AssetDepartmentQuery;
import com.antiy.asset.vo.response.AssetDepartmentResponse;
import com.antiy.asset.vo.response.AssetNettypeManageResponse;
import com.antiy.common.base.SysArea;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: zhangbing
 * @date: 2019/5/5 15:10
 * @description:
 */
@Service
public class AssetTemplateServiceImpl implements IAssetTemplateService {

    @Resource
    private IAssetUserService          iAssetUserService;
    @Resource
    private IAssetDepartmentService  iAssetDepartmentService;
    @Resource
    private IAssetNettypeManageService iAssetNettypeManageService;
    @Resource
    private AssetCpeTreeDao            treeDao;


    @Override
    public List<String> queryAllCategoryModels() throws Exception {
        return null;
    }

    @Override
    public List<String> queryAllArea() throws Exception {
        List<SysArea> areaList = LoginUserUtil.getLoginUser().getAreas();
        Map<String, String> map = areaList.parallelStream().collect(Collectors.toMap(SysArea::getParentId, e -> e.getId(), (k1, k2)->k1));
        List<String> ret = new ArrayList<>();
        // fix: 安天资产安全运维平台ASMP-811【资产1.1】资产信息管理-导入：导入模板区域项数据应为全部区域
        for (SysArea area : areaList) {
            if (map.get(area.getId()) == null) {
                ret.add(area.getFullName());
            }
        }
        return ret;
    }

    /**
     * 叶子节点
     * @return
     * @throws Exception
     */
    @Override
    public List<String> queryAllAreaWithUser() throws Exception {
        List<SysArea> areaList = LoginUserUtil.getLoginUser().getAreas();
        Map<String, String> map = areaList.parallelStream()
            .collect(Collectors.toMap(SysArea::getParentId, e -> e.getId(), (k1, k2) -> k1));
        List<String> ret = new ArrayList<>();
        for (SysArea area : areaList) {
            if (map.get(area.getId()) == null) {
                ret.add(area.getFullName());
            }
        }
        return ret;
    }

    @Override
    public List<String> getAllUser() throws Exception {
        return iAssetUserService.getAll().stream().map(AssetUser::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getNetType() throws Exception {
        return iAssetNettypeManageService.getAllList().stream().map(AssetNettypeManageResponse::getNetTypeName)
            .collect(Collectors.toList());
    }

    @Override
    public List<String> queryAllDepartment() throws Exception {
        List<AssetDepartmentResponse> listAssetDepartment = iAssetDepartmentService
            .findListAssetDepartment(new AssetDepartmentQuery());
        return listAssetDepartment.stream().map(AssetDepartmentResponse::getName).collect(Collectors.toList());

    }

    @Override
    public List<String> getAllSystemOs() throws Exception {
        return treeDao.getOsNameList();
    }
    @Override
    public List<String> yesNo() throws Exception {
        List<String> objects = Lists.newArrayList();
        objects.add("否");
        objects.add("是");
        return objects;
    }
}
