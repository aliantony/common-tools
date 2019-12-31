package com.antiy.asset.templet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetTemplateService;
import com.antiy.asset.service.IAssetUserService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.common.base.SysArea;
import com.antiy.common.utils.LoginUserUtil;

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
    private IAssetHardSoftLibService iAssetHardSoftLibService;
    @Resource
    private IRedisService              redisService;


    private static List<String>        REMOVE_SYSTEM_OS = new LinkedList<>();
    static {
        REMOVE_SYSTEM_OS.add("unix");
        REMOVE_SYSTEM_OS.add("windows");
        REMOVE_SYSTEM_OS.add("linux");
    }

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
            // if (map.get(area.getId()) == null) {
                ret.add(area.getFullName());
            // }
        }
        return ret;
    }

    @Override
    public List<String> getAllUser() throws Exception {
        return iAssetUserService.getAll().stream().map(allUser -> allUser.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllSystemOs() throws Exception {
        OsQuery osQuery = new OsQuery();
        List<OsSelectResponse> osList = iAssetHardSoftLibService.pullDownOs(osQuery);
        return osList.stream().map(OsSelectResponse::getValue).collect(Collectors.toList());
    }
}
